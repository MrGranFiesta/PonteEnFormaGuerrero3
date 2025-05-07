package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.CharacterCountLabelText
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Red200

@Composable
fun CharacterCountLabel(text: String, maxCount: Int) {
    val remainingLength = maxCount - text.length
    val textColor = if (remainingLength >= 0) {
        Color.Gray
    } else {
        Color.Red
    }

    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "$remainingLength/$maxCount",
            color = textColor,
            fontSize = 12.sp
        )
    }
    if (remainingLength < 0) {
        Text(
            text = CharacterCountLabelText.characterCountLabelMaxLength(maxCount),
            color = Red200,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}