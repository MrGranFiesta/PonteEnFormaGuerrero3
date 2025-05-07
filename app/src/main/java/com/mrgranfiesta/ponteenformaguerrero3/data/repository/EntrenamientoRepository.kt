package com.mrgranfiesta.ponteenformaguerrero3.data.repository

import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono.EntrenamientoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono.MaterialEntrenamientoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono.StepEntrenamientoDB
import kotlinx.coroutines.flow.Flow

interface EntrenamientoRepository {
    fun getEntrenamiento(idRutina: Long): Flow<EntrenamientoDB>
    fun getStepEntrenamiento(idRutina: Long): Flow<List<StepEntrenamientoDB>>
    fun getMaterialEntrenamiento(idRutina: Long): Flow<List<MaterialEntrenamientoDB>>
}