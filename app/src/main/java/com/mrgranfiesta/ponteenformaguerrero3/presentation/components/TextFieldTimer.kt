package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_MINUTOS
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SEGUNDOS
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TextFieldTimerVM

@Composable
fun TextFieldTimer(
    time: Int,
    modifier: Modifier = Modifier,
    changeTime: (Int) -> Unit,
    textFieldTimerVM: TextFieldTimerVM = hiltViewModel()
) {
    textFieldTimerVM.initData(time)
    val minTxt by textFieldTimerVM.minTxt.collectAsState()
    val segTxt by textFieldTimerVM.segTxt.collectAsState()
    val focusRequesterMin = FocusRequester()
    val focusRequesterSeg = FocusRequester()
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        OutlinedTextField(
            value = minTxt,
            onValueChange = {
                textFieldTimerVM.onChangeMin(it)
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequesterMin)
                .onFocusChanged {
                    textFieldTimerVM.onChangeFocusMin(changeTime)
                },
            label = {
                Text(LABEL_MINUTOS)
            },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {
                            textFieldTimerVM.onClickPlusMin(changeTime)
                        }
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Remove,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {
                            textFieldTimerVM.onClickMinusMin(changeTime)
                        }
                )
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        OutlinedTextField(
            value = segTxt,
            onValueChange = {
                textFieldTimerVM.onChangeSeg(it)
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequesterSeg)
                .onFocusChanged {
                    textFieldTimerVM.onChangeFocusSeg(changeTime)
                },
            label = {
                Text(LABEL_SEGUNDOS)
            },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {
                            textFieldTimerVM.onClickPlusSeg(changeTime)
                        }
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Remove,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {
                            textFieldTimerVM.onClickMinusSeg(changeTime)
                        }
                )
            }
        )
    }
}