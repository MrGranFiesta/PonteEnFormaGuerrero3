package com.mrgranfiesta.ponteenformaguerrero3.presentation.screem

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
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
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ActionModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ADD_MATERIAL_TOOLSTIP
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ERROR_CHARACTER
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_MATERIAL_LIST_TITLE
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NOT_FIND_MATERIAL
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.FloatingButtonVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.RegexExpresion
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.FloatingButtonVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.MaterialListVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.CenteredCircularProgressIndicator
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.ErrorRowItem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.NoneRowItem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.RowMaterial
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.material.list.MaterialListUiState

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun ConfMaterialList(
    navController: NavController,
    mateialListVM: MaterialListVM = hiltViewModel(),
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<MaterialListUiState>(
        initialValue = MaterialListUiState.Loading,
        key1 = lifecycle,
        key2 = mateialListVM
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            mateialListVM.uiState.collect { value = it }
        }
    }

    when (uiState) {
        is MaterialListUiState.ErrorUI -> {
            navController.popBackStack()
        }

        is MaterialListUiState.Loading -> {
            CenteredCircularProgressIndicator()
        }

        is MaterialListUiState.Success -> {
            val materialListDB = (uiState as MaterialListUiState.Success).materialListDB

            val searchText by topBarVM.searchText.collectAsState()

            mateialListVM.initData(
                searchText = searchText,
                materialListDB = materialListDB.toMutableSet()
            )

            MaterialListPrepareMenu(
                navController = navController,
            )
            MaterialList(
                navController = navController,
            )
        }
    }
}

@Composable
fun MaterialListPrepareMenu(
    navController: NavController,
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    floatingButtonVM: FloatingButtonVM = FloatingButtonVMSingle.getFloatingButtonVM()
) {
    LaunchedEffect(true) {
        topBarVM.config(
            title = LABEL_MATERIAL_LIST_TITLE,
            icon = IconTopBar.BURGER,
            mode = ModeTopBar.SEARCH,
            isGeasture = true,
            action = ActionModeTopBar.OPEN_DRAWER
        )
        if (Rol.isEditable(CurrentUser.user?.rol)) {
            floatingButtonVM.makeFloatingButton(
                icon = Icons.Filled.Add,
                autoClose = true,
                contentDescripcion = "Añadir Material",
                tooltipText = LABEL_ADD_MATERIAL_TOOLSTIP,
                onClick = {
                    navController.navigate(AppScreem.MaterialFormScreem.createRoute(0))
                },
            )
        }
    }
    topBarVM.clearActionList()
}

@Composable
fun MaterialList(
    navController: NavController,
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    materialListVM: MaterialListVM = hiltViewModel()
) {
    val searchText by topBarVM.searchText.collectAsState()
    val materialList by materialListVM.materialList.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 12.dp,
                end = 12.dp
            )
    ) {
        item {
            if (searchText.isNotEmpty() && !searchText.contains(RegexExpresion.SECURED_RE)) {
                ErrorRowItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 8.dp,
                        ),
                    errorTxt = LABEL_ERROR_CHARACTER
                )
            } else if (materialList.isEmpty()) {
                NoneRowItem(
                    text = LABEL_NOT_FIND_MATERIAL,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 8.dp,
                        ),
                    icon = Icons.Filled.SentimentVeryDissatisfied
                )
            }
        }
        items(
            items = materialList,
            key = {
                it.idMaterial
            }
        ) {
            RowMaterial(
                nombre = it.nombre,
                photoUri = it.photoUri,
                onClickItem = {
                    navController.navigate(
                        route = AppScreem.MaterialInfoScreem.createRoute(it.idMaterial)
                    )
                }
            )
        }
    }
}