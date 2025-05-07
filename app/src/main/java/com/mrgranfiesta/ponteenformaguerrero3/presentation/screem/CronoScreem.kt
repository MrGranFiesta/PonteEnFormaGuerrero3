package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem


import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ActionModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABAL_QUESTION_WARNING_EXIT_RUTINA
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_NO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_SI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_QUESTION_CALENTAMIENTO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_QUESTION_MOV_ARTICULAR
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_VIEW_MATERIAL_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.PARAMS_ID
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.StateBatchCrono
import com.mrgranfiesta.ponteenformaguerrero3.domain.core.viewmodel.LifecicleActionVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.EntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.MaterialEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.CronoBatchFactory
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.ActionItem
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.AnswerStateLoading
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.FloatingButtonVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.DtoMapper
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.CountdownVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.FloatingButtonVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.CronoVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem.ConfManagerVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CenteredCircularProgressIndicator
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.MaterialListDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.SimpleDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.subScreem.ConfManagerCronoScreem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.subScreem.EndCronoScreem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.subScreem.QuestionCronoScreem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.subScreem.QuestionEstirameintoCronoScreem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.subScreem.WarpUpScreem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.crono.CronoEntretenimientoUiState
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.crono.CronoMaterialEntretenimientoUiState
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.crono.CronoStepEntretenimientoUiState

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun ConfCronoScreem(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    cronoVM: CronoVM = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val params = navBackStackEntry.arguments?.getString(PARAMS_ID)
    if (params != null) {
        val idRutina = params.toLong()
        cronoVM.initData(idRutina)
        val uiStateEntrenamiento by produceState<CronoEntretenimientoUiState>(
            initialValue = CronoEntretenimientoUiState.Loading,
            key1 = lifecycle,
            key2 = cronoVM
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                cronoVM.uiStateEntrenamiento.collect { value = it }
            }
        }
        val uiStateStep by produceState<CronoStepEntretenimientoUiState>(
            initialValue = CronoStepEntretenimientoUiState.Loading,
            key1 = lifecycle,
            key2 = cronoVM
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                cronoVM.uiStateStep.collect { value = it }
            }
        }
        val uiStateMaterial by produceState<CronoMaterialEntretenimientoUiState>(
            initialValue = CronoMaterialEntretenimientoUiState.Loading,
            key1 = lifecycle,
            key2 = cronoVM
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                cronoVM.uiStateMaterial.collect { value = it }
            }
        }
        when {
            uiStateEntrenamiento is CronoEntretenimientoUiState.ErrorUI ||
                    uiStateStep is CronoStepEntretenimientoUiState.ErrorUI ||
                    uiStateMaterial is CronoMaterialEntretenimientoUiState.ErrorUI
            -> {
                navController.popBackStack()
            }

            uiStateEntrenamiento is CronoEntretenimientoUiState.Loading ||
                    uiStateStep is CronoStepEntretenimientoUiState.Loading ||
                    uiStateMaterial is CronoMaterialEntretenimientoUiState.Loading
            -> {
                CenteredCircularProgressIndicator()
            }

            uiStateEntrenamiento is CronoEntretenimientoUiState.Success ||
                    uiStateStep is CronoStepEntretenimientoUiState.Success ||
                    uiStateMaterial is CronoMaterialEntretenimientoUiState.Success
            -> {
                val entrenamientoDB =
                    (uiStateEntrenamiento as CronoEntretenimientoUiState.Success).entrenamientoDB
                val stepDB = (uiStateStep as CronoStepEntretenimientoUiState.Success).stepDB
                val materialDB =
                    (uiStateMaterial as CronoMaterialEntretenimientoUiState.Success).materialDB
                val stateBatchCrono by cronoVM.stateBatchCrono.collectAsState()
                val confVM: ConfManagerVM = viewModel(
                    key = cronoVM.getConfVMName(
                        stateBatchCrono = stateBatchCrono,
                        entrenamientoDB = entrenamientoDB
                    )
                )
                CronoLifecycle(
                    stepDB = stepDB,
                    entrenamientoDB = entrenamientoDB,
                    materialDB = materialDB,
                )
                CronoScreemDialog(
                    navController = navController,
                    stepDB = stepDB,
                    entrenamientoDB = entrenamientoDB,
                    materialDB = materialDB,
                    confVM = confVM,
                )
                CronoScreemMenu(
                    entrenamientoDB = entrenamientoDB,
                    stepDB = stepDB,
                    confVM = confVM,
                    materialDB = materialDB
                )
                CronoBatch(
                    entrenamientoDB = entrenamientoDB,
                    stepDB = stepDB,
                    navController = navController,
                    confVM = confVM,
                    materialDB = materialDB
                )
            }
        }
    }
}

