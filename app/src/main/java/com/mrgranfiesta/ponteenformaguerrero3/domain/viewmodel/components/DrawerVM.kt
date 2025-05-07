package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.data.datastore.UserDataStore
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.ListManager
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawerVM @Inject constructor(
    private val userDataStore: UserDataStore
): ViewModel() {
    val ioScope = CoroutineScope(Dispatchers.IO)

    private val _closeSesionDialog = MutableStateFlow(false)
    val closeSesionDialog: StateFlow<Boolean> = _closeSesionDialog
    fun setCloseSesionDialog(closeSesionDialog: Boolean) {
        _closeSesionDialog.value = closeSesionDialog
    }

    private val _isEditableProfile = MutableStateFlow(false)
    val isEditableProfile: StateFlow<Boolean> = _isEditableProfile
    fun setIsEditableProfile(isEditableProfile: Boolean) {
        _isEditableProfile.value = isEditableProfile
    }

    private val _optList = MutableStateFlow(listOf<AppScreem>())
    val optList: StateFlow<List<AppScreem>> = _optList
    fun setOptList(optList: List<AppScreem>) {
        viewModelScope.launch {
            _optList.value = optList
        }
    }

    fun logout(navController: NavController){
        navController.navigate(AppScreem.Login.route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
        ioScope.launch {
            userDataStore.cleanUser()
        }
        CurrentUser.logout()
    }

    fun initData() {
        val rol = CurrentUser.user?.rol
        this.setIsEditableProfile(Rol.isEditable(rol))
        if (rol != null) {
            this.setOptList(ListManager.getDrawerOpt(rol))
        } else {
            this.setOptList(listOf())
        }
    }
}