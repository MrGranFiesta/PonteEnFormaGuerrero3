package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState

@Composable
fun TriStateCheckList(
    txt: String,
    parentCheckState: ToggleableState,
    modifier: Modifier = Modifier,
    changeIsChecked: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                changeIsChecked()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TriStateCheckbox(
            state = parentCheckState,
            onClick = {
                changeIsChecked()
            },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary
            )
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = txt
        )
    }
}