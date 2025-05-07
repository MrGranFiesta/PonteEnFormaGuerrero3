package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem

import androidx.lifecycle.ViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.StateSerie
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.CountUpVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WarpUpVM : ViewModel() {
    private var _state = MutableStateFlow(StateSerie.ACTIVO_PLAY)
    val state: StateFlow<StateSerie> = _state
    private fun setState(state: StateSerie) {
        _state.value = state
    }

    fun changeReverseState(countUp: CountUpVM) {
        this.setState(StateSerie.changeReverseState(_state.value))
        countUp.setActive(StateSerie.isPlay(_state.value))
    }

    var isUserPauseManualy : Boolean = false
    fun setIsUserPauseManualy(isUserPauseManualy : Boolean){
        this.isUserPauseManualy = isUserPauseManualy
    }

}