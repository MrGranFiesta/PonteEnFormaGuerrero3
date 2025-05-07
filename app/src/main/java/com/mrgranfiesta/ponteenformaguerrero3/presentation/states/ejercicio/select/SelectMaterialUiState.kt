package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.ejercicio.select

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto


sealed interface SelectMaterialUiState {
    data object Loading : SelectMaterialUiState
    data class ErrorUI(val throwable: Throwable) : SelectMaterialUiState
    data class Success(val materialListDB: List<RowMaterialDto>) : SelectMaterialUiState
}