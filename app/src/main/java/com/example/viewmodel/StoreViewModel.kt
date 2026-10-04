package com.example.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppStoreRepository
import com.example.model.AppCategory
import com.example.model.AppItem
import com.example.model.DownloadState
import com.example.model.DownloadStatus
import com.example.model.Review
import com.example.util.ApkDownloadManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

enum class StoreTab(val titleRu: String) {
    FEATURED("ГЛАВНАЯ"),
    GAMES("ИГРЫ"),
    APPS("ПРИЛОЖЕНИЯ"),
    TOP_CHARTS("ТОП БЕСПЛАТНЫХ"),
    CATEGORIES("КАТЕГОРИИ"),
    MY_APPS("МОИ ПРИЛОЖЕНИЯ")
}

data class AdMobRetroAd(
    val title: String,
    val subtitle: String,
    val cta: String,
    val iconEmoji: String
)

data class StoreUiState(
    val currentTab: StoreTab = StoreTab.FEATURED,
    val selectedAppId: String? = null,
    val selectedCategory: AppCategory = AppCategory.ALL,
    val searchQuery: String = "",
    val isSearchOpen: Boolean = false,
    val launchedApp: AppItem? = null,
    val isWritingReviewForApp: AppItem? = null,
    val adVisible: Boolean = true,
    val currentAdIndex: Int = 0,
    val statusNotification: String? = null,
    val sdCardAvailableMb: Int = 1840
)

