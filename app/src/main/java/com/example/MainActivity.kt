package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.AppCategory
import com.example.ui.components.RetroAdMobBanner
import com.example.ui.components.RetroHeader
import com.example.ui.components.SearchOverlay
import com.example.ui.components.SimulatedRetroAppDialog
import com.example.ui.components.WriteCommentDialog
import com.example.ui.screens.CategoriesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MyAppsScreen
import com.example.ui.screens.RetroAppDetailView
import com.example.ui.screens.TopChartsScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.PlayStoreBackground
import com.example.viewmodel.StoreTab
import com.example.viewmodel.StoreViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                GalaxyMarketApp()
            }
        }
    }
}

@Composable
fun GalaxyMarketApp(viewModel: StoreViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val apps by viewModel.apps.collectAsState()
    val searchHistory by viewModel.searchHistory.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.statusNotification) {
        uiState.statusNotification?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            viewModel.clearNotification()
        }
    }

    // Back handling for custom navigation
    BackHandler(enabled = uiState.selectedAppId != null || uiState.isSearchOpen) {
        if (uiState.selectedAppId != null) {
            viewModel.selectApp(null)
        } else if (uiState.isSearchOpen) {
            viewModel.openSearch(false)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(PlayStoreBackground)
        ) {
            val selectedApp = apps.firstOrNull { it.id == uiState.selectedAppId }

            if (selectedApp != null) {
                // Detailed App View (Real APK Download & System Install, Ratings, Comments, Screenshots)
                RetroAppDetailView(
                    app = selectedApp,
                    onBackClick = { viewModel.selectApp(null) },
                    onDownloadApkClick = { viewModel.startDownloadRealApk(context, selectedApp) },
                    onInstallSystemClick = { viewModel.installDownloadedApk(context, selectedApp) },
                    onShareApkClick = { viewModel.shareDownloadedApk(context, selectedApp) },
                    onOpenSimulatedClick = { viewModel.launchApp(selectedApp) },
                    onUninstallClick = { viewModel.uninstallApp(selectedApp.id) },
                    onWriteReviewClick = { viewModel.openWriteReviewDialog(selectedApp) }
                )
            } else {
                // Main Galaxy Market Tabs
                Column(modifier = Modifier.fillMaxSize()) {
                    RetroHeader(
                        currentTab = uiState.currentTab,
                        onTabSelected = { viewModel.setTab(it) },
                        onSearchClick = { viewModel.openSearch(true) }
                    )

                    Box(modifier = Modifier.weight(1f)) {
                        when (uiState.currentTab) {
                            StoreTab.FEATURED -> {
                                HomeScreen(
                                    apps = apps,
                                    onAppClick = { viewModel.selectApp(it.id) },
                                    onInstallClick = {
                                        if (it.isInstalled || it.downloadState.apkFilePath != null) {
                                            viewModel.installDownloadedApk(context, it)
                                        } else {
                                            viewModel.startDownloadRealApk(context, it)
                                        }
                                    },
                                    onNavigateCategory = { cat ->
                                        if (cat == AppCategory.GAMES) viewModel.setTab(StoreTab.GAMES)
                                        else viewModel.setTab(StoreTab.CATEGORIES)
                                    }
                                )
                            }
                            StoreTab.GAMES -> {
                                val gamesOnly = apps.filter { it.category == AppCategory.GAMES }
                                TopChartsScreen(
                                    apps = gamesOnly,
                                    onAppClick = { viewModel.selectApp(it.id) },
                                    onInstallClick = {
                                        if (it.isInstalled || it.downloadState.apkFilePath != null) {
                                            viewModel.installDownloadedApk(context, it)
                                        } else {
                                            viewModel.startDownloadRealApk(context, it)
                                        }
                                    }
                                )
                            }
                            StoreTab.APPS -> {
                                val appsOnly = apps.filter { it.category != AppCategory.GAMES }
                                TopChartsScreen(
                                    apps = appsOnly,
                                    onAppClick = { viewModel.selectApp(it.id) },
                                    onInstallClick = {
                                        if (it.isInstalled || it.downloadState.apkFilePath != null) {
                                            viewModel.installDownloadedApk(context, it)
                                        } else {
                                            viewModel.startDownloadRealApk(context, it)
                                        }
                                    }
                                )
                            }
                            StoreTab.TOP_CHARTS -> {
                                TopChartsScreen(
                                    apps = apps,
                                    onAppClick = { viewModel.selectApp(it.id) },
                                    onInstallClick = {
                                        if (it.isInstalled || it.downloadState.apkFilePath != null) {
                                            viewModel.installDownloadedApk(context, it)
                                        } else {
                                            viewModel.startDownloadRealApk(context, it)
                                        }
                                    }
                                )
                            }
                            StoreTab.CATEGORIES -> {
                                CategoriesScreen(
                                    apps = apps,
                                    onAppClick = { viewModel.selectApp(it.id) },
                                    onInstallClick = {
                                        if (it.isInstalled || it.downloadState.apkFilePath != null) {
                                            viewModel.installDownloadedApk(context, it)
                                        } else {
                                            viewModel.startDownloadRealApk(context, it)
                                        }
                                    }
                                )
                            }
                            StoreTab.MY_APPS -> {
                                MyAppsScreen(
                                    apps = apps,
                                    sdCardFreeMb = uiState.sdCardAvailableMb,
                                    onAppClick = { viewModel.selectApp(it.id) },
                                    onInstallApkClick = { viewModel.installDownloadedApk(context, it) },
                                    onShareApkClick = { viewModel.shareDownloadedApk(context, it) },
                                    onOpenSimulatedClick = { viewModel.launchApp(it) },
                                    onUninstallClick = { viewModel.uninstallApp(it.id) }
                                )
                            }
                        }
                    }

                    // Authentic 2013 AdMob Mobile Banner at the bottom
                    if (uiState.adVisible) {
                        val currentAd = viewModel.retroAds[uiState.currentAdIndex]
                        RetroAdMobBanner(
                            ad = currentAd,
                            onAdClick = { viewModel.tapAdBanner() },
                            onCloseClick = { viewModel.closeAdBanner() }
                        )
                    }
                }
            }

            // Search Overlay
            if (uiState.isSearchOpen) {
                SearchOverlay(
                    allApps = apps,
                    searchHistory = searchHistory,
                    onQueryChange = { viewModel.setSearchQuery(it) },
                    onClearHistory = { viewModel.clearSearchHistory() },
                    onAppClick = {
                        viewModel.openSearch(false)
                        viewModel.selectApp(it.id)
                    },
                    onInstallClick = {
                        if (it.isInstalled || it.downloadState.apkFilePath != null) {
                            viewModel.installDownloadedApk(context, it)
                        } else {
                            viewModel.startDownloadRealApk(context, it)
                        }
                    },
                    onClose = { viewModel.openSearch(false) }
                )
            }

            // Rate and Write Review / Comment Dialog
            uiState.isWritingReviewForApp?.let { targetApp ->
                WriteCommentDialog(
                    app = targetApp,
                    onDismiss = { viewModel.closeWriteReviewDialog() },
                    onSubmit = { author, rating, comment ->
                        viewModel.submitReview(targetApp.id, author, rating, comment)
                    }
                )
            }

            // Simulated Retro App Window
            uiState.launchedApp?.let { launchedApp ->
                SimulatedRetroAppDialog(
                    app = launchedApp,
                    onDismiss = { viewModel.closeLaunchedApp() }
                )
            }
        }
    }
}
