package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.compose.ui.state.ToggleableState
import androidx.lifecycle.ViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.ListManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CheckMusculoListVM : ViewModel() {
    val musculoCheckList = ListManager.getMusculoListCheck()

    private val _parentCheckState = MutableStateFlow(ToggleableState.Off)
    val parentCheckState: StateFlow<ToggleableState> = _parentCheckState
    fun setParentCheckState(parentCheckState: ToggleableState) {
        _parentCheckState.value = parentCheckState
    }


    fun initData(musculoSetTemp: Set<Musculo>) {
        if (musculoSetTemp.containsAll(musculoCheckList)) {
            setParentCheckState(ToggleableState.On)
        } else if (musculoSetTemp.isEmpty()) {
            setParentCheckState(ToggleableState.Off)
        } else {
            setParentCheckState(ToggleableState.Indeterminate)
        }
    }

    fun onCheckTriState(
        onChangeMusculoSet: (Set<Musculo>) -> Unit
    ) {
        if (_parentCheckState.value == ToggleableState.Off) {
            onChangeMusculoSet(musculoCheckList.toSet())
            setParentCheckState(ToggleableState.On)
        } else if (_parentCheckState.value == ToggleableState.On || _parentCheckState.value == ToggleableState.Indeterminate) {
            onChangeMusculoSet(setOf())
            setParentCheckState(ToggleableState.Off)
        }
    }
}