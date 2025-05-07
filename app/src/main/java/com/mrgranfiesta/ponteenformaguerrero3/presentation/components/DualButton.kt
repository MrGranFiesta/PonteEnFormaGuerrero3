package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun DualButton(
    leftAction: () -> Unit = {},
    rightAction: () -> Unit = {},
    textLeft: String,
    textRight: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Button(
            onClick = leftAction,
            modifier = Modifier
                .weight(1f),
            shape = RoundedCornerShape(0.dp)
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = textLeft
            )
        }
        Spacer(
            modifier = Modifier.background(
                MaterialTheme.colorScheme.onBackground
            ).width(2.dp)
        )
        Button(
            onClick = rightAction,
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .weight(1f),
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = textRight
            )
        }
    }
}
