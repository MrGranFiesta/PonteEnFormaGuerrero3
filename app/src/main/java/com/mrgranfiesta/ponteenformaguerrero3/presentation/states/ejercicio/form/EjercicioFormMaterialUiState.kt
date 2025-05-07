package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.ejercicio.form

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto

sealed interface EjercicioFormMaterialUiState {
    data object Loading : EjercicioFormMaterialUiState
    data class ErrorUI(val throwable: Throwable) : EjercicioFormMaterialUiState
    data class Success(val materialDB: List<RowMaterialDto>) : EjercicioFormMaterialUiState
}