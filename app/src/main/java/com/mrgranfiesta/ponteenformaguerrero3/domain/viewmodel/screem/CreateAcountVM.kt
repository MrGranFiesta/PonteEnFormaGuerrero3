package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import android.content.Context
import android.net.Uri
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.UserBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CERRAR
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.SnackbarHostText
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user.CreateAccountUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user.IsDuplicatedEmailUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.ValidationAnnotations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAcountVM @Inject constructor(
    private val createAccountUseCase: CreateAccountUseCase,
    private val isDuplicatedEmailUseCase: IsDuplicatedEmailUseCase
) : ViewModel() {
    private val nombreFocusRequester = FocusRequester()
    private val emailFocusRequester = FocusRequester()
    private val passwordFocusRequester = FocusRequester()
    private val passwordConfirmFocusRequester = FocusRequester()

    fun getNombreFocusRequester() = nombreFocusRequester
    fun getEmailFocusRequester() = emailFocusRequester
    fun getPasswordFocusRequester() = passwordFocusRequester
    fun getPasswordConfirmFocusRequester() = passwordConfirmFocusRequester

    private var _nombre: MutableStateFlow<String> = MutableStateFlow("")
    var nombre: StateFlow<String> = _nombre
    fun setNombre(nombre: String) {
        _nombre.value = nombre
    }

    private var _email: MutableStateFlow<String> = MutableStateFlow("")
    var email: StateFlow<String> = _email
    fun setEmail(email: String) {
        _email.value = email
    }

    private var _password: MutableStateFlow<String> = MutableStateFlow("")
    var password: StateFlow<String> = _password
    fun setPassword(password: String) {
        _password.value = password
    }

    private var _passwordConfirm: MutableStateFlow<String> = MutableStateFlow("")
    var passwordConfirm: StateFlow<String> = _passwordConfirm
    fun setPasswordConfirm(passwordConfirm: String) {
        _passwordConfirm.value = passwordConfirm
    }

    private var _photoUri: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY)
    var photoUri: StateFlow<Uri> = _photoUri
    fun setPhotoUri(photoUri: Uri) {
        _photoUri.value = photoUri
    }

    private var _isShotDialogProfile: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var isShotDialogProfile: StateFlow<Boolean> = _isShotDialogProfile
    fun setIsShotDialogProfile(isShotDialogProfile: Boolean) {
        _isShotDialogProfile.value = isShotDialogProfile
    }

    fun registerUser(
        context : Context,
        snackbarHost: SnackbarHostState,
        navController: NavController
    ) {
        if (this.isValid(snackbarHost)) {
            createAccountUseCase.invoke(context, getNewUser())
            navController.popBackStack()
        }
    }

    fun isValid(snackbarHost: SnackbarHostState): Boolean {
        var showError: (String) -> Boolean = { error ->
            viewModelScope.launch {
                snackbarHost.showSnackbar(
                    message = error,
                    actionLabel = LABEL_CERRAR,
                    duration = SnackbarDuration.Short
                )
            }
            false
        }
        return when {
            !ValidationAnnotations.isValid(
                bean = this.getNewUser(),
                viewModelScope = viewModelScope,
                snackbarHost = snackbarHost
            ) -> {
                false
            }

            _passwordConfirm.value.isEmpty() -> {
                showError(
                    SnackbarHostText.snackbarHostErrorEmpty(
                        "confirmar contraseña"
                    )
                )
            }

            _password.value != _passwordConfirm.value -> showError("Las contraseñas no coinciden")
            isDuplicatedEmailUseCase(_email.value) -> showError("Error, el usuario no se puede registrar")
            else -> true
        }
    }

    private fun getNewUser() = UserBean(
        idUser = 0,
        rol = Rol.STANDAR_USER,
        username = _nombre.value,
        password = _password.value,
        email = _email.value,
        photoUri = _photoUri.value
    )
}