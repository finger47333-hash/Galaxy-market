package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GalaxyBlueDark
import com.example.ui.theme.GalaxyBlueHeader
import com.example.ui.theme.GalaxyCyan
import com.example.viewmodel.StoreTab

@Composable
fun RetroHeader(
    currentTab: StoreTab,
    onTabSelected: (StoreTab) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(GalaxyBlueHeader, GalaxyBlueDark)
                )
            )
            .statusBarsPadding()
    ) {
        // Upper Action Bar (Galaxy Market branding)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Galaxy Market Emblem
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(GalaxyCyan.copy(alpha = 0.25f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Galaxy Market Logo",
                        tint = GalaxyCyan,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "GALAXY",
                            fontWeight = FontWeight.Black,
                            fontSize = 17.sp,
                            color = Color.White,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "MARKET",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = GalaxyCyan,
                            letterSpacing = 1.sp
                        )
                    }
                    Text(
                        text = "Официальный каталог APK приложений",
                        fontSize = 10.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            // Search Icon & Menu
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onSearchClick,
                    modifier = Modifier.testTag("action_search")
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
                IconButton(onClick = onSearchClick) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }
            }
        }

        // Holo Scrollable Tabs
        ScrollableTabRow(
            selectedTabIndex = currentTab.ordinal,
            containerColor = Color.Transparent,
            contentColor = Color.White,
            edgePadding = 12.dp,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[currentTab.ordinal]),
                    color = GalaxyCyan,
                    height = 3.dp
                )
            },
            divider = {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.White.copy(alpha = 0.2f))
                )
            }
        ) {
            StoreTab.values().forEach { tab ->
                val isSelected = tab == currentTab
                Tab(
                    selected = isSelected,
                    onClick = { onTabSelected(tab) },
                    text = {
                        Text(
                            text = if (tab == StoreTab.MY_APPS) "СКАЧАННЫЕ APK" else tab.titleRu,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp,
                            color = if (isSelected) GalaxyCyan else Color.White.copy(alpha = 0.8f)
                        )
                    }
                )
            }
        }
    }
}
