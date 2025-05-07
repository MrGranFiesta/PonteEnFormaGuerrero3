package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconButtonWithTooltip(
    tooltipText: String,
    interactionSource: MutableInteractionSource? = null,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider()
    val tooltipState = rememberTooltipState()

    TooltipBox(
        positionProvider = positionProvider,
        tooltip = {
            PlainTooltip(
                contentColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.background,
                shadowElevation = 5.dp
            ) {
                Text(tooltipText)
            }
        },
        state = tooltipState
    ) {
        IconButton(
            onClick = onClick,
            content = content,
            interactionSource = interactionSource
        )
    }
}
