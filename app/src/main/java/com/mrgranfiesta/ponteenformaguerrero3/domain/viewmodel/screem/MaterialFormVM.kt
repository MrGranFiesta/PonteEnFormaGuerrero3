package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import android.content.Context
import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.MaterialInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.FlowManager
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.CreateMaterialUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.GetMaterialByIdUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.UpdateMaterialUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.ValidationAnnotations
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.Validators
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.material.form.MaterialFormUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MaterialFormVM @Inject constructor(
    private val getMaterialByIdUseCase: GetMaterialByIdUseCase,
    private val updateMaterialUseCase: UpdateMaterialUseCase,
    private val createMaterialUseCase: CreateMaterialUseCase
) : ViewModel() {
    var uiState: StateFlow<MaterialFormUiState> = this.getFlowInitial()

    private var status = true
    private var statusInitFields = true

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

    private var _isMaterialWeight = MutableStateFlow(false)
    var isMaterialWeight: StateFlow<Boolean> = _isMaterialWeight
    fun setMaterialWeight(isMaterialWeight: Boolean) {
        _isMaterialWeight.value = isMaterialWeight
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
            }
            status = false
        }
    }

    @Synchronized
    fun initModeUpd(material: MaterialInfoDto, id: Long) {
        if (id != 0L && statusInitFields) {
            this.setNombre(material.nombre)
            this.setDescripcion(material.descripcion)
            this.setMaterialWeight(material.isMaterialWeight)
            this.setPhotoUri(material.photoUri)
            statusInitFields = false
        }
    }

    fun crudActionForm(context: Context, material: MaterialInfoDto) {
        _nombre.value = _nombre.value.replace(Regex("[\n\r]"), "")
        val idUser = CurrentUser.user?.idUser ?: 0
        val newMaterial = this.getNewMaterial(
            idMaterial = material.idMaterial,
            idUser = idUser
        )
        if (newMaterial.idMaterial == 0L) {
            createMaterialUseCase(
                context = context,
                material = newMaterial
            )
        } else {
            updateMaterialUseCase(
                context = context,
                deleteImgUri = material.photoUri,
                material = newMaterial
            )
        }
    }

    fun activateDialogChange(
        materialDB: MaterialInfoDto,
        snackbarHost: SnackbarHostState,
        popBackStack: () -> Unit
    ) {
        val idUser = CurrentUser.user?.idUser ?: 0
        if (ValidationAnnotations.isValid(
                bean = this.getNewMaterial(idUser, materialDB.idMaterial),
                viewModelScope = viewModelScope,
                snackbarHost = snackbarHost
            )
        ) {
            if (this.isChangeValues(materialDB)) {
                setSelectSaveDialog(true)
            } else {
                popBackStack()
            }
        }
    }

    private fun getNewMaterial(
        idMaterial: Long,
        idUser: Long
    ) = MaterialInfoDto(
        idMaterial = idMaterial,
        idUser = idUser,
        nombre = _nombre.value.trim(),
        descripcion = _descripcion.value.trim(),
        isMaterialWeight = _isMaterialWeight.value,
        rol = Rol.STANDAR_USER,
        photoUri = _photoUri.value
    )

    fun isChangeValues(materialDB: MaterialInfoDto): Boolean {
        val idUser = CurrentUser.user?.idUser ?: return true
        return Validators.isChangedValuesMaterial(
            materialOrigen = materialDB,
            materialUpdate = getNewMaterial(idUser, materialDB.idMaterial)
        )
    }

    private fun getFlowInitial(): StateFlow<MaterialFormUiState> {
        return FlowManager.getFlowMaterialInfoDefault().map { MaterialFormUiState.Success(it) }
            .catch { MaterialFormUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = MaterialFormUiState.Loading
            )
    }

    private fun getFlowInitData(id: Long): StateFlow<MaterialFormUiState> {
        return getMaterialByIdUseCase(id).map { MaterialFormUiState.Success(it) }
            .catch { MaterialFormUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = MaterialFormUiState.Loading
            )
    }
}