package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ActionModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CHANGE_PASSWORD
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CONFIRM_NEW_PASSWORD
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NEW_PASSWORD
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OLD_PASSWORD
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.ActionItem
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.FloatingButtonVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.FloatingButtonVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TextFieldPasswordVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.ChangePasswordVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.TextFieldPassword

@Composable
fun ConfChangePassword(
    navController: NavController,
    snackbarHost: SnackbarHostState,
) {
    ChangePasswordPrepareMenu(
        navController = navController,
        snackbarHost = snackbarHost
    )
    ChangePasswordScreem()
}

@Composable
fun ChangePasswordPrepareMenu(
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    floatingButtonVM: FloatingButtonVM = FloatingButtonVMSingle.getFloatingButtonVM(),
    snackbarHost: SnackbarHostState,
    navController: NavController,
    changePasswordVM: ChangePasswordVM = hiltViewModel()
) {
    val passwordOldFieldVM: TextFieldPasswordVM = viewModel(key = "1")
    val passwordNewFieldVM: TextFieldPasswordVM = viewModel(key = "2")
    val passwordNewConfirmFieldVM: TextFieldPasswordVM = viewModel(key = "3")

    val focusManager = LocalFocusManager.current

    LaunchedEffect(true) {
        topBarVM.config(
            title = LABEL_CHANGE_PASSWORD,
            icon = IconTopBar.ARRROW,
            mode = ModeTopBar.TITLE,
            isGeasture = false,
            action = ActionModeTopBar.POP_BACK_STACK
        )
        floatingButtonVM.resetFloatingButton()
    }
    topBarVM.addActionList(
        ActionItem(
            icon = Icons.Filled.Check,
            tooltipText = LABEL_CHANGE_PASSWORD,
            name = "Guardar elemento",
            action = {
                passwordOldFieldVM.onCompleteFirstIteration()
                passwordNewFieldVM.onCompleteFirstIteration()
                passwordNewConfirmFieldVM.onCompleteFirstIteration()
                focusManager.clearFocus()
                changePasswordVM.updatePasword(
                    snackbarHost = snackbarHost,
                    popBackStack = { navController.popBackStack() }
                )
            }
        )
    )
}

@Composable
fun ChangePasswordScreem(
    changePasswordVM: ChangePasswordVM = hiltViewModel()
) {
    val passwordOldFieldVM: TextFieldPasswordVM = viewModel(key = "1")
    val passwordNewFieldVM: TextFieldPasswordVM = viewModel(key = "2")
    val passwordNewConfirmFieldVM: TextFieldPasswordVM = viewModel(key = "3")

    val passwordOldFocus = changePasswordVM.getPasswordOldFocusRequester()
    val passwordNewFocus = changePasswordVM.getPasswordNewFocusRequester()
    val passwordNewConfirmFocus = changePasswordVM.getPasswordNewConfirmFocusRequester()

    val passwordOld by changePasswordVM.passwordOld.collectAsState()
    val passwordNew by changePasswordVM.passwordNew.collectAsState()
    val passwordNewConfirm by changePasswordVM.passwordNewConfirm.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldPassword(
            txt = passwordOld,
            onValueChange = {
                changePasswordVM.setPasswordOld(it)
                passwordOldFocus.requestFocus()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .focusRequester(passwordOldFocus),
            label = LABEL_OLD_PASSWORD,
            maxCount = 20,
            textFieldPasswordVM = passwordOldFieldVM
        )

        TextFieldPassword(
            txt = passwordNew,
            onValueChange = {
                changePasswordVM.setPasswordNew(it)
                passwordNewFocus.requestFocus()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .focusRequester(passwordNewFocus),
            label = LABEL_NEW_PASSWORD,
            maxCount = 20,
            textFieldPasswordVM = passwordNewFieldVM
        )

        TextFieldPassword(
            txt = passwordNewConfirm,
            onValueChange = {
                changePasswordVM.setPasswordNewConfirm(it)
                passwordNewConfirmFocus.requestFocus()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .focusRequester(passwordNewConfirmFocus),
            label = LABEL_CONFIRM_NEW_PASSWORD,
            maxCount = 20,
            textFieldPasswordVM = passwordNewConfirmFieldVM
        )
    }
}