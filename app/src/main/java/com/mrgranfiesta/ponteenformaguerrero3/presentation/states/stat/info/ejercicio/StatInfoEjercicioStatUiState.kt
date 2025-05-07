package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.stat.info.ejercicio

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot.EjercicioSnapshotInfoDto

sealed interface StatInfoEjercicioStatUiState {
    data object Loading : StatInfoEjercicioStatUiState
    data class ErrorInfoEjercicioStatUI(val throwable: Throwable) : StatInfoEjercicioStatUiState
    data class Success(val ejercicioDB: EjercicioSnapshotInfoDto) : StatInfoEjercicioStatUiState
}