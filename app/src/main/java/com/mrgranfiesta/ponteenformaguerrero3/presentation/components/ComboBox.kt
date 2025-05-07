package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.ComboBoxVM

@Composable
fun ComboBox(
    selectedOptions: String,
    options: List<String>,
    labelText: String,
    changeSelectOption: (String) -> Unit,
    changeExtras: () -> Unit = {},
    comboBoxVM: ComboBoxVM = hiltViewModel()
) {
    val isExplanded by comboBoxVM.isExplanded.collectAsState()
    val icon by comboBoxVM.icon.collectAsState()
    val source = remember { MutableInteractionSource() }
    comboBoxVM.initData()

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            value = selectedOptions,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {},
            label = { Text(text = labelText) },
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable { comboBoxVM.setIsExplanded(!isExplanded) }
                )
            },
            singleLine = true,
            maxLines = 1,
            readOnly = true,
            interactionSource = source
        )

        DropdownMenu(
            expanded = isExplanded,
            onDismissRequest = { comboBoxVM.setIsExplanded(isExplanded = false) },
        ) {
            options.forEach { optionSelected ->
                DropdownMenuItem(
                    onClick = {
                        comboBoxVM.onClickDropMenu(
                            optionSelected = optionSelected,
                            changeSelectOption = changeSelectOption,
                            changeExtras = changeExtras
                        )
                    },
                    text = {
                        Text(text = optionSelected)
                    })
            }
        }

        if (source.collectIsPressedAsState().value) {
            comboBoxVM.setIsExplanded(isExplanded = true)
        }
    }
}
