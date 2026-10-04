package com.example.model

enum class DownloadStatus {
    NOT_INSTALLED,
    DOWNLOADING,
    INSTALLING,
    INSTALLED
}

data class DownloadState(
    val status: DownloadStatus = DownloadStatus.NOT_INSTALLED,
    val progress: Float = 0f, // 0.0 to 1.0
    val statusMessage: String = "",
    val downloadedBytesText: String = "",
    val apkFilePath: String? = null
)

data class ScreenshotItem(
    val title: String,
    val subtitle: String,
    val gradientColors: List<Long>,
    val iconName: String
)

data class AppItem(
    val id: String,
    val name: String,
    val developer: String,
    val category: AppCategory,
    val packageName: String,
    val rating: Float,
    val reviewsCount: String,
    val downloadsCount: String,
    val sizeText: String, // e.g. "14,8 МБ", "890 КБ"
    val androidVersion: String = "Android 2.3 и выше",
    val shortDescription: String,
    val fullDescription: String,
    val whatsNew: String,
    val version: String,
    val updatedDate: String,
    val isEditorChoice: Boolean = false,
    val isTopFree: Boolean = true,
    val price: String = "Бесплатно",
    val rank: Int = 1,
    val primaryColor: Long,
    val secondaryColor: Long,
    val iconSymbol: String,
    val apkFileName: String,
    val apkDownloadUrl: String,
    val isInstalled: Boolean = false,
    val downloadState: DownloadState = DownloadState(),
    val reviews: List<Review> = emptyList(),
    val screenshots: List<ScreenshotItem> = emptyList()
)
