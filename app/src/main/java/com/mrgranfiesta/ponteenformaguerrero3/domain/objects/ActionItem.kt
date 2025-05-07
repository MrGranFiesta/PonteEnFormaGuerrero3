package com.mrgranfiesta.ponteenformaguerrero3.domain.objects

import androidx.compose.ui.graphics.vector.ImageVector

data class ActionItem(
    val name: String,
    val icon: ImageVector,
    val tooltipText : String,
    val action: () -> Unit
)