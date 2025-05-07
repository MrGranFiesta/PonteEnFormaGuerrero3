package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.StatBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.StateBatchCrono
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.EntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.MaterialEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.FlowManager
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.AnswerStateLoading
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.crono.GetEntrenamientoUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.crono.GetMaterialEntrenamientoUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.crono.GetStepEntrenamientoUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.crono.RegisterDataUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.stat.CreateStatUseCase
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.crono.CronoEntretenimientoUiState
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.crono.CronoMaterialEntretenimientoUiState
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.crono.CronoStepEntretenimientoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CronoVM @Inject constructor(
    private val getEntrenamientoUseCase: GetEntrenamientoUseCase,
    private val getStepEntrenamientoUseCase: GetStepEntrenamientoUseCase,
    private val getMaterialEntrenamientoUseCase: GetMaterialEntrenamientoUseCase,
    private val registerDataUseCase: RegisterDataUseCase,
    private val createStatUseCase: CreateStatUseCase
) : ViewModel() {
    var uiStateEntrenamiento: StateFlow<CronoEntretenimientoUiState> =
        this.getFlowInitialEntrenamiento()
    var uiStateStep: StateFlow<CronoStepEntretenimientoUiState> = this.getFlowInitialStep()
    var uiStateMaterial: StateFlow<CronoMaterialEntretenimientoUiState> =
        this.getFlowInitialMaterial()

    private val keyRutConfMV = "rut${UUID.randomUUID()}"
    private val keyMovConfMV = "movArt${UUID.randomUUID()}"
    private val keyEstConfMV = "est${UUID.randomUUID()}"

    private var status = true

    private val _stateBatchCrono = MutableStateFlow(StateBatchCrono.CALENTAMIENTO_QUESTION)
    val stateBatchCrono: StateFlow<StateBatchCrono> = _stateBatchCrono
    fun setStateBatchCrono(stateBatchCrono: StateBatchCrono) {
        this._stateBatchCrono.value = stateBatchCrono
    }

    private val _isShowConfMaterialDialog = MutableStateFlow(false)
    val isShowConfMaterialDialog: StateFlow<Boolean> = _isShowConfMaterialDialog
    fun setIsShowConfMaterialDialog(isShowConfMaterialList: Boolean) {
        this._isShowConfMaterialDialog.value = isShowConfMaterialList
    }

    private val _isShowBackDialog = MutableStateFlow(false)
    val isShowBackDialog: StateFlow<Boolean> = _isShowBackDialog
    fun setIsShowBackDialog(isShowBackDialog: Boolean) {
        this._isShowBackDialog.value = isShowBackDialog
    }

    val initRutinaTime: LocalDateTime = LocalDateTime.now()
    var calentamientoDone: Boolean = false
    var movArticularDone: Boolean = false
    var estiramientoDone: Boolean = false

    var idStatsOnPause: Long = 0

    var extrasOnPause : () -> Unit = {}
    fun updateExtrasOnPause(
        extras: () -> Unit = {}
    ) {
        extrasOnPause = extras
    }

    var extrasOnResume : () -> Unit = {}
    fun updateExtrasOnResume(
        extras: () -> Unit = {}
    ) {
        extrasOnResume = extras
    }

    @Synchronized
    fun initData(idRutina: Long) {
        if (status) {
            uiStateEntrenamiento = this.getFlowInitialDataEntrenamiento(idRutina)
            uiStateStep = this.getFlowInitialDataStep(idRutina)
            uiStateMaterial = this.getFlowInitialDataMaterial(idRutina)
            status = false
        }
    }

    @Synchronized
    fun registerData(
        entrenamientoDB: EntrenamientoDto,
        stepDB: List<StepEntrenamientoDto>,
        materialDB: List<MaterialEntrenamientoDto>
    ) {
        val idRutinaSnapshot = registerDataUseCase(
            entrenamientoDB = entrenamientoDB,
            stepDB = stepDB,
            materialDB = materialDB
        )
        CurrentUser.user?.let { user ->
            idStatsOnPause = createStatUseCase(
                stat = StatBean(
                    idStatHistory = idStatsOnPause,
                    idRutinaSnapshot = idRutinaSnapshot,
                    idRutina = entrenamientoDB.idRutina,
                    idUser = user.idUser,
                    dateInit = initRutinaTime,
                    dateEnd = LocalDateTime.now(),
                    isCalentamientoDone = calentamientoDone,
                    isMovArticularDone = movArticularDone,
                    isEstiramientoDone = estiramientoDone,
                    isCompleted = this.isEndRutina()
                )
            )
        }
    }

    fun getTitleTopBar(
        stateBatchCrono: StateBatchCrono,
        entrenamientoDB: EntrenamientoDto,
    ) = when (stateBatchCrono) {
        StateBatchCrono.CALENTAMIENTO_QUESTION -> {
            when (entrenamientoDB.calentamiento) {
                AnswerStateLoading.YES -> "Calentamiento"
                AnswerStateLoading.NO -> ""
                AnswerStateLoading.ASK_LATER -> "¿Calentamiento?"
                AnswerStateLoading.ON_LOADING -> ""
            }
        }

        StateBatchCrono.CALENTAMIENTO -> "Calentamiento"
        StateBatchCrono.MOVILIDAD_ARTICULAR_QUESTION -> {
            when (entrenamientoDB.movArticular) {
                AnswerState.YES -> "Movilidad articular"
                AnswerState.NO -> "Rutina"
                AnswerState.ASK_LATER -> "¿Movilidad articular?"
            }
        }

        StateBatchCrono.MOVILIDAD_ARTICULAR -> "Movilidad articular"
        StateBatchCrono.STEPS -> "Rutina"
        StateBatchCrono.ESTIRAMIENTO_QUESTIONS -> {
            when (entrenamientoDB.estiramiento) {
                AnswerState.YES -> "Estiramiento"
                AnswerState.NO -> "Fin"
                AnswerState.ASK_LATER -> "¿Estiramiento?"
            }
        }

        StateBatchCrono.ESTIRAMIENTOS -> "Estiramiento"
        StateBatchCrono.END -> "Fin"
    }

    fun getConfVMName(
        stateBatchCrono: StateBatchCrono,
        entrenamientoDB: EntrenamientoDto
    ) = when (stateBatchCrono) {
        StateBatchCrono.CALENTAMIENTO_QUESTION, StateBatchCrono.CALENTAMIENTO,
        StateBatchCrono.MOVILIDAD_ARTICULAR_QUESTION -> {
            when (entrenamientoDB.movArticular) {
                AnswerState.YES, AnswerState.ASK_LATER -> this.keyMovConfMV
                AnswerState.NO -> this.keyRutConfMV
            }
        }

        StateBatchCrono.MOVILIDAD_ARTICULAR -> this.keyMovConfMV
        StateBatchCrono.STEPS -> this.keyRutConfMV
        StateBatchCrono.ESTIRAMIENTO_QUESTIONS, StateBatchCrono.ESTIRAMIENTOS,
        StateBatchCrono.END -> this.keyEstConfMV
    }

    private fun isEndRutina(): Boolean {
        return _stateBatchCrono.value == StateBatchCrono.END ||
                _stateBatchCrono.value == StateBatchCrono.ESTIRAMIENTO_QUESTIONS ||
                _stateBatchCrono.value == StateBatchCrono.ESTIRAMIENTOS
    }

    fun isStepContentMaterial(
        stateBatchCrono: StateBatchCrono,
        stepDB: List<StepEntrenamientoDto>,
        materialDB: List<MaterialEntrenamientoDto>,
        cursor: Int
    ): Boolean {
        return try {
            stateBatchCrono == StateBatchCrono.STEPS && materialDB.any { it.idStep == stepDB[cursor].idStep }
        } catch (_: IndexOutOfBoundsException) {
            false
        }
    }

    private fun getFlowInitialEntrenamiento(): StateFlow<CronoEntretenimientoUiState> {
        return FlowManager.getFlowEntrenamientoDefault()
            .map { CronoEntretenimientoUiState.Success(it) }
            .catch { CronoEntretenimientoUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = CronoEntretenimientoUiState.Loading
            )
    }

    private fun getFlowInitialDataEntrenamiento(idRutina: Long): StateFlow<CronoEntretenimientoUiState> {
        return getEntrenamientoUseCase(idRutina)
            .map { CronoEntretenimientoUiState.Success(it) }
            .catch { CronoEntretenimientoUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = CronoEntretenimientoUiState.Loading
            )
    }

    private fun getFlowInitialStep(): StateFlow<CronoStepEntretenimientoUiState> {
        return FlowManager.getListFlowStepEntrenamientoListDefault()
            .map { CronoStepEntretenimientoUiState.Success(it) }
            .catch { CronoStepEntretenimientoUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = CronoStepEntretenimientoUiState.Loading
            )
    }

    private fun getFlowInitialDataStep(idRutina: Long): StateFlow<CronoStepEntretenimientoUiState> {
        return getStepEntrenamientoUseCase(idRutina).map {
            CronoStepEntretenimientoUiState.Success(it)
        }
            .catch { CronoStepEntretenimientoUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = CronoStepEntretenimientoUiState.Loading
            )
    }

    private fun getFlowInitialMaterial(): StateFlow<CronoMaterialEntretenimientoUiState> {
        return FlowManager.getListFlowMaterialEntrenamientoListDefault()
            .map { CronoMaterialEntretenimientoUiState.Success(it) }
            .catch { CronoMaterialEntretenimientoUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = CronoMaterialEntretenimientoUiState.Loading
            )
    }

    private fun getFlowInitialDataMaterial(idRutina: Long): StateFlow<CronoMaterialEntretenimientoUiState> {
        return getMaterialEntrenamientoUseCase(idRutina)
            .map { CronoMaterialEntretenimientoUiState.Success(it) }
            .catch { CronoMaterialEntretenimientoUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = CronoMaterialEntretenimientoUiState.Loading
            )
    }
}