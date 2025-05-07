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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ActionModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_DESCRIPCION_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EMPTY_DESCRIPCION
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EMPTY_GRUPO_MUSCULAR
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EMPTY_STEP_LIST
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_GRUPO_MUSCULAR_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NIVEL_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_ACTIVO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_INACTIVO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SIMETRIA_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_STAT_INFO_TITLE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.PARAMS_ID
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot.EjercicioSnapshotInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot.MaterialWithConfSnapshotDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.FloatingButtonVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.FloatingButtonVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.StatEjercicioInfoVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CenteredCircularProgressIndicator
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.ImageFromUri
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.NoneRowItem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.RowMaterialWithConf
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.TextNivel
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.stat.info.ejercicio.StatInfoEjercicioMaterialStatUiState
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.stat.info.ejercicio.StatInfoEjercicioStatUiState

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun ConfStatEjercicioInfo(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    statEjercicioInfoVM: StatEjercicioInfoVM = hiltViewModel()
){
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val params = navBackStackEntry.arguments?.getString(PARAMS_ID)
    if (params != null) {
        val idEjercicio = params.toLong()
        statEjercicioInfoVM.initData(idEjercicio)

        val uiStateStatEjercicio by produceState<StatInfoEjercicioStatUiState>(
            initialValue = StatInfoEjercicioStatUiState.Loading,
            key1 = lifecycle,
            key2 = statEjercicioInfoVM
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                statEjercicioInfoVM.uiStateStatEjercicio.collect { value = it }
            }
        }

        val uiStateStatMaterial by produceState<StatInfoEjercicioMaterialStatUiState>(
            initialValue = StatInfoEjercicioMaterialStatUiState.Loading,
            key1 = lifecycle,
            key2 = statEjercicioInfoVM
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                statEjercicioInfoVM.uiStateStatMaterial.collect { value = it }
            }
        }

        when {
            uiStateStatEjercicio is StatInfoEjercicioStatUiState.ErrorInfoEjercicioStatUI ||
                    uiStateStatMaterial is StatInfoEjercicioMaterialStatUiState.ErrorUI -> {
                navController.popBackStack()
            }

            uiStateStatEjercicio is StatInfoEjercicioStatUiState.Loading ||
                    uiStateStatMaterial is StatInfoEjercicioMaterialStatUiState.Loading -> {
                CenteredCircularProgressIndicator()
            }

            uiStateStatEjercicio is StatInfoEjercicioStatUiState.Success ||
                    uiStateStatMaterial is StatInfoEjercicioMaterialStatUiState.Success -> {
                val ejercicioDB =
                    (uiStateStatEjercicio as StatInfoEjercicioStatUiState.Success).ejercicioDB
                val materialListDB =
                    (uiStateStatMaterial as StatInfoEjercicioMaterialStatUiState.Success).materialListDB

                StatEjercicioInfoPrepareMenu()
                StatEjercicioInfoLazyColumn(
                    ejercicio = ejercicioDB,
                    materialList = materialListDB
                )
            }
        }
    }
}

@Composable
fun StatEjercicioInfoPrepareMenu(
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    floatingButtonVM: FloatingButtonVM = FloatingButtonVMSingle.getFloatingButtonVM(),
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
    topBarVM.clearActionList()
}

@Composable
fun StatEjercicioInfoLazyColumn(
    ejercicio : EjercicioSnapshotInfoDto,
    materialList: List<MaterialWithConfSnapshotDto>
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
                StatEjercicioInfo(ejercicio)
            }
            item {
                if (materialList.isEmpty()) {
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
            items(materialList) { material ->
                RowMaterialWithConf(
                    photoUri = material.photoUri,
                    nombre = material.nombre,
                    isMaterialWeight = material.isMaterialWeight,
                    confValue = material.confValue
                )
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StatEjercicioInfo(
    ejercicio: EjercicioSnapshotInfoDto
) {
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