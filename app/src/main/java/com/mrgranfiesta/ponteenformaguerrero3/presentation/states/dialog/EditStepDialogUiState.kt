package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.dialog

import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.MaterialBean

sealed interface EditStepDialogUiState {
    data object Loading : EditStepDialogUiState
    data class ErrorUI(val throwable: Throwable) : EditStepDialogUiState
    data class Success(val ejercicioDB: List<MaterialBean>) : EditStepDialogUiState
}