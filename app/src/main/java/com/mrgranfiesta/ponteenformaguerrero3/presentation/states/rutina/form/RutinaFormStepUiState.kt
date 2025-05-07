package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.rutina.form

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.step.RowStepFormWithConfMaterialDto

sealed interface RutinaFormStepUiState {
    data object Loading : RutinaFormStepUiState
    data class ErrorUI(val throwable: Throwable) : RutinaFormStepUiState
    data class Success(val stepListDB: List<RowStepFormWithConfMaterialDto>) : RutinaFormStepUiState
}