package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioNameAndPhotoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.ObjectFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RowStepVM @Inject constructor() : ViewModel() {
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private var status = true

    private val _ejercicio = MutableStateFlow(ObjectFactory.getDefaultEjercicioNameAndPhotoDto())
    val ejercicio: StateFlow<EjercicioNameAndPhotoDto> = _ejercicio
    private fun setEjercicio(ejercicio: EjercicioNameAndPhotoDto) {
        _ejercicio.value = ejercicio
    }

    @Synchronized
    fun initData(
        dataSource : () -> Flow<EjercicioNameAndPhotoDto>
    ) {
        if (status) {
            ioScope.launch {
                dataSource().collect {
                    setEjercicio(it)
                }
                status = false
            }
        }
    }

    companion object {
        fun createRowStepVM(): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    RowStepVM()
                }
            }
        }
    }
}