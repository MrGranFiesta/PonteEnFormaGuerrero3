package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.crono

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.EntrenamientoDto

sealed interface CronoEntretenimientoUiState {
    data object Loading : CronoEntretenimientoUiState
    data class ErrorUI(val throwable: Throwable) : CronoEntretenimientoUiState
    data class Success(val entrenamientoDB: EntrenamientoDto) : CronoEntretenimientoUiState
}