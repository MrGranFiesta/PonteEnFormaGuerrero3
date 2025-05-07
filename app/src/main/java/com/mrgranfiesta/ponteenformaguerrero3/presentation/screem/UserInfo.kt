package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ActionModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CANCEL_PREMIUM
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CHANGE_PASSWORD
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_DELETE_USER
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_DELETE_USER_BT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_DELETE_USER_WARNING
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EMAIL_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NOMBRE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_NO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_SI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SUSCRIPCTION_PREMIUM
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_TRY_FREE_PREMIUM_WARNING
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_USER_INFO_TITLE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_YOU_NOMBRE_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.SizeProfile
import com.mrgranfiesta.ponteenformaguerrero3.domain.core.viewmodel.PopBackEffectVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.FloatingButtonVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.PopBackEffectVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.FloatingButtonVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.UserInfoVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.PhotoProfileUser
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.UsernameUser
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.SelectOptionPhotoDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.SimpleDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.TextFieldDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun ConfCurrentUserInfo(
    navController: NavController,
    userInfoVM: UserInfoVM = hiltViewModel(),
    popBackEffectVM: PopBackEffectVM = PopBackEffectVMSingle.getPopBackEffectVM(),
) {
    val context = LocalContext.current

    userInfoVM.initData()
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                userInfoVM.updatePhotoUri(
                    photoUri = uri,
                    context = context
                )
            }
        }
    val photoPickerLaunch = {
        launcher.launch(
            PickVisualMediaRequest(
                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }
    val endTimeDelay by popBackEffectVM.endTimeDelay.collectAsState()
    CurrentUserInfoPrepareDialog(
        photoPickerLaunch = photoPickerLaunch,
        navController = navController
    )
    CurrentUserInfoPrepareMenu()
    UserInfo(
        navController = navController,
        photoPickerLaunch = photoPickerLaunch
    )
    BackHandler(true) {
        if (endTimeDelay < System.currentTimeMillis()) {
            navController.popBackStack()
        }
    }
}

@Composable
fun CurrentUserInfoPrepareMenu(
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    floatingButtonVM: FloatingButtonVM = FloatingButtonVMSingle.getFloatingButtonVM()
) {
    LaunchedEffect(true) {
        topBarVM.config(
            title = LABEL_USER_INFO_TITLE,
            icon = IconTopBar.ARRROW,
            mode = ModeTopBar.TITLE,
            isGeasture = true,
            action = ActionModeTopBar.POP_BACK_STACK
        )
        floatingButtonVM.resetFloatingButton()
    }
}

@Composable
fun CurrentUserInfoPrepareDialog(
    photoPickerLaunch: () -> Unit,
    navController: NavController,
    userInfoVM: UserInfoVM = hiltViewModel()
) {
    val context = LocalContext.current

    val selectDeleteDialog by userInfoVM.selectDeleteDialog.collectAsState()
    val isShowDialogProfile by userInfoVM.isShowDialogProfile.collectAsState()
    val isShowEditUsernameDialog by userInfoVM.isShowEditUsernameDialog.collectAsState()
    val isShowJoinPremiumDialog by userInfoVM.isShowJoinPremiumDialog.collectAsState()
    val isShowCancelPremiumDialog by userInfoVM.isShowCancelPremiumDialog.collectAsState()
    val username by userInfoVM.username.collectAsState()

    if (selectDeleteDialog) {
        SimpleDialog(
            dismissDialog = { userInfoVM.onChangeSelectDeleteDialog(it) },
            title = "$LABEL_DELETE_USER \n $LABEL_DELETE_USER_WARNING",
            posOpt = LABEL_OPT_SI,
            negOpt = LABEL_OPT_NO
        ) {
            userInfoVM.deleteUser(
                navController = navController,
                context = context
            )
        }
    }
    if (isShowDialogProfile) {
        SelectOptionPhotoDialog(
            dismissDialog = {
                userInfoVM.onChangeShowDialogProfile(false)
            },
            onClickDelete = {
                userInfoVM.setPhotoUri(Uri.EMPTY)
            },
            onClickOpenAlbum = {
                photoPickerLaunch()
            }
        )
    }
    if (isShowEditUsernameDialog) {
        TextFieldDialog(
            dismissDialog = { userInfoVM.onChangeShowEditUsernameDialog(false) },
            posOptActions = { userInfoVM.updateUsername(it) },
            label = LABEL_NOMBRE,
            text = username
        )
    }

    if (isShowJoinPremiumDialog) {
        SimpleDialog(
            dismissDialog = { userInfoVM.onChangeShowJoinPremiumDialog(it) },
            title = "$LABEL_SUSCRIPCTION_PREMIUM \n\n $LABEL_TRY_FREE_PREMIUM_WARNING",
            posOpt = LABEL_OPT_SI,
            negOpt = LABEL_OPT_NO
        ) {
            userInfoVM.joinPremium()
        }
    }
    if (isShowCancelPremiumDialog) {
        SimpleDialog(
            dismissDialog = { userInfoVM.onChangeShowCancelPremiumDialog(it) },
            title = LABEL_CANCEL_PREMIUM,
            posOpt = LABEL_OPT_SI,
            negOpt = LABEL_OPT_NO
        ) {
            userInfoVM.cancelPremium()
        }
    }
}

@Composable
fun UserInfo(
    navController: NavController,
    photoPickerLaunch: () -> Unit,
    userInfoVM: UserInfoVM = hiltViewModel()
) {
    val photoUri by userInfoVM.photoUri.collectAsState()
    val username by userInfoVM.username.collectAsState()
    val email by userInfoVM.email.collectAsState()
    val rol by userInfoVM.rol.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 12.dp,
                end = 12.dp
            )
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PhotoProfileUser(
            size = SizeProfile.BIG,
            uri = photoUri,
            isEditable = true,
            onEditClick = {
                if (photoUri == Uri.EMPTY) {
                    photoPickerLaunch()
                } else {
                    userInfoVM.onChangeShowDialogProfile(true)
                }

            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = LABEL_YOU_NOMBRE_COLON,
                modifier = Modifier
                    .padding(top = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                UsernameUser(
                    username = username,
                    rol = rol
                )
                IconButton(
                    onClick = {
                        userInfoVM.onChangeShowEditUsernameDialog(true)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = ""
                    )
                }
            }

            Text(
                text = LABEL_EMAIL_COLON,
                modifier = Modifier
                    .padding(top = 24.dp)
            )
            Text(
                text = email,
                modifier = Modifier
                    .padding(top = 16.dp)
            )
        }
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (rol == Rol.PREMIUN_USER) {
                    userInfoVM.onChangeShowCancelPremiumDialog(true)
                } else {
                    userInfoVM.onChangeShowJoinPremiumDialog(true)
                }
            }
        ) {
            Text(userInfoVM.getButtonTextForSubscription(rol))
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(AppScreem.ChangePassword.createRoute())
            }
        ) {
            Text(LABEL_CHANGE_PASSWORD)
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                userInfoVM.onChangeSelectDeleteDialog(true)
            }
        ) {
            Text(LABEL_DELETE_USER_BT)
        }
    }
}