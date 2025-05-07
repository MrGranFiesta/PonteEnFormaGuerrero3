package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.ejercicio.list

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.RowEjercicioDto

sealed interface EjercicioListUiState {
    data object Loading : EjercicioListUiState
    data class ErrorUI(val throwable: Throwable) : EjercicioListUiState
    data class Success(val ejercicioListDB: List<RowEjercicioDto>) : EjercicioListUiState
}