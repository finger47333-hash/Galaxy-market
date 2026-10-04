package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.AppItem
import com.example.ui.theme.PlayCardBackground
import com.example.ui.theme.PlayCardBorder
import com.example.ui.theme.PlayGreenButton
import com.example.ui.theme.PlayTextDark
import com.example.ui.theme.PlayTextMuted

@Composable
fun SimulatedRetroAppDialog(
    app: AppItem,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.85f))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                color = PlayCardBackground,
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color(0xFF4C9029))
            ) {
                Column {
                    // Retro Window Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF333333))
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AppIconComposable(
                                iconSymbol = app.iconSymbol,
                                primaryColor = app.primaryColor,
                                secondaryColor = app.secondaryColor,
                                size = 26.dp,
                                shapeRadius = 4.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = app.name,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }

                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .size(28.dp)
                                .testTag("close_simulated_app")
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White, modifier = Modifier.size(18.dp))
                        }
                    }

                    // Simulated Screen
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(310.dp)
                            .background(Color(0xFF1E1E1E))
                            .padding(14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        when (app.id) {
                            "talking_tom" -> SimulatedTom()
                            "talking_ben" -> SimulatedBen()
                            "fruit_ninja" -> SimulatedFruitNinja()
                            "doodle_jump" -> SimulatedDoodleJump()
                            "flappy_bird" -> SimulatedFlappyBird()
                            "tiny_flashlight" -> SimulatedFlashlight()
                            "vkontakte_retro" -> SimulatedVKontakte()
                            "hill_climb_racing" -> SimulatedHillClimb()
                            else -> SimulatedGenericApp(app)
                        }
                    }

                    // Window footer
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFEEEEEE))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Android 4.4 KitKat Runtime",
                            color = PlayTextMuted,
                            fontSize = 11.sp
                        )

                        Button(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF666666)),
                            shape = RoundedCornerShape(2.dp),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text("ЗАКРЫТЬ", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SimulatedTom() {
    var tomState by remember { mutableStateOf("Том ждет ваших слов...") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("🐱", fontSize = 72.sp)
        Text(
            text = tomState,
            color = Color(0xFFFFD54F),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { tomState = "Том замурлыкал: Муррр-муррр!" },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                shape = RoundedCornerShape(2.dp)
            ) {
                Text("Погладить", fontSize = 11.sp)
            }
            Button(
                onClick = { tomState = "Том выпил стакан молока! 🥛" },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                shape = RoundedCornerShape(2.dp)
            ) {
                Text("Молоко", fontSize = 11.sp)
            }
            Button(
                onClick = { tomState = "Том повторил писклявым голосом!" },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                shape = RoundedCornerShape(2.dp)
            ) {
                Text("Сказать", fontSize = 11.sp)
            }
        }
    }
}

@Composable
fun SimulatedBen() {
    var benAction by remember { mutableStateOf("Бен читает газету...") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("🐶 🧪", fontSize = 60.sp)
        Text(
            text = benAction,
            color = Color(0xFF81D4FA),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { benAction = "Бен снял трубку: 'Yes? No? Ho-ho-ho!'" },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                shape = RoundedCornerShape(2.dp)
            ) {
                Text("Позвонить", fontSize = 11.sp)
            }
            Button(
                onClick = { benAction = "БУХХХ! Зелье взорвалось в пробирке! 💥" },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                shape = RoundedCornerShape(2.dp)
            ) {
                Text("Смешать", fontSize = 11.sp)
            }
        }
    }
}

@Composable
fun SimulatedFruitNinja() {
    var score by remember { mutableIntStateOf(0) }
    var cutNotice by remember { mutableStateOf("Свайпайте по фруктам!") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("🍉 🍌 🍎 💣", fontSize = 42.sp)
        Text(
            text = "СЧЕТ: $score",
            color = Color.White,
            fontWeight = FontWeight.Black,
            fontSize = 24.sp
        )
        Text(
            text = cutNotice,
            color = Color(0xFFFFB300),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Button(
            onClick = {
                score += 150
                cutNotice = "💥 СУПЕР КОМБО x3! +150 Очков!"
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
            shape = RoundedCornerShape(2.dp)
        ) {
            Text("РАЗРЕЗАТЬ АРБУЗ СВАЙПОМ!", fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun SimulatedDoodleJump() {
    var height by remember { mutableIntStateOf(2450) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("🦘 🟩 🟩", fontSize = 48.sp)
        Text(
            text = "ВЫСОТА: $height м",
            color = Color(0xFF8BC34A),
            fontWeight = FontWeight.Black,
            fontSize = 26.sp
        )
        Text("Наклоняйте влево/вправо!", color = Color.White, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(14.dp))
        Button(
            onClick = { height += 350 },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8BC34A)),
            shape = RoundedCornerShape(2.dp)
        ) {
            Text("ПРУЖИНА! ВВЕРХ!", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SimulatedFlappyBird() {
    var birdScore by remember { mutableIntStateOf(0) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("🐤", fontSize = 54.sp)
        Text(
            text = "$birdScore",
            color = Color.White,
            fontWeight = FontWeight.Black,
            fontSize = 38.sp
        )
        Text("Пролетайте между зелеными трубами!", color = Color(0xFFB0BEC5), fontSize = 11.sp)
        Spacer(modifier = Modifier.height(14.dp))
        Button(
            onClick = { birdScore += 1 },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            shape = RoundedCornerShape(2.dp)
        ) {
            Text("ВЗМАХ КРЫЛЬЯМИ (FLAP!)", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SimulatedFlashlight() {
    var isFlashOn by remember { mutableStateOf(true) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(if (isFlashOn) Color(0xFFFFD54F) else Color(0xFF424242))
                .clickable { isFlashOn = !isFlashOn },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.FlashOn,
                contentDescription = "Flashlight",
                tint = if (isFlashOn) Color.Black else Color(0xFF757575),
                modifier = Modifier.size(48.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = if (isFlashOn) "ФОНАРИК: ВКЛЮЧЕН" else "ФОНАРИК: ВЫКЛЮЧЕН",
            color = if (isFlashOn) Color(0xFFFFD54F) else Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Text(
            text = "Щелкните по кнопке для переключения режима",
            color = Color(0xFFB0BEC5),
            fontSize = 11.sp
        )
    }
}

@Composable
fun SimulatedVKontakte() {
    var isPlaying by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            color = Color(0xFF45668E),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.MusicNote, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Макс Корж — Жить в кайф", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text("3:14 • Кэш сохранен на SD-карту", color = Color.White.copy(alpha = 0.8f), fontSize = 10.sp)
                }
                IconButton(onClick = { isPlaying = !isPlaying }) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.PlayArrow else Icons.Default.FastForward,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Surface(
            color = Color(0xFF2C3E50),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text("Павел Дуров", color = Color(0xFF81D4FA), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Text("Дуров, верни стену!", color = Color.White, fontSize = 13.sp)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 6.dp)
                ) {
                    Icon(Icons.Default.ThumbUp, contentDescription = null, tint = Color(0xFF81D4FA), modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("14 820 Мне нравится", color = Color(0xFF81D4FA), fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
fun SimulatedHillClimb() {
    var coins by remember { mutableIntStateOf(14500) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("🚙 ⛰️ 🌕", fontSize = 52.sp)
        Text(
            text = "МОНЕТЫ: $coins 🪙",
            color = Color(0xFFFFD54F),
            fontWeight = FontWeight.Black,
            fontSize = 22.sp
        )
        Text("Локация: Луна (Пониженная гравитация)", color = Color.White, fontSize = 11.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(
                onClick = { coins += 2500 },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                shape = RoundedCornerShape(2.dp)
            ) {
                Text("ГАЗ (ПЕДАЛЬ)", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = { coins += 500 },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                shape = RoundedCornerShape(2.dp)
            ) {
                Text("ТОРМОЗ", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SimulatedGenericApp(app: AppItem) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AppIconComposable(
            iconSymbol = app.iconSymbol,
            primaryColor = app.primaryColor,
            secondaryColor = app.secondaryColor,
            size = 64.dp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(app.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text("Версия: ${app.version}", color = Color(0xFF81D4FA), fontSize = 12.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Приложение успешно запущено в среде Android 4.4 KitKat!",
            color = Color(0xFFB0BEC5),
            fontSize = 12.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
