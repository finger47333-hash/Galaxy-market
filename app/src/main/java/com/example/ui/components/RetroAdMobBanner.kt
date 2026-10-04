package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.ui.theme.AdMobBadge
import com.example.ui.theme.AdMobBannerBg
import com.example.ui.theme.AdMobBorder
import com.example.ui.theme.AdMobText
import com.example.viewmodel.AdMobRetroAd

@Composable
fun RetroAdMobBanner(
    ad: AdMobRetroAd,
    onAdClick: () -> Unit,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .background(AdMobBannerBg, RoundedCornerShape(8.dp))
            .border(1.dp, AdMobBorder, RoundedCornerShape(8.dp))
            .clickable(onClick = onAdClick)
            .testTag("retroadmob_banner")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ad Icon Emoji
            Text(
                text = ad.iconEmoji,
                fontSize = 24.sp,
                modifier = Modifier.padding(end = 8.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = AdMobBadge,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "Реклама",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = ad.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = AdMobText,
                        maxLines = 1
                    )
                }

                Text(
                    text = ad.subtitle,
                    fontSize = 10.sp,
                    color = Color(0xFF6D4C41),
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            // Action Pill
            Surface(
                color = Color(0xFF2E7D32),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = ad.cta,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 9.sp,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                )
            }

            IconButton(
                onClick = onCloseClick,
                modifier = Modifier
                    .size(24.dp)
                    .padding(start = 4.dp)
                    .testTag("close_ad_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Ad",
                    tint = Color(0xFF8D6E63),
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}
