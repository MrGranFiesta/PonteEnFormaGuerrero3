package com.mrgranfiesta.ponteenformaguerrero3.domain.core.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PopBackEffectVM : ViewModel() {
    private var _isBack: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var isBack: StateFlow<Boolean> = _isBack
    fun setBack(isBack: Boolean) {
        _isBack.value = isBack
    }

    private var _endTimeDelay: MutableStateFlow<Long> = MutableStateFlow(0L)
    var endTimeDelay: StateFlow<Long> = _endTimeDelay

    fun programEndTimeDelay(milliseconds: Long) {
        _endTimeDelay.value = System.currentTimeMillis() + milliseconds
    }
}