package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppItem
import com.example.ui.theme.HoloBlue
import com.example.ui.theme.PlayCardBackground
import com.example.ui.theme.PlayCardBorder
import com.example.ui.theme.PlayGreenDark
import com.example.ui.theme.PlayGreenHeader
import com.example.ui.theme.PlayStoreBackground
import com.example.ui.theme.PlayTextDark
import com.example.ui.theme.PlayTextMuted

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchOverlay(
    allApps: List<AppItem>,
    searchHistory: List<String>,
    onQueryChange: (String) -> Unit,
    onClearHistory: () -> Unit,
    onAppClick: (AppItem) -> Unit,
    onInstallClick: (AppItem) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }

    val filteredApps = remember(query, allApps) {
        if (query.isBlank()) emptyList()
        else allApps.filter {
            it.name.contains(query, ignoreCase = true) ||
            it.developer.contains(query, ignoreCase = true) ||
            it.shortDescription.contains(query, ignoreCase = true)
        }
    }

    val popularTags = listOf("Том", "Бен", "Фрукт Ниндзя", "Doodle Jump", "Flappy Bird", "ВКонтакте", "Фонарик", "NotPipe", "Subway Surfers")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PlayStoreBackground)
    ) {
        // Green Holo Search Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(PlayGreenHeader)
                .statusBarsPadding()
                .padding(horizontal = 4.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onClose,
                modifier = Modifier.testTag("search_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            TextField(
                value = query,
                onValueChange = {
                    query = it
                    onQueryChange(it)
                },
                placeholder = {
                    Text(
                        text = "Поиск в Google Play 2013...",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onQueryChange(query) }),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f)
                ),
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = {
                            query = ""
                            onQueryChange("")
                        }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                                tint = Color.White
                            )
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("search_text_input")
            )
        }

        if (query.isBlank()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                // Popular tags
                item {
                    Text(
                        text = "ПОПУЛЯРНЫЕ ЗАПРОСЫ 2013 ГОДА",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = PlayTextMuted,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp)
                    ) {
                        popularTags.forEach { tag ->
                            Surface(
                                shape = RoundedCornerShape(2.dp),
                                color = PlayCardBackground,
                                border = androidx.compose.foundation.BorderStroke(1.dp, PlayCardBorder),
                                modifier = Modifier.clickable {
                                    query = tag
                                    onQueryChange(tag)
                                }
                            ) {
                                Text(
                                    text = tag,
                                    color = PlayTextDark,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }

                // Recent history
                if (searchHistory.isNotEmpty()) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "ИСТОРИЯ ПОИСКА",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = PlayTextMuted
                            )
                            Text(
                                text = "Очистить",
                                color = HoloBlue,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable(onClick = onClearHistory)
                            )
                        }
                    }

                    items(searchHistory) { historyItem ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    query = historyItem
                                    onQueryChange(historyItem)
                                }
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = null,
                                tint = PlayTextMuted,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = historyItem,
                                color = PlayTextDark,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        } else {
            // Search Results
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                item {
                    Text(
                        text = "Результаты поиска: ${filteredApps.size}",
                        fontSize = 12.sp,
                        color = PlayTextMuted,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                    )
                }

                if (filteredApps.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 40.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🔍", fontSize = 42.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Ничего не найдено",
                                color = PlayTextDark,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Text(
                                text = "Проверьте правильность написания названия игры или программы",
                                color = PlayTextMuted,
                                fontSize = 12.sp
                            )
                        }
                    }
                } else {
                    items(filteredApps, key = { "search_${it.id}" }) { app ->
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
}
