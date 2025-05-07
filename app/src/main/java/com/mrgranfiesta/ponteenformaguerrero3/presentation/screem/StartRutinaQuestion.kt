package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ActionModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DRAWABLE_URI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EMPTY_MATERIAL
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_MUSICA
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_QUESTION_START_MATERIAL
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_QUESTION_START_MUSIC
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_START_RUTINA_BT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_START_RUTINA_QUESTION_TITLE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.PARAMS_ID
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.StartRutinaQuestionVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CenteredCircularProgressIndicator
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.NoneRowItem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.RowMaterial
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.crono.StartQuestionUiState
import androidx.core.net.toUri

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun ConfStartRutinaQuestion(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    startRutinaQuestionVM: StartRutinaQuestionVM = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val params = navBackStackEntry.arguments?.getString(PARAMS_ID)
    if (params != null) {
        val idRutina = params.toLong()
        startRutinaQuestionVM.initData(idRutina)

        val uiState by produceState<StartQuestionUiState>(
            initialValue = StartQuestionUiState.Loading,
            key1 = lifecycle,
            key2 = startRutinaQuestionVM
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                startRutinaQuestionVM.uiState.collect { value = it }
            }
        }

        when (uiState) {
            is StartQuestionUiState.ErrorUI -> {
                navController.popBackStack()
            }

            is StartQuestionUiState.Loading -> {
                CenteredCircularProgressIndicator()
            }

            is StartQuestionUiState.Success -> {
                val materialListDB = (uiState as StartQuestionUiState.Success).materialListDB

                StartRutinaQuestionPrepareMenu()
                //No tocar, sino no recompone, necesita elemento externo que recomponga
                topBarVM.clearActionList()
                StartRutinaQuestion(
                    materialList = materialListDB,
                    navController = navController,
                    idRutina = idRutina
                )
            }
        }
    }
}

@Composable
fun StartRutinaQuestionPrepareMenu(
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM()
) {
    LaunchedEffect(true) {
        topBarVM.config(
            title = LABEL_START_RUTINA_QUESTION_TITLE,
            icon = IconTopBar.ARRROW,
            mode = ModeTopBar.TITLE,
            isGeasture = false,
            action = ActionModeTopBar.POP_BACK_STACK
        )
    }
}

@Composable
fun StartRutinaQuestion(
    materialList: List<RowMaterialDto>,
    navController: NavController,
    startRutinaQuestionVM: StartRutinaQuestionVM = hiltViewModel(),
    idRutina: Long
) {
    LaunchedEffect(Unit) {
        startRutinaQuestionVM.isEnableOneClick = true
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 12.dp,
                end = 12.dp
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            item {
                Text(
                    text = LABEL_QUESTION_START_MUSIC,
                    modifier = Modifier.fillMaxWidth()
                        .padding(
                            top = 8.dp,
                            end = 8.dp,
                        )
                )
                RowMaterial(
                    nombre = LABEL_MUSICA,
                    photoUri = "${DRAWABLE_URI}material_music".toUri(),
                    isSelectionable = true,
                    initialSelect = false,
                    onClickCheck = {},
                    rowSelectMaterialVM = viewModel(key = "Music Icon")
                )
            }
            item {
                Text(text = LABEL_QUESTION_START_MATERIAL)
            }
            item {
                if (materialList.isEmpty()) {
                    NoneRowItem(
                        modifier = Modifier.fillMaxWidth()
                            .padding(
                                top = 8.dp,
                                bottom = 8.dp
                            ),
                        text = LABEL_EMPTY_MATERIAL,
                        icon = Icons.Filled.Info
                    )
                }
            }
            items(materialList) {
                RowMaterial(
                    nombre = it.nombre,
                    photoUri = it.photoUri,
                    initialSelect = false,
                    isSelectionable = true,
                    onClickCheck = {},
                    rowSelectMaterialVM = viewModel(key = it.idMaterial.toString())
                )
            }
        }
        Button(
            onClick = {
                startRutinaQuestionVM.startRutina(navController, idRutina)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp,
                    bottom = 8.dp
                ),
            shape = RoundedCornerShape(0.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.RocketLaunch,
                contentDescription = ""
            )
            Text(text = LABEL_START_RUTINA_BT)
        }
    }
}
