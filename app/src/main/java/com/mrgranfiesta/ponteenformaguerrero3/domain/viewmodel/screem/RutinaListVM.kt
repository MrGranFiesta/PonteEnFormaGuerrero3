package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_CERRAR
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_SNACKBAR_ERROR_RUTINA_PREMIUM
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RowRutinaDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina.GetRowRutinaUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.CollectionUtil
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.FinderUtils
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.RegexExpresion
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.states.rutina.list.RutinaListUiState
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
class RutinaListVM @Inject constructor(
    private val getRowRutinaUseCase : GetRowRutinaUseCase
) : ViewModel() {
    var uiState: StateFlow<RutinaListUiState> = this.getFlowInitial()

    private val _rutinaList = MutableStateFlow(listOf<RowRutinaDto>())
    val rutinaList: StateFlow<List<RowRutinaDto>> = _rutinaList

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
        rutinaListDB: MutableSet<RowRutinaDto>
    ) {
        if (searchText.contains(RegexExpresion.SECURED_RE)) {
            val searchRE = FinderUtils.filterSearchTxtRE(searchText)

            if (searchRE.isNotEmpty()) {
                val tempSearch = FinderUtils.filterSearchRutina(
                    rutinaListDB = rutinaListDB,
                    searchRE = searchRE
                )
                CollectionUtil.copyCollectionsSet(
                    rutinaListDB,
                    tempSearch.toHashSet()
                )
            }
        }

        if (musculoSet.isNotEmpty()) {
            val tempMusculo = FinderUtils.filterMusculoRutina(
                rutinaListDB = rutinaListDB,
                musculoSetSearch = musculoSet.toHashSet()
            )
            CollectionUtil.copyCollectionsSet(
                rutinaListDB,
                tempMusculo.toHashSet()
            )
        }

        if (nivelSet.isNotEmpty()) {
            val tempNivel = FinderUtils.filterNivelRutina(
                rutinaListDB = rutinaListDB,
                nivelSetSearch = nivelSet.toHashSet()
            )
            CollectionUtil.copyCollectionsSet(
                rutinaListDB,
                tempNivel.toHashSet()
            )
        }

        if (searchText.isEmpty() || searchText.contains(RegexExpresion.SECURED_RE)) {
            _rutinaList.value =
                rutinaListDB.sortedWith(compareBy({ it.nombre.uppercase() }, { it.idRutina }))
        } else {
            _rutinaList.value = listOf()
        }
    }

    fun navigate(
        isPremium : Boolean,
        idRutina : Long,
        navController : NavController,
        snackbarHost: SnackbarHostState
    ){
        val isRolUserPremium = CurrentUser.user?.rol == Rol.PREMIUN_USER
        when {
            isPremium && isRolUserPremium || !isPremium -> {
                navController.navigate(
                    route = AppScreem.RutinaInfoScreem.createRoute(idRutina)
                )
            }
            else -> {
                viewModelScope.launch {
                    snackbarHost.showSnackbar(
                        message = LABEL_SNACKBAR_ERROR_RUTINA_PREMIUM,
                        actionLabel = LABEL_CERRAR,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    private fun getFlowInitial(): StateFlow<RutinaListUiState> {
        return getRowRutinaUseCase().map { RutinaListUiState.Success(it) }
            .catch { RutinaListUiState.ErrorUI(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = RutinaListUiState.Loading
            )
    }
}