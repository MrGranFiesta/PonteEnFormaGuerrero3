package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.ejercicio.info

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto

sealed interface EjercicioInfoMaterialUiState {
    data object Loading : EjercicioInfoMaterialUiState
    data class ErrorUI(val throwable: Throwable) : EjercicioInfoMaterialUiState
    data class Success(val materialDB: List<RowMaterialDto>) : EjercicioInfoMaterialUiState
}