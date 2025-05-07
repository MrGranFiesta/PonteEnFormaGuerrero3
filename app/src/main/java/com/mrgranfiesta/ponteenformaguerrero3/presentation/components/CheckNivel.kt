package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel

@Composable
fun CheckNivel(
    nivel: Nivel,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    changeIsChecked: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                changeIsChecked(!isChecked)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                changeIsChecked(!isChecked)
            },
            colors = CheckboxDefaults.colors(MaterialTheme.colorScheme.primary)
        )
        TextNivel(
            nivel = nivel,
        )
    }
}