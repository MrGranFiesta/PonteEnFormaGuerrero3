package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ImageFromUriVM : ViewModel() {
    private val _isOnProcess = MutableStateFlow(false)
    val isOnProcess: StateFlow<Boolean> = _isOnProcess
    private fun setOnProcess(isOnProcess: Boolean) {
        _isOnProcess.value = isOnProcess
    }

    val interationUpdBt = MutableInteractionSource()

    fun actionUpdateButton(
        onClickUpload: () -> Unit = {},
        interaction: Interaction
    ) {
        when (interaction) {
            is PressInteraction.Press -> {
                this.setOnProcess(true)
            }

            is PressInteraction.Release -> {
                onClickUpload()
                this.setOnProcess(false)
            }

            is PressInteraction.Cancel -> {
                this.setOnProcess(false)
            }
        }
    }
}