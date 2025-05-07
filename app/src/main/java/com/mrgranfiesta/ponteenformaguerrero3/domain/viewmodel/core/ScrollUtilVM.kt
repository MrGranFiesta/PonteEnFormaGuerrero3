package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ScrollUtilVM : ViewModel() {
    private val _isScrollDown = MutableStateFlow(false)
    val isScrollDown: StateFlow<Boolean> = _isScrollDown
    fun setScrollDown(isScrollDown: Boolean) {
        _isScrollDown.value = isScrollDown
    }
}