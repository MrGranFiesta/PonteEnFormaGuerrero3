package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.ejercicio.info

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioInfoDto

sealed interface EjercicioInfoUiState {
    data object Loading : EjercicioInfoUiState
    data class ErrorUI(val throwable: Throwable) : EjercicioInfoUiState
    data class Success(val ejercicioDB: EjercicioInfoDto) : EjercicioInfoUiState
}