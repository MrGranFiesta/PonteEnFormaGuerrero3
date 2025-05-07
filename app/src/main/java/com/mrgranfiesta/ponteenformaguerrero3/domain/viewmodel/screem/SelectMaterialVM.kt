package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.PARAMS_MATERIAL_BACK_SELECT
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.PARAMS_MATERIAL_INITIAL_SELECT
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.GetMaterialAllUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.CollectionUtil
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.FinderUtils
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.RegexExpresion
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.ejercicio.select.SelectMaterialUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class SelectMaterialVM @Inject constructor(
    private val getMaterialAllUseCase: GetMaterialAllUseCase
) : ViewModel() {
    var uiState: StateFlow<SelectMaterialUiState> = this.getFlowInitial()
    private val mutexMaterialListSelected = Mutex()

    private var status = true
    private val _materialList = MutableStateFlow(listOf<RowMaterialDto>())
    val materialList: StateFlow<List<RowMaterialDto>> = _materialList
    private fun setMaterialList(materialList: List<RowMaterialDto>) {
        viewModelScope.launch {
            mutexMaterialListSelected.withLock {
                _materialList.value = materialList
            }
        }
    }

    private val _materialListSelected = MutableStateFlow(setOf<Long>())
    val materialListSelected: StateFlow<Set<Long>> = _materialListSelected
    private fun setMaterialListSelected(materialList: Set<Long>) {
        viewModelScope.launch {
            mutexMaterialListSelected.withLock {
                _materialListSelected.value = materialList
            }
        }
    }


    private fun addMaterialListSelected(id: Long) {
        viewModelScope.launch {
            mutexMaterialListSelected.withLock {
                _materialListSelected.value = (_materialListSelected.value + id).toSet()
            }
        }
    }

    private fun removeMaterialListSelected(id: Long) {
        viewModelScope.launch {
            _materialListSelected.value = (_materialListSelected.value - id).toSet()
        }
    }

    fun initData(
        searchText: String,
        materialListDB: MutableSet<RowMaterialDto>,
        navController: NavController,
        navBackStackEntry: NavBackStackEntry
    ) {
        if (searchText.contains(RegexExpresion.SECURED_RE)) {
            val searchRE = FinderUtils.filterSearchTxtRE(searchText)

            if (searchRE.isNotEmpty()) {
                val tempSearch = FinderUtils.filterSearchMaterial(
                    materialListDB = materialListDB,
                    searchRE = searchRE
                )
                CollectionUtil.copyCollectionsSet(
                    materialListDB,
                    tempSearch.toHashSet()
                )
            }
        }
        if (searchText.isEmpty() || searchText.contains(RegexExpresion.SECURED_RE)) {
            this.setMaterialList(
                materialListDB.sortedWith(
                    compareBy(
                        { it.nombre.uppercase() },
                        { it.idMaterial })
                )
            )
            if (status) {
                val listMaterialSelected = navController
                    .previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<Set<Long>>(PARAMS_MATERIAL_INITIAL_SELECT)

                if (listMaterialSelected != null) {
                    this.setMaterialListSelected(listMaterialSelected)
                }

                navBackStackEntry
                    .savedStateHandle
                    .remove<Set<Long>>(PARAMS_MATERIAL_INITIAL_SELECT)
                status = false
            }
        } else {
            _materialList.value = listOf()
        }
    }

    private fun getFlowInitial(): StateFlow<SelectMaterialUiState> {
        return getMaterialAllUseCase().map { SelectMaterialUiState.Success(it) }
            .catch { SelectMaterialUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = SelectMaterialUiState.Loading
            )
    }

    fun onCheckAction(check: Boolean, material: RowMaterialDto) {
        if (check) {
            this.addMaterialListSelected(material.idMaterial)
        } else {
            this.removeMaterialListSelected(material.idMaterial)
        }
    }

    fun clickSelecionarBt(navController: NavController) {
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set(PARAMS_MATERIAL_BACK_SELECT, _materialListSelected.value)
        navController.popBackStack()
    }
}