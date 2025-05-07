package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class CountUpVM : ViewModel() {
    private var isNotExecuteAction = true

    private var _forzeRecomposition = MutableStateFlow("")
    val forzeRecomposition: StateFlow<String> = _forzeRecomposition
    private fun forzeRecompositionByUUID(autoPlay: Boolean) {
        if (autoPlay) {
            _forzeRecomposition.value = UUID.randomUUID().toString()
        }
    }

    private var _acumulatorTime = MutableStateFlow(-1L)
    val acumulatorTime: StateFlow<Long> = _acumulatorTime
    fun setAcumulatorTime(acumulatorTime: Long) {
        _acumulatorTime.value = acumulatorTime
        if (_acumulatorTime.value < 0) {
            _acumulatorTime.value = 0
        }
    }

    private fun plusAcumulatorTime() {
        if ((acumulatorTime.value + 1000L) < 1_000_000_000_000) {
            _acumulatorTime.value += 1000L
        }
    }

    private var _isActive = MutableStateFlow(false)
    val isActive: StateFlow<Boolean> = _isActive
    fun setActive(isActive: Boolean) {
        _isActive.value = isActive
    }

    fun stopCountdown() {
        _isActive.value = false
    }

    fun playCountdown() {
        _isActive.value = true
    }

    fun isPause() = !_isActive.value

    fun iterationTime(
        actionByTime: () -> Unit,
        timeAction: Long,
        launcher: CoroutineScope
    ) {
        launcher.launch {
            if (_isActive.value) {
                delay(1000L)
                plusAcumulatorTime()
            }
            if (isNotExecuteAction && timeAction <= _acumulatorTime.value) {
                actionByTime()
                isNotExecuteAction = false
            }
        }
    }

    fun initData(
        initMiliseconds: Long,
        autoPlay: Boolean
    ) {
        if (_acumulatorTime.value == -1L) {
            this.setAcumulatorTime(initMiliseconds)
            this.setActive(autoPlay)
            forzeRecompositionByUUID(autoPlay)
        }
    }
}