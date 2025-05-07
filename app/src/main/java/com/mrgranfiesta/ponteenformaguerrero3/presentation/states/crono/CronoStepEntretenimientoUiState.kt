package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.crono

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto

sealed interface CronoStepEntretenimientoUiState {
    data object Loading : CronoStepEntretenimientoUiState
    data class ErrorUI(val throwable: Throwable) : CronoStepEntretenimientoUiState
    data class Success(val stepDB: List<StepEntrenamientoDto>) : CronoStepEntretenimientoUiState
}