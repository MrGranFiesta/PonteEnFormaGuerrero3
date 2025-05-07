package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ActionModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CONFIRM_PASSWORD
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CREATE_ACCOUNT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EMAIL
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NOMBRE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_PASSWORD
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_REGISTRARSE_BT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.SizeProfile
import com.mrgranfiesta.ponteenformaguerrero3.domain.core.viewmodel.PopBackEffectVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.FloatingButtonVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.PopBackEffectVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.RegexExpresion
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.FloatingButtonVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TextFieldPasswordVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TextFieldSecuredVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.CreateAcountVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.PhotoProfileUser
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.TextFieldPassword
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.TextFieldSecured
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.SelectOptionPhotoDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem

@Composable
fun ConfCreateAcount(
    snackbarHost: SnackbarHostState,
    navController: NavController,
    createAcountVM: CreateAcountVM = hiltViewModel()
) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                createAcountVM.setPhotoUri(uri)
            }
        }
    val photoPickerLaunch = {
        launcher.launch(
            PickVisualMediaRequest(
                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }

    CreateAcountPrepareMenu()
    CreateAccountPrepareDialog(photoPickerLaunch)
    CreateAcount(
        snackbarHost = snackbarHost,
        navController = navController,
        photoPickerLaunch = photoPickerLaunch
    )
}

@Composable
fun CreateAccountPrepareDialog(
    photoPickerLaunch : () -> Unit,
    createAcountVM: CreateAcountVM = hiltViewModel()
) {
    val isShotDialogProfile by createAcountVM.isShotDialogProfile.collectAsState()
    if (isShotDialogProfile) {
        SelectOptionPhotoDialog(
            dismissDialog = {
                createAcountVM.setIsShotDialogProfile(false)
            },
            onClickDelete = {
                createAcountVM.setPhotoUri(Uri.EMPTY)
            },
            onClickOpenAlbum = {
                photoPickerLaunch()
            }
        )
    }
}

@Composable
fun CreateAcountPrepareMenu(
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    floatingButtonVM: FloatingButtonVM = FloatingButtonVMSingle.getFloatingButtonVM(),
) {
    LaunchedEffect(true) {
        topBarVM.config(
            title = LABEL_CREATE_ACCOUNT,
            icon = IconTopBar.ARRROW,
            mode = ModeTopBar.TITLE,
            isGeasture = false,
            action = ActionModeTopBar.POP_BACK_STACK
        )
        floatingButtonVM.resetFloatingButton()
    }
}

@Composable
fun CreateAcount(
    photoPickerLaunch : () -> Unit,
    snackbarHost: SnackbarHostState,
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    popBackEffectVM: PopBackEffectVM = PopBackEffectVMSingle.getPopBackEffectVM(),
    navController: NavController,
    createAcountVM: CreateAcountVM = hiltViewModel()
) {
    val context = LocalContext.current
    val nombreFocus = createAcountVM.getNombreFocusRequester()
    val emailFocus = createAcountVM.getEmailFocusRequester()
    val passwordFocus = createAcountVM.getPasswordFocusRequester()
    val passwordConfirmFocus = createAcountVM.getPasswordConfirmFocusRequester()

    val isOnProcessTopBt by topBarVM.isOnProcess.collectAsState()
    val isBack by popBackEffectVM.isBack.collectAsState()

    val nombre by createAcountVM.nombre.collectAsState()
    val email by createAcountVM.email.collectAsState()
    val password by createAcountVM.password.collectAsState()
    val passwordConfirm by createAcountVM.passwordConfirm.collectAsState()
    val photoUri by createAcountVM.photoUri.collectAsState()

    val nombreFieldVM: TextFieldSecuredVM = viewModel(key = "1")
    val emailFieldVM: TextFieldSecuredVM = viewModel(key = "2")
    val passwordFieldVM: TextFieldPasswordVM = viewModel(key = "3")
    val passwordConfirmFieldVM: TextFieldPasswordVM = viewModel(key = "4")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PhotoProfileUser(
            size = SizeProfile.BIG,
            uri = photoUri,
            onClick = { navController.navigate(AppScreem.CurrentUserInfo.createRoute()) },
            isEditable = true,
            onEditClick = {
                if(photoUri == Uri.EMPTY && !isOnProcessTopBt && !isBack){
                    photoPickerLaunch()
                } else {
                    createAcountVM.setIsShotDialogProfile(true)
                }
            }
        )
        TextFieldSecured(
            txt = nombre,
            onValueChange = {
                createAcountVM.setNombre(it)
                nombreFocus.requestFocus()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .focusRequester(nombreFocus),
            label = LABEL_NOMBRE,
            maxLines = 1,
            maxCount = 20,
            isActiveCheckIsEmpty = true,
            textFieldSecuredVM = nombreFieldVM,
        )
        TextFieldSecured(
            txt = email,
            onValueChange = {
                createAcountVM.setEmail(it)
                emailFocus.requestFocus()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .focusRequester(emailFocus),
            label = LABEL_EMAIL,
            maxLines = 1,
            maxCount = 50,
            isActiveCheckIsEmpty = true,
            mask = RegexExpresion.SECURED_SQL_EMAIL,
            textFieldSecuredVM = emailFieldVM
        )
        TextFieldPassword(
            txt = password,
            onValueChange = {
                createAcountVM.setPassword(it)
                passwordFocus.requestFocus()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .focusRequester(passwordFocus),
            label = LABEL_PASSWORD,
            maxCount = 20,
            textFieldPasswordVM = passwordFieldVM
        )
        TextFieldPassword(
            txt = passwordConfirm,
            onValueChange = {
                createAcountVM.setPasswordConfirm(it)
                passwordConfirmFocus.requestFocus()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .focusRequester(passwordConfirmFocus),
            label = LABEL_CONFIRM_PASSWORD,
            maxCount = 20,
            textFieldPasswordVM = passwordConfirmFieldVM
        )
        Button(
            modifier = Modifier.padding(
                top = 8.dp
            ),
            onClick = {
                nombreFieldVM.onCompleteFirstIteration()
                emailFieldVM.onCompleteFirstIteration()
                passwordFieldVM.onCompleteFirstIteration()
                passwordConfirmFieldVM.onCompleteFirstIteration()
                createAcountVM.registerUser(
                    context = context,
                    snackbarHost = snackbarHost,
                    navController = navController
                )
            }
        ) {
            Icon(
                imageVector = Icons.Filled.AppRegistration,
                contentDescription = ""
            )
            Text(LABEL_REGISTRARSE_BT)
        }
    }
}