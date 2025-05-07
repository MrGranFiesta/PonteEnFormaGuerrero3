package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ComboBoxVM : ViewModel() {
    private var _isExplanded = MutableStateFlow(false)
    val isExplanded: StateFlow<Boolean> = _isExplanded
    fun setIsExplanded(isExplanded: Boolean) {
        _isExplanded.value = isExplanded
    }

    private var _icon = MutableStateFlow(Icons.Filled.KeyboardArrowUp)
    val icon: StateFlow<ImageVector> = _icon
    private fun setIsExplanded(icon: ImageVector) {
        _icon.value = icon
    }

    fun initData() {
        if (isExplanded.value) {
            setIsExplanded(Icons.Filled.KeyboardArrowUp)
        } else {
            setIsExplanded(Icons.Filled.KeyboardArrowDown)
        }
    }

    fun onClickDropMenu(
        optionSelected: String,
        changeSelectOption: (String) -> Unit,
        changeExtras: () -> Unit = {}
    ) {
        changeSelectOption(optionSelected)
        changeExtras()
        setIsExplanded(false)
    }
}