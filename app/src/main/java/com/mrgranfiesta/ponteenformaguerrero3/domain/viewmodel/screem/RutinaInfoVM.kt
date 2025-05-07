package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CERRAR
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ERROR_EMPTY_RUTINA_STEP
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioNameAndPhotoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RutinaInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.step.RowStepInfoWithConfMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.FlowManager
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio.GetEjercicioNameAndPhotoByIdUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina.DeleteRutinaUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina.GetRutinaByIdUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.step.GetStepInfoByIdRutinaUseCase
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.rutina.info.RutinaInfoStepUiState
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.rutina.info.RutinaInfoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RutinaInfoVM @Inject constructor(
    private val getRutinaByIdUseCase : GetRutinaByIdUseCase,
    private val deleteRutinaUseCase : DeleteRutinaUseCase,
    private val getStepInfoByIdRutinaUseCase: GetStepInfoByIdRutinaUseCase,
    private val getEjercicioNameAndPhotoByIdUseCase : GetEjercicioNameAndPhotoByIdUseCase
) : ViewModel() {
    private val ioScope = CoroutineScope(Dispatchers.Main)
    var uiStateRutina: StateFlow<RutinaInfoUiState> = this.getFlowInitialRutina()

    var uiStateStep: StateFlow<RutinaInfoStepUiState> = this.getFlowInitialStep()

    private var status = true

    private val _selectDeleteDialog = MutableStateFlow(false)
    val selectDeleteDialog: StateFlow<Boolean> = _selectDeleteDialog
    fun onChangeSelectDeleteDialog(selectDeleteDialog: Boolean) {
        _selectDeleteDialog.value = selectDeleteDialog
    }

    private val _showMaterialDialog = MutableStateFlow(false)
    val showMaterialDialog: StateFlow<Boolean> = _showMaterialDialog
    fun onChangeShowMaterialDialog(showMaterialDialog: Boolean) {
        _showMaterialDialog.value = showMaterialDialog
    }

    private val _cursor = MutableStateFlow(0)
    val cursor: StateFlow<Int> = _cursor
    fun setCursor(cursor: Int) {
        _cursor.value = cursor
    }

    @Synchronized
    fun initData(idRutina: Long) {
        if (status) {
            uiStateRutina = this.getFlowInitDataRutina(idRutina)
            uiStateStep = this.getFlowInitDataStep(idRutina)
            status = false
        }
    }

    fun deleteRutina(rutinaOrigen: RutinaInfoDto) {
        deleteRutinaUseCase(rutinaOrigen)
    }

    fun showErrorEmptyStepRutina(snackbarHost: SnackbarHostState) {
        ioScope.launch {
            snackbarHost.showSnackbar(
                message = LABEL_ERROR_EMPTY_RUTINA_STEP,
                actionLabel = LABEL_CERRAR,
                duration = SnackbarDuration.Short
            )
        }
    }

    private fun getFlowInitialRutina(): StateFlow<RutinaInfoUiState> {
        return FlowManager.getFlowRutinaInfoDefault().map { RutinaInfoUiState.Success(it) }
            .catch { RutinaInfoUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = RutinaInfoUiState.Loading
            )
    }

    private fun getFlowInitDataRutina(id: Long): StateFlow<RutinaInfoUiState> {
        return getRutinaByIdUseCase(id).map { RutinaInfoUiState.Success(it) }
            .catch { RutinaInfoUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = RutinaInfoUiState.Loading
            )
    }

    private fun getFlowInitialStep(): StateFlow<RutinaInfoStepUiState> {
        return FlowManager.getEmptyList<RowStepInfoWithConfMaterialDto>()
            .map { RutinaInfoStepUiState.Success(it) }
            .catch { RutinaInfoStepUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = RutinaInfoStepUiState.Loading
            )
    }

    private fun getFlowInitDataStep(id: Long): StateFlow<RutinaInfoStepUiState> {
        return getStepInfoByIdRutinaUseCase(id).map { RutinaInfoStepUiState.Success(it) }
            .catch { RutinaInfoStepUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = RutinaInfoStepUiState.Loading
            )
    }

    fun getEjercicioNameAndPhoto(idEjercicio : Long): Flow<EjercicioNameAndPhotoDto> {
        return getEjercicioNameAndPhotoByIdUseCase(idEjercicio)
    }
}