package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.PARAMS_STEP_SELECTED
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog.MaterialNameDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog.StepEditDialogDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioNameAndPhotoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RutinaInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.step.RowStepFormWithConfMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.FlowManager
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.DraggableItem
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio.GetEjercicioNameAndPhotoByIdUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina.CreateRutinaWithStepUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina.GetRutinaByIdUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina.UpdateRutinaWithStepUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.step.GetStepFormByIdRutinaUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.ValidationAnnotations
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.Validators
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.DtoMapper
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.rutina.form.RutinaFormStepUiState
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.rutina.form.RutinaFormUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Long
import kotlin.collections.map

@HiltViewModel
class RutinaFormVM @Inject constructor(
    private val getRutinaByIdUseCase: GetRutinaByIdUseCase,
    private val updateRutinaWithStepUseCase: UpdateRutinaWithStepUseCase,
    private val createRutinaWithStepUseCase: CreateRutinaWithStepUseCase,
    private val getStepFormByIdRutinaUseCase: GetStepFormByIdRutinaUseCase,
    private val getEjercicioNameAndPhotoByIdUseCase: GetEjercicioNameAndPhotoByIdUseCase
) : ViewModel() {
    var uiStateRutina: StateFlow<RutinaFormUiState> = this.getFlowInitialRutina()
    var uiStateStep: StateFlow<RutinaFormStepUiState> = this.getFlowInitialStep()

    private val nombreFocusRequester = FocusRequester()
    private val descripcionFocusRequester = FocusRequester()

    fun getNombreFocusRequester() = nombreFocusRequester
    fun getDescripcionFocusRequester() = descripcionFocusRequester

    private var status = true
    private var statusBackup = true

    private val _stepListOnRutina = MutableStateFlow(listOf<RowStepFormWithConfMaterialDto>())
    var stepListOnRutina: StateFlow<List<RowStepFormWithConfMaterialDto>> = _stepListOnRutina
    fun setStepListOnRutina(stepSelected: List<RowStepFormWithConfMaterialDto>) {
        viewModelScope.launch {
            _stepListOnRutina.value = stepSelected.toList()
        }
    }

    private fun addStepOnRutina(step: RowStepFormWithConfMaterialDto) {
        _stepListOnRutina.value += step
    }

    private fun revomeStepOnRutina(step: RowStepFormWithConfMaterialDto) {
        _stepListOnRutina.value -= step
    }

    private var stepListBackup = mutableListOf<RowStepFormWithConfMaterialDto>()

    private val _stepSelectedModifId = MutableStateFlow(-1)
    val stepSelectedModifId: StateFlow<Int> = _stepSelectedModifId

    private val _stepListDelete = mutableListOf<RowStepFormWithConfMaterialDto>()

    private val _selectNivelDialog = MutableStateFlow(false)
    val selectNivelDialog: StateFlow<Boolean> = _selectNivelDialog
    fun setSelectNivelDialog(selectNivelDialog: Boolean) {
        _selectNivelDialog.value = selectNivelDialog
    }

    private val _selectMusculoDialog = MutableStateFlow(false)
    val selectMusculoDialog: StateFlow<Boolean> = _selectMusculoDialog
    fun setSelectMusculoDialog(selectMusculoDialog: Boolean) {
        _selectMusculoDialog.value = selectMusculoDialog
    }

    private val _selectSaveDialog = MutableStateFlow(false)
    val selectSaveDialog: StateFlow<Boolean> = _selectSaveDialog
    fun setSelectSaveDialog(selectSaveDialog: Boolean) {
        _selectSaveDialog.value = selectSaveDialog
    }

    private val _selectBackDialog = MutableStateFlow(false)
    val selectBackDialog: StateFlow<Boolean> = _selectBackDialog
    fun setSelectBackDialog(selectBackDialog: Boolean) {
        _selectBackDialog.value = selectBackDialog
    }

    private val _isUpdateStepDialog = MutableStateFlow(false)
    val isUpdateStepDialog: StateFlow<Boolean> = _isUpdateStepDialog
    fun setIsUpdateStepDialog(isUpdateStepDialog: Boolean) {
        _isUpdateStepDialog.value = isUpdateStepDialog
    }

    private val _nivel = MutableStateFlow(Nivel.NINGUNO)
    val nivel: StateFlow<Nivel> = _nivel
    fun setNivel(nivel: Nivel) {
        _nivel.value = nivel
    }

    private val _musculoSet = MutableStateFlow(setOf<Musculo>())
    val musculoSet: StateFlow<Set<Musculo>> = _musculoSet
    fun setMusculoSet(musculoSet: Set<Musculo>) {
        viewModelScope.launch {
            _musculoSet.value = musculoSet.toSet()
        }
    }

    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre
    fun setNombre(nombre: String) {
        _nombre.value = nombre
    }

    private val _descripcion = MutableStateFlow("")
    val descripcion: StateFlow<String> = _descripcion
    fun setDescripcion(descripcion: String) {
        _descripcion.value = descripcion
    }

    private val _calentamiento = MutableStateFlow(AnswerState.YES)
    val calentamiento: StateFlow<AnswerState> = _calentamiento
    fun setCalentamiento(calentamiento: AnswerState) {
        _calentamiento.value = calentamiento
    }

    private val _movArticular = MutableStateFlow(AnswerState.YES)
    val movArticular: StateFlow<AnswerState> = _movArticular
    fun setMovArticular(movArticular: AnswerState) {
        _movArticular.value = movArticular
    }

    private val _estiramiento = MutableStateFlow(AnswerState.YES)
    val estiramiento: StateFlow<AnswerState> = _estiramiento
    fun setEstiramiento(estiramiento: AnswerState) {
        _estiramiento.value = estiramiento
    }

    private val _descanso = MutableStateFlow(5)
    val descanso: StateFlow<Int> = _descanso
    fun setDescanso(descanso: Int) {
        _descanso.value = descanso
    }

    private val _draggingItemIndex = MutableStateFlow<Int?>(null)
    val draggingItemIndex: StateFlow<Int?> = _draggingItemIndex
    fun setDraggingItemIndex(draggingItemIndex: Int?) {
        _draggingItemIndex.value = draggingItemIndex
    }

    private val _draggingItem = MutableStateFlow<LazyListItemInfo?>(null)
    val draggingItem: StateFlow<LazyListItemInfo?> = _draggingItem
    fun setDraggingItem(draggingItem: LazyListItemInfo?) {
        _draggingItem.value = draggingItem
    }

    private val _delta = MutableStateFlow<Float>(0f)
    val delta: StateFlow<Float> = _delta
    fun setDelta(delta: Float) {
        _delta.value = delta
    }

    @Synchronized
    fun initData(idRutina: Long) {
        if (status && idRutina != 0L) {
            uiStateRutina = this.getFlowInitDataRutina(idRutina)
            uiStateStep = this.getFlowInitDataStep(idRutina)
            status = false
        }
    }

    @Synchronized
    fun createdBackupRutina(
        rutinaOrigen: RutinaInfoDto,
        id: Long,
        stepListDB: List<RowStepFormWithConfMaterialDto>
    ) {
        if (statusBackup && id != 0L) {
            this.setNombre(rutinaOrigen.nombre)
            this.setNivel(rutinaOrigen.nivel)
            this.setMusculoSet(rutinaOrigen.musculoSet)
            this.setDescripcion(rutinaOrigen.descripcion)
            this.setCalentamiento(rutinaOrigen.calentamiento)
            this.setMovArticular(rutinaOrigen.movArticular)
            this.setEstiramiento(rutinaOrigen.estiramiento)
            this.setDescanso(rutinaOrigen.descanso)
            this.setStepListOnRutina(stepListDB.toMutableList())
            this.stepListBackup.clear()
            this.stepListBackup = stepListDB.map { it.copy() }.toMutableList()
            statusBackup = false
        }
    }

    fun activateDialogChange(
        rutinaOrigen: RutinaInfoDto,
        popBackStack: () -> Unit,
        snackbarHost: SnackbarHostState
    ) {
        val idUser = CurrentUser.user?.idUser ?: 0
        if (ValidationAnnotations.isValid(
                bean = this.getNewRutinaInfo(idUser, rutinaOrigen.idRutina),
                viewModelScope = viewModelScope,
                snackbarHost = snackbarHost
            )
        ) {
            if (this.isChangeValues(rutinaOrigen)) {
                this.setSelectSaveDialog(true)
            } else {
                popBackStack()
            }
        }
    }

    private fun getNewRutinaInfo(
        idUser: Long,
        idRutina: Long
    ) = RutinaInfoDto(
        idRutina = idRutina,
        idUser = idUser,
        nombre = _nombre.value.trim(),
        descripcion = _descripcion.value.trim(),
        musculoSet = _musculoSet.value,
        nivel = _nivel.value,
        calentamiento = _calentamiento.value,
        estiramiento = _estiramiento.value,
        movArticular = _movArticular.value,
        descanso = _descanso.value,
        rol = Rol.STANDAR_USER
    )

    fun isChangeValues(rutinaOrigen: RutinaInfoDto): Boolean {
        val idUser = CurrentUser.user?.idUser ?: return true
        return Validators.isChangedValuesRutina(
            rutinaOrigen = rutinaOrigen,
            rutinaUpdate = this.getNewRutinaInfo(idUser, rutinaOrigen.idRutina),
            stepOrigen = this.stepListBackup,
            stepUpdate = _stepListOnRutina.value
        )
    }

    fun crudActionForm(rutinaOrigen: RutinaInfoDto) {
        _nombre.value = _nombre.value.replace(Regex("[\n\r]"), "")
        val idUser = CurrentUser.user?.idUser ?: 0
        val rutina = this.getNewRutinaInfo(idUser, rutinaOrigen.idRutina)
        if (rutinaOrigen.idRutina == 0L) {
            createRutinaWithStepUseCase(
                rutina = rutina,
                stepList = _stepListOnRutina.value
            )
        } else {
            updateRutinaWithStepUseCase(
                rutina = rutina,
                stepListUpdate = _stepListOnRutina.value,
                stepListDelete = _stepListDelete
            )
        }
    }

    fun getCopyStepEditDialogDto(stepSelectedId: Int): StepEditDialogDto {
        val step = _stepListOnRutina.value[stepSelectedId]
        return StepEditDialogDto(
            idStep = step.idStep,
            idEjercicio = step.idEjercicio,
            cantidad = step.cantidad,
            serie = step.serie,
            tipo = step.tipo,
            confMaterialList = step.confMaterialList.map { confMaterial ->
                MaterialNameDto(
                    idMaterial = confMaterial.idMaterial,
                    idConfMaterial = confMaterial.idConfMaterial,
                    nombre = confMaterial.nombre,
                    isMaterialWeight = confMaterial.isMaterialWeight,
                    confValue = confMaterial.confValue
                )
            }
        )
    }

    @SuppressWarnings("kotlin:S6518")
    fun addOptionalStep(
        navBackStackEntry: NavBackStackEntry,
        idRutina: Long
    ) {
        val step = navBackStackEntry.savedStateHandle.get<StepEditDialogDto>(
            PARAMS_STEP_SELECTED
        )
        navBackStackEntry.savedStateHandle
            .remove<StepEditDialogDto>(PARAMS_STEP_SELECTED)
        viewModelScope.launch {
            if (step != null) {
                addStepOnRutina(
                    RowStepFormWithConfMaterialDto(
                        idStep = 0,
                        idRutina = idRutina,
                        idEjercicio = step.idEjercicio,
                        cantidad = step.cantidad,
                        serie = step.serie,
                        ordenExecution = _stepListOnRutina.value.size,
                        tipo = step.tipo,
                        confMaterialList = step.confMaterialList.map {
                            DtoMapper.toRowMaterialFormWithConfDto(
                                obj = it,
                                idStep = 0
                            )
                        }
                    )
                )
            }
        }
    }

    private fun getFlowInitialRutina(): StateFlow<RutinaFormUiState> {
        return FlowManager.getFlowRutinaInfoDefault().map { RutinaFormUiState.Success(it) }
            .catch { RutinaFormUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = RutinaFormUiState.Loading
            )
    }

    private fun getFlowInitDataRutina(id: Long): StateFlow<RutinaFormUiState> {
        return getRutinaByIdUseCase(id).map { RutinaFormUiState.Success(it) }
            .catch { RutinaFormUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = RutinaFormUiState.Loading
            )
    }

    private fun getFlowInitialStep(): StateFlow<RutinaFormStepUiState> {
        return FlowManager.getEmptyList<RowStepFormWithConfMaterialDto>()
            .map { RutinaFormStepUiState.Success(it) }
            .catch { RutinaFormStepUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = RutinaFormStepUiState.Loading
            )
    }

    private fun getFlowInitDataStep(id: Long): StateFlow<RutinaFormStepUiState> {
        return getStepFormByIdRutinaUseCase(id).map { RutinaFormStepUiState.Success(it) }
            .catch { RutinaFormStepUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = RutinaFormStepUiState.Loading
            )
    }

    fun selectStep(step: RowStepFormWithConfMaterialDto) {
        _stepSelectedModifId.value = _stepListOnRutina.value.indexOf(step)
        this.setIsUpdateStepDialog(true)
    }

    fun stepUpdate(
        cantidad: Int,
        serie: Int,
        tipo: TipoEsfuerzo,
        confMaterialList: List<MaterialNameDto>
    ) {
        setStepListOnRutina(
            _stepListOnRutina.value.mapIndexed { index, step ->
                if (index == _stepSelectedModifId.value) {
                    step.copy(
                        confMaterialList = confMaterialList.map {
                            DtoMapper.toRowMaterialFormWithConfDto(
                                idStep = step.idStep,
                                obj = it
                            )
                        },
                        cantidad = cantidad,
                        serie = serie,
                        tipo = tipo
                    )
                } else {
                    step
                }
            }
        )
    }

    fun onDeleteIconAction(step: RowStepFormWithConfMaterialDto) {
        if (step.idStep != 0L) {
            _stepListDelete.add(step)
        }
        this.revomeStepOnRutina(step)
    }

    fun getEjercicioNameAndPhoto(idEjercicio: Long): Flow<EjercicioNameAndPhotoDto> {
        return getEjercicioNameAndPhotoByIdUseCase(idEjercicio)
    }

    fun resetDragAndDrop() {
        setDraggingItem(null)
        setDraggingItemIndex(null)
        setDelta(0f)
    }

    fun onMoveRow(fromIndex: Int, toIndex: Int) {
        setStepListOnRutina(
            _stepListOnRutina.value.toMutableList().apply { add(toIndex, removeAt(fromIndex)) }
        )
    }

    fun onDragStart(
        offset: Offset,
        stateList: LazyListState
    ) {
        stateList.layoutInfo.visibleItemsInfo
            .firstOrNull { item ->
                offset.y.toInt() in item.offset..(item.offset + item.size)
            }
            ?.also {
                val row = (it.contentType as? DraggableItem)
                row?.let { draggableItem ->
                    setDraggingItem(it)
                    setDraggingItemIndex(draggableItem.index)
                }
            }
    }

    fun onDrag(
        currentDraggingItemIndex: Int,
        currentDraggingItem: LazyListItemInfo,
        stateList: LazyListState,
        scrollChannel: Channel<Float>
    ) {
        val startOffset = currentDraggingItem.offset + _delta.value
        val endOffset = currentDraggingItem.offset + currentDraggingItem.size + _delta.value

        val offset40 = startOffset + (endOffset - startOffset) * 0.4f
        val offset60 = startOffset + (endOffset - startOffset) * 0.6f

        val targetItem = stateList.layoutInfo.visibleItemsInfo.find { item ->
            item.contentType is DraggableItem &&
                    ((offset40.toInt() in item.offset..item.offset + item.size &&
                            currentDraggingItem.index < item.index) ||
                    (offset60.toInt() in item.offset..item.offset + item.size &&
                            currentDraggingItem.index > item.index))
        }


        if (targetItem != null) {
            val targetIndex = (targetItem.contentType as DraggableItem).index
            onMoveRow(currentDraggingItemIndex, targetIndex)
            setDraggingItemIndex(targetIndex)
            setDraggingItem(targetItem)
            setDelta(_delta.value + currentDraggingItem.offset - targetItem.offset)
        } else {
            val startOffsetToTop = startOffset - stateList.layoutInfo.viewportStartOffset
            val endOffsetToBottom = endOffset - stateList.layoutInfo.viewportEndOffset
            val scroll = when {
                startOffsetToTop < 0 -> startOffsetToTop.coerceAtMost(0f)
                endOffsetToBottom > 0 -> endOffsetToBottom.coerceAtLeast(0f)
                else -> 0f
            }
            val canScrollDown =
                currentDraggingItemIndex != _stepListOnRutina.value.size - 1 && endOffsetToBottom > 0
            val canScrollUp = currentDraggingItemIndex != 0 && startOffsetToTop < 0
            if (scroll != 0f && (canScrollUp || canScrollDown)) {
                scrollChannel.trySend(scroll)
            }
        }
    }
}