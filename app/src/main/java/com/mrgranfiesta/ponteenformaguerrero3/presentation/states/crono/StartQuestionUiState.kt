package com.mrgranfiesta.ponteenformaguerrero3.presentation.states.crono

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto

sealed interface StartQuestionUiState {
    data object Loading : StartQuestionUiState
    data class ErrorUI(val throwable: Throwable) : StartQuestionUiState
    data class Success(val materialListDB: List<RowMaterialDto>) : StartQuestionUiState
}