package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CLOSE_SESION_BT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CONFIRM_CLOSE_SESION
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_NO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_SI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.SizeProfile
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.DrawerVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.SimpleDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.currentRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Drawer(
    scope: CoroutineScope,
    drawerState: DrawerState,
    navController: NavController,
    drawerVM: DrawerVM = hiltViewModel()
) {
    val isEditableProfile by drawerVM.isEditableProfile.collectAsState()
    val optList by drawerVM.optList.collectAsState()

    LaunchedEffect(CurrentUser.user) {
        drawerVM.initData()
    }

    val closeDrawer = {
        scope.launch {
            drawerState.close()
        }
    }

    val navigateInfoUser = {
        if (CurrentUser.user?.rol != Rol.GUEST) {
            closeDrawer()
            navController.navigate(AppScreem.CurrentUserInfo.createRoute())
        }
    }
    DrawerDialog(navController)
    Column {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PhotoProfileUser(
                size = SizeProfile.SMALL,
                uri = CurrentUser.user?.photoUri ?: Uri.EMPTY,
                onClick = {
                    navigateInfoUser()
                },
                isEditable = isEditableProfile,
                onEditClick = {
                    navigateInfoUser()
                },
                editableIcon = Icons.Filled.Edit
            )
            UsernameUser(
                Modifier.padding(top = 16.dp),
                username = CurrentUser.user?.username,
                rol = CurrentUser.user?.rol
            )

        }

        val currentRoute = currentRoute(navController)

        optList.forEach { item ->
            DrawerItem(item = item, selected = currentRoute == item.route) {
                navController.navigate(item.route) {
                    launchSingleTop = true
                    closeDrawer()
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            onClick = {
                closeDrawer()
                drawerVM.setCloseSesionDialog(true)
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Logout,
                contentDescription = ""
            )
            Text(LABEL_CLOSE_SESION_BT)
        }
    }
}

@Composable
fun DrawerItem(
    item: AppScreem,
    selected: Boolean,
    onItemClick: (AppScreem) -> Unit
) {
    val primary: Color = MaterialTheme.colorScheme.primary
    val secondary: Color = MaterialTheme.colorScheme.secondary
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(6.dp)
            .clip(RoundedCornerShape(12))
            .background(if (selected) secondary.copy(alpha = 0.25f) else Color.Transparent)
            .padding(8.dp)
            .clickable { onItemClick(item) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            imageVector = item.imageVector,
            contentDescription = item.title,
            tint = if (selected) primary else Color.Gray
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = item.title,
            style = TextStyle(fontSize = 18.sp),
            color = if (selected) primary else Color.Unspecified
        )
    }
}

@Composable
fun DrawerDialog(
    navController: NavController,
    drawerVM: DrawerVM = hiltViewModel()
){
    val closeSesionDialog by drawerVM.closeSesionDialog.collectAsState()

    if (closeSesionDialog) {
        SimpleDialog(
            dismissDialog = { drawerVM.setCloseSesionDialog(false) },
            title = LABEL_CONFIRM_CLOSE_SESION,
            posOpt = LABEL_OPT_SI,
            negOpt = LABEL_OPT_NO
        ) {
            drawerVM.logout(navController)
        }
    }
}