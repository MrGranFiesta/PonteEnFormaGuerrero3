package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.data.datastore.UserDataStore
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CANCEL_PREMIUM_BT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_USER_PREMIUM_BT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user.DeleteUserByIdUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user.UpdateUserPhotoUriUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user.UpdateUserRolUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user.UpdateUserUsernameUseCase
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoVM @Inject constructor(
    private val deleteUserByIdUseCase: DeleteUserByIdUseCase,
    private val updateUserPhotoUriUseCase: UpdateUserPhotoUriUseCase,
    private val updateUserUsernameUseCase: UpdateUserUsernameUseCase,
    private val updateUserRolUseCase : UpdateUserRolUseCase,
    private val userDataStore: UserDataStore
) : ViewModel() {
    val ioScope = CoroutineScope(Dispatchers.IO)

    private var status = true

    @Synchronized
    fun initData() {
        if (status) {
            CurrentUser.user?.let {
                _photoUri.value = it.photoUri
                _username.value = it.username
                _email.value = it.email
                _rol.value = it.rol
            }
            status = false
        }
    }

    private var _photoUri: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY)
    var photoUri: StateFlow<Uri> = _photoUri
    fun setPhotoUri(photoUri: Uri) {
        _photoUri.value = photoUri
    }

    private var _username: MutableStateFlow<String> = MutableStateFlow("")
    var username: StateFlow<String> = _username
    fun setUsername(username: String) {
        _username.value = username
    }

    private var _email: MutableStateFlow<String> = MutableStateFlow("")
    var email: StateFlow<String> = _email

    private var _rol: MutableStateFlow<Rol> = MutableStateFlow(Rol.STANDAR_USER)
    var rol: StateFlow<Rol> = _rol
    fun setRol(rol: Rol) {
        _rol.value = rol
    }

    private val _selectDeleteDialog = MutableStateFlow(false)
    val selectDeleteDialog: StateFlow<Boolean> = _selectDeleteDialog
    fun onChangeSelectDeleteDialog(selectDeleteDialog: Boolean) {
        _selectDeleteDialog.value = selectDeleteDialog
    }

    private val _isShowDialogProfile = MutableStateFlow(false)
    val isShowDialogProfile: StateFlow<Boolean> = _isShowDialogProfile
    fun onChangeShowDialogProfile(isShowDialogProfile: Boolean) {
        _isShowDialogProfile.value = isShowDialogProfile
    }

    private val _isShowEditUsernameDialog = MutableStateFlow(false)
    val isShowEditUsernameDialog: StateFlow<Boolean> = _isShowEditUsernameDialog
    fun onChangeShowEditUsernameDialog(isShowEditUsernameDialog: Boolean) {
        _isShowEditUsernameDialog.value = isShowEditUsernameDialog
    }

    private val _isShowJoinPremiumDialog = MutableStateFlow(false)
    val isShowJoinPremiumDialog : StateFlow<Boolean> = _isShowJoinPremiumDialog
    fun onChangeShowJoinPremiumDialog(isShowJoinPremiumDialog: Boolean) {
        _isShowJoinPremiumDialog.value = isShowJoinPremiumDialog
    }

    private val _isShowCancelPremiumDialog = MutableStateFlow(false)
    val isShowCancelPremiumDialog: StateFlow<Boolean> = _isShowCancelPremiumDialog
    fun onChangeShowCancelPremiumDialog(isShowCancelPremiumDialog: Boolean) {
        _isShowCancelPremiumDialog.value = isShowCancelPremiumDialog
    }

    fun deleteUser(
        context: Context,
        navController: NavController
    ) {
        navController.navigate(AppScreem.Login.route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
        ioScope.launch {
            userDataStore.cleanUser()
        }
        CurrentUser.user?.let {
            deleteUserByIdUseCase(
                idUser = it.idUser,
                context = context
            )
        }
        CurrentUser.logout()
    }

    fun updateUsername(username: String) {
        CurrentUser.user?.let {
            updateUserUsernameUseCase(
                idUser = it.idUser,
                username = username
            )
            setUsername(username)
        }
    }

    fun updatePhotoUri(
        photoUri: Uri,
        context: Context
    ) {
        CurrentUser.user?.let {
            updateUserPhotoUriUseCase(
                context = context,
                idUser = it.idUser,
                photoUri = photoUri,
                deleteImgUri = it.photoUri
            )
            setPhotoUri(photoUri)
        }
    }

    fun getButtonTextForSubscription(rol: Rol) : String {
        return when (rol) {
            Rol.STANDAR_USER, Rol.GUEST, Rol.INIT_DATA_USER -> LABEL_USER_PREMIUM_BT
            Rol.PREMIUN_USER -> LABEL_CANCEL_PREMIUM_BT
        }
    }

    fun joinPremium() {
        updateRol(Rol.PREMIUN_USER)
    }

    fun cancelPremium() {
        updateRol(Rol.STANDAR_USER)
    }

    private fun updateRol(rol : Rol){
        CurrentUser.user?.let {
            updateUserRolUseCase(
                rol = rol,
                idUser = it.idUser
            )
            setRol(rol)
        }
    }
}