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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppCategory
import com.example.model.AppItem
import com.example.ui.components.AppCardRetroRow
import com.example.ui.theme.HoloBlue
import com.example.ui.theme.PlayCardBackground
import com.example.ui.theme.PlayCardBorder
import com.example.ui.theme.PlayStoreBackground
import com.example.ui.theme.PlayTextDark
import com.example.ui.theme.PlayTextMuted

data class CategoryItemInfo(
    val category: AppCategory,
    val description: String,
    val icon: ImageVector,
    val iconColor: Color
)

@Composable
fun CategoriesScreen(
    apps: List<AppItem>,
    onAppClick: (AppItem) -> Unit,
    onInstallClick: (AppItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf<AppCategory?>(null) }

    val categoriesList = listOf(
        CategoryItemInfo(AppCategory.GAMES, "Том, Бен, Фрукт Ниндзя, Doodle Jump, Flappy Bird, Angry Birds", Icons.Default.SportsEsports, Color(0xFF4CAF50)),
        CategoryItemInfo(AppCategory.SOCIAL, "ВКонтакте, ICQ Аська, Соцсети и мессенджеры", Icons.Default.Chat, Color(0xFF2196F3)),
        CategoryItemInfo(AppCategory.TOOLS, "Фонарик LED, Opera Mini, Clean Master, Аквариум", Icons.Default.Build, Color(0xFFFF9800)),
        CategoryItemInfo(AppCategory.MEDIA, "NotPipe/TubeMate, Shazam, MX Player", Icons.Default.MusicNote, Color(0xFFE91E63)),
        CategoryItemInfo(AppCategory.RETRO, "Хиты с кнопочных телефонов и первых версий Android", Icons.Default.Star, Color(0xFFFFC107))
    )

    if (selectedCategory == null) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(PlayStoreBackground),
            contentPadding = PaddingValues(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 70.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            item {
                Text(
                    text = "КАТЕГОРИИ ПРИЛОЖЕНИЙ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = PlayTextMuted,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp)
                )
            }

            items(categoriesList) { catInfo ->
                val count = if (catInfo.category == AppCategory.RETRO) apps.size else apps.count { it.category == catInfo.category }
                Surface(
                    color = PlayCardBackground,
                    shape = RoundedCornerShape(2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, PlayCardBorder, RoundedCornerShape(2.dp))
                        .clickable { selectedCategory = catInfo.category }
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(catInfo.iconColor.copy(alpha = 0.15f), RoundedCornerShape(4.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(catInfo.icon, contentDescription = null, tint = catInfo.iconColor, modifier = Modifier.size(24.dp))
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = catInfo.category.titleRu,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = PlayTextDark
                            )
                            Text(
                                text = catInfo.description,
                                fontSize = 11.sp,
                                color = PlayTextMuted,
                                maxLines = 1
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "$count",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = HoloBlue
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = Color(0xFFBDBDBD),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    } else {
        val cat = selectedCategory!!
        val filtered = if (cat == AppCategory.RETRO) apps else apps.filter { it.category == cat }

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(PlayStoreBackground)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PlayCardBackground)
                    .border(0.5.dp, PlayCardBorder)
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { selectedCategory = null }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = PlayTextDark)
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = cat.titleRu,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = PlayTextDark
                    )
                    Text(
                        text = "${filtered.size} приложений в категории",
                        fontSize = 11.sp,
                        color = PlayTextMuted
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 70.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(filtered, key = { "cat_item_${it.id}" }) { app ->
                    AppCardRetroRow(
                        app = app,
                        onClick = { onAppClick(app) },
                        onInstallClick = { onInstallClick(app) }
                    )
                }
            }
        }
    }
}
