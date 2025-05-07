package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.GetMaterialAllUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.CollectionUtil.Companion.copyCollectionsSet
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.FinderUtils.Companion.filterSearchMaterial
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.FinderUtils.Companion.filterSearchTxtRE
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.RegexExpresion
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.material.list.MaterialListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MaterialListVM @Inject constructor(
    private val getMaterialAllUseCase: GetMaterialAllUseCase
) : ViewModel() {
    var uiState: StateFlow<MaterialListUiState> = this.getFlowInitial()

    private val _materialList = MutableStateFlow(listOf<RowMaterialDto>())
    val materialList: StateFlow<List<RowMaterialDto>> = _materialList

    fun initData(
        searchText: String,
        materialListDB: MutableSet<RowMaterialDto>
    ) {
        if (searchText.contains(RegexExpresion.SECURED_RE)) {
            val searchRE = filterSearchTxtRE(searchText)

            if (searchRE.isNotEmpty()) {
                val tempSearch = filterSearchMaterial(
                    materialListDB = materialListDB,
                    searchRE = searchRE
                )
                copyCollectionsSet(
                    materialListDB,
                    tempSearch.toHashSet()
                )
            }
        }
        if (searchText.isEmpty() || searchText.contains(RegexExpresion.SECURED_RE)) {
            _materialList.value =
                materialListDB.sortedWith(compareBy({ it.nombre.uppercase() }, { it.idMaterial }))
        } else {
            _materialList.value = listOf()
        }
    }

    private fun getFlowInitial(): StateFlow<MaterialListUiState> {
        return getMaterialAllUseCase().map { MaterialListUiState.Success(it) }
            .catch { MaterialListUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = MaterialListUiState.Loading
            )
    }
}