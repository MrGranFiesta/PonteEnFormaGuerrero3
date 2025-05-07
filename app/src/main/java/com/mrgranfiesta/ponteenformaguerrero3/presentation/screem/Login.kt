package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ActionModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CREATE_ACCOUNT_BT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EMAIL
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_INIT_SESION_BT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_LOGGIN
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_PASSWORD
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_TITLE_APP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.FloatingButtonVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.RegexExpresion
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.FloatingButtonVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TextFieldPasswordVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TextFieldSecuredVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.LoginVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.TextFieldPassword
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.TextFieldSecured
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem

@Composable
fun LoginScreem(
    snackbarHost: SnackbarHostState,
    navController: NavController
) {
    LoginPrepareMenu(navController)
    LoginBody(snackbarHost, navController)
}

@Composable
fun LoginPrepareMenu(
    navController: NavController,
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    floatingButtonVM: FloatingButtonVM = FloatingButtonVMSingle.getFloatingButtonVM()
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        topBarVM.config(
            title = LABEL_LOGGIN,
            icon = IconTopBar.NONE,
            mode = ModeTopBar.TITLE,
            isGeasture = false,
            action = ActionModeTopBar.POP_BACK_STACK,
        )
        floatingButtonVM.resetFloatingButton()
    }
}

@Composable
fun LoginBody(
    snackbarHost: SnackbarHostState,
    navController: NavController,
    loginVM: LoginVM = hiltViewModel()
) {
    val emailFocus = loginVM.getEmailFocusRequester()
    val passwordFocus = loginVM.getPasswordFocusRequester()

    val email by loginVM.email.collectAsState()
    val password by loginVM.password.collectAsState()
    val textFieldSecuredVM = viewModel<TextFieldSecuredVM>(key = "1")
    val textFieldPasswordVM = viewModel<TextFieldPasswordVM>(key = "2")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            text = LABEL_TITLE_APP
        )
        TextFieldSecured(
            txt = email,
            onValueChange = {
                loginVM.setEmail(it)
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
            textFieldSecuredVM = textFieldSecuredVM
        )

        TextFieldPassword(
            txt = password,
            onValueChange = {
                loginVM.setPassword(it)
                passwordFocus.requestFocus()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .focusRequester(passwordFocus),
            label = LABEL_PASSWORD,
            maxCount = 20,
            textFieldPasswordVM = textFieldPasswordVM
        )
        Button(
            modifier = Modifier.padding(top = 8.dp),
            onClick = {
                if (loginVM.isCompleteLoginUser()) {
                    navController.navigate(AppScreem.RutinaListScreem.createRoute()) {
                        popUpTo(AppScreem.Login.createRoute()) { inclusive = true }
                    }
                } else {
                    loginVM.showErrorLoginUser(snackbarHost)
                }

            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Login,
                contentDescription = ""
            )
            Text(LABEL_INIT_SESION_BT)
        }
        Button(
            onClick = {
                loginVM.clearFields()
                textFieldSecuredVM.resetFinishFirstIteration()
                textFieldPasswordVM.resetFinishFirstIteration()
                navController.navigate(AppScreem.CreateAcount.createRoute())
            }
        ) {
            Icon(
                imageVector = Icons.Filled.PersonAdd,
                contentDescription = ""
            )
            Text(LABEL_CREATE_ACCOUNT_BT)
        }
        TextButton(
            onClick = {
                loginVM.loginGuestUser()
                navController.navigate(AppScreem.RutinaListScreem.createRoute()) {
                    popUpTo(AppScreem.Login.createRoute()) { inclusive = true }
                }
            }
        ) {
            Text(
                text = "Entrar como invitado"
            )
        }

    }
}