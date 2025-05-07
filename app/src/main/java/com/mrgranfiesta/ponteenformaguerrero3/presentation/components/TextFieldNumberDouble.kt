package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TextFieldNumberDoubleVM


@Composable
fun TextFieldNumberDouble(
    cont: Double,
    changeContNumber: (Double) -> Unit,
    amount: Double = 0.25,
    labelTxt: String = "",
    textFieldNumberDoubleVM: TextFieldNumberDoubleVM = hiltViewModel()
) {
    val cantidadTxt by textFieldNumberDoubleVM.cantidadTxt.collectAsState()
    textFieldNumberDoubleVM.initData(cont)
    val focusRequester = FocusRequester()

    OutlinedTextField(
        value = cantidadTxt,
        onValueChange = {
            textFieldNumberDoubleVM.onChangeTextField(it)
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged {
                textFieldNumberDoubleVM.setHasFocus(it.hasFocus)
                textFieldNumberDoubleVM.onChangeFocus(changeContNumber)
            },
        label = {
            Text(labelTxt)
        },
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "",
                modifier = Modifier
                    .clickable {
                        textFieldNumberDoubleVM.onClickIconPlus(
                            changeContNumber = changeContNumber,
                            amount = amount
                        )
                    }
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Remove,
                contentDescription = "",
                modifier = Modifier
                    .clickable {
                        textFieldNumberDoubleVM.onClickIconLess(
                            changeContNumber = changeContNumber,
                            amount = amount
                        )
                    }
            )
        }
    )
}