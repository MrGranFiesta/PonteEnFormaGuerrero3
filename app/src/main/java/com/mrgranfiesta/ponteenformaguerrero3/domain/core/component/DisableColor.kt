package com.mrgranfiesta.ponteenformaguerrero3.domain.core.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun getColorDisableBy(condiccion: Boolean): Color {
    return if (condiccion) {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
    } else {
        MaterialTheme.colorScheme.onSurface
    }
}