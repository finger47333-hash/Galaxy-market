package com.example.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppItem
import com.example.model.DownloadStatus
import com.example.ui.theme.PlayCardBackground
import com.example.ui.theme.PlayCardBorder
import com.example.ui.theme.PlayGreenButton
import com.example.ui.theme.PlayStarYellow
import com.example.ui.theme.PlayTextDark
import com.example.ui.theme.PlayTextMuted

@Composable
fun AppCardRetroRow(
    app: AppItem,
    onClick: () -> Unit,
    onInstallClick: () -> Unit,
    rankNumber: Int? = null,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(1.dp, RoundedCornerShape(2.dp))
            .clip(RoundedCornerShape(2.dp))
            .clickable(onClick = onClick)
            .border(1.dp, PlayCardBorder, RoundedCornerShape(2.dp)),
        color = PlayCardBackground
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rank Number
            if (rankNumber != null) {
                Text(
                    text = "$rankNumber.",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = PlayTextMuted,
                    modifier = Modifier.width(24.dp)
                )
            }

            // App Icon
            AppIconComposable(
                iconSymbol = app.iconSymbol,
                primaryColor = app.primaryColor,
                secondaryColor = app.secondaryColor,
                size = 52.dp,
                shapeRadius = 10.dp
            )

            Spacer(modifier = Modifier.width(12.dp))

            // App info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = app.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = PlayTextDark,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "${app.developer} • ${app.packageName}",
                    fontSize = 10.sp,
                    color = PlayTextMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Ratings and Size
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = PlayStarYellow,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "%.1f".format(app.rating),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PlayTextDark
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "• APK ${app.sizeText}",
                        fontSize = 10.sp,
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Action Button
            RetroActionButton(
                app = app,
                onClick = onInstallClick,
                isCompact = true
            )
        }
    }
}

@Composable
fun AppCardRetroVertical(
    app: AppItem,
    onClick: () -> Unit,
    onInstallClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .width(120.dp)
            .shadow(1.dp, RoundedCornerShape(2.dp))
            .clip(RoundedCornerShape(2.dp))
            .clickable(onClick = onClick)
            .border(1.dp, PlayCardBorder, RoundedCornerShape(2.dp)),
        color = PlayCardBackground
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppIconComposable(
                iconSymbol = app.iconSymbol,
                primaryColor = app.primaryColor,
                secondaryColor = app.secondaryColor,
                size = 56.dp,
                shapeRadius = 10.dp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = app.name,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = PlayTextDark,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "APK ${app.sizeText}",
                fontSize = 10.sp,
                color = Color(0xFF2E7D32),
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 3.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = PlayStarYellow,
                    modifier = Modifier.size(11.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "%.1f".format(app.rating),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = PlayTextDark
                )
            }

            RetroActionButton(
                app = app,
                onClick = onInstallClick,
                isCompact = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun RetroActionButton(
    app: AppItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isCompact: Boolean = false
) {
    val state = app.downloadState

    when (state.status) {
        DownloadStatus.DOWNLOADING, DownloadStatus.INSTALLING -> {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEEEEEE)),
                shape = RoundedCornerShape(2.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                modifier = modifier.testTag("retro_progress_btn_${app.id}")
            ) {
                CircularProgressIndicator(
                    progress = { state.progress },
                    modifier = Modifier.size(12.dp),
                    color = PlayGreenButton,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${(state.progress * 100).toInt()}%",
                    fontSize = 10.sp,
                    color = PlayGreenButton,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        DownloadStatus.INSTALLED -> {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0099CC)),
                shape = RoundedCornerShape(2.dp),
                contentPadding = PaddingValues(
                    horizontal = if (isCompact) 8.dp else 16.dp,
                    vertical = if (isCompact) 4.dp else 8.dp
                ),
                modifier = modifier.testTag("retro_open_btn_${app.id}")
            ) {
                Text(
                    text = "APK ГОТОВ",
                    fontSize = if (isCompact) 9.sp else 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
        DownloadStatus.NOT_INSTALLED -> {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = PlayGreenButton),
                shape = RoundedCornerShape(2.dp),
                contentPadding = PaddingValues(
                    horizontal = if (isCompact) 8.dp else 16.dp,
                    vertical = if (isCompact) 4.dp else 8.dp
                ),
                modifier = modifier.testTag("retro_install_btn_${app.id}")
            ) {
                Text(
                    text = "СКАЧАТЬ APK",
                    fontSize = if (isCompact) 9.sp else 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
