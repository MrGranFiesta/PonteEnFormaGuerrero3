package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.dialog

import androidx.lifecycle.ViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FilterRutinaDialogVM : ViewModel() {
    private var status = true
    private val _musculoSetTemp = MutableStateFlow(setOf<Musculo>())
    val musculoSetTemp: StateFlow<Set<Musculo>> = _musculoSetTemp
    fun setMusculoSetTemp(set: Set<Musculo>) {
        _musculoSetTemp.value = set.toSet()
    }

    private val _nivelSetTemp = MutableStateFlow(setOf<Nivel>())
    val nivelSetTemp: StateFlow<Set<Nivel>> = _nivelSetTemp
    fun setNivelSetTemp(set: Set<Nivel>) {
        _nivelSetTemp.value = set.toSet()
    }

    @Synchronized
    fun initData(
        musculoSet: Set<Musculo>,
        nivelSet: Set<Nivel>
    ) {
        if (status) {
            _musculoSetTemp.value = musculoSet
            _nivelSetTemp.value = nivelSet
            status = false
        }
    }

    fun onAcept(
        onChangeMusculoSet: (Set<Musculo>) -> Unit,
        onChangeNivelSet: (Set<Nivel>) -> Unit,
        dismissDialog: () -> Unit,
    ) {
        onChangeMusculoSet(_musculoSetTemp.value)
        onChangeNivelSet(_nivelSetTemp.value)
        status = true
        dismissDialog()
    }

    fun onDismissDialog(
        dismissDialog: () -> Unit,
    ) {
        status = true
        dismissDialog()
    }
}