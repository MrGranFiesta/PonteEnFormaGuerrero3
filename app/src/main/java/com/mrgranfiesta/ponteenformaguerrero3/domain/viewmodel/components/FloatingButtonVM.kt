package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FloatingButtonVM : ViewModel() {
    private val _isShow = MutableStateFlow(false)
    val isShow: StateFlow<Boolean> = _isShow
    private fun setIsShow(isShow: Boolean) {
        _isShow.value = isShow
    }

    private val _contentDescripcion = MutableStateFlow("")
    val contentDescripcion: StateFlow<String> = _contentDescripcion
    private fun setContentDescripcion(contentDescripcion: String) {
        _contentDescripcion.value = contentDescripcion
    }

    private val _icon = MutableStateFlow(Icons.Filled.Add)
    val icon: StateFlow<ImageVector> = _icon
    private fun setIcon(icon: ImageVector) {
        _icon.value = icon
    }

    private val _tooltipText = MutableStateFlow("")
    val tooltipText: StateFlow<String> = _tooltipText
    private fun setTooltipText(tooltipText: String) {
        _tooltipText.value = tooltipText
    }

    var actionButton: () -> Unit = {}

    fun makeFloatingButton(
        contentDescripcion: String,
        icon: ImageVector,
        autoClose: Boolean = false,
        tooltipText : String,
        onClick: () -> Unit = {}
    ) {
        setContentDescripcion(contentDescripcion)
        setIcon(icon)
        setIsShow(true)
        setTooltipText(tooltipText)
        actionButton = {
            setIsShow(!autoClose)
            onClick()
        }
    }

    fun resetFloatingButton(
    ) {
        setContentDescripcion("")
        setIsShow(false)
        actionButton = {}
    }
}