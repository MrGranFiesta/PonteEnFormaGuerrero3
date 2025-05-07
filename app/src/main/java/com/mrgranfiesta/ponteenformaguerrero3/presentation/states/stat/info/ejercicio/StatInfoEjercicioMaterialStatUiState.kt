package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.stat.info.ejercicio

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot.MaterialWithConfSnapshotDto

sealed interface StatInfoEjercicioMaterialStatUiState {
    data object Loading : StatInfoEjercicioMaterialStatUiState
    data class ErrorUI(val throwable: Throwable) : StatInfoEjercicioMaterialStatUiState
    data class Success(val materialListDB: List<MaterialWithConfSnapshotDto>) : StatInfoEjercicioMaterialStatUiState
}