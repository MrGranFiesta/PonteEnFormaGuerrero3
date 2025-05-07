package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.lifecycle.ViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ERROR_CHARACTER_FIELD
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NOT_EMPTY
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TextFieldSecuredVM : ViewModel() {
    private val _isFinishFirstIteration = MutableStateFlow(false)
    val isFinishFirstIteration : StateFlow<Boolean> = _isFinishFirstIteration
    fun onCompleteFirstIteration() {
        _isFinishFirstIteration.value = true
    }

    fun isHasError(
        txt : String,
        mask: Regex,
        isActivePermitIsEmpty : Boolean
    ) : Boolean {
        if (_isFinishFirstIteration.value == false) return false

        val isCheckMask = { txt.isNotEmpty() && !txt.contains(mask) }
        return isCheckMask() || isActivePermitIsEmpty && txt.isEmpty()
    }

    fun getTextError(
        txt : String,
        mask: Regex,
        isActivePermitIsEmpty : Boolean,
        isFinishFirstIteration : Boolean
    ) : String {
        val isCheckMask = { txt.isNotEmpty() && !txt.contains(mask) }
        return when {
            !isFinishFirstIteration -> ""
            isActivePermitIsEmpty && txt.isEmpty() -> LABEL_NOT_EMPTY
            isCheckMask() -> LABEL_ERROR_CHARACTER_FIELD
            else -> ""
        }
    }

    fun resetFinishFirstIteration() {
        _isFinishFirstIteration.value = false
    }
}