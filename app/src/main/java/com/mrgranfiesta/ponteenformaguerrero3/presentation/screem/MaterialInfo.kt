package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ActionModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_DELETE_MATERIAL
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_DELETE_MATERIAL_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_DESCRIPCION_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EDITS_MATERIAL_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_MATERIAL_INFO_TITLE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_MATERIAL_PESO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_NO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_SI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.PARAMS_ID
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.core.viewmodel.PopBackEffectVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.MaterialInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.ActionItem
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.FloatingButtonVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.PopBackEffectVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.FloatingButtonVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.MaterialInfoVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CenteredCircularProgressIndicator
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.ImageFromUri
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.SimpleDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.material.info.MaterialInfoUiState

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun ConfMaterialInfo(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    popBackEffectVM: PopBackEffectVM = PopBackEffectVMSingle.getPopBackEffectVM(),
    materialInfoVM: MaterialInfoVM = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val params: String? = navBackStackEntry.arguments?.getString(PARAMS_ID)
    if (params != null) {
        val idEjercicio = params.toLong()
        materialInfoVM.initData(idEjercicio)

        val uiState by produceState<MaterialInfoUiState>(
            initialValue = MaterialInfoUiState.Loading,
            key1 = lifecycle,
            key2 = materialInfoVM
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                materialInfoVM.uiState.collect { value = it }
            }
        }

        when (uiState) {
            is MaterialInfoUiState.ErrorUI -> {
                navController.popBackStack()
            }

            is MaterialInfoUiState.Loading -> {
                CenteredCircularProgressIndicator()
            }

            is MaterialInfoUiState.Success -> {
                val materialDB = (uiState as MaterialInfoUiState.Success).materialDB
                val endTimeDelay by popBackEffectVM.endTimeDelay.collectAsState()

                MaterialInfoPrepareDialog(
                    navController = navController,
                    material = materialDB
                )
                MaterialInfoPrepareMenu(
                    navController = navController,
                    material = materialDB
                )
                MaterialInfo(materialDB)
                BackHandler(true) {
                    if (endTimeDelay < System.currentTimeMillis()) {
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}

@Composable
fun MaterialInfoPrepareDialog(
    navController: NavController,
    material: MaterialInfoDto,
    materialInfoVM: MaterialInfoVM = hiltViewModel()
) {
    val context = LocalContext.current
    val selectDeleteDialog by materialInfoVM.selectDeleteDialog.collectAsState()
    if (selectDeleteDialog)
        SimpleDialog(
            dismissDialog = { materialInfoVM.onChangeSelectDeleteDialog(it) },
            title = LABEL_DELETE_MATERIAL,
            posOpt = LABEL_OPT_SI,
            negOpt = LABEL_OPT_NO
        ) {
            navController.popBackStack()
            materialInfoVM.deleteMaterial(context, material)
        }
}

@Composable
fun MaterialInfoPrepareMenu(
    navController: NavController,
    material: MaterialInfoDto,
    materialInfoVM: MaterialInfoVM = hiltViewModel(),
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    floatingButtonVM: FloatingButtonVM = FloatingButtonVMSingle.getFloatingButtonVM()
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        topBarVM.config(
            title = LABEL_MATERIAL_INFO_TITLE,
            icon = IconTopBar.ARRROW,
            mode = ModeTopBar.TITLE,
            isGeasture = true,
            action = ActionModeTopBar.POP_BACK_STACK
        )
        floatingButtonVM.resetFloatingButton()
    }
    if (Rol.isEditable(CurrentUser.user?.rol) && material.rol != Rol.INIT_DATA_USER) {
        topBarVM.addActionList(
            ActionItem(
                icon = Icons.Filled.Delete,
                name = "Eliminar elemento",
                tooltipText = LABEL_DELETE_MATERIAL_TOOLSTIP,
                action = {
                    materialInfoVM.onChangeSelectDeleteDialog(true)
                }),
            ActionItem(
                icon = Icons.Filled.Edit,
                name = "Editar Elemento",
                tooltipText = LABEL_EDITS_MATERIAL_TOOLSTIP,
                action = {
                    navController.navigate(AppScreem.MaterialFormScreem.createRoute(material.idMaterial))
                })
        )
    }
}

@Composable
fun MaterialInfo(
    material: MaterialInfoDto
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 12.dp,
                end = 12.dp
            )
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = material.nombre,
            modifier = Modifier
                .padding(top = 8.dp)
        )
        ImageFromUri(
            photoUri = material.photoUri,
            contentDescription = "Imagen material"
        )
        if (material.isMaterialWeight) {
            Text(
                text = LABEL_MATERIAL_PESO,
                modifier = Modifier
                    .padding(top = 8.dp)
            )
        }
        if (material.descripcion.isNotEmpty()) {
            Text(
                text = LABEL_DESCRIPCION_COLON,
                modifier = Modifier
                    .padding(top = 16.dp)
            )
        }
        Text(material.descripcion)
    }
}