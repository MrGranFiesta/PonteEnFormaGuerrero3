package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.compose.ui.state.ToggleableState
import androidx.lifecycle.ViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.ListManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CheckNivelListItemVM : ViewModel() {
    private val nivelList = ListManager.getNivelList()

    private val _isChecked = MutableStateFlow(false)
    val isChecked: StateFlow<Boolean> = _isChecked
    fun setIsChecked(isChecked: Boolean) {
        _isChecked.value = isChecked
    }

    fun onCheckText(
        isCheck: Boolean,
        nivel: Nivel,
        onChangeNivelSet: (Set<Nivel>) -> Unit,
        nivelList: Set<Nivel>,
        changeGlobalCheck: (ToggleableState) -> Unit
    ) {
        this.setIsChecked(isCheck)
        if (isCheck) {
            onChangeNivelSet(nivelList + nivel)
        } else {
            onChangeNivelSet(nivelList - nivel)
        }

        if (nivelList.containsAll(this.nivelList)) {
            changeGlobalCheck(ToggleableState.On)
        } else if (nivelList.contains(nivel)) {
            changeGlobalCheck(ToggleableState.Indeterminate)
        } else {
            changeGlobalCheck(ToggleableState.Off)
        }
    }
}