class StoreViewModel(
    private val repository: AppStoreRepository = AppStoreRepository()
) : ViewModel() {

    val apps: StateFlow<List<AppItem>> = repository.apps
    val searchHistory: StateFlow<List<String>> = repository.searchHistory

    private val _uiState = MutableStateFlow(StoreUiState())
    val uiState: StateFlow<StoreUiState> = _uiState.asStateFlow()

    private val downloadJobs = mutableMapOf<String, Job>()

    val retroAds = listOf(
        AdMobRetroAd(
            title = "🔥 Скачай хитовые мелодии на звонок 2013!",
            subtitle = "Бумер, Crazy Frog, Макс Корж, Бригада в MP3!",
            cta = "СКАЧАТЬ (WAP/GPRS)",
            iconEmoji = "🎵"
        ),
        AdMobRetroAd(
            title = "⚡ Батарея садится за 2 часа?",
            subtitle = "Ускоритель аккумулятора Du Battery Saver удвоит заряд!",
            cta = "УСТАНОВИТЬ БЕСПЛАТНО",
            iconEmoji = "🔋"
        ),
        AdMobRetroAd(
            title = "★ Поздравляем! Вы стали 1 000 000-м посетителем!",
            subtitle = "Заберите новенький смартфон Samsung Galaxy S4 прямо сейчас!",
            cta = "ЗАБРАТЬ ПРИЗ",
            iconEmoji = "🎁"
        ),
        AdMobRetroAd(
            title = "🎮 Браузерная игра года: Битва Танков!",
            subtitle = "Без скачивания прямо в браузере Opera Mini!",
            cta = "ИГРАТЬ СЕЙЧАС",
            iconEmoji = "💥"
        )
    )

    fun setTab(tab: StoreTab) {
        _uiState.update { it.copy(currentTab = tab, selectedAppId = null) }
    }

    fun selectApp(appId: String?) {
        _uiState.update { it.copy(selectedAppId = appId) }
    }

    fun selectCategory(category: AppCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        if (query.isNotBlank()) {
            repository.addSearchQuery(query)
        }
    }

    fun openSearch(open: Boolean) {
        _uiState.update { it.copy(isSearchOpen = open) }
    }

    fun clearSearchHistory() {
        repository.clearSearchHistory()
    }

    fun startDownloadRealApk(context: Context, app: AppItem) {
        downloadJobs[app.id]?.cancel()

        val job = viewModelScope.launch {
            repository.updateDownloadState(
                app.id,
                DownloadState(
                    status = DownloadStatus.DOWNLOADING,
                    progress = 0.05f,
                    statusMessage = "Подготовка к скачиванию ${app.apkFileName}..."
                )
            )

            try {
                val apkFile = ApkDownloadManager.downloadOrPrepareApk(
                    context = context,
                    app = app,
                    onProgress = { progress, statusText ->
                        repository.updateDownloadState(
                            app.id,
                            DownloadState(
                                status = DownloadStatus.DOWNLOADING,
                                progress = progress,
                                statusMessage = statusText,
                                apkFilePath = null
                            )
                        )
                    }
                )

                // Completed and ready
                repository.updateDownloadState(
                    app.id,
                    DownloadState(
                        status = DownloadStatus.INSTALLED,
                        progress = 1.0f,
                        statusMessage = "APK скачан: ${apkFile.name} (${apkFile.length() / 1024} КБ)",
                        apkFilePath = apkFile.absolutePath
                    )
                )

                _uiState.update {
                    it.copy(
                        statusNotification = "APK файл ${apkFile.name} скачан! Нажмите 'Установить APK'",
                        sdCardAvailableMb = (it.sdCardAvailableMb - (apkFile.length() / (1024 * 1024)).toInt()).coerceAtLeast(50)
                    )
                }

                // Automatically prompt package installer!
                ApkDownloadManager.installApk(context, apkFile)

            } catch (e: Exception) {
                repository.updateDownloadState(
                    app.id,
                    DownloadState(
                        status = DownloadStatus.NOT_INSTALLED,
                        progress = 0f,
                        statusMessage = "Ошибка загрузки: ${e.localizedMessage}"
                    )
                )
            }
        }
        downloadJobs[app.id] = job
    }

    fun installDownloadedApk(context: Context, app: AppItem) {
        val path = app.downloadState.apkFilePath
        if (path != null) {
            val file = File(path)
            if (file.exists()) {
                ApkDownloadManager.installApk(context, file)
                return
            }
        }
        // If file not cached yet, download it
        startDownloadRealApk(context, app)
    }

    fun shareDownloadedApk(context: Context, app: AppItem) {
        val path = app.downloadState.apkFilePath
        if (path != null) {
            val file = File(path)
            if (file.exists()) {
                ApkDownloadManager.shareApk(context, file, app.name)
                return
            }
        }
        _uiState.update { it.copy(statusNotification = "Сначала скачайте APK файл!") }
    }

    fun uninstallApp(appId: String) {
        downloadJobs[appId]?.cancel()
        downloadJobs.remove(appId)
        repository.uninstallApp(appId)
        _uiState.update { it.copy(statusNotification = "APK удален из списка") }
    }

    fun launchApp(app: AppItem) {
        _uiState.update { it.copy(launchedApp = app) }
    }

    fun closeLaunchedApp() {
        _uiState.update { it.copy(launchedApp = null) }
    }

    fun openWriteReviewDialog(app: AppItem) {
        _uiState.update { it.copy(isWritingReviewForApp = app) }
    }

    fun closeWriteReviewDialog() {
        _uiState.update { it.copy(isWritingReviewForApp = null) }
    }

    fun submitReview(appId: String, authorName: String, rating: Int, comment: String) {
        val newReview = Review(
            id = "user_rev_${System.currentTimeMillis()}",
            authorName = if (authorName.isNotBlank()) authorName else "Пользователь Galaxy S4",
            authorAvatarColor = 0xFF1428A0,
            rating = rating,
            date = "Только что",
            comment = if (comment.isNotBlank()) comment else "Отличная программа, работает отлично на моем Galaxy!",
            helpfulCount = 1
        )
        repository.addReview(appId, newReview)
        _uiState.update {
            it.copy(
                isWritingReviewForApp = null,
                statusNotification = "Ваш отзыв успешно опубликован в Galaxy Market!"
            )
        }
    }

    fun closeAdBanner() {
        _uiState.update { it.copy(adVisible = false) }
    }

    fun tapAdBanner() {
        _uiState.update {
            val nextIndex = (it.currentAdIndex + 1) % retroAds.size
            it.copy(
                currentAdIndex = nextIndex,
                statusNotification = "Переход по ссылке AdMob... (Ретро-реклама)"
            )
        }
    }

    fun clearNotification() {
        _uiState.update { it.copy(statusNotification = null) }
    }
}
