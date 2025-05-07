package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.dialog

import androidx.lifecycle.ViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SelectMusculoDialogVM : ViewModel() {
    private var status = true

    private val _musculoSetTemp = MutableStateFlow(setOf<Musculo>())
    val musculoSet: StateFlow<Set<Musculo>> = _musculoSetTemp
    fun setMusculoSetTemp(set: Set<Musculo>) {
        _musculoSetTemp.value = set.toSet()
    }

    @Synchronized
    fun initData(musculoSet: Set<Musculo>) {
        if (status) {
            _musculoSetTemp.value = musculoSet
            status = false
        }
    }

    fun onAcept(
        onChangeMusculoSet: (Set<Musculo>) -> Unit = {},
        dismissDialog: () -> Unit
    ) {
        onChangeMusculoSet(_musculoSetTemp.value)
        status = true
        dismissDialog()
    }

    fun onDismiss(dismissDialog: () -> Unit) {
        status = true
        dismissDialog()
    }
}