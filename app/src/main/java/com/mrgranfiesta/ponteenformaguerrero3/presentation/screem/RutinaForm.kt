package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.EmojiObjects
import androidx.compose.material.icons.filled.Pending
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ActionModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABAL_QUESTION_WARNING_EXIT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ADD_EJERCICIO_BT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CALENTAMIENTO_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CONFIRM_CHANGE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_DESCANSO_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_DESCRIPCION_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EJERCICIOS_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EMPTY_GRUPO_MUSCULAR
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EMPTY_STEP_LIST
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ESTIRAMIENTOS_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_GRUPO_MUSCULAR_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_MOVILIDAD_ARTICULAR_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NIVEL_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NOMBRE_COLON
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_NO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_SI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_RUTINA_FORM_TITLE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SAVE_RUTINA_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SELECIONAR_MUSCULOS_BT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SELECT_NIVEL_BT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_TUTORIAL_STEP_REORDER_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.PARAMS_ID
import com.mrgranfiesta.ponteenformaguerrero3.domain.core.component.PopBackEffect
import com.mrgranfiesta.ponteenformaguerrero3.domain.core.viewmodel.PopBackEffectVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RutinaInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.ActionItem
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.DraggableItem
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.FloatingButtonVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.PopBackEffectVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.FloatingButtonVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.RowStepVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TextFieldSecuredVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.RutinaFormVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CenteredCircularProgressIndicator
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.EditOptionsRowStepView
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.IconButtonWithTooltip
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.NoneRowItem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.RowStepView
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.TextFieldAnswer
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.TextFieldSecured
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.TextFieldTimer
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.TextNivel
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.EditStepDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.SelectMusculoDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.SelectNivelDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.SimpleDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.rutina.form.RutinaFormStepUiState
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.rutina.form.RutinaFormUiState
import kotlinx.coroutines.channels.Channel
import java.util.UUID

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun ConfRutinaForm(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    snackbarHost: SnackbarHostState,
    rutinaFormVM: RutinaFormVM = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val params = navBackStackEntry.arguments?.getString(PARAMS_ID)
    if (params != null) {
        val idRutina = params.toLong()
        LaunchedEffect(true) {
            rutinaFormVM.addOptionalStep(
                navBackStackEntry = navBackStackEntry,
                idRutina = idRutina
            )
        }
        rutinaFormVM.initData(idRutina)

        val uiStateRutina by produceState<RutinaFormUiState>(
            initialValue = RutinaFormUiState.Loading,
            key1 = lifecycle,
            key2 = rutinaFormVM
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                rutinaFormVM.uiStateRutina.collect { value = it }
            }
        }

        val uiStateStep by produceState<RutinaFormStepUiState>(
            initialValue = RutinaFormStepUiState.Loading,
            key1 = lifecycle,
            key2 = rutinaFormVM
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                rutinaFormVM.uiStateStep.collect { value = it }
            }
        }

        when {
            uiStateRutina is RutinaFormUiState.ErrorUI ||
                    uiStateStep is RutinaFormStepUiState.ErrorUI -> {
                navController.popBackStack()
            }

            uiStateRutina is RutinaFormUiState.Loading ||
                    uiStateStep is RutinaFormStepUiState.Loading -> {
                CenteredCircularProgressIndicator()
            }

            uiStateRutina is RutinaFormUiState.Success ||
                    uiStateStep is RutinaFormStepUiState.Success -> {
                val rutinaDB = (uiStateRutina as RutinaFormUiState.Success).rutinaDB
                val stepListDB = (uiStateStep as RutinaFormStepUiState.Success).stepListDB

                rutinaFormVM.createdBackupRutina(
                    rutinaOrigen = rutinaDB,
                    id = idRutina,
                    stepListDB = stepListDB
                )
                RutinaFormPrepareDialog(rutinaDB)
                RutinaFormPrepareMenu(
                    navController = navController,
                    rutinaOrigen = rutinaDB,
                    snackbarHost = snackbarHost
                )
                RutinaFormLazyColumn(navController)
                PopBackEffect(navController)
            }
        }
    }
}

