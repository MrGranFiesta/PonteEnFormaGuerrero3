package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.compose.ui.state.ToggleableState
import androidx.lifecycle.ViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.ListManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CheckNivelListVM : ViewModel() {
    val nivelList = ListManager.getNivelList()

    private val _parentCheckState = MutableStateFlow(ToggleableState.Off)
    val parentCheckState: StateFlow<ToggleableState> = _parentCheckState
    fun setParentCheckState(parentCheckState: ToggleableState) {
        _parentCheckState.value = parentCheckState
    }

    fun initData(nivelSetTemp: Set<Nivel>) {
        if (nivelSetTemp.containsAll(nivelList)) {
            setParentCheckState(ToggleableState.On)
        } else if (nivelSetTemp.isEmpty()) {
            setParentCheckState(ToggleableState.Off)
        } else {
            setParentCheckState(ToggleableState.Indeterminate)
        }
    }

    fun onCheckTriState(
        onChangeNivelSet: (Set<Nivel>) -> Unit
    ) {
        if (_parentCheckState.value == ToggleableState.Off) {
            onChangeNivelSet(nivelList.toSet())
            setParentCheckState(ToggleableState.On)
        } else if (_parentCheckState.value == ToggleableState.On || _parentCheckState.value == ToggleableState.Indeterminate) {
            onChangeNivelSet(setOf())
            setParentCheckState(ToggleableState.Off)
        }
    }
}