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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppCategory
import com.example.model.AppItem
import com.example.ui.components.AppCardRetroRow
import com.example.ui.components.AppCardRetroVertical
import com.example.ui.components.AppIconComposable
import com.example.ui.theme.HoloBlue
import com.example.ui.theme.PlayCardBackground
import com.example.ui.theme.PlayCardBorder
import com.example.ui.theme.PlayGreenButton
import com.example.ui.theme.PlayGreenHeader
import com.example.ui.theme.PlayStarYellow
import com.example.ui.theme.PlayStoreBackground
import com.example.ui.theme.PlayTextDark
import com.example.ui.theme.PlayTextMuted

@Composable
fun HomeScreen(
    apps: List<AppItem>,
    onAppClick: (AppItem) -> Unit,
    onInstallClick: (AppItem) -> Unit,
    onNavigateCategory: (AppCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val games = apps.filter { it.category == AppCategory.GAMES }
    val toolsAndSocial = apps.filter { it.category != AppCategory.GAMES }
    val featuredHero = apps.firstOrNull { it.id == "talking_tom" } ?: apps.first()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(PlayStoreBackground),
        contentPadding = PaddingValues(bottom = 70.dp)
    ) {
        // Spotlight 2013 Featured Banner (Говорящий Том / Play Store banner)
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .shadow(2.dp, RoundedCornerShape(2.dp))
                    .clip(RoundedCornerShape(2.dp))
                    .clickable { onAppClick(featuredHero) }
                    .border(1.dp, PlayCardBorder, RoundedCornerShape(2.dp)),
                color = PlayCardBackground
            ) {
                Column {
                    // Vintage Green Banner Top
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF33691E), Color(0xFF689F38))
                                )
                            )
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("🐱", fontSize = 56.sp)
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Surface(
                                    color = Color(0xFFFFD54F),
                                    shape = RoundedCornerShape(2.dp)
                                ) {
                                    Text(
                                        text = "★ ВЫБОР РЕДАКЦИИ 2013",
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = featuredHero.name,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                )
                                Text(
                                    text = "Повторяет слова забавным голосом!",
                                    color = Color.White.copy(alpha = 0.9f),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    // Banner bottom info & install
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "${featuredHero.developer} • Бесплатно",
                                color = PlayTextMuted,
                                fontSize = 11.sp
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                for (i in 1..5) {
                                    Icon(Icons.Default.Star, contentDescription = null, tint = PlayStarYellow, modifier = Modifier.size(12.dp))
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("4.8 (${featuredHero.downloadsCount})", fontSize = 11.sp, color = PlayTextDark)
                            }
                        }

                        Button(
                            onClick = { onInstallClick(featuredHero) },
                            colors = ButtonDefaults.buttonColors(containerColor = PlayGreenButton),
                            shape = RoundedCornerShape(2.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Text("УСТАНОВИТЬ", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Section: "ЛЕГЕНДАРНЫЕ ИГРЫ 2013"
        item {
            RetroSectionHeader(
                title = "ЛЕГЕНДАРНЫЕ ИГРЫ",
                onSeeAllClick = { onNavigateCategory(AppCategory.GAMES) }
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(games.take(8), key = { "game_${it.id}" }) { app ->
                    AppCardRetroVertical(
                        app = app,
                        onClick = { onAppClick(app) },
                        onInstallClick = { onInstallClick(app) }
                    )
                }
            }
        }

        // Section: "НЕОБХОДИМЫЕ ПРИЛОЖЕНИЯ И СОЦСЕТИ"
        item {
            Spacer(modifier = Modifier.height(10.dp))
            RetroSectionHeader(
                title = "ПРИЛОЖЕНИЯ И СОЦСЕТИ",
                onSeeAllClick = { onNavigateCategory(AppCategory.SOCIAL) }
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(toolsAndSocial.take(8), key = { "app_${it.id}" }) { app ->
                    AppCardRetroVertical(
                        app = app,
                        onClick = { onAppClick(app) },
                        onInstallClick = { onInstallClick(app) }
                    )
                }
            }
        }

        // Section: "БЕСТСЕЛЛЕРЫ GOOGLE PLAY"
        item {
            Spacer(modifier = Modifier.height(12.dp))
            RetroSectionHeader(
                title = "БЕСТСЕЛЛЕРЫ МАРКЕТА",
                onSeeAllClick = { onNavigateCategory(AppCategory.ALL) }
            )
        }

        items(apps.take(6), key = { "top_${it.id}" }) { app ->
            Box(modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)) {
                AppCardRetroRow(
                    app = app,
                    onClick = { onAppClick(app) },
                    onInstallClick = { onInstallClick(app) },
                    rankNumber = app.rank
                )
            }
        }
    }
}

@Composable
fun RetroSectionHeader(
    title: String,
    onSeeAllClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = PlayTextDark
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = onSeeAllClick)
        ) {
            Text(
                text = "ЕЩЁ",
                color = HoloBlue,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = HoloBlue,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}
