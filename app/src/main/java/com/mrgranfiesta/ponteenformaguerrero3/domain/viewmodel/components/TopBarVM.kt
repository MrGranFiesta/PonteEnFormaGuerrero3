package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ActionModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.ActionItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TopBarVM : ViewModel() {
    private val _titleBar = MutableStateFlow("")
    val titleBar: StateFlow<String> = _titleBar
    fun setTitleBar(titleBar: String) {
        _titleBar.value = titleBar
    }

    val interationIconNav = MutableInteractionSource()

    private val _iconTopBar = MutableStateFlow(IconTopBar.BURGER)
    val iconTopBar: StateFlow<IconTopBar> = _iconTopBar
    private fun setIconTopBar(iconTopBar: IconTopBar) {
        _iconTopBar.value = iconTopBar
    }

    private val _modeTopBar = MutableStateFlow(ModeTopBar.TITLE)
    val modeTopBar: StateFlow<ModeTopBar> = _modeTopBar
    private fun setModeTopBar(modeTopBar: ModeTopBar) {
        _modeTopBar.value = modeTopBar
    }

    private val _isGestures = MutableStateFlow(true)
    val isGestures: StateFlow<Boolean> = _isGestures
    private fun setGeature(isGestures: Boolean) {
        _isGestures.value = isGestures
    }

    private var _actionModeTopBar = ActionModeTopBar.POP_BACK_STACK
    private fun setActionModeTopBar(actionIconTopBar: ActionModeTopBar) {
        _actionModeTopBar = actionIconTopBar
    }

    fun config(
        title: String = "",
        icon: IconTopBar,
        mode: ModeTopBar,
        isGeasture: Boolean,
        action: ActionModeTopBar,
        extras: () -> Unit = {}
    ) {
        setTitleBar(title)
        setIconTopBar(icon)
        setModeTopBar(mode)
        setGeature(isGeasture)
        setActionModeTopBar(action)
        setActionExtra(extras)
        setSearchText("")
    }

    private val _actionExtra = MutableStateFlow {}
    val actionExtra: StateFlow<() -> Unit> = _actionExtra
    private fun setActionExtra(actionExtra: () -> Unit) {
        _actionExtra.value = actionExtra
    }

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText
    fun setSearchText(searchText: String) {
        _searchText.value = searchText
    }

    private val _filtersDialog = MutableStateFlow(false)
    val filtersDialog: StateFlow<Boolean> = _filtersDialog
    fun setFiltersDialog(filtersDialog: Boolean) {
        _filtersDialog.value = filtersDialog
    }

    private val _isOnProcess = MutableStateFlow(false)
    val isOnProcess: StateFlow<Boolean> = _isOnProcess
    private fun setOnProcess(isOnProcess: Boolean) {
        _isOnProcess.value = isOnProcess
    }

    private val _actionIconList = MutableStateFlow(listOf<ActionItem>())
    val actionIconList: StateFlow<List<ActionItem>> = _actionIconList
    fun addActionList(vararg actionIconList: ActionItem) {
        viewModelScope.launch {
            _actionIconList.value = actionIconList.toList()
        }
    }

    fun clearActionList() {
        viewModelScope.launch {
            _actionIconList.value = listOf()
        }
    }

    fun generateAction(
        scope: CoroutineScope,
        drawerState: DrawerState,
        actionExtra: () -> Unit,
        navController: NavController
    ) {
        when (_actionModeTopBar) {
            ActionModeTopBar.OPEN_DRAWER -> {
                scope.launch {
                    drawerState.open()
                }
            }

            ActionModeTopBar.POP_BACK_STACK -> {
                scope.launch {
                    setActionModeTopBar(ActionModeTopBar.NO_ACTION)
                    actionExtra()
                    navController.popBackStack()
                }
            }

            ActionModeTopBar.NO_ACTION -> {
                //NOTHING TO DO
            }

            ActionModeTopBar.ONLY_EXTRAS -> {
                actionExtra()
            }
        }
    }

    fun generateImg(iconTopBar: IconTopBar): ImageVector {
        return when (iconTopBar) {
            IconTopBar.BURGER -> Icons.Filled.Menu
            IconTopBar.ARRROW -> Icons.AutoMirrored.Filled.ArrowBack
            IconTopBar.NONE -> ImageVector.Builder("", 0.dp, 0.dp, 0f, 0f).build()
        }
    }

    fun generateContentDescription(): String {
        return when (_iconTopBar.value) {
            IconTopBar.BURGER -> "Menu Icon Menu"
            IconTopBar.ARRROW -> "Menu Icon Arrow"
            IconTopBar.NONE -> ""
        }
    }

    fun actionNavIcon(
        interaction: Interaction,
        drawerState: DrawerState,
        scope: CoroutineScope,
        navController: NavController
    ) {
        when (interaction) {
            is PressInteraction.Press -> {
                this.setOnProcess(true)
            }

            is PressInteraction.Release -> {
                this.generateAction(
                    scope = scope,
                    drawerState = drawerState,
                    navController = navController,
                    actionExtra = _actionExtra.value
                )
                this.setOnProcess(false)
            }

            is PressInteraction.Cancel -> {
                this.setOnProcess(false)
            }
        }
    }

    fun actionListIcons(
        interaction: Interaction,
        action: () -> Unit
    ) {
        when (interaction) {
            is PressInteraction.Press -> {
                this.setOnProcess(true)
            }

            is PressInteraction.Release -> {
                action()
                this.setOnProcess(false)
            }

            is PressInteraction.Cancel -> {
                this.setOnProcess(false)
            }
        }
    }
}