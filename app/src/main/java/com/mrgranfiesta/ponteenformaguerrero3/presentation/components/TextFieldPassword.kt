package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TextFieldPasswordVM
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Red200

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldPassword(
    modifier: Modifier,
    txt: String,
    onValueChange: (String) -> Unit,
    label: String,
    maxCount: Int,
    textFieldPasswordVM: TextFieldPasswordVM = hiltViewModel()
) {
    val isVisibility by textFieldPasswordVM.isVisibility.collectAsState()
    val isFinishFirstIteration by textFieldPasswordVM.isFinishFirstIteration.collectAsState()

    OutlinedTextField(
        value = txt,
        onValueChange = {
            onValueChange(it)
            textFieldPasswordVM.onCompleteFirstIteration()
        },
        keyboardOptions = textFieldPasswordVM.getKeyboardPassword(),
        visualTransformation = textFieldPasswordVM.getVisualTransformation(isVisibility),
        modifier = modifier,
        label = { Text(text = label) },
        maxLines = 1,
        leadingIcon = {
            IconButtonWithTooltip(
                tooltipText = textFieldPasswordVM.getTextByVisibility(isVisibility),
                onClick = {
                    textFieldPasswordVM.setIsVisibility(!isVisibility)
                }
            ){
                Icon(
                    imageVector = textFieldPasswordVM.getIconByVisibility(isVisibility),
                    contentDescription = "ChangeVisibility",
                )
            }
        },
        trailingIcon = {
            if (textFieldPasswordVM.isHasError(txt)) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = "Error",
                    tint = Red200
                )

            }
        }
    )

    CharacterCountLabel(
        text = txt,
        maxCount = maxCount
    )
    Text(
        text = textFieldPasswordVM.getTextError(
            txt = txt,
            isFinishFirstIteration = isFinishFirstIteration
        ),
        color = Red200,
        style = MaterialTheme.typography.labelMedium,
    )
}