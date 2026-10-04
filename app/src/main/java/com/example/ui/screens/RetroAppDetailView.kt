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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.InstallMobile
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppItem
import com.example.model.DownloadStatus
import com.example.ui.components.AppIconComposable
import com.example.ui.theme.GalaxyBlueDark
import com.example.ui.theme.GalaxyBlueHeader
import com.example.ui.theme.GalaxyCyan
import com.example.ui.theme.HoloBlue
import com.example.ui.theme.PlayCardBackground
import com.example.ui.theme.PlayCardBorder
import com.example.ui.theme.PlayGreenButton
import com.example.ui.theme.PlayStarYellow
import com.example.ui.theme.PlayStoreBackground
import com.example.ui.theme.PlayTextDark
import com.example.ui.theme.PlayTextMuted

@Composable
fun RetroAppDetailView(
    app: AppItem,
    onBackClick: () -> Unit,
    onDownloadApkClick: () -> Unit,
    onInstallSystemClick: () -> Unit,
    onShareApkClick: () -> Unit,
    onOpenSimulatedClick: () -> Unit,
    onUninstallClick: () -> Unit,
    onWriteReviewClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isDescExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PlayStoreBackground)
    ) {
        // Galaxy Blue Action Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(listOf(GalaxyBlueHeader, GalaxyBlueDark))
                )
                .statusBarsPadding()
                .padding(horizontal = 4.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.testTag("detail_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                text = app.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onShareApkClick) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share APK",
                    tint = Color.White
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            // Header Info Card
            item {
                Surface(
                    color = PlayCardBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(1.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AppIconComposable(
                                iconSymbol = app.iconSymbol,
                                primaryColor = app.primaryColor,
                                secondaryColor = app.secondaryColor,
                                size = 68.dp,
                                shapeRadius = 12.dp
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = app.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp,
                                    color = PlayTextDark
                                )
                                Text(
                                    text = app.developer,
                                    fontSize = 13.sp,
                                    color = HoloBlue,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "Пакет: ${app.packageName}",
                                    fontSize = 11.sp,
                                    color = PlayTextMuted
                                )
                                Text(
                                    text = "Файл: ${app.apkFileName}",
                                    fontSize = 10.sp,
                                    color = Color(0xFF2E7D32),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Actions: Download APK / Install System / Share / Open Simulation
                        val state = app.downloadState
                        when (state.status) {
                            DownloadStatus.DOWNLOADING, DownloadStatus.INSTALLING -> {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = state.statusMessage,
                                            fontSize = 12.sp,
                                            color = PlayTextDark,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = "${(state.progress * 100).toInt()}%",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = PlayGreenButton
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    LinearProgressIndicator(
                                        progress = { state.progress },
                                        color = PlayGreenButton,
                                        trackColor = Color(0xFFE0E0E0),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(8.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                    )
                                }
                            }
                            DownloadStatus.INSTALLED -> {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Surface(
                                        color = Color(0xFFE8F5E9),
                                        shape = RoundedCornerShape(4.dp),
                                        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(Icons.Default.Security, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = "APK файл скачан на устройство! Готов к установке.",
                                                color = Color(0xFF2E7D32),
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        // Real System Package Installer button
                                        Button(
                                            onClick = onInstallSystemClick,
                                            colors = ButtonDefaults.buttonColors(containerColor = PlayGreenButton),
                                            shape = RoundedCornerShape(2.dp),
                                            modifier = Modifier
                                                .weight(1.2f)
                                                .height(46.dp)
                                                .testTag("detail_system_install_button")
                                        ) {
                                            Icon(Icons.Default.InstallMobile, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("УСТАНОВИТЬ APK", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                        }

                                        // Share APK button
                                        OutlinedButton(
                                            onClick = onShareApkClick,
                                            shape = RoundedCornerShape(2.dp),
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(46.dp)
                                                .testTag("detail_share_apk_button")
                                        ) {
                                            Icon(Icons.Default.Share, contentDescription = null, tint = PlayTextDark, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("СОХРАНИТЬ", color = PlayTextDark, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    // Interactive test run inside Market
                                    Button(
                                        onClick = onOpenSimulatedClick,
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0099CC)),
                                        shape = RoundedCornerShape(2.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(38.dp)
                                            .testTag("detail_open_sim_button")
                                    ) {
                                        Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("ЗАПУСТИТЬ ТЕСТ В МАРКЕТЕ", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                    }
                                }
                            }
                            DownloadStatus.NOT_INSTALLED -> {
                                Button(
                                    onClick = onDownloadApkClick,
                                    colors = ButtonDefaults.buttonColors(containerColor = PlayGreenButton),
                                    shape = RoundedCornerShape(2.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .testTag("detail_download_apk_button")
                                ) {
                                    Icon(Icons.Default.FileDownload, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "СКАЧАТЬ НАСТОЯЩИЙ APK (${app.sizeText})",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color.White,
                                        letterSpacing = 0.5.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Stats Bar
            item {
                Surface(
                    color = Color(0xFFF7F7F7),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, PlayCardBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "%.1f".format(app.rating),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = PlayTextDark
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Icon(Icons.Default.Star, contentDescription = null, tint = PlayStarYellow, modifier = Modifier.size(16.dp))
                            }
                            Text(
                                text = "${app.reviewsCount} отзывов",
                                fontSize = 11.sp,
                                color = PlayTextMuted
                            )
                        }

                        Spacer(modifier = Modifier.width(1.dp).height(30.dp).background(PlayCardBorder))

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = app.downloadsCount,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = PlayTextDark
                            )
                            Text(
                                text = "Загрузок APK",
                                fontSize = 11.sp,
                                color = PlayTextMuted
                            )
                        }

                        Spacer(modifier = Modifier.width(1.dp).height(30.dp).background(PlayCardBorder))

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = app.sizeText,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = PlayTextDark
                            )
                            Text(
                                text = "Размер APK",
                                fontSize = 11.sp,
                                color = PlayTextMuted
                            )
                        }
                    }
                }
            }

            // Screenshots Gallery
            if (app.screenshots.isNotEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
                        Text(
                            text = "СКРИНШОТЫ ПРИЛОЖЕНИЯ",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = PlayTextMuted,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                        )

                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(app.screenshots) { sc ->
                                Box(
                                    modifier = Modifier
                                        .size(width = 170.dp, height = 260.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(
                                            Brush.verticalGradient(
                                                sc.gradientColors.map { Color(it) }
                                            )
                                        )
                                        .border(1.dp, PlayCardBorder, RoundedCornerShape(4.dp))
                                        .padding(12.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        AppIconComposable(
                                            iconSymbol = app.iconSymbol,
                                            primaryColor = app.primaryColor,
                                            secondaryColor = app.secondaryColor,
                                            size = 48.dp,
                                            shapeRadius = 10.dp
                                        )
                                        Spacer(modifier = Modifier.height(14.dp))
                                        Text(
                                            text = sc.title,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            fontSize = 14.sp,
                                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = sc.subtitle,
                                            color = Color.White.copy(alpha = 0.85f),
                                            fontSize = 11.sp,
                                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Description Section
            item {
                Surface(
                    color = PlayCardBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .border(1.dp, PlayCardBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "ОПИСАНИЕ И APK ДЕТАЛИ",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = GalaxyBlueDark
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (isDescExpanded) app.fullDescription else app.shortDescription,
                            fontSize = 13.sp,
                            color = PlayTextDark,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (isDescExpanded) "СВЕРНУТЬ" else "ЧИТАТЬ ПОЛНОСТЬЮ...",
                            color = HoloBlue,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .clickable { isDescExpanded = !isDescExpanded }
                                .padding(vertical = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "ЧТО НОВОГО В ВЕРСИИ ${app.version}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = PlayTextMuted
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = app.whatsNew,
                            fontSize = 12.sp,
                            color = PlayTextDark
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Обновлено: ${app.updatedDate} • Требуется: ${app.androidVersion}",
                            fontSize = 11.sp,
                            color = PlayTextMuted
                        )
                    }
                }
            }

            // Ratings and Comments Section
            item {
                Surface(
                    color = PlayCardBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .border(1.dp, PlayCardBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "ОТЗЫВЫ И КОММЕНТАРИИ",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = GalaxyBlueDark
                            )

                            // Rate & Comment button
                            Button(
                                onClick = onWriteReviewClick,
                                colors = ButtonDefaults.buttonColors(containerColor = PlayGreenButton),
                                shape = RoundedCornerShape(2.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier.testTag("rate_and_comment_button")
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("НАПИСАТЬ ОТЗЫВ", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color.White)
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Rating overview bar
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(end = 20.dp)
                            ) {
                                Text(
                                    text = "%.1f".format(app.rating),
                                    fontWeight = FontWeight.Black,
                                    fontSize = 42.sp,
                                    color = PlayTextDark
                                )
                                Row {
                                    for (i in 1..5) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = PlayStarYellow,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = "${app.reviewsCount} всего",
                                    fontSize = 11.sp,
                                    color = PlayTextMuted
                                )
                            }

                            // Star Bars breakdown
                            Column(modifier = Modifier.weight(1f)) {
                                StarBarRow(star = 5, fraction = 0.85f)
                                StarBarRow(star = 4, fraction = 0.10f)
                                StarBarRow(star = 3, fraction = 0.03f)
                                StarBarRow(star = 2, fraction = 0.01f)
                                StarBarRow(star = 1, fraction = 0.01f)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            // User Comments list
            items(app.reviews, key = { it.id }) { review ->
                Surface(
                    color = PlayCardBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(0.5.dp, PlayCardBorder)
                ) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(Color(review.authorAvatarColor)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = review.authorName.take(1),
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = review.authorName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = PlayTextDark
                                )
                            }
                            Text(
                                text = review.date,
                                fontSize = 11.sp,
                                color = PlayTextMuted
                            )
                        }

                        Row(modifier = Modifier.padding(top = 4.dp)) {
                            for (s in 1..5) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = if (s <= review.rating) PlayStarYellow else Color(0xFFDDDDDD),
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = review.comment,
                            fontSize = 13.sp,
                            color = PlayTextDark,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Отзыв полезен? Да (${review.helpfulCount})",
                            fontSize = 11.sp,
                            color = HoloBlue
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StarBarRow(star: Int, fraction: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 1.dp)
    ) {
        Text(text = "$star", fontSize = 10.sp, color = PlayTextMuted, modifier = Modifier.width(10.dp))
        Spacer(modifier = Modifier.width(4.dp))
        LinearProgressIndicator(
            progress = { fraction },
            color = PlayGreenButton,
            trackColor = Color(0xFFEEEEEE),
            modifier = Modifier
                .weight(1f)
                .height(6.dp)
        )
    }
}
