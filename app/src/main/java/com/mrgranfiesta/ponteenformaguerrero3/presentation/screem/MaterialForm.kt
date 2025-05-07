package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_MATERIAL_FORM_TITLE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_DESCRIPCION
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NOMBRE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_NO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_OPT_SI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_QUESTION_USA_PESO
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SAVE_MATERIAL_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.PARAMS_ID
import com.mrgranfiesta.ponteenformaguerrero3.domain.core.component.PopBackEffect
import com.mrgranfiesta.ponteenformaguerrero3.domain.core.component.ScrollDown
import com.mrgranfiesta.ponteenformaguerrero3.domain.core.viewmodel.PopBackEffectVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.MaterialInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.ActionItem
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.FloatingButtonVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.PopBackEffectVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.FloatingButtonVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.ImageFromUriVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TextFieldSecuredVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.core.ScrollUtilVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.MaterialFormVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CenteredCircularProgressIndicator
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CheckText
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.ImageFromUri
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.TextFieldSecured
import com.mrgranfiesta.ponteenformaguerrero3.presentation.dialog.SimpleDialog
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.material.form.MaterialFormUiState

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun ConfMaterialForm(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    snackbarHost: SnackbarHostState,
    materialFormVM: MaterialFormVM = hiltViewModel(),
    imageFromUriVM: ImageFromUriVM = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val params = navBackStackEntry.arguments?.getString(PARAMS_ID)
    if (params != null) {
        val idMaterial = params.toLong()
        materialFormVM.initData(idMaterial)

        val uiState by produceState<MaterialFormUiState>(
            initialValue = MaterialFormUiState.Loading,
            key1 = lifecycle,
            key2 = materialFormVM
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                materialFormVM.uiState.collect { value = it }
            }
        }

        when (uiState) {
            is MaterialFormUiState.ErrorUI -> {
                navController.popBackStack()
            }

            is MaterialFormUiState.Loading -> {
                CenteredCircularProgressIndicator()
            }

            is MaterialFormUiState.Success -> {
                val materialDB = (uiState as MaterialFormUiState.Success).materialDB
                materialFormVM.initModeUpd(materialDB, idMaterial)

                MaterialFormPrepareDialog(
                    materialDB = materialDB,
                    imageFromUriVM = imageFromUriVM
                )
                MaterialFormPrepareMenu(
                    materialDB = materialDB,
                    snackbarHost = snackbarHost
                )
                MaterialForm(imageFromUriVM = imageFromUriVM)
                PopBackEffect(navController)
            }
        }
    }
}

@Composable
fun MaterialFormPrepareDialog(
    materialDB: MaterialInfoDto,
    popBackEffectVM: PopBackEffectVM = PopBackEffectVMSingle.getPopBackEffectVM(),
    materialFormVM: MaterialFormVM = hiltViewModel(),
    imageFromUriVM: ImageFromUriVM
) {
    val selectSaveDialog by materialFormVM.selectSaveDialog.collectAsState()
    val selectBackDialog by materialFormVM.selectBackDialog.collectAsState()
    val isOnProcess by imageFromUriVM.isOnProcess.collectAsState()

    val context = LocalContext.current

    if (selectSaveDialog) {
        SimpleDialog(
            dismissDialog = { materialFormVM.setSelectSaveDialog(false) },
            title = LABEL_CONFIRM_CHANGE,
            posOpt = LABEL_OPT_SI,
            negOpt = LABEL_OPT_NO
        ) {
            materialFormVM.crudActionForm(
                context = context,
                material = materialDB
            )
            popBackEffectVM.setBack(true)
        }
    }

    if (selectBackDialog) {
        SimpleDialog(
            dismissDialog = { materialFormVM.setSelectBackDialog(false) },
            title = LABAL_QUESTION_WARNING_EXIT,
            posOpt = LABEL_OPT_SI,
            negOpt = LABEL_OPT_NO
        ) {
            popBackEffectVM.setBack(true)
        }
    }
    BackHandler(true) {
        if (materialFormVM.isChangeValues(materialDB)) {
            materialFormVM.setSelectBackDialog(true)
        } else if (!isOnProcess) {
            popBackEffectVM.setBack(true)
        }
    }
}

