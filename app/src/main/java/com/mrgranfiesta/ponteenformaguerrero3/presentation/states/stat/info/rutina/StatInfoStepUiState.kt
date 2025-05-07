package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.stat.info.rutina

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot.StepSnapshotInfoDto

sealed interface StatInfoStepUiState {
    data object Loading : StatInfoStepUiState
    data class ErrorUI(val throwable: Throwable) : StatInfoStepUiState
    data class Success(val stepListDB: List<StepSnapshotInfoDto>) : StatInfoStepUiState
}