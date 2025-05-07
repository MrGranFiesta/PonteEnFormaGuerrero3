package com.mrgranfiesta.ponteenformaguerrero3.presentation.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.RegexExpresion
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TextFieldSecuredVM
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Red200

@Suppress("kotlin:S107")
@Composable
fun TextFieldSecured(
    modifier: Modifier,
    txt: String,
    onValueChange: (String) -> Unit,
    label: String,
    maxLines: Int,
    maxCount: Int,
    isActiveCheckIsEmpty: Boolean,
    mask: Regex = RegexExpresion.SECURED_SQL,
    textFieldSecuredVM: TextFieldSecuredVM = hiltViewModel()
) {
    val isFinishFirstIteration by textFieldSecuredVM.isFinishFirstIteration.collectAsState()

    OutlinedTextField(
        value = txt,
        onValueChange = {
            onValueChange(it)
            textFieldSecuredVM.onCompleteFirstIteration()
        },
        modifier = modifier,
        label = { Text(text = label) },
        maxLines = maxLines,
        trailingIcon = {
            if (
                textFieldSecuredVM.isHasError(
                    mask = mask,
                    txt = txt,
                    isActivePermitIsEmpty = isActiveCheckIsEmpty
                )
            ) {
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
        text = textFieldSecuredVM.getTextError(
            mask = mask,
            txt = txt,
            isActivePermitIsEmpty = isActiveCheckIsEmpty,
            isFinishFirstIteration = isFinishFirstIteration
        ),
        color = Red200,
        style = MaterialTheme.typography.labelMedium,
    )
}