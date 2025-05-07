package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class CountdownVM : ViewModel() {
    companion object {
        fun getCountdown(): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    CountdownVM()
                }
            }
        }
    }

    private var _forzeRecomposition = MutableStateFlow("")
    val forzeRecomposition: StateFlow<String> = _forzeRecomposition
    private fun forzeRecompositionByUUID(autoPlay: Boolean) {
        if (autoPlay) {
            _forzeRecomposition.value = UUID.randomUUID().toString()
        }
    }

    private var _endTime = MutableStateFlow(-1L)
    val endTime: StateFlow<Long> = _endTime
    fun setEndTime(endTime: Long) {
        _endTime.value = endTime
        if (_endTime.value < 0) {
            _endTime.value = 0
        }
    }

    private fun minusEndTime() {
        if ((endTime.value - 1000L) >= 0) {
            _endTime.value -= 1000L
        } else {
            _endTime.value = 0
        }
    }

    fun minusEndTimeMin(minusTime: Long) {
        if ((endTime.value - minusTime) > 0) {
            _endTime.value -= minusTime
        } else {
            _endTime.value = 1_000
        }
    }

    fun plusEndTime(plusTime: Long) {
        if ((endTime.value + plusTime) < 1_000_000_000_000) {
            _endTime.value += plusTime
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

    fun iterationTime(
        actionFinish: () -> Unit,
        launcher: CoroutineScope
    ) {
        launcher.launch {
            if (_endTime.value > 0 && _isActive.value) {
                delay(1000L)
                minusEndTime()
            } else if (_endTime.value == 0L) {
                _endTime.value = -1L
                actionFinish()
            }
        }
    }

    fun initData(
        initMiliseconds: Long,
        autoPlay: Boolean
    ) {
        if (_endTime.value == -1L) {
            this.setEndTime(initMiliseconds)
            this.setActive(autoPlay)
            forzeRecompositionByUUID(autoPlay)
        }
    }
}