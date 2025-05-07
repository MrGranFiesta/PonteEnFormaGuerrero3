package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ERROR_CHARACTER_FIELD
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NOT_EMPTY
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.RegexExpresion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TextFieldPasswordVM : ViewModel() {
    private val _isFinishFirstIteration = MutableStateFlow(false)
    val isFinishFirstIteration : StateFlow<Boolean> = _isFinishFirstIteration
    fun onCompleteFirstIteration() {
        _isFinishFirstIteration.value = true
    }

    fun isHasError(txt: String): Boolean {
        if (_isFinishFirstIteration.value == false) return false
        val isCheckMask = { txt.isNotEmpty() && txt.contains(RegexExpresion.CHAT_NOT_PERMIT_PASSWORD) }
        return isCheckMask() || txt.isEmpty()
    }

    fun getTextError(
        txt: String,
        isFinishFirstIteration : Boolean
    ): String {
        val isCheckMask = { txt.isNotEmpty() && txt.contains(RegexExpresion.CHAT_NOT_PERMIT_PASSWORD) }

        return when {
            !isFinishFirstIteration -> ""
            txt.isEmpty() -> LABEL_NOT_EMPTY
            isCheckMask() -> LABEL_ERROR_CHARACTER_FIELD
            else -> ""
        }
    }

    private val _isVisibility = MutableStateFlow(false)
    val isVisibility: StateFlow<Boolean> = _isVisibility
    fun setIsVisibility(isVisibility: Boolean) {
        _isVisibility.value = isVisibility
    }

    fun getKeyboardPassword() = KeyboardOptions(
        keyboardType = KeyboardType.Password
    )

    fun getIconByVisibility(isVisibility: Boolean) = if (isVisibility) {
        Icons.Filled.VisibilityOff
    } else {
        Icons.Filled.Visibility
    }

    fun getTextByVisibility(isVisibility: Boolean) = if (isVisibility) {
        "Ocultar"
    } else {
        "Mostrar"
    }

    fun getVisualTransformation(isVisibility: Boolean) = if (isVisibility) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }

    fun resetFinishFirstIteration() {
        _isFinishFirstIteration.value = false
    }
}