@Composable
fun RutinaFormPrepareDialog(
    rutinaOrigen: RutinaInfoDto,
    popBackEffectVM: PopBackEffectVM = PopBackEffectVMSingle.getPopBackEffectVM(),
    rutinaFormVM: RutinaFormVM = hiltViewModel(),
) {
    val selectNivelDialog by rutinaFormVM.selectNivelDialog.collectAsState()
    val selectMusculoDialog by rutinaFormVM.selectMusculoDialog.collectAsState()
    val selectSaveDialog by rutinaFormVM.selectSaveDialog.collectAsState()
    val selectBackDialog by rutinaFormVM.selectBackDialog.collectAsState()
    val isUpdateStepDialog by rutinaFormVM.isUpdateStepDialog.collectAsState()
    val stepSelectedId by rutinaFormVM.stepSelectedModifId.collectAsState()
    val musculoSet by rutinaFormVM.musculoSet.collectAsState()

    if (selectNivelDialog) {
        SelectNivelDialog(
            dismissDialog = { rutinaFormVM.setSelectNivelDialog(false) },
            changeNivel = { rutinaFormVM.setNivel(it) }
        )
    }

    if (selectMusculoDialog) {
        SelectMusculoDialog(
            dismissDialog = { rutinaFormVM.setSelectMusculoDialog(false) },
            musculoSetOrigen = musculoSet,
            onChangeMusculoSet = { rutinaFormVM.setMusculoSet(it) },
        )
    }

    if (selectSaveDialog) {
        SimpleDialog(
            dismissDialog = { rutinaFormVM.setSelectSaveDialog(false) },
            title = LABEL_CONFIRM_CHANGE,
            posOpt = LABEL_OPT_SI,
            negOpt = LABEL_OPT_NO
        ) {
            rutinaFormVM.crudActionForm(rutinaOrigen)
            popBackEffectVM.setBack(true)
        }
    }

    if (isUpdateStepDialog) {
        EditStepDialog(
            dismissDialog = { rutinaFormVM.setIsUpdateStepDialog(false) },
            step = rutinaFormVM.getCopyStepEditDialogDto(stepSelectedId),
            onAcceptAction = { stepUpd ->
                rutinaFormVM.stepUpdate(
                    cantidad = stepUpd.cantidad,
                    serie = stepUpd.serie,
                    tipo = stepUpd.tipo,
                    confMaterialList = stepUpd.confMaterialList
                )
            }
        )
    }

    if (selectBackDialog) {
        SimpleDialog(
            dismissDialog = { rutinaFormVM.setSelectBackDialog(false) },
            title = LABAL_QUESTION_WARNING_EXIT,
            posOpt = LABEL_OPT_SI,
            negOpt = LABEL_OPT_NO
        ) {
            popBackEffectVM.setBack(true)
        }
    }

    BackHandler(true) {
        if (rutinaFormVM.isChangeValues(rutinaOrigen)) {
            rutinaFormVM.setSelectBackDialog(true)
        } else {
            popBackEffectVM.setBack(true)
        }
    }
}

@Composable
fun RutinaFormPrepareMenu(
    navController: NavController,
    rutinaOrigen: RutinaInfoDto,
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    snackbarHost: SnackbarHostState,
    popBackEffectVM: PopBackEffectVM = PopBackEffectVMSingle.getPopBackEffectVM(),
    rutinaFormVM: RutinaFormVM = hiltViewModel(),
    floatingButtonVM: FloatingButtonVM = FloatingButtonVMSingle.getFloatingButtonVM()
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val nombreFieldVM: TextFieldSecuredVM = viewModel(key = "1")
    val focusManager = LocalFocusManager.current
    LaunchedEffect(currentRoute) {
        topBarVM.config(
            title = LABEL_RUTINA_FORM_TITLE,
            icon = IconTopBar.ARRROW,
            mode = ModeTopBar.TITLE,
            isGeasture = false,
            action = ActionModeTopBar.ONLY_EXTRAS,
            extras = {
                popBackEffectVM.setBack(true)
            }
        )
        floatingButtonVM.resetFloatingButton()
    }
    topBarVM.addActionList(
        ActionItem(
            icon = Icons.Filled.Check,
            name = "Guardar elemento",
            tooltipText = LABEL_SAVE_RUTINA_TOOLSTIP,
            action = {
                nombreFieldVM.onCompleteFirstIteration()
                focusManager.clearFocus()
                rutinaFormVM.activateDialogChange(
                    rutinaOrigen = rutinaOrigen,
                    snackbarHost = snackbarHost,
                    popBackStack = {
                        popBackEffectVM.setBack(true)
                    }
                )
            }
        )
    )
}

@Composable
fun RutinaFormLazyColumn(
    navController: NavController,
    rutinaFormVM: RutinaFormVM = hiltViewModel()
) {
    val stepList by rutinaFormVM.stepListOnRutina.collectAsState()
    val draggingItemIndex by rutinaFormVM.draggingItemIndex.collectAsState()
    val draggingItem by rutinaFormVM.draggingItem.collectAsState()
    val delta by rutinaFormVM.delta.collectAsState()

    val stateList = rememberLazyListState()
    val scrollChannel = Channel<Float>()

    LaunchedEffect(stateList) {
        while (true) {
            val diff = scrollChannel.receive()
            stateList.scrollBy(diff)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 12.dp,
                end = 12.dp
            )
            .pointerInput(key1 = stateList) {
                detectDragGesturesAfterLongPress(
                    onDragStart = { offset ->
                        rutinaFormVM.onDragStart(offset, stateList)
                    },
                    onDragEnd = {
                        rutinaFormVM.resetDragAndDrop()
                    },
                    onDragCancel = {
                        rutinaFormVM.resetDragAndDrop()
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        rutinaFormVM.setDelta(delta + dragAmount.y)
                        val currentDraggingItemIndex =
                            draggingItemIndex ?: return@detectDragGesturesAfterLongPress
                        val currentDraggingItem =
                            draggingItem ?: return@detectDragGesturesAfterLongPress
                        rutinaFormVM.onDrag(
                            currentDraggingItemIndex = currentDraggingItemIndex,
                            currentDraggingItem = currentDraggingItem,
                            stateList = stateList,
                            scrollChannel = scrollChannel
                        )
                    }
                )
            },
        state = stateList,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item {
            RutinaForm(navController)
        }
        item {
            if (stepList.isEmpty()) {
                NoneRowItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 16.dp,
                            bottom = 8.dp
                        ),
                    text = LABEL_EMPTY_STEP_LIST,
                    icon = Icons.Filled.Pending
                )
            }
        }
        itemsIndexed(
            items = stepList,
            contentType = { index, _ -> DraggableItem(index) }
        ) { index, step ->
            RowStepView(
                modifier = if (draggingItemIndex == index) {
                    Modifier
                        .zIndex(1f)
                        .graphicsLayer {
                            translationY = delta
                        }
                } else {
                    Modifier
                },
                onClickRow = { item ->
                    navController.navigate(
                        route = AppScreem.EjercicioInfoScreemNoOption.createRoute(item.idEjercicio)
                    )
                },
                dataSource = {
                    rutinaFormVM.getEjercicioNameAndPhoto(step.idEjercicio)
                },
                editOptions = EditOptionsRowStepView(
                    isActiveEdit = true,
                    onClickButtonDelete = {
                        rutinaFormVM.onDeleteIconAction(step)
                    },
                    onClickButtonUpdate = {
                        rutinaFormVM.selectStep(step)
                    },
                ),
                serie = step.serie,
                cantidad = step.cantidad,
                tipo = step.tipo,
                rowStepVM = viewModel(
                    key = step.toString(),
                    factory = RowStepVM.createRowStepVM()
                )
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun RutinaForm(
    navController: NavController,
    rutinaFormVM: RutinaFormVM = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val nombre by rutinaFormVM.nombre.collectAsState()
        val descripcion by rutinaFormVM.descripcion.collectAsState()
        val nivel by rutinaFormVM.nivel.collectAsState()
        val musculoSet by rutinaFormVM.musculoSet.collectAsState()
        val calentamiento by rutinaFormVM.calentamiento.collectAsState()
        val movArticular by rutinaFormVM.movArticular.collectAsState()
        val estiramiento by rutinaFormVM.estiramiento.collectAsState()
        val descanso by rutinaFormVM.descanso.collectAsState()

        val nombreFocus = rutinaFormVM.getNombreFocusRequester()
        val descripcionFocus = rutinaFormVM.getDescripcionFocusRequester()

        val descripcionBringIntoViewRequester = BringIntoViewRequester()

        TextFieldSecured(
            txt = nombre,
            onValueChange = { rutinaFormVM.setNombre(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .focusRequester(nombreFocus),
            label = LABEL_NOMBRE_COLON,
            maxLines = 3,
            maxCount = 50,
            isActiveCheckIsEmpty = true,
            textFieldSecuredVM = viewModel(key = "1")
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = LABEL_NIVEL_COLON
        )
        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 16.dp),
            onClick = { rutinaFormVM.setSelectNivelDialog(true) },
        ) {
            Text(LABEL_SELECT_NIVEL_BT)
        }
        TextNivel(
            nivel = nivel,
            modifier = Modifier.padding(top = 12.dp)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = LABEL_GRUPO_MUSCULAR_COLON
        )
        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(
                    top = 12.dp,
                    bottom = 8.dp
                ),
            onClick = { rutinaFormVM.setSelectMusculoDialog(true) },
        ) {
            Text(LABEL_SELECIONAR_MUSCULOS_BT)
        }
        if (musculoSet.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                musculoSet.forEach {
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
        TextFieldAnswer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp
                ),
            answer = calentamiento,
            changeAnswer = { rutinaFormVM.setCalentamiento(it) },
            label = LABEL_CALENTAMIENTO_COLON,
            textFieldAnswer = viewModel(key = "1${UUID.randomUUID()}")
        )
        TextFieldAnswer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp
                ),
            answer = movArticular,
            changeAnswer = { rutinaFormVM.setMovArticular(it) },
            label = LABEL_MOVILIDAD_ARTICULAR_COLON,
            textFieldAnswer = viewModel(key = "2${UUID.randomUUID()}")
        )
        TextFieldAnswer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp
                ),
            answer = estiramiento,
            changeAnswer = { rutinaFormVM.setEstiramiento(it) },
            label = LABEL_ESTIRAMIENTOS_COLON,
            textFieldAnswer = viewModel(key = "3${UUID.randomUUID()}")
        )
        Text(
            text = LABEL_DESCANSO_COLON,
            modifier = Modifier.padding(top = 8.dp)
        )
        TextFieldTimer(
            time = descanso,
            modifier = Modifier.padding(top = 8.dp),
            changeTime = { rutinaFormVM.setDescanso(it) },
            textFieldTimerVM = viewModel(key = "4${UUID.randomUUID()}")
        )
        TextFieldSecured(
            txt = descripcion,
            onValueChange = { rutinaFormVM.setDescripcion(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp
                )
                .focusRequester(descripcionFocus)
                .bringIntoViewRequester(descripcionBringIntoViewRequester),
            label = LABEL_DESCRIPCION_COLON,
            maxLines = 12,
            maxCount = 500,
            isActiveCheckIsEmpty = false,
            textFieldSecuredVM = viewModel(key = "2")
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = LABEL_EJERCICIOS_COLON
            )
            IconButtonWithTooltip(
                tooltipText = LABEL_TUTORIAL_STEP_REORDER_TOOLSTIP,
            ) {
                Icon(
                    contentDescription = "Help",
                    imageVector = Icons.Filled.EmojiObjects
                )
            }
        }

        Button(
            onClick = {
                navController.navigate(AppScreem.EjercicioListStepCreateScreem.createRoute())
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 12.dp,
                )
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = ""
            )
            Text(text = LABEL_ADD_EJERCICIO_BT)
        }
    }
}