@Composable
fun CronoBatch(
    entrenamientoDB: EntrenamientoDto,
    stepDB: List<StepEntrenamientoDto>,
    materialDB: List<MaterialEntrenamientoDto>,
    navController: NavController,
    confVM: ConfManagerVM,
    cronoVM: CronoVM = hiltViewModel()
) {
    val stateBatchCrono by cronoVM.stateBatchCrono.collectAsState()
    val navigate = {
        cronoVM.registerData(
            entrenamientoDB = entrenamientoDB,
            stepDB = stepDB,
            materialDB = materialDB
        )
        LifecicleActionVM.getLifecicleActionVM().reset()
        navController.popBackStack(route = AppScreem.RutinaListScreem.route, inclusive = false)
    }

    when (stateBatchCrono) {
        StateBatchCrono.CALENTAMIENTO_QUESTION -> {
            when (entrenamientoDB.calentamiento) {
                AnswerStateLoading.ON_LOADING -> {
                    CenteredCircularProgressIndicator()
                }

                AnswerStateLoading.ASK_LATER -> {
                    QuestionCronoScreem(
                        question = LABEL_QUESTION_CALENTAMIENTO,
                        omissionState = StateBatchCrono.MOVILIDAD_ARTICULAR_QUESTION,
                        nextStateBatch = StateBatchCrono.CALENTAMIENTO,
                        countdownVM = viewModel<CountdownVM>(key ="Q1")
                    )
                }

                AnswerStateLoading.YES -> {
                    cronoVM.setStateBatchCrono(StateBatchCrono.CALENTAMIENTO)
                }

                AnswerStateLoading.NO -> {
                    cronoVM.setStateBatchCrono(StateBatchCrono.MOVILIDAD_ARTICULAR_QUESTION)
                }
            }
        }

        StateBatchCrono.CALENTAMIENTO -> {
            cronoVM.calentamientoDone = true
            WarpUpScreem(
                cronoVM = cronoVM,
                nextStateBatch = {
                    cronoVM.setStateBatchCrono(StateBatchCrono.MOVILIDAD_ARTICULAR_QUESTION)
                }
            )
        }

        StateBatchCrono.MOVILIDAD_ARTICULAR_QUESTION -> {
            when (entrenamientoDB.movArticular) {
                AnswerState.YES -> {
                    cronoVM.setStateBatchCrono(StateBatchCrono.MOVILIDAD_ARTICULAR)
                }

                AnswerState.NO -> {
                    cronoVM.setStateBatchCrono(StateBatchCrono.STEPS)
                }

                AnswerState.ASK_LATER -> {
                    QuestionCronoScreem(
                        question = LABEL_QUESTION_MOV_ARTICULAR,
                        omissionState = StateBatchCrono.STEPS,
                        nextStateBatch = StateBatchCrono.MOVILIDAD_ARTICULAR,
                        countdownVM = viewModel<CountdownVM>(key ="Q2")
                    )
                }
            }
        }

        StateBatchCrono.MOVILIDAD_ARTICULAR -> {
            cronoVM.movArticularDone = true
            ConfManagerCronoScreem(
                descanso = 3,
                stepList = CronoBatchFactory.getMovilidadArticular(),
                nextStateBatch = {
                    cronoVM.setStateBatchCrono(StateBatchCrono.STEPS)
                },
                cronoVM = cronoVM,
                confVM = confVM
            )
        }

        StateBatchCrono.STEPS -> {
            ConfManagerCronoScreem(
                descanso = entrenamientoDB.descanso,
                stepList = stepDB,
                nextStateBatch = {
                    cronoVM.setStateBatchCrono(StateBatchCrono.ESTIRAMIENTO_QUESTIONS)
                },
                cronoVM = cronoVM,
                confVM = confVM
            )
        }

        StateBatchCrono.ESTIRAMIENTO_QUESTIONS -> {
            when (entrenamientoDB.estiramiento) {
                AnswerState.YES -> {
                    cronoVM.setStateBatchCrono(StateBatchCrono.ESTIRAMIENTOS)
                }

                AnswerState.NO -> {
                    cronoVM.setStateBatchCrono(StateBatchCrono.END)
                }

                AnswerState.ASK_LATER -> {
                    QuestionEstirameintoCronoScreem(
                        stepList = stepDB,
                        entrenamiento = entrenamientoDB,
                        endOnClick = { navigate() },
                        cronoVM = cronoVM,
                        endCronoVM = viewModel(
                            key = "EndCronoQuestion"
                        )
                    )
                }
            }
        }

        StateBatchCrono.ESTIRAMIENTOS -> {
            cronoVM.estiramientoDone = true
            ConfManagerCronoScreem(
                descanso = 10,
                stepList = CronoBatchFactory.getEstiramientos(entrenamientoDB.musculoSet),
                nextStateBatch = {
                    cronoVM.setStateBatchCrono(StateBatchCrono.END)
                },
                cronoVM = cronoVM,
                confVM = confVM
            )
        }

        StateBatchCrono.END -> {
            EndCronoScreem(
                stepList = stepDB,
                entrenamiento = entrenamientoDB,
                cronoVM = cronoVM,
                onClick = { navigate() },
                endCronoVM = viewModel(
                    key = "EndCronoFinal"
                )
            )
        }
    }
}

