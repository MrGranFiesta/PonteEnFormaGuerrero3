package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.ejercicio.form

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioInfoDto

sealed interface EjercicioFormUiState {
    data object Loading : EjercicioFormUiState
    data class ErrorUI(val throwable: Throwable) : EjercicioFormUiState
    data class Success(val ejercicioDB: EjercicioInfoDto) : EjercicioFormUiState
}