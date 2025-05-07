package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ActionModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CALENTAMIENTO_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_DELETE_RUTINA
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_DELETE_RUTINA_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_DESCRIPCION_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EDIT_RUTINA_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EJERCICIO_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EMPTY_DESCRIPCION
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EMPTY_GRUPO_MUSCULAR
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EMPTY_STEP_LIST
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ESTIRAMIENTOS_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_GRUPO_MUSCULAR_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_MOVILIDAD_ARTICULAR_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NIVEL_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_NO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_SI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_RUTINA_INFO_TITLE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_START_RUTINA_BT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.PARAMS_ID
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TimerFormmertText
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RutinaInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.step.RowStepInfoWithConfMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.ActionItem
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.FloatingButtonVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.DtoMapper
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.FloatingButtonVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.RowStepVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.RutinaInfoVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CenteredCircularProgressIndicator
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.EditOptionsRowStepView
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.NoneRowItem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.RowStepView
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.TextNivel
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.MaterialListDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.SimpleDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.rutina.info.RutinaInfoStepUiState
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.rutina.info.RutinaInfoUiState

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun ConfRutinaInfo(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    snackbarHost: SnackbarHostState,
    rutinaInfoVM: RutinaInfoVM = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val params = navBackStackEntry.arguments?.getString(PARAMS_ID)
    if (params != null) {
        val idRutina = params.toLong()
        rutinaInfoVM.initData(idRutina)

        val uiStateRutina by produceState<RutinaInfoUiState>(
            initialValue = RutinaInfoUiState.Loading,
            key1 = lifecycle,
            key2 = rutinaInfoVM
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                rutinaInfoVM.uiStateRutina.collect { value = it }
            }
        }

        val uiStateStep by produceState<RutinaInfoStepUiState>(
            initialValue = RutinaInfoStepUiState.Loading,
            key1 = lifecycle,
            key2 = rutinaInfoVM
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                rutinaInfoVM.uiStateStep.collect { value = it }
            }
        }

        when {
            uiStateRutina is RutinaInfoUiState.ErrorUI ||
                    uiStateStep is RutinaInfoStepUiState.ErrorUI -> {
                navController.popBackStack()
            }

            uiStateRutina is RutinaInfoUiState.Loading ||
                    uiStateStep is RutinaInfoStepUiState.Loading -> {
                CenteredCircularProgressIndicator()
            }

            uiStateRutina is RutinaInfoUiState.Success ||
                    uiStateStep is RutinaInfoStepUiState.Success -> {
                val rutinaDB = (uiStateRutina as RutinaInfoUiState.Success).rutinaDB
                val stepListDB = (uiStateStep as RutinaInfoStepUiState.Success).stepListDB
                RutinaInfoPrepareDialog(
                    navController = navController,
                    rutinaDelete = rutinaDB,
                    stepListDB = stepListDB
                )
                RutinaInfoPrepareMenu(
                    navController = navController,
                    rutinaDB = rutinaDB
                )
                RutinaInfoLazyColumn(
                    navController = navController,
                    rutinaOrigen = rutinaDB,
                    stepList = stepListDB,
                    snackbarHost = snackbarHost
                )
            }
        }
    }
}

@Composable
fun RutinaInfoPrepareDialog(
    navController: NavController,
    stepListDB: List<RowStepInfoWithConfMaterialDto>,
    rutinaInfoVM: RutinaInfoVM = hiltViewModel(),
    rutinaDelete: RutinaInfoDto
) {
    val selectDeleteDialog by rutinaInfoVM.selectDeleteDialog.collectAsState()
    val showMaterialDialog by rutinaInfoVM.showMaterialDialog.collectAsState()
    val cursor by rutinaInfoVM.cursor.collectAsState()

    if (selectDeleteDialog) {
        SimpleDialog(
            dismissDialog = { rutinaInfoVM.onChangeSelectDeleteDialog(false) },
            title = LABEL_DELETE_RUTINA,
            posOpt = LABEL_OPT_SI,
            negOpt = LABEL_OPT_NO
        ) {
            rutinaInfoVM.deleteRutina(rutinaDelete)
            navController.popBackStack()
        }
    }

    if (showMaterialDialog) {
        MaterialListDialog(
            dismissDialog = { rutinaInfoVM.onChangeShowMaterialDialog(false) },
            confMaterialList = stepListDB[cursor].confMaterialList.map {
                DtoMapper.toRowMaterialWithConfDto(it)
            }
        )
    }
}

@Composable
fun RutinaInfoPrepareMenu(
    navController: NavController,
    rutinaDB: RutinaInfoDto,
    rutinaInfoVM: RutinaInfoVM = hiltViewModel(),
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    floatingButtonVM: FloatingButtonVM = FloatingButtonVMSingle.getFloatingButtonVM(),
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(currentBackStackEntry) {
        topBarVM.config(
            title = LABEL_RUTINA_INFO_TITLE,
            icon = IconTopBar.ARRROW,
            mode = ModeTopBar.TITLE,
            isGeasture = true,
            action = ActionModeTopBar.POP_BACK_STACK
        )
        floatingButtonVM.resetFloatingButton()
    }
    if (Rol.isEditable(CurrentUser.user?.rol) && rutinaDB.rol != Rol.INIT_DATA_USER) {
        topBarVM.addActionList(
            ActionItem(
                icon = Icons.Filled.Delete,
                name = "Eliminar elemento",
                tooltipText = LABEL_DELETE_RUTINA_TOOLSTIP,
                action = {
                    rutinaInfoVM.onChangeSelectDeleteDialog(true)
                }),
            ActionItem(
                icon = Icons.Filled.Edit,
                name = "Modificar Elemento",
                tooltipText = LABEL_EDIT_RUTINA_TOOLSTIP,
                action = {
                    navController.navigate(AppScreem.RutinaFormScreem.createRoute(rutinaDB.idRutina))
                }
            )
        )
    }
}


@Composable
fun RutinaInfoLazyColumn(
    navController: NavController,
    rutinaOrigen: RutinaInfoDto,
    stepList: List<RowStepInfoWithConfMaterialDto>,
    snackbarHost: SnackbarHostState,
    rutinaInfoVM: RutinaInfoVM = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 12.dp,
                end = 12.dp
            )
    ) {
        LazyColumn {
            item {
                RutinaInfo(
                    navController = navController,
                    rutinaOrigen = rutinaOrigen,
                    stepList = stepList,
                    snackbarHost = snackbarHost
                )
            }
            item {
                if (stepList.isEmpty()) {
                    NoneRowItem(
                        text = LABEL_EMPTY_STEP_LIST,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = 8.dp
                            ),
                        icon = Icons.Filled.Pending
                    )
                }
            }
            itemsIndexed(stepList) { index, it ->
                RowStepView(
                    onClickRow = {
                        navController.navigate(
                            route = AppScreem.EjercicioInfoScreemNoOption.createRoute(it.idEjercicio)
                        )
                    },
                    dataSource = {
                        rutinaInfoVM.getEjercicioNameAndPhoto(it.idEjercicio)
                    },
                    serie = it.serie,
                    cantidad = it.cantidad,
                    tipo = it.tipo,
                    editOptions = EditOptionsRowStepView(
                        isShowMaterial = it.confMaterialList.isNotEmpty(),
                        showMaterial = {
                            rutinaInfoVM.setCursor(index)
                            rutinaInfoVM.onChangeShowMaterialDialog(true)
                        }
                    ),
                    rowStepVM = viewModel(
                        key = it.toString(),
                        factory = RowStepVM.createRowStepVM()
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RutinaInfo(
    navController: NavController,
    rutinaOrigen: RutinaInfoDto,
    stepList: List<RowStepInfoWithConfMaterialDto>,
    snackbarHost: SnackbarHostState,
    rutinaInfoVM: RutinaInfoVM = hiltViewModel(),
) {
    Text(
        text = rutinaOrigen.nombre,
        modifier = Modifier
            .padding(top = 8.dp)
    )
    Button(
        onClick = {
            if (stepList.isNotEmpty()) {
                navController.navigate(
                    route = AppScreem.StartRutinaQuestionScreem.createRoute(rutinaOrigen.idRutina)
                )
            } else {
                rutinaInfoVM.showErrorEmptyStepRutina(snackbarHost)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp
            )
    ) {
        Icon(
            imageVector = Icons.Filled.RocketLaunch,
            contentDescription = ""
        )
        Text(text = LABEL_START_RUTINA_BT)
    }
    Text(
        text = LABEL_NIVEL_COLON,
        modifier = Modifier
            .padding(top = 16.dp)
    )
    TextNivel(
        rutinaOrigen.nivel,
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
    if (rutinaOrigen.musculoSet.isNotEmpty()) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            rutinaOrigen.musculoSet.forEach {
                AssistChip(
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
        text = "$LABEL_CALENTAMIENTO_COLON ${AnswerState.getOptionTxt(rutinaOrigen.calentamiento)}",
        modifier = Modifier
            .padding(
                top = 16.dp,
            )
    )
    Text(
        text = "$LABEL_MOVILIDAD_ARTICULAR_COLON ${AnswerState.getOptionTxt(rutinaOrigen.movArticular)}",
        modifier = Modifier
            .padding(
                top = 16.dp,
            )
    )
    Text(
        text = "$LABEL_ESTIRAMIENTOS_COLON ${AnswerState.getOptionTxt(rutinaOrigen.estiramiento)}",
        modifier = Modifier
            .padding(
                top = 16.dp,
            )
    )
    Text(
        text = TimerFormmertText.getDescansoFormatter(rutinaOrigen.descanso),
        modifier = Modifier
            .padding(
                top = 16.dp,
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
    if (rutinaOrigen.descripcion.isNotEmpty()) {
        Text(
            text = rutinaOrigen.descripcion,
        )
    } else {
        Text(
            text = LABEL_EMPTY_DESCRIPCION,
        )
    }
    Text(
        text = LABEL_EJERCICIO_COLON,
        modifier = Modifier
            .padding(
                top = 16.dp,
                bottom = 8.dp
            )
    )
}