@Composable
fun MaterialFormPrepareMenu(
    materialDB: MaterialInfoDto,
    snackbarHost: SnackbarHostState,
    materialFormVM: MaterialFormVM = hiltViewModel(),
    popBackEffectVM: PopBackEffectVM = PopBackEffectVMSingle.getPopBackEffectVM(),
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    floatingButtonVM: FloatingButtonVM = FloatingButtonVMSingle.getFloatingButtonVM()
) {
    val nombreFieldVM : TextFieldSecuredVM = viewModel(key = "1")

    val focusManager = LocalFocusManager.current
    LaunchedEffect(true) {
        topBarVM.config(
            title = LABEL_MATERIAL_FORM_TITLE,
            icon = IconTopBar.ARRROW,
            mode = ModeTopBar.TITLE,
            isGeasture = false,
            action = ActionModeTopBar.ONLY_EXTRAS,
            extras = { popBackEffectVM.setBack(true) }
        )
        floatingButtonVM.resetFloatingButton()
    }
    topBarVM.addActionList(
        ActionItem(
            icon = Icons.Filled.Check,
            tooltipText = LABEL_SAVE_MATERIAL_TOOLSTIP,
            name = "Guardar elemento",
            action = {
                nombreFieldVM.onCompleteFirstIteration()
                focusManager.clearFocus()
                materialFormVM.activateDialogChange(
                    materialDB = materialDB,
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
fun MaterialForm(
    materialFormVM: MaterialFormVM = hiltViewModel(),
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    popBackEffectVM: PopBackEffectVM = PopBackEffectVMSingle.getPopBackEffectVM(),
    scrollUtilVM: ScrollUtilVM = hiltViewModel(),
    imageFromUriVM: ImageFromUriVM
) {
    val scrollState = rememberScrollState()
    ScrollDown(scrollState)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 12.dp,
                end = 12.dp
            )
            .verticalScroll(scrollState),
    ) {
        val nombre by materialFormVM.nombre.collectAsState()
        val descripcion by materialFormVM.descripcion.collectAsState()
        val isMaterialWeight by materialFormVM.isMaterialWeight.collectAsState()
        val photoUri by materialFormVM.photoUri.collectAsState()
        val isOnProcessTopBt by topBarVM.isOnProcess.collectAsState()
        val isBack by popBackEffectVM.isBack.collectAsState()

        val launcher =
            rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    materialFormVM.setPhotoUri(uri)
                }
            }
        TextFieldSecured(
            txt = nombre,
            onValueChange = {
                scrollUtilVM.setScrollDown(false)
                materialFormVM.setNombre(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            label = LABEL_NOMBRE,
            maxLines = 3,
            maxCount = 50,
            isActiveCheckIsEmpty = true,
            textFieldSecuredVM = viewModel(key = "1")
        )
        ImageFromUri(
            imageFromUriVM = imageFromUriVM,
            photoUri = photoUri,
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
                materialFormVM.setPhotoUri(Uri.EMPTY)
            },
            isActiveEdit = true,
            contentDescription = "Imagen ejecicio"
        )
        CheckText(
            txt = LABEL_QUESTION_USA_PESO,
            isChecked = isMaterialWeight,
            changeIsChecked = { materialFormVM.setMaterialWeight(!isMaterialWeight) },
            modifier = Modifier.padding(
                top = 16.dp
            )
        )
        TextFieldSecured(
            txt = descripcion,
            onValueChange = {
                materialFormVM.setDescripcion(it)
                scrollUtilVM.setScrollDown(true)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp,
                    bottom = 8.dp
                ),
            label = LABEL_DESCRIPCION,
            maxLines = 12,
            maxCount = 500,
            isActiveCheckIsEmpty = false,
            textFieldSecuredVM = viewModel(key = "2")
        )
    }
}