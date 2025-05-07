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
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material3.AssistChip
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
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ActionModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CALENTAMIENTO_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_DELETE_STAT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_DESCRIPCION_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EJERCICOS_REALIZADOS
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EMPTY_STEP_LIST
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ENFOCADOS_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ESTIRAMIENTOS_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_MOVILIDAD_ARTICULAR_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_MUSCULOS_ENTRENADOS_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_NO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_SI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OTROS_MUSCULOS_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_STAT_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_STAT_INFO_TITLE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_STAT_RUTINA_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_TIEMPO_TOTAL
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.PARAMS_ID
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot.StepSnapshotInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.stat.StatRutinaInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.ActionItem
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.FloatingButtonVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.DateUtils
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.DtoMapper
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.FloatingButtonVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.RowStepVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.StatRutinaInfoVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.StadisticsUtilsVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CenteredCircularProgressIndicator
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.EditOptionsRowStepView
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.NoneRowItem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.RowStepView
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.MaterialListDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.SimpleDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.stat.info.rutina.StatInfoRutinaStatUiState
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.stat.info.rutina.StatInfoStepUiState

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun ConfStatRutinaInfo(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    statRutinaInfoVM: StatRutinaInfoVM = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val params = navBackStackEntry.arguments?.getString(PARAMS_ID)
    if (params != null) {
        val idRutina = params.toLong()
        statRutinaInfoVM.initData(idRutina)

        val uiStateStatRutina by produceState<StatInfoRutinaStatUiState>(
            initialValue = StatInfoRutinaStatUiState.Loading,
            key1 = lifecycle,
            key2 = statRutinaInfoVM
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                statRutinaInfoVM.uiStateStatRutina.collect { value = it }
            }
        }

        val uiStateStepList by produceState<StatInfoStepUiState>(
            initialValue = StatInfoStepUiState.Loading,
            key1 = lifecycle,
            key2 = statRutinaInfoVM
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                statRutinaInfoVM.uiStateStepSnapshot.collect { value = it }
            }
        }

        when {
            uiStateStatRutina is StatInfoRutinaStatUiState.ErrorUI || uiStateStepList is StatInfoStepUiState.ErrorUI -> {
                navController.popBackStack()
            }

            uiStateStatRutina is StatInfoRutinaStatUiState.Loading || uiStateStepList is StatInfoStepUiState.Loading -> {
                CenteredCircularProgressIndicator()
            }

            uiStateStatRutina is StatInfoRutinaStatUiState.Success || uiStateStepList is StatInfoStepUiState.Success -> {
                val statRutinaDB =
                    (uiStateStatRutina as StatInfoRutinaStatUiState.Success).statRutinaListDB
                val stepListDB =
                    (uiStateStepList as StatInfoStepUiState.Success).stepListDB

                StatRutinaInfoPrepareMenu()
                StatRutinaInfoPrepareDialog(
                    navController = navController,
                    stepListDB = stepListDB,
                    statDelete = statRutinaDB
                )
                StatRutinaInfoLazyColumn(
                    navController = navController,
                    statRutinaOrigen = statRutinaDB,
                    stepListDB = stepListDB
                )
            }
        }
    }
}

@Composable
fun StatRutinaInfoPrepareMenu(
    statRutinaInfoVM: StatRutinaInfoVM = hiltViewModel(),
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    floatingButtonVM: FloatingButtonVM = FloatingButtonVMSingle.getFloatingButtonVM()
) {
    LaunchedEffect(true) {
        topBarVM.config(
            title = LABEL_STAT_INFO_TITLE,
            icon = IconTopBar.ARRROW,
            mode = ModeTopBar.TITLE,
            isGeasture = true,
            action = ActionModeTopBar.POP_BACK_STACK
        )
        floatingButtonVM.resetFloatingButton()
    }
    topBarVM.addActionList(
        ActionItem(
            icon = Icons.Filled.Delete,
            name = "Eliminar elemento",
            tooltipText = LABEL_STAT_RUTINA_TOOLSTIP,
            action = {
                statRutinaInfoVM.onChangeSelectDeleteDialog(true)
            }),
    )
}

@Composable
fun StatRutinaInfoPrepareDialog(
    navController: NavController,
    stepListDB: List<StepSnapshotInfoDto>,
    statDelete: StatRutinaInfoDto,
    statRutinaInfoVM: StatRutinaInfoVM = hiltViewModel()
) {
    val selectDeleteDialog by statRutinaInfoVM.selectDeleteDialog.collectAsState()
    val showMaterialDialog by statRutinaInfoVM.showMaterialDialog.collectAsState()
    val cursor by statRutinaInfoVM.cursor.collectAsState()

    if (selectDeleteDialog) {
        SimpleDialog(
            dismissDialog = { statRutinaInfoVM.onChangeSelectDeleteDialog(false) },
            title = LABEL_DELETE_STAT,
            posOpt = LABEL_OPT_SI,
            negOpt = LABEL_OPT_NO
        ) {
            navController.popBackStack()
            statRutinaInfoVM.deleteStat(statDelete)
        }
    }

    if (showMaterialDialog) {
        MaterialListDialog(
            dismissDialog = { statRutinaInfoVM.onChangeShowMaterialDialog(false) },
            confMaterialList = stepListDB[cursor].confMaterialList.map {
                DtoMapper.toRowMaterialWithConfDto(it)
            }
        )
    }
}

@Composable
fun StatRutinaInfoLazyColumn(
    navController: NavController,
    statRutinaOrigen: StatRutinaInfoDto,
    stepListDB: List<StepSnapshotInfoDto>,
    statRutinaInfoVM: StatRutinaInfoVM = hiltViewModel()
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
                StatRutinaInfo(
                    statRutinaOrigen = statRutinaOrigen,
                    stepList = stepListDB
                )
            }
            item {
                if (stepListDB.isEmpty()) {
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
            itemsIndexed(stepListDB) { index, step ->
                RowStepView(
                    onClickRow = {
                        navController.navigate(
                            route = AppScreem.StatEjercicioInfoScreem.createRoute(step.idEjercicioSnapshot)
                        )
                    },
                    dataSource = {
                        statRutinaInfoVM.getEjercicioNameAndPhoto(step.idEjercicioSnapshot)
                    },
                    serie = step.serie,
                    cantidad = step.cantidad,
                    tipo = step.tipo,
                    editOptions = EditOptionsRowStepView(
                        isShowMaterial = step.confMaterialList.isNotEmpty(),
                        showMaterial = {
                            statRutinaInfoVM.setCursor(index)
                            statRutinaInfoVM.onChangeShowMaterialDialog(true)
                        }
                    ),
                    rowStepVM = viewModel(
                        key = step.toString(),
                        factory = RowStepVM.createRowStepVM()
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StatRutinaInfo(
    statRutinaOrigen: StatRutinaInfoDto,
    stepList: List<StepSnapshotInfoDto>,
    stadisticsUtilsVM: StadisticsUtilsVM = hiltViewModel()
) {
    Text(
        text = statRutinaOrigen.nombre,
        modifier = Modifier
            .padding(top = 8.dp)
    )
    Text(
        text = LABEL_STAT_COLON,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        text = "$LABEL_TIEMPO_TOTAL ${
            DateUtils.getMinusFormat(
                initTime = statRutinaOrigen.dateInit,
                endTime = statRutinaOrigen.dateEnd
            )
        }",
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        text = "$LABEL_EJERCICOS_REALIZADOS ${stepList.size}",
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        text = LABEL_MUSCULOS_ENTRENADOS_COLON,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        text = LABEL_ENFOCADOS_COLON,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        stadisticsUtilsVM.calculateMuscleRutina(statRutinaOrigen.musculoSet).forEach {
            AssistChip(
                modifier = Modifier.wrapContentWidth(),
                onClick = { },
                label = { Text(it.toString()) }
            )
        }
    }
    if (
        stadisticsUtilsVM.minusMusculoAndSorted(
            minuendo = stepList.flatMap { it.musculoSet }.toSet(),
            sustraendo = statRutinaOrigen.musculoSet,
        ).isNotEmpty()
    ) {
        Text(
            text = LABEL_OTROS_MUSCULOS_COLON,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            stadisticsUtilsVM.minusMusculoAndSorted(
                minuendo = stepList.flatMap { it.musculoSet }.toSet(),
                sustraendo = statRutinaOrigen.musculoSet,
            ).forEach {
                AssistChip(
                    modifier = Modifier.wrapContentWidth(),
                    onClick = { },
                    label = { Text(it.toString()) }
                )
            }
        }
    }
    Text(
        text = "$LABEL_CALENTAMIENTO_COLON ${stadisticsUtilsVM.isDoneTxt(statRutinaOrigen.isCalentamientoDone)}",
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        text = "$LABEL_MOVILIDAD_ARTICULAR_COLON ${stadisticsUtilsVM.isDoneTxt(statRutinaOrigen.isMovArticularDone)}",
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        text = "$LABEL_ESTIRAMIENTOS_COLON ${stadisticsUtilsVM.isDoneTxt(statRutinaOrigen.isEstiramientoDone)}",
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        text = LABEL_DESCRIPCION_COLON,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        text = statRutinaOrigen.descripcion,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}