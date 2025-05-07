package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.crono

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.MaterialEntrenamientoDto

sealed interface CronoMaterialEntretenimientoUiState {
    data object Loading : CronoMaterialEntretenimientoUiState
    data class ErrorUI(val throwable: Throwable) : CronoMaterialEntretenimientoUiState
    data class Success(val materialDB: List<MaterialEntrenamientoDto>) :
        CronoMaterialEntretenimientoUiState
}