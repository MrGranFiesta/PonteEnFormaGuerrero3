package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.dialog

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TextFieldDialogVM : ViewModel() {
    private val _textDialog = MutableStateFlow("")
    val textDialog: StateFlow<String> = _textDialog
    fun setTextDialog(text: String) {
        _textDialog.value = text
    }

    private var status = true
    fun resetStatus() {
        status = true
    }

    @Synchronized
    fun initData(text: String) {
        if (status) {
            _textDialog.value = text
            status = false
        }
    }
}