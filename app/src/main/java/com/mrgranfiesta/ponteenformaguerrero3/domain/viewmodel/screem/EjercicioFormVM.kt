package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.PARAMS_MATERIAL_BACK_SELECT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.PARAMS_MATERIAL_INITIAL_SELECT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.FlowManager
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio.CreateEjercicioUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio.GetEjercicioInfoByIdUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.GetMaterialByIdEjercicioUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.GetMaterialByIdListUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio.UpdateEjercicioUserCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.ValidationAnnotations
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.Validators
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.ejercicio.form.EjercicioFormMaterialUiState
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.ejercicio.form.EjercicioFormUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EjercicioFormVM @Inject constructor(
    private val getMaterialByIdList : GetMaterialByIdListUseCase,
    private val getMaterialByIdEjercicio : GetMaterialByIdEjercicioUseCase,
    private val getEjercicioInfoByIdUseCase : GetEjercicioInfoByIdUseCase,
    private val createEjercicioUseCase : CreateEjercicioUseCase,
    private val updateEjercicioUserCase : UpdateEjercicioUserCase
) : ViewModel() {
    var uiState: StateFlow<EjercicioFormUiState> = this.getFlowInitial()
    var uiStateMaterial: StateFlow<EjercicioFormMaterialUiState> = this.getFlowInitialMaterial()

    private val nombreFocusRequester = FocusRequester()
    private val descripcionFocusRequester = FocusRequester()

    fun getNombreFocusRequester() = nombreFocusRequester
    fun getDescripcionFocusRequester() = descripcionFocusRequester

    private var status = true
    private var statusMaterial = true
    private var statusBackup = true

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

    private val _isSimetria = MutableStateFlow(false)
    val isSimetria: StateFlow<Boolean> = _isSimetria
    fun setIsSimetria(isSimetria: Boolean) {
        _isSimetria.value = isSimetria
    }

    private var _materialBackup = setOf<RowMaterialDto>()
    private fun setMaterialBackup(materialBackup: Set<RowMaterialDto>) {
        viewModelScope.launch {
            _materialBackup = materialBackup
        }
    }

    private var _materialSelected = MutableStateFlow(setOf<RowMaterialDto>())
    var materialSelected: StateFlow<Set<RowMaterialDto>> = _materialSelected
    private fun setMaterialSelected(materialSelected: Set<RowMaterialDto>) {
        viewModelScope.launch {
            _materialSelected.value = materialSelected
        }
    }

    private var _photoUri: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY)
    var photoUri: StateFlow<Uri> = _photoUri
    fun setPhotoUri(photoUri: Uri) {
        _photoUri.value = photoUri
    }

    @Synchronized
    fun initData(id: Long) {
        if (status) {
            if (id != 0L) {
                uiState = this.getFlowInitData(id)
                uiStateMaterial = this.getFlowInitDataMaterial(id)
            }
            status = false
        }
    }

    @Synchronized
    fun createdBackupEjercicio(
        ejercicio:  EjercicioInfoDto,
        materialDB: List<RowMaterialDto>,
        id: Long
    ) {
        if (statusBackup && id != 0L) {
            this.setNombre(ejercicio.nombre)
            this.setNivel(ejercicio.nivel)
            this.setMusculoSet(ejercicio.musculoSet)
            this.setDescripcion(ejercicio.descripcion)
            this.setIsSimetria(ejercicio.isSimetria)
            this.setPhotoUri(ejercicio.photoUri)
            this.setMaterialBackup(materialDB.toSet())
            statusBackup = false
        }
        this.setMaterialSelected(materialDB.toSet())
    }

    fun crudActionForm(ejercicioDB: EjercicioInfoDto, context: Context) {
        _nombre.value = _nombre.value.replace(Regex("[\n\r]"), "")
        val idUser = CurrentUser.user?.idUser ?: 0
        val ejercicio = this.getNewEjercicio(idUser, ejercicioDB.idEjercicio)
        if (ejercicioDB.idEjercicio == 0L) {
            createEjercicioUseCase(
                ejercicio = ejercicio,
                materialList = _materialSelected.value,
                context = context
            )
        } else {
            updateEjercicioUserCase(
                ejercicio = ejercicio,
                materialListOrigin = _materialBackup,
                materialListUpdate = _materialSelected.value,
                deleteImgUri = ejercicioDB.photoUri,
                context = context
            )
        }
    }

    fun activateDialogChange(
        ejercicioDB: EjercicioInfoDto,
        snackbarHost: SnackbarHostState,
        popBackStack: () -> Unit
    ) {
        val idUser = CurrentUser.user?.idUser ?: 0
        if (ValidationAnnotations.isValid(
                bean = this.getNewEjercicio(idUser, ejercicioDB.idEjercicio),
                viewModelScope = viewModelScope,
                snackbarHost = snackbarHost
            )
        ) {
            if (this.isChangeValues(ejercicioDB)) {
                setSelectSaveDialog(true)
            } else {
                popBackStack()
            }
        }
    }

    private fun getNewEjercicio(
        idUser : Long,
        idEjercicio: Long
    ) = EjercicioInfoDto(
        idEjercicio = idEjercicio,
        idUser = idUser,
        nombre = _nombre.value.trim(),
        descripcion = _descripcion.value.trim(),
        musculoSet = _musculoSet.value,
        nivel = _nivel.value,
        isSimetria = _isSimetria.value,
        photoUri = _photoUri.value,
        rol = Rol.STANDAR_USER
    )

    fun isChangeValues(ejercicioOrigen: EjercicioInfoDto): Boolean {
        val idUser = CurrentUser.user?.idUser ?: return true
        return Validators.isChangedValuesEjercicio(
            ejercicioOrigen = ejercicioOrigen,
            ejercicioUpdate = this.getNewEjercicio(idUser, ejercicioOrigen.idEjercicio),
            materialOrigen = _materialBackup,
            materialUpdate = _materialSelected.value
        )
    }

    private fun getFlowInitial(): StateFlow<EjercicioFormUiState> {
        return FlowManager.getEjercicioInfoDtoDefault().map { EjercicioFormUiState.Success(it) }
            .catch { EjercicioFormUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = EjercicioFormUiState.Loading
            )
    }

    private fun getFlowInitData(id: Long): StateFlow<EjercicioFormUiState> {
        return getEjercicioInfoByIdUseCase(id).map { EjercicioFormUiState.Success(it) }
            .catch { EjercicioFormUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = EjercicioFormUiState.Loading
            )
    }

    private fun getFlowInitialMaterial(): StateFlow<EjercicioFormMaterialUiState> {
        return FlowManager.getEmptyList<RowMaterialDto>()
            .map { EjercicioFormMaterialUiState.Success(it) }
            .catch { EjercicioFormMaterialUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = EjercicioFormMaterialUiState.Loading
            )
    }

    private fun getFlowInitDataMaterial(id: Long): StateFlow<EjercicioFormMaterialUiState> {
        return getMaterialByIdEjercicio(id)
            .map { EjercicioFormMaterialUiState.Success(it) }
            .catch { EjercicioFormMaterialUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = EjercicioFormMaterialUiState.Loading
            )
    }

    private fun getFlowInitDataMaterial(materialIds: List<Long>): StateFlow<EjercicioFormMaterialUiState> {
        return getMaterialByIdList(materialIds)
            .map { EjercicioFormMaterialUiState.Success(it) }
            .catch { EjercicioFormMaterialUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = EjercicioFormMaterialUiState.Loading
            )
    }

    @SuppressWarnings("kotlin:S6518")
    fun addOptionalMaterial(navBackStackEntry: NavBackStackEntry) {
        val materialSelectedIdBack =
            navBackStackEntry.savedStateHandle.get<Set<Long>>(PARAMS_MATERIAL_BACK_SELECT)
        navBackStackEntry.savedStateHandle.remove<MutableSet<Long>>(PARAMS_MATERIAL_BACK_SELECT)
        val materialSelectedId = _materialSelected.value.map { it.idMaterial }
        if (Validators.isNotEqualsContent(materialSelectedId, materialSelectedIdBack ?: listOf())) {
            statusMaterial = true
        }
        if (materialSelectedIdBack != null && statusMaterial) {
            uiStateMaterial = if (materialSelectedIdBack.isNotEmpty()) {
                this.getFlowInitDataMaterial(materialSelectedIdBack.toList())
            } else {
                this.getFlowInitialMaterial()
            }
            statusMaterial = false
        }
    }

    fun navigationSelectStep(navController: NavController) {
        navController.currentBackStackEntry?.savedStateHandle?.apply {
            val materialIds = _materialSelected.value.map { it.idMaterial }.toSet()
            set(PARAMS_MATERIAL_INITIAL_SELECT, materialIds)
        }
        navController.navigate(AppScreem.SelectMaterialScreem.createRoute())
    }

    @OptIn(ExperimentalFoundationApi::class)
    fun onActionBringIntoViewRequester(descripcionBringIntoViewRequester: BringIntoViewRequester) {
        viewModelScope.launch {
            descripcionBringIntoViewRequester.bringIntoView()
        }
    }
}