package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ActionModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABAL_QUESTION_WARNING_EXIT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CONFIRM_CHANGE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_DESCRIPCION
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EJERCICIO_FORM_TITLE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_EMPTY_GRUPO_MUSCULAR
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_GRUPO_MUSCULAR
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_MATERIALES
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_MATERIAL_FOR_EJERCICIO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NIVEL
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NOMBRE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_NO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_SI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SAVE_EJERCICIO_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SELECIONAR_MUSCULOS_BT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SELECT_MATERIAL_BT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SELECT_NIVEL_BT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SIMETRIA
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.PARAMS_ID
import com.mrgranfiesta.ponteenformaguerrero3.domain.core.component.PopBackEffect
import com.mrgranfiesta.ponteenformaguerrero3.domain.core.viewmodel.PopBackEffectVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.ActionItem
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.FloatingButtonVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.PopBackEffectVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.FloatingButtonVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.ImageFromUriVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TextFieldSecuredVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.EjercicioFormVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CenteredCircularProgressIndicator
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.ImageFromUri
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.NoneRowItem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.RowMaterial
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.SwitchKey
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.TextFieldSecured
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.TextNivel
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.SelectMusculoDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.SelectNivelDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.SimpleDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.ejercicio.form.EjercicioFormMaterialUiState
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.ejercicio.form.EjercicioFormUiState

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun ConfEjercicioForm(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    snackbarHost: SnackbarHostState,
    ejercicioFormVM: EjercicioFormVM = hiltViewModel(),
    imageFromUriVM: ImageFromUriVM = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(true) {
        ejercicioFormVM.addOptionalMaterial(navBackStackEntry)
    }
    val params = navBackStackEntry.arguments?.getString(PARAMS_ID)
    if (params != null) {
        val idEjercicio = params.toLong()
        ejercicioFormVM.initData(idEjercicio)

        val uiState by produceState<EjercicioFormUiState>(
            initialValue = EjercicioFormUiState.Loading,
            key1 = lifecycle,
            key2 = ejercicioFormVM
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                ejercicioFormVM.uiState.collect { value = it }
            }
        }

        val uiStateMaterial by produceState<EjercicioFormMaterialUiState>(
            initialValue = EjercicioFormMaterialUiState.Loading,
            key1 = lifecycle,
            key2 = ejercicioFormVM
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                ejercicioFormVM.uiStateMaterial.collect { value = it }
            }
        }

        when {
            uiState is EjercicioFormUiState.ErrorUI ||
                    uiStateMaterial is EjercicioFormMaterialUiState.ErrorUI -> {
                navController.popBackStack()
            }

            uiState is EjercicioFormUiState.Loading ||
                    uiStateMaterial is EjercicioFormMaterialUiState.Loading -> {
                CenteredCircularProgressIndicator()
            }

            uiState is EjercicioFormUiState.Success ||
                    uiStateMaterial is EjercicioFormMaterialUiState.Success -> {
                val ejercicioDB = (uiState as EjercicioFormUiState.Success).ejercicioDB
                val materialDB =
                    (uiStateMaterial as EjercicioFormMaterialUiState.Success).materialDB
                ejercicioFormVM.createdBackupEjercicio(ejercicioDB, materialDB, idEjercicio)

                EjercicioFormPrepareDialog(
                    ejercicioDB = ejercicioDB,
                    imageFromUriVM = imageFromUriVM
                )
                EjercicioFormPrepareMenu(
                    ejercicioDB = ejercicioDB,
                    snackbarHost = snackbarHost
                )
                EjercicioFormLazyColumn(
                    navController = navController,
                    imageFromUriVM = imageFromUriVM
                )
                PopBackEffect(navController)
            }
        }
    }
}

@Composable
fun EjercicioFormPrepareDialog(
    ejercicioDB: EjercicioInfoDto,
    popBackEffectVM: PopBackEffectVM = PopBackEffectVMSingle.getPopBackEffectVM(),
    ejercicioFormVM: EjercicioFormVM = hiltViewModel(),
    imageFromUriVM: ImageFromUriVM
) {
    val selectNivelDialog by ejercicioFormVM.selectNivelDialog.collectAsState()
    val selectMusculoDialog by ejercicioFormVM.selectMusculoDialog.collectAsState()
    val selectSaveDialog by ejercicioFormVM.selectSaveDialog.collectAsState()
    val selectBackDialog by ejercicioFormVM.selectBackDialog.collectAsState()
    val isOnProcess by imageFromUriVM.isOnProcess.collectAsState()

    val context = LocalContext.current

    val musculoSet by ejercicioFormVM.musculoSet.collectAsState()

    if (selectNivelDialog) {
        SelectNivelDialog(
            dismissDialog = { ejercicioFormVM.setSelectNivelDialog(false) },
            changeNivel = {
                ejercicioFormVM.setNivel(it)
            }
        )
    }

    if (selectMusculoDialog) {
        SelectMusculoDialog(
            dismissDialog = { ejercicioFormVM.setSelectMusculoDialog(false) },
            musculoSetOrigen = musculoSet,
            onChangeMusculoSet = { ejercicioFormVM.setMusculoSet(it) },
        )
    }

    if (selectSaveDialog) {
        SimpleDialog(
            dismissDialog = { ejercicioFormVM.setSelectSaveDialog(false) },
            title = LABEL_CONFIRM_CHANGE,
            posOpt = LABEL_OPT_SI,
            negOpt = LABEL_OPT_NO
        ) {
            ejercicioFormVM.crudActionForm(
                ejercicioDB = ejercicioDB,
                context = context
            )
            popBackEffectVM.setBack(true)
        }
    }

    if (selectBackDialog) {
        SimpleDialog(
            dismissDialog = { ejercicioFormVM.setSelectBackDialog(false) },
            title = LABAL_QUESTION_WARNING_EXIT,
            posOpt = LABEL_OPT_SI,
            negOpt = LABEL_OPT_NO
        ) {
            popBackEffectVM.setBack(true)
        }
    }

    BackHandler(true) {
        if (ejercicioFormVM.isChangeValues(ejercicioDB)) {
            ejercicioFormVM.setSelectBackDialog(true)
        } else if (!isOnProcess) {
            popBackEffectVM.setBack(true)
        }
    }
}

@Composable
fun EjercicioFormPrepareMenu(
    ejercicioDB: EjercicioInfoDto,
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    snackbarHost: SnackbarHostState,
    popBackEffectVM: PopBackEffectVM = PopBackEffectVMSingle.getPopBackEffectVM(),
    ejercicioFormVM: EjercicioFormVM = hiltViewModel(),
    floatingButtonVM: FloatingButtonVM = FloatingButtonVMSingle.getFloatingButtonVM(),
) {
    val nombreFieldVM : TextFieldSecuredVM = viewModel(key = "1")

    val focusManager = LocalFocusManager.current
    LaunchedEffect(key1 = true) {
        topBarVM.config(
            title = LABEL_EJERCICIO_FORM_TITLE,
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
            tooltipText = LABEL_SAVE_EJERCICIO_TOOLSTIP,
            name = "Guardar elemento",
            action = {
                nombreFieldVM.onCompleteFirstIteration()
                focusManager.clearFocus()
                ejercicioFormVM.activateDialogChange(
                    ejercicioDB = ejercicioDB,
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
fun EjercicioFormLazyColumn(
    navController: NavController,
    imageFromUriVM: ImageFromUriVM,
    ejercicioFormVM: EjercicioFormVM = hiltViewModel(),
) {
    val materialSelected by ejercicioFormVM.materialSelected.collectAsState()
    val lazyListState = rememberLazyListState()

    LazyColumn(
        state = lazyListState,
        modifier = Modifier.padding(
            bottom = 8.dp,
            start = 12.dp,
            end = 12.dp
        )
    ) {
        item {
            EjercicioForm(imageFromUriVM)
        }
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                text = LABEL_MATERIALES
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                onClick = { ejercicioFormVM.navigationSelectStep(navController) },
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = LABEL_SELECT_MATERIAL_BT
                )
            }
        }
        item {
            if (materialSelected.isEmpty()) {
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
        items(materialSelected.toList()) {
            RowMaterial(
                nombre = it.nombre,
                photoUri = it.photoUri
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun EjercicioForm(
    imageFromUriVM: ImageFromUriVM,
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    ejercicioFormVM: EjercicioFormVM = hiltViewModel(),
    popBackEffectVM: PopBackEffectVM = PopBackEffectVMSingle.getPopBackEffectVM()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val nombre by ejercicioFormVM.nombre.collectAsState()
        val descripcion by ejercicioFormVM.descripcion.collectAsState()
        val isSimetria by ejercicioFormVM.isSimetria.collectAsState()
        val nivel by ejercicioFormVM.nivel.collectAsState()
        val musculoSet by ejercicioFormVM.musculoSet.collectAsState()
        val photoUri by ejercicioFormVM.photoUri.collectAsState()

        val nombreFocus = ejercicioFormVM.getNombreFocusRequester()
        val descripcionFocus = ejercicioFormVM.getDescripcionFocusRequester()

        val descripcionBringIntoViewRequester = BringIntoViewRequester()
        val isOnProcessTopBt by topBarVM.isOnProcess.collectAsState()

        val isBack by popBackEffectVM.isBack.collectAsState()
        val launcher =
            rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    ejercicioFormVM.setPhotoUri(uri)
                }
            }

        TextFieldSecured(
            txt = nombre,
            onValueChange = {
                ejercicioFormVM.setNombre(it)
                nombreFocus.requestFocus()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .focusRequester(nombreFocus),
            label = LABEL_NOMBRE,
            maxLines = 3,
            maxCount = 50,
            isActiveCheckIsEmpty = true,
            textFieldSecuredVM = viewModel(key = "1")
        )
        ImageFromUri(
            photoUri = photoUri,
            imageFromUriVM = imageFromUriVM,
            onClickUpload = {
                if (!isOnProcessTopBt && !isBack) {
                    launcher.launch(
                        PickVisualMediaRequest(
                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
            },
            onClickDelete = {
                ejercicioFormVM.setPhotoUri(Uri.EMPTY)
            },
            isActiveEdit = true,
            contentDescription = "Imagen material"
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = LABEL_NIVEL
        )
        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 16.dp),
            onClick = { ejercicioFormVM.setSelectNivelDialog(true) },
        ) {
            Text(LABEL_SELECT_NIVEL_BT)
        }
        TextNivel(
            nivel = nivel,
            modifier = Modifier.padding(top = 12.dp)
        )
        SwitchKey(
            key = LABEL_SIMETRIA,
            active = isSimetria,
            onChanged = {
                ejercicioFormVM.setIsSimetria(!isSimetria)
            },
            modifier = Modifier.padding(top = 12.dp)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            text = LABEL_GRUPO_MUSCULAR
        )
        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(
                    top = 12.dp,
                    bottom = 8.dp
                ),
            onClick = { ejercicioFormVM.setSelectMusculoDialog(true) },
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
        TextFieldSecured(
            txt = descripcion,
            onValueChange = {
                ejercicioFormVM.setDescripcion(it)
                ejercicioFormVM.onActionBringIntoViewRequester(descripcionBringIntoViewRequester)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp,
                    bottom = 8.dp
                )
                .focusRequester(descripcionFocus)
                .bringIntoViewRequester(descripcionBringIntoViewRequester),
            label = LABEL_DESCRIPCION,
            maxLines = 12,
            maxCount = 500,
            isActiveCheckIsEmpty = false,
            textFieldSecuredVM = viewModel(key = "2")
        )
    }
}