@Composable
fun CronoScreemMenu(
    entrenamientoDB: EntrenamientoDto,
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    floatingButtonVM: FloatingButtonVM = FloatingButtonVMSingle.getFloatingButtonVM(),
    stepDB: List<StepEntrenamientoDto>,
    materialDB: List<MaterialEntrenamientoDto>,
    confVM: ConfManagerVM,
    cronoVM: CronoVM = hiltViewModel()
) {
    val stateBatchCrono by cronoVM.stateBatchCrono.collectAsState()
    val cursor by confVM.cursorStep.collectAsState()
    val title = cronoVM.getTitleTopBar(stateBatchCrono, entrenamientoDB)
    topBarVM.setTitleBar(title)
    LaunchedEffect(key1 = true) {
        topBarVM.config(
            title = title,
            icon = IconTopBar.ARRROW,
            mode = ModeTopBar.TITLE,
            isGeasture = false,
            action = ActionModeTopBar.ONLY_EXTRAS,
            extras = { cronoVM.setIsShowBackDialog(true) }
        )
        floatingButtonVM.resetFloatingButton()
    }
    if (cronoVM.isStepContentMaterial(
            stateBatchCrono = stateBatchCrono,
            stepDB = stepDB,
            materialDB = materialDB,
            cursor = cursor
    )) {
        topBarVM.addActionList(
            ActionItem(
                tooltipText = LABEL_VIEW_MATERIAL_TOOLSTIP,
                icon = Icons.Filled.FitnessCenter,
                name = "Ver materiales",
                action = {
                    cronoVM.setIsShowConfMaterialDialog(true)
                }),
        )
    } else {
        topBarVM.clearActionList()
    }
}

@Composable
fun CronoScreemDialog(
    navController: NavController,
    entrenamientoDB: EntrenamientoDto,
    stepDB: List<StepEntrenamientoDto>,
    materialDB: List<MaterialEntrenamientoDto>,
    confVM: ConfManagerVM,
    cronoVM: CronoVM = hiltViewModel()
) {
    val isShowConfMaterialList by cronoVM.isShowConfMaterialDialog.collectAsState()
    val isShowBackDialog by cronoVM.isShowBackDialog.collectAsState()
    val cursor by confVM.cursorStep.collectAsState()

    if (isShowConfMaterialList) {
        MaterialListDialog(
            dismissDialog = { cronoVM.setIsShowConfMaterialDialog(false) },
            confMaterialList = materialDB
                .filter { it.idStep == stepDB[cursor].idStep }
                .map {
                    DtoMapper.toRowMaterialWithConfDto(it)
                }
        )
    }

    BackHandler(true) {
        cronoVM.setIsShowBackDialog(true)
    }

    if (isShowBackDialog) {
        SimpleDialog(
            dismissDialog = { cronoVM.setIsShowBackDialog(false) },
            title = LABAL_QUESTION_WARNING_EXIT_RUTINA,
            posOpt = LABEL_OPT_SI,
            negOpt = LABEL_OPT_NO
        ) {
            cronoVM.registerData(
                entrenamientoDB = entrenamientoDB,
                stepDB = stepDB,
                materialDB = materialDB
            )
            LifecicleActionVM.getLifecicleActionVM().reset()
            navController.popBackStack(route = AppScreem.RutinaListScreem.route, inclusive = false)
        }
    }
}

@Composable
fun CronoLifecycle(
    entrenamientoDB: EntrenamientoDto,
    stepDB: List<StepEntrenamientoDto>,
    materialDB: List<MaterialEntrenamientoDto>,
    cronoVM: CronoVM = hiltViewModel()
){
    LifecicleActionVM.getLifecicleActionVM().apply {
        setOnPause {
            cronoVM.extrasOnPause()
            cronoVM.registerData(
                entrenamientoDB = entrenamientoDB,
                stepDB = stepDB,
                materialDB = materialDB
            )
        }
        setOnResume {
            cronoVM.extrasOnResume()
        }
    }
}