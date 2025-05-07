package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.compose.ui.state.ToggleableState
import androidx.lifecycle.ViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.ListManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CheckMusculoListItemVM : ViewModel() {
    private val musculoCheckList = ListManager.getMusculoListCheck()

    private val _isChecked = MutableStateFlow(false)
    val isChecked: StateFlow<Boolean> = _isChecked
    fun setIsChecked(isChecked: Boolean) {
        _isChecked.value = isChecked
    }

    fun onCheckText(
        isCheck: Boolean,
        musculo: Musculo,
        onChangeMusculoSet: (Set<Musculo>) -> Unit,
        musculosList: Set<Musculo>,
        changeGlobalCheck: (ToggleableState) -> Unit
    ) {
        this.setIsChecked(isCheck)
        if (isCheck) {
            onChangeMusculoSet(musculosList + musculo)
        } else {
            onChangeMusculoSet(musculosList - musculo)
        }

        if (musculosList.containsAll(musculoCheckList)) {
            changeGlobalCheck(ToggleableState.On)
        } else if (musculosList.contains(musculo)) {
            changeGlobalCheck(ToggleableState.Indeterminate)
        } else {
            changeGlobalCheck(ToggleableState.Off)
        }
    }
}