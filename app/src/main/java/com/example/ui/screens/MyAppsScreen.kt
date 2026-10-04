package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FileDownloadDone
import androidx.compose.material.icons.filled.InstallMobile
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SdCard
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppItem
import com.example.ui.components.AppIconComposable
import com.example.ui.theme.PlayCardBackground
import com.example.ui.theme.PlayCardBorder
import com.example.ui.theme.PlayGreenButton
import com.example.ui.theme.PlayStoreBackground
import com.example.ui.theme.PlayTextDark
import com.example.ui.theme.PlayTextMuted

@Composable
fun MyAppsScreen(
    apps: List<AppItem>,
    sdCardFreeMb: Int,
    onAppClick: (AppItem) -> Unit,
    onInstallApkClick: (AppItem) -> Unit,
    onShareApkClick: (AppItem) -> Unit,
    onOpenSimulatedClick: (AppItem) -> Unit,
    onUninstallClick: (AppItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val downloadedApps = apps.filter { it.isInstalled || it.downloadState.apkFilePath != null }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PlayStoreBackground)
    ) {
        // SD Card Storage Bar
        Surface(
            color = PlayCardBackground,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, PlayCardBorder)
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.SdCard,
                            contentDescription = null,
                            tint = Color(0xFF1428A0),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Память SD-карты (APK хранилище)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = PlayTextDark
                        )
                    }
                    Text(
                        text = "$sdCardFreeMb МБ свободно из 2048 МБ",
                        fontSize = 11.sp,
                        color = PlayTextMuted
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { ((2048 - sdCardFreeMb) / 2048f).coerceIn(0.05f, 1f) },
                    color = Color(0xFF1428A0),
                    trackColor = Color(0xFFEEEEEE),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 70.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "СКАЧАННЫЕ APK ПАКЕТЫ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = PlayTextMuted
                    )
                    Text(
                        text = "Всего: ${downloadedApps.size}",
                        fontSize = 12.sp,
                        color = PlayTextMuted
                    )
                }
            }

            if (downloadedApps.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("📁", fontSize = 42.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Нет скачанных APK файлов",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = PlayTextDark
                        )
                        Text(
                            text = "Нажмите «СКАЧАТЬ APK» на странице любого приложения или игры",
                            fontSize = 12.sp,
                            color = PlayTextMuted
                        )
                    }
                }
            } else {
                items(downloadedApps, key = { "my_${it.id}" }) { app ->
                    Surface(
                        color = PlayCardBackground,
                        shape = RoundedCornerShape(2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, PlayCardBorder, RoundedCornerShape(2.dp))
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AppIconComposable(
                                    iconSymbol = app.iconSymbol,
                                    primaryColor = app.primaryColor,
                                    secondaryColor = app.secondaryColor,
                                    size = 46.dp,
                                    shapeRadius = 8.dp
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = app.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = PlayTextDark,
                                        maxLines = 1
                                    )
                                    Text(
                                        text = "${app.apkFileName} • ${app.sizeText}",
                                        fontSize = 11.sp,
                                        color = Color(0xFF2E7D32),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = app.packageName,
                                        fontSize = 10.sp,
                                        color = PlayTextMuted
                                    )
                                }

                                IconButton(
                                    onClick = { onUninstallClick(app) },
                                    modifier = Modifier.size(32.dp).testTag("delete_apk_${app.id}")
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFC62828), modifier = Modifier.size(18.dp))
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Action buttons row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                // Install via Android system package installer
                                Button(
                                    onClick = { onInstallApkClick(app) },
                                    colors = ButtonDefaults.buttonColors(containerColor = PlayGreenButton),
                                    shape = RoundedCornerShape(2.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                    modifier = Modifier.weight(1f).testTag("install_apk_btn_${app.id}")
                                ) {
                                    Icon(Icons.Default.InstallMobile, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("УСТАНОВИТЬ", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }

                                // Share APK file
                                Button(
                                    onClick = { onShareApkClick(app) },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1428A0)),
                                    shape = RoundedCornerShape(2.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                    modifier = Modifier.weight(1f).testTag("share_apk_btn_${app.id}")
                                ) {
                                    Icon(Icons.Default.Share, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("СОХРАНИТЬ", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }

                                // Simulated test
                                Button(
                                    onClick = { onOpenSimulatedClick(app) },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0099CC)),
                                    shape = RoundedCornerShape(2.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                    modifier = Modifier.weight(0.9f).testTag("sim_apk_btn_${app.id}")
                                ) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("ТЕСТ", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
