package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem


import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
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
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ActionModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_DELETE_EJERCICIO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_DELETE_EJERCICIO_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_DESCRIPCION_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EDITS_EJERCICIO_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EJERCICIO_INFO_TITLE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EMPTY_DESCRIPCION
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EMPTY_GRUPO_MUSCULAR
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_GRUPO_MUSCULAR_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_MATERIALES_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_MATERIAL_FOR_EJERCICIO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NIVEL_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_ACTIVO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_INACTIVO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_NO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_SI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SIMETRIA_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.PARAMS_ID
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.core.viewmodel.PopBackEffectVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.ActionItem
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.FloatingButtonVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.PopBackEffectVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.FloatingButtonVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.EjercicioInfoVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CenteredCircularProgressIndicator
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.ImageFromUri
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.NoneRowItem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.RowMaterial
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.TextNivel
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.SimpleDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.ejercicio.info.EjercicioInfoMaterialUiState
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.ejercicio.info.EjercicioInfoUiState

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun ConfEjercicioInfo(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    popBackEffectVM: PopBackEffectVM = PopBackEffectVMSingle.getPopBackEffectVM(),
    ejercicioInfoVM: EjercicioInfoVM = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val params: String? = navBackStackEntry.arguments?.getString(PARAMS_ID)
    if (params != null) {
        val idEjercicio = params.toLong()
        ejercicioInfoVM.initData(idEjercicio)

        val uiStateEjercicio by produceState<EjercicioInfoUiState>(
            initialValue = EjercicioInfoUiState.Loading,
            key1 = lifecycle,
            key2 = ejercicioInfoVM
        ) {
            value = EjercicioInfoUiState.Loading
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                ejercicioInfoVM.uiStateEjercicio.collect { value = it }
            }
        }

        val uiStateMaterial by produceState<EjercicioInfoMaterialUiState>(
            initialValue = EjercicioInfoMaterialUiState.Loading,
            key1 = lifecycle,
            key2 = ejercicioInfoVM
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                ejercicioInfoVM.uiStateMaterial.collect { value = it }
            }
        }

        when {
            uiStateEjercicio is EjercicioInfoUiState.ErrorUI ||
                    uiStateMaterial is EjercicioInfoMaterialUiState.ErrorUI -> {
                navController.popBackStack()
            }

            uiStateEjercicio is EjercicioInfoUiState.Loading ||
                    uiStateMaterial is EjercicioInfoMaterialUiState.Loading -> {
                CenteredCircularProgressIndicator()
            }

            uiStateEjercicio is EjercicioInfoUiState.Success ||
                    uiStateMaterial is EjercicioInfoMaterialUiState.Success -> {
                val ejercicioDB = (uiStateEjercicio as EjercicioInfoUiState.Success).ejercicioDB
                val materialListDB =
                    (uiStateMaterial as EjercicioInfoMaterialUiState.Success).materialDB
                val endTimeDelay by popBackEffectVM.endTimeDelay.collectAsState()
                EjercicioInfoPrepareDialog(
                    navController = navController,
                    ejercicio = ejercicioDB
                )
                EjercicioInfoPrepareMenu(
                    navController = navController,
                    ejercicio = ejercicioDB
                )
                EjercicioInfoLazyColumn(
                    ejercicio = ejercicioDB,
                    materialList = materialListDB
                )
                BackHandler(true) {
                    if (endTimeDelay < System.currentTimeMillis()) {
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun ConfEjercicioInfoNoOption(
    navBackStackEntry: NavBackStackEntry,
    ejercicioInfoVM: EjercicioInfoVM = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val params = navBackStackEntry.arguments?.getString(PARAMS_ID)
    if (params != null) {
        val idEjercicio = params.toLong()
        ejercicioInfoVM.initData(idEjercicio)

        val uiStateEjercicio by produceState<EjercicioInfoUiState>(
            initialValue = EjercicioInfoUiState.Loading,
            key1 = lifecycle,
            key2 = ejercicioInfoVM
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                ejercicioInfoVM.uiStateEjercicio.collect { value = it }
            }
        }

        val uiStateMaterial by produceState<EjercicioInfoMaterialUiState>(
            initialValue = EjercicioInfoMaterialUiState.Loading,
            key1 = lifecycle,
            key2 = ejercicioInfoVM
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                ejercicioInfoVM.uiStateMaterial.collect { value = it }
            }
        }

        when {
            uiStateEjercicio is EjercicioInfoUiState.ErrorUI ||
                    uiStateMaterial is EjercicioInfoMaterialUiState.ErrorUI -> {
                //NOTHING TO DO
            }

            uiStateEjercicio is EjercicioInfoUiState.Loading ||
                    uiStateMaterial is EjercicioInfoMaterialUiState.Loading -> {
                CircularProgressIndicator()
            }

            uiStateEjercicio is EjercicioInfoUiState.Success ||
                    uiStateMaterial is EjercicioInfoMaterialUiState.Success -> {
                EjercicioInfoPrepareMenuNoOptions()

                //No tocar, sino no recompone, necesita elemento externo que recomponga
                //topBarVM.clearActionList()
                EjercicioInfoLazyColumn(
                    ejercicio = (uiStateEjercicio as EjercicioInfoUiState.Success).ejercicioDB,
                    materialList = (uiStateMaterial as EjercicioInfoMaterialUiState.Success).materialDB,
                )
            }
        }
    }
}

@Composable
fun EjercicioInfoPrepareDialog(
    navController: NavController,
    ejercicio: EjercicioInfoDto,
    ejercicioInfoVM: EjercicioInfoVM = hiltViewModel()
) {
    val context = LocalContext.current
    val selectDeleteDialog by ejercicioInfoVM.selectDeleteDialog.collectAsState()
    if (selectDeleteDialog)
        SimpleDialog(
            dismissDialog = { ejercicioInfoVM.onChangeSelectDeleteDialog(it) },
            title = LABEL_DELETE_EJERCICIO,
            posOpt = LABEL_OPT_SI,
            negOpt = LABEL_OPT_NO
        ) {
            navController.popBackStack()
            ejercicioInfoVM.deleteEjercicio(ejercicio, context)
        }
}

@Composable
fun EjercicioInfoPrepareMenu(
    navController: NavController,
    ejercicio: EjercicioInfoDto,
    ejercicioInfoVM: EjercicioInfoVM = hiltViewModel(),
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    floatingButtonVM: FloatingButtonVM = FloatingButtonVMSingle.getFloatingButtonVM()
) {
    LaunchedEffect(true) {
        topBarVM.config(
            title = LABEL_EJERCICIO_INFO_TITLE,
            icon = IconTopBar.ARRROW,
            mode = ModeTopBar.TITLE,
            isGeasture = true,
            action = ActionModeTopBar.POP_BACK_STACK,
        )
        floatingButtonVM.resetFloatingButton()
    }
    if (Rol.isEditable(ejercicio.rol) && ejercicio.rol != Rol.INIT_DATA_USER) {
        topBarVM.addActionList(
            ActionItem(
                icon = Icons.Filled.Delete,
                tooltipText = LABEL_DELETE_EJERCICIO_TOOLSTIP,
                name = "Eliminar elemento",
                action = {
                    ejercicioInfoVM.onChangeSelectDeleteDialog(true)
                }),
            ActionItem(
                icon = Icons.Filled.Edit,
                tooltipText = LABEL_EDITS_EJERCICIO_TOOLSTIP,
                name = "Editar Elemento",
                action = {
                    navController.navigate(AppScreem.EjercicioFormScreem.createRoute(ejercicio.idEjercicio))
                })
        )
    }
}

@Composable
fun EjercicioInfoPrepareMenuNoOptions(
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM()
) {
    LaunchedEffect(true) {
        topBarVM.config(
            title = LABEL_EJERCICIO_INFO_TITLE,
            icon = IconTopBar.ARRROW,
            mode = ModeTopBar.TITLE,
            isGeasture = true,
            action = ActionModeTopBar.POP_BACK_STACK,
        )
    }
    topBarVM.clearActionList()
}

@Composable
fun EjercicioInfoLazyColumn(
    ejercicio: EjercicioInfoDto,
    materialList: List<RowMaterialDto>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 12.dp,
                end = 12.dp,
                bottom = 8.dp,
            )
    ) {
        item {
            EjercicioInfo(ejercicio)
        }
        item {
            Text(
                text = LABEL_MATERIALES_COLON,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 16.dp,
                        bottom = 8.dp
                    )
            )
        }
        item {
            if (materialList.isEmpty()) {
                NoneRowItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 8.dp
                        ),
                    text = LABEL_MATERIAL_FOR_EJERCICIO,
                    icon = Icons.Filled.Pending
                )
            }
        }
        items(materialList) {
            RowMaterial(
                nombre = it.nombre,
                photoUri = it.photoUri
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EjercicioInfo(ejercicio: EjercicioInfoDto) {
    Column {
        Text(
            text = ejercicio.nombre,
            modifier = Modifier
                .padding(top = 8.dp)
        )
        ImageFromUri(
            photoUri = ejercicio.photoUri,
            contentDescription = "Imagen ejecicio"
        )
        Text(
            text = LABEL_NIVEL_COLON,
            modifier = Modifier
                .padding(top = 16.dp)
        )
        TextNivel(
            ejercicio.nivel,
            modifier = Modifier
                .padding(
                    top = 16.dp,
                )
        )
        Text(
            text = LABEL_GRUPO_MUSCULAR_COLON,
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    bottom = 8.dp
                )
        )
        if (ejercicio.musculoSet.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                ejercicio.musculoSet.forEach {
                    AssistChip(
                        modifier = Modifier.wrapContentWidth(),
                        onClick = { },
                        label = { Text(it.toString()) }
                    )
                }
            }
        } else {
            AssistChip(
                modifier = Modifier.wrapContentWidth(),
                onClick = { },
                label = { Text(LABEL_EMPTY_GRUPO_MUSCULAR) }
            )
        }
        Text(
            text = LABEL_SIMETRIA_COLON + if (ejercicio.isSimetria) LABEL_OPT_ACTIVO else LABEL_OPT_INACTIVO,
            modifier = Modifier.padding(
                top = 16.dp
            )
        )

        Text(
            text = LABEL_DESCRIPCION_COLON,
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    bottom = 8.dp,
                )
        )
        if (ejercicio.descripcion.isNotEmpty()) {
            Text(
                text = ejercicio.descripcion,
            )
        } else {
            Text(
                text = LABEL_EMPTY_DESCRIPCION,
            )
        }
    }
}