package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.FlowManager
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.GetMaterialByIdRutinaUseCase
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.crono.StartQuestionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StartRutinaQuestionVM @Inject constructor(
    private val getMaterialByIdRutinaUseCase : GetMaterialByIdRutinaUseCase
) : ViewModel() {
    var uiState: StateFlow<StartQuestionUiState> = this.getFlowInitial()

    private var status = true
    var isEnableOneClick = true

    @Synchronized
    fun startRutina(
        navController: NavController,
        idRutina: Long
    ) {
        if (isEnableOneClick) {
            isEnableOneClick = false
            navController.navigate(AppScreem.CronoScreem.createRoute(idRutina))
        }
    }

    @Synchronized
    fun initData(id: Long) {
        if (status) {
            uiState = this.getFlowInitData(id)
            status = false
        }
    }

    private fun getFlowInitial(): StateFlow<StartQuestionUiState> {
        return FlowManager.getEmptyList<RowMaterialDto>()
            .map { StartQuestionUiState.Success(it) }
            .catch { StartQuestionUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = StartQuestionUiState.Loading
            )
    }

    private fun getFlowInitData(idRutina: Long): StateFlow<StartQuestionUiState> {
        return getMaterialByIdRutinaUseCase(idRutina)
            .map { StartQuestionUiState.Success(it) }
            .catch { StartQuestionUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = StartQuestionUiState.Loading
            )
    }
}