package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.filled.Train
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppIconComposable(
    iconSymbol: String,
    primaryColor: Long,
    secondaryColor: Long,
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
    shapeRadius: Dp = 12.dp
) {
    val gradient = Brush.verticalGradient(
        listOf(
            Color(primaryColor),
            Color(secondaryColor)
        )
    )

    Box(
        modifier = modifier
            .size(size)
            .shadow(2.dp, shape = RoundedCornerShape(shapeRadius), clip = false)
            .clip(RoundedCornerShape(shapeRadius))
            .background(gradient)
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.35f),
                shape = RoundedCornerShape(shapeRadius)
            ),
        contentAlignment = Alignment.Center
    ) {
        when (iconSymbol.lowercase()) {
            "cat" -> Text("🐱", fontSize = (size.value * 0.55f).sp)
            "dog" -> Text("🐶", fontSize = (size.value * 0.55f).sp)
            "ninja" -> Text("🍉", fontSize = (size.value * 0.55f).sp)
            "jump" -> Text("🦘", fontSize = (size.value * 0.55f).sp)
            "bird" -> Text("🐤", fontSize = (size.value * 0.55f).sp)
            "vk" -> Text("ВК", color = Color.White, fontWeight = FontWeight.Black, fontSize = (size.value * 0.42f).sp)
            "download" -> Icon(Icons.Default.Download, contentDescription = null, tint = Color.White, modifier = Modifier.size(size * 0.6f))
            "flashlight" -> Icon(Icons.Default.FlashOn, contentDescription = null, tint = Color(0xFFFFD54F), modifier = Modifier.size(size * 0.65f))
            "angry_bird" -> Text("😡", fontSize = (size.value * 0.55f).sp)
            "subway" -> Icon(Icons.Default.Train, contentDescription = null, tint = Color.White, modifier = Modifier.size(size * 0.6f))
            "hill_climb" -> Text("🚙", fontSize = (size.value * 0.55f).sp)
            "temple" -> Text("🗿", fontSize = (size.value * 0.55f).sp)
            "candy" -> Text("🍬", fontSize = (size.value * 0.55f).sp)
            "flower" -> Icon(Icons.Default.LocalFlorist, contentDescription = null, tint = Color(0xFFC8E6C9), modifier = Modifier.size(size * 0.6f))
            "opera" -> Text("O", color = Color.White, fontWeight = FontWeight.Black, fontSize = (size.value * 0.55f).sp)
            "broom" -> Icon(Icons.Default.CleaningServices, contentDescription = null, tint = Color.White, modifier = Modifier.size(size * 0.6f))
            "shazam" -> Icon(Icons.Default.MusicNote, contentDescription = null, tint = Color.White, modifier = Modifier.size(size * 0.6f))
            "motorcycle" -> Icon(Icons.Default.TwoWheeler, contentDescription = null, tint = Color.White, modifier = Modifier.size(size * 0.6f))
            "water" -> Text("🐟", fontSize = (size.value * 0.55f).sp)
            "play_circle" -> Icon(Icons.Default.PlayCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(size * 0.6f))
            else -> Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White, modifier = Modifier.size(size * 0.6f))
        }
    }
}
