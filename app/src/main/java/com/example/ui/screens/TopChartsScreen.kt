package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppCategory
import com.example.model.AppItem
import com.example.ui.components.AppCardRetroRow
import com.example.ui.theme.PlayCardBorder
import com.example.ui.theme.PlayGreenButton
import com.example.ui.theme.PlayStoreBackground
import com.example.ui.theme.PlayTextDark
import com.example.ui.theme.PlayTextMuted

@Composable
fun TopChartsScreen(
    apps: List<AppItem>,
    onAppClick: (AppItem) -> Unit,
    onInstallClick: (AppItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf(AppCategory.ALL) }

    val filteredApps = remember(apps, selectedCategory) {
        val list = if (selectedCategory == AppCategory.ALL) apps else apps.filter { it.category == selectedCategory }
        list.sortedBy { it.rank }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PlayStoreBackground)
    ) {
        // Quick Category Filter Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(AppCategory.values()) { cat ->
                val isSelected = cat == selectedCategory
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedCategory = cat },
                    label = {
                        Text(
                            text = cat.titleRu,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PlayGreenButton,
                        selectedLabelColor = Color.White,
                        containerColor = Color.White,
                        labelColor = PlayTextDark
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = PlayCardBorder,
                        selectedBorderColor = PlayGreenButton,
                        enabled = true,
                        selected = isSelected
                    ),
                    shape = RoundedCornerShape(2.dp)
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 10.dp, end = 10.dp, top = 4.dp, bottom = 70.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            itemsIndexed(filteredApps, key = { index, app -> "chart_${app.id}_$index" }) { index, app ->
                AppCardRetroRow(
                    app = app,
                    onClick = { onAppClick(app) },
                    onInstallClick = { onInstallClick(app) },
                    rankNumber = index + 1
                )
            }
        }
    }
}
