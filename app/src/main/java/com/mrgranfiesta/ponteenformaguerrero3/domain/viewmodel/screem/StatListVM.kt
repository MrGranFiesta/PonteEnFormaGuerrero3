package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.stat.StatRutinaSearchDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.OptionalLocalDate
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.stat.GetStatRutinaSearchUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.CollectionUtil
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.FinderUtils
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.RegexExpresion
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.stat.list.StatListUiState
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
class StatListVM @Inject constructor(
    private val getStatRutinaSearchUseCase : GetStatRutinaSearchUseCase
) : ViewModel() {
    var uiState: StateFlow<StatListUiState> = this.getFlowInitial()

    private val _statList = MutableStateFlow(listOf<StatRutinaSearchDto>())
    val statList: StateFlow<List<StatRutinaSearchDto>> = _statList

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

    private val _rangeDate = MutableStateFlow<Pair<OptionalLocalDate, OptionalLocalDate>>(
        Pair(
            OptionalLocalDate.Empty,
            OptionalLocalDate.Empty
        )
    )
    val rangeDate: StateFlow<Pair<OptionalLocalDate, OptionalLocalDate>> = _rangeDate
    fun setRangeDate(rangeDate: Pair<OptionalLocalDate, OptionalLocalDate>) {
        viewModelScope.launch {
            _rangeDate.value = rangeDate
        }
    }

    fun initData(
        searchText: String,
        musculoSet: Set<Musculo>,
        nivelSet: Set<Nivel>,
        rangeDate: Pair<OptionalLocalDate, OptionalLocalDate>,
        statListDB: MutableSet<StatRutinaSearchDto>
    ) {
        if (searchText.contains(RegexExpresion.SECURED_RE)) {
            val searchRE = FinderUtils.filterSearchTxtRE(searchText)

            if (searchRE.isNotEmpty()) {
                val tempSearch = FinderUtils.filterSearchStatRutina(
                    statListDB = statListDB,
                    searchRE = searchRE
                )
                CollectionUtil.copyCollectionsSet(
                    statListDB,
                    tempSearch.toHashSet()
                )
            }
        }

        if (musculoSet.isNotEmpty()) {
            val tempMusculo = FinderUtils.filterMusculoStatRutina(
                statRutinaListDB = statListDB,
                musculoSetSearch = musculoSet.toHashSet()
            )
            CollectionUtil.copyCollectionsSet(
                statListDB,
                tempMusculo.toHashSet()
            )
        }

        if (nivelSet.isNotEmpty()) {
            val tempNivel = FinderUtils.filterNivelStatRutina(
                statRutinaListDB = statListDB,
                nivelSetSearch = nivelSet.toHashSet()
            )
            CollectionUtil.copyCollectionsSet(
                statListDB,
                tempNivel.toHashSet()
            )
        }

        when {
            rangeDate.second is OptionalLocalDate.Empty && rangeDate.first !is OptionalLocalDate.Empty -> {
                val initDate = (rangeDate.first as? OptionalLocalDate.Date)?.date
                if (initDate != null) {
                    val tempDate = FinderUtils.filterSearchStatStartDate(
                        statListDB = statListDB,
                        date = initDate
                    )
                    CollectionUtil.copyCollectionsSet(
                        statListDB,
                        tempDate.toHashSet()
                    )
                }
            }

            rangeDate.first is OptionalLocalDate.Empty && rangeDate.second !is OptionalLocalDate.Empty -> {
                val endDate = (rangeDate.second as? OptionalLocalDate.Date)?.date
                if (endDate != null) {
                    val tempDate = FinderUtils.filterSearchStatEndDate(
                        statListDB = statListDB,
                        date = endDate
                    )
                    CollectionUtil.copyCollectionsSet(
                        statListDB,
                        tempDate.toHashSet()
                    )
                }
            }

            rangeDate.first.isBefore(rangeDate.second) -> {
                val tempDate = FinderUtils.filterSearchStatRange(
                    statListDB = statListDB,
                    rangeDate = rangeDate
                )
                CollectionUtil.copyCollectionsSet(
                    statListDB,
                    tempDate.toHashSet()
                )
            }
        }

        if (searchText.isEmpty() || searchText.contains(RegexExpresion.SECURED_RE)) {
            _statList.value = statListDB.sortedWith(compareByDescending { it.dateEnd })
        } else {
            _statList.value = listOf()
        }
    }

    private fun getFlowInitial(): StateFlow<StatListUiState> {
        if (CurrentUser.user?.rol != Rol.PREMIUN_USER) {
            return MutableStateFlow(StatListUiState.NoPermision)
        }
        return getStatRutinaSearchUseCase().map { StatListUiState.Success(it) }
            .catch { StatListUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = StatListUiState.Loading
            )
    }
}