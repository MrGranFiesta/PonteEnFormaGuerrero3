package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import androidx.lifecycle.ViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog.StepEditDialogDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.ObjectFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SelectEjercicioListVM : ViewModel() {
    private val _isCreateStepDialog = MutableStateFlow(false)
    val isCreateStepDialog: StateFlow<Boolean> = _isCreateStepDialog
    fun onChangeIsCreateStepDialog(isCreateStepDialog: Boolean) {
        _isCreateStepDialog.value = isCreateStepDialog
    }

    private val _idEjercicioSelect = MutableStateFlow(0L)
    val idEjercicioSelect: StateFlow<Long> = _idEjercicioSelect
    fun setIdEjercicioSelect(idEjercicioSelect: Long) {
        _idEjercicioSelect.value = idEjercicioSelect
    }

    fun setIdDefaultStepEditDialogDto(
        idEjercicio: Long,
        step: StepEditDialogDto = ObjectFactory.getDefaultStepEditDialogDto()
    ): StepEditDialogDto {
        step.idEjercicio = idEjercicio
        return step
    }
}