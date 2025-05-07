package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrgranfiesta.ponteenformaguerrero3.data.datastore.UserDataStore
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CERRAR
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.SnackbarHostText
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.ObjectFactory
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val userDataStore: UserDataStore,
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val emailFocusRequester = FocusRequester()
    private val passwordFocusRequester = FocusRequester()

    private val ioScope = CoroutineScope(Dispatchers.IO)

    fun getEmailFocusRequester() = emailFocusRequester
    fun getPasswordFocusRequester() = passwordFocusRequester

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

    fun isCompleteLoginUser() : Boolean {
        val user = loginUseCase(
            email = _email.value,
            password = _password.value
        )
        if (user != null) {
            ioScope.launch {
                userDataStore.saveUser(user)
            }
            CurrentUser.user = user
        }
        return user != null
    }

    fun loginGuestUser(){
        val user = ObjectFactory.getGuestUser()
        ioScope.launch {
            userDataStore.saveUser(user)
        }
        CurrentUser.user = user
    }

    fun showErrorLoginUser(snackbarHost: SnackbarHostState) {
        viewModelScope.launch {
            snackbarHost.showSnackbar(
                message = getTextError(),
                actionLabel = LABEL_CERRAR,
                duration = SnackbarDuration.Short
            )
        }
    }

    private fun getTextError() = when {
        _email.value.isBlank() -> SnackbarHostText.snackbarHostErrorEmpty("email")
        _password.value.isBlank() -> SnackbarHostText.snackbarHostErrorEmpty("contraseña")
        else -> "Error, no se ha podido iniciar sesión"
    }

    fun clearFields() {
        _email.value = ""
        _password.value = ""
    }
}