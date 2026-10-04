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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.AppItem
import com.example.ui.theme.PlayCardBackground
import com.example.ui.theme.PlayCardBorder
import com.example.ui.theme.PlayGreenButton
import com.example.ui.theme.PlayGreenHeader
import com.example.ui.theme.PlayStarYellow
import com.example.ui.theme.PlayTextDark
import com.example.ui.theme.PlayTextMuted

@Composable
fun WriteCommentDialog(
    app: AppItem,
    onDismiss: () -> Unit,
    onSubmit: (author: String, rating: Int, comment: String) -> Unit
) {
    var selectedStars by remember { mutableIntStateOf(5) }
    var authorName by remember { mutableStateOf("") }
    var commentText by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            color = PlayCardBackground,
            shape = RoundedCornerShape(2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, PlayCardBorder)
        ) {
            Column {
                // Header (Holo green)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(PlayGreenHeader)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Column {
                        Text(
                            text = "Оценить и написать отзыв",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                        Text(
                            text = app.name,
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    // Star rating picker
                    Text(
                        text = "Ваша оценка:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = PlayTextDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (star in 1..5) {
                            val filled = star <= selectedStars
                            Icon(
                                imageVector = if (filled) Icons.Default.Star else Icons.Outlined.StarOutline,
                                contentDescription = "Star $star",
                                tint = if (filled) PlayStarYellow else Color(0xFFBDBDBD),
                                modifier = Modifier
                                    .size(38.dp)
                                    .clickable { selectedStars = star }
                                    .testTag("star_rate_$star")
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Author input
                    OutlinedTextField(
                        value = authorName,
                        onValueChange = { authorName = it },
                        label = { Text("Ваше имя или ник") },
                        placeholder = { Text("Например: AndroidFan_2013") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PlayGreenButton,
                            unfocusedBorderColor = PlayCardBorder,
                            focusedTextColor = PlayTextDark,
                            unfocusedTextColor = PlayTextDark
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("author_name_input")
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Comment input
                    OutlinedTextField(
                        value = commentText,
                        onValueChange = { commentText = it },
                        label = { Text("Ваш комментарий") },
                        placeholder = { Text("Поделитесь мнением о приложении...") },
                        minLines = 3,
                        maxLines = 5,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PlayGreenButton,
                            unfocusedBorderColor = PlayCardBorder,
                            focusedTextColor = PlayTextDark,
                            unfocusedTextColor = PlayTextDark
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("comment_text_input")
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        OutlinedButton(
                            onClick = onDismiss,
                            shape = RoundedCornerShape(2.dp)
                        ) {
                            Text("ОТМЕНА", color = PlayTextMuted, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Button(
                            onClick = {
                                onSubmit(authorName, selectedStars, commentText)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PlayGreenButton),
                            shape = RoundedCornerShape(2.dp),
                            modifier = Modifier.testTag("submit_comment_btn")
                        ) {
                            Text("ОТПРАВИТЬ", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
