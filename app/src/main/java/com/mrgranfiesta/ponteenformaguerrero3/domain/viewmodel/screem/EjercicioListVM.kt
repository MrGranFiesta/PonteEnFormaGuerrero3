package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.RowEjercicioDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio.GetRowEjercicioUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.CollectionUtil
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.FinderUtils
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.RegexExpresion
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.ejercicio.list.EjercicioListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EjercicioListVM @Inject constructor(
    private val getRowEjercicioUseCase : GetRowEjercicioUseCase
) : ViewModel() {
    var uiState: StateFlow<EjercicioListUiState> = this.getFlowInitial()

    private val _ejercicioList = MutableStateFlow(listOf<RowEjercicioDto>())
    val ejercicioList: StateFlow<List<RowEjercicioDto>> = _ejercicioList

    private val _musculoSetSearch = MutableStateFlow(setOf<Musculo>())
    val musculoSetSearch: StateFlow<Set<Musculo>> = _musculoSetSearch
    fun setMusculoSetSearch(musculoSet: Set<Musculo>) {
        viewModelScope.launch {
            _musculoSetSearch.value = musculoSet.toSet()
        }
    }

    private val _nivelSetSearch = MutableStateFlow(setOf<Nivel>())
    val nivelSetSearch: StateFlow<Set<Nivel>> = _nivelSetSearch
    fun setNivelSetSearch(nivelSet: Set<Nivel>) {
        viewModelScope.launch {
            _nivelSetSearch.value = nivelSet.toSet()
        }
    }

    fun initData(
        searchText: String,
        musculoSet: Set<Musculo>,
        nivelSet: Set<Nivel>,
        ejercicioListDB: MutableSet<RowEjercicioDto>
    ) {
        if (searchText.contains(RegexExpresion.SECURED_RE)) {
            val searchRE = FinderUtils.filterSearchTxtRE(searchText)

            if (searchRE.isNotEmpty()) {
                val tempSearch = FinderUtils.filterSearchEjercicio(
                    ejercicioListDB = ejercicioListDB,
                    searchRE = searchRE
                )
                CollectionUtil.copyCollectionsSet(
                    ejercicioListDB,
                    tempSearch.toHashSet()
                )
            }
        }
        if (musculoSet.isNotEmpty()) {
            val tempMusculo = FinderUtils.filterMusculoEjercicio(
                ejercicioListDB = ejercicioListDB,
                musculoSetSearch = musculoSet.toHashSet()
            )

            CollectionUtil.copyCollectionsSet(
                ejercicioListDB,
                tempMusculo.toHashSet()
            )
        }

        if (nivelSet.isNotEmpty()) {
            val tempNivel = FinderUtils.filterNivelEjercicio(
                ejercicioListDB = ejercicioListDB,
                nivelSetSearch = nivelSet.toHashSet()
            )

            CollectionUtil.copyCollectionsSet(
                ejercicioListDB,
                tempNivel.toHashSet()
            )

        }
        if (searchText.isEmpty() || searchText.contains(RegexExpresion.SECURED_RE)) {
            _ejercicioList.value =
                ejercicioListDB.sortedWith(
                    compareBy({ it.nombre.uppercase() }, { it.idEjercicio })
                )
        } else {
            _ejercicioList.value = listOf()
        }
    }

    private fun getFlowInitial(): StateFlow<EjercicioListUiState> {
        return getRowEjercicioUseCase().map { EjercicioListUiState.Success(it) }
            .catch { EjercicioListUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = EjercicioListUiState.Loading
            )
    }
}