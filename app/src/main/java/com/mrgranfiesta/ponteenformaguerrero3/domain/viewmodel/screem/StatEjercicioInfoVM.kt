package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.FlowManager
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.snapshot.confmaterial.GetMaterialWithConfByIdEjercicioUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.snapshot.ejercicio.GetEjercicioSnapshotByIdUseCase
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.stat.info.ejercicio.StatInfoEjercicioMaterialStatUiState
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.stat.info.ejercicio.StatInfoEjercicioStatUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StatEjercicioInfoVM @Inject constructor(
    private val getEjercicioSnapshotByIdUseCase : GetEjercicioSnapshotByIdUseCase,
    private val getMaterialWithConfByIdEjercicioUseCase : GetMaterialWithConfByIdEjercicioUseCase
) : ViewModel() {
    var uiStateStatEjercicio: StateFlow<StatInfoEjercicioStatUiState> = this.getFlowEjercicioStatInitial()
    var uiStateStatMaterial: StateFlow<StatInfoEjercicioMaterialStatUiState> = this.getFlowMaterialStatInitial()

    private var status = true

    @Synchronized
    fun initData(id: Long) {
        if (status) {
            uiStateStatEjercicio = this.getFlowEjercicioStatInitData(id)
            uiStateStatMaterial = this.getFlowMaterialStatInitData(id)
            status = false
        }
    }

    private fun getFlowEjercicioStatInitial(): StateFlow<StatInfoEjercicioStatUiState> {
        return FlowManager.getEjercicioSnapshotInfoDefault().map { StatInfoEjercicioStatUiState.Success(it) }
            .catch { StatInfoEjercicioStatUiState.ErrorInfoEjercicioStatUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = StatInfoEjercicioStatUiState.Loading
            )
    }

    private fun getFlowEjercicioStatInitData(id : Long): StateFlow<StatInfoEjercicioStatUiState> {
        return getEjercicioSnapshotByIdUseCase(id).map { StatInfoEjercicioStatUiState.Success(it) }
            .catch { StatInfoEjercicioStatUiState.ErrorInfoEjercicioStatUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = StatInfoEjercicioStatUiState.Loading
            )
    }

    private fun getFlowMaterialStatInitial(): StateFlow<StatInfoEjercicioMaterialStatUiState> {
        return FlowManager.getMaterialWithConfSnapshotDefault().map { StatInfoEjercicioMaterialStatUiState.Success(it) }
            .catch { StatInfoEjercicioMaterialStatUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = StatInfoEjercicioMaterialStatUiState.Loading
            )
    }

    private fun getFlowMaterialStatInitData(id : Long): StateFlow<StatInfoEjercicioMaterialStatUiState> {
        return getMaterialWithConfByIdEjercicioUseCase(id).map { StatInfoEjercicioMaterialStatUiState.Success(it) }
            .catch { StatInfoEjercicioMaterialStatUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = StatInfoEjercicioMaterialStatUiState.Loading
            )
    }

}