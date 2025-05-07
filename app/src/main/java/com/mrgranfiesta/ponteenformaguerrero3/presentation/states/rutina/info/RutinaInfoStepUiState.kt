package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.rutina.info

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.step.RowStepInfoWithConfMaterialDto

sealed interface RutinaInfoStepUiState {
    data object Loading : RutinaInfoStepUiState
    data class ErrorUI(val throwable: Throwable) : RutinaInfoStepUiState
    data class Success(val stepListDB: List<RowStepInfoWithConfMaterialDto>) : RutinaInfoStepUiState
}