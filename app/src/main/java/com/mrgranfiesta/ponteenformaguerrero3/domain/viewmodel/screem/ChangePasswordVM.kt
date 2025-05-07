package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem


import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CERRAR
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.user.PasswordValidDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user.IsNotCorrectPasswordUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user.UpdatePasswordByIdUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.ValidationAnnotations
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ChangePasswordVM @Inject constructor(
    private val isNotCorrectPasswordUseCase : IsNotCorrectPasswordUseCase,
    private val updatePasswordByIdUseCase : UpdatePasswordByIdUseCase
) : ViewModel() {
    private val passwordOldFocusRequester = FocusRequester()
    private val passwordNewFocusRequester = FocusRequester()
    private val passwordNewConfirmFocusRequester = FocusRequester()

    fun getPasswordOldFocusRequester() = passwordOldFocusRequester
    fun getPasswordNewFocusRequester() = passwordNewFocusRequester
    fun getPasswordNewConfirmFocusRequester() = passwordNewConfirmFocusRequester

    private var _passwordOld: MutableStateFlow<String> = MutableStateFlow("")
    var passwordOld: StateFlow<String> = _passwordOld
    fun setPasswordOld(passwordOld: String) {
        _passwordOld.value = passwordOld
    }

    private var _passwordNew: MutableStateFlow<String> = MutableStateFlow("")
    var passwordNew: StateFlow<String> = _passwordNew
    fun setPasswordNew(passwordNew: String) {
        _passwordNew.value = passwordNew
    }

    private var _passwordNewConfirm: MutableStateFlow<String> = MutableStateFlow("")
    var passwordNewConfirm: StateFlow<String> = _passwordNewConfirm
    fun setPasswordNewConfirm(passwordNewConfirm: String) {
        _passwordNewConfirm.value = passwordNewConfirm
    }

    fun isValid(snackbarHost: SnackbarHostState) : Boolean {
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
                bean = this.getPasswordValidDto(),
                snackbarHost = snackbarHost,
                viewModelScope = viewModelScope
            ) -> false
            _passwordNew.value != _passwordNewConfirm.value -> showError("Las contraseñas no coinciden")
            isNotCorrectPasswordUseCase(_passwordOld.value) -> showError("Error, no se puede cambiar la contraseña")
            else -> true
        }
    }

    private fun getPasswordValidDto() = PasswordValidDto(
        passwordOld = _passwordOld.value,
        passwordNew = _passwordNew.value,
        passwordNewConfirm = _passwordNewConfirm.value
    )

    fun updatePasword(
        snackbarHost: SnackbarHostState,
        popBackStack : () -> Unit
    ){
        if (this.isValid(snackbarHost)) {
            popBackStack()
            updatePasswordByIdUseCase(passwordNew.value)
        }
    }
}