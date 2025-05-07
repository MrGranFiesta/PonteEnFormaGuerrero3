package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RowSelectMaterialVM : ViewModel() {
    private var status = true
    private val _isChecked = MutableStateFlow(false)
    val isChecked: StateFlow<Boolean> = _isChecked
    private fun setChecked(isChecked: Boolean) {
        _isChecked.value = isChecked
    }

    fun onClickItem(onClickItem: (Boolean) -> Unit) {
        this.setChecked(!_isChecked.value)
        onClickItem(_isChecked.value)
    }

    @Synchronized
    fun initData(isChecked: Boolean) {
        if (status) {
            this.setChecked(isChecked)
            status = false
        }
    }
}