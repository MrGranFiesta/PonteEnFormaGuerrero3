package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TextFieldAnswerVM

@Composable
fun TextFieldAnswer(
    modifier: Modifier,
    answer: AnswerState,
    changeAnswer: (AnswerState) -> Unit,
    label: String,
    textFieldAnswer: TextFieldAnswerVM = hiltViewModel()
) {
    textFieldAnswer.initData(answer = answer)
    OutlinedTextField(
        value = AnswerState.getOptionTxt(answer),
        readOnly = true,
        onValueChange = { },
        modifier = modifier,
        label = { Text(text = label) },
        leadingIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Siguiente",
                modifier = Modifier.clickable {
                    textFieldAnswer.changeLeft(changeAnswer)
                }
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "anterior",
                modifier = Modifier.clickable {
                    textFieldAnswer.changeRight(changeAnswer)
                }
            )
        },
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
        maxLines = 1
    )
}