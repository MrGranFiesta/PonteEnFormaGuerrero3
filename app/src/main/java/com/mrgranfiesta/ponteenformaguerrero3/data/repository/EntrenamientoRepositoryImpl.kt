package com.mrgranfiesta.ponteenformaguerrero3.data.repository

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.EntrenamientoDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono.EntrenamientoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono.MaterialEntrenamientoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono.StepEntrenamientoDB
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EntrenamientoRepositoryImpl @Inject constructor(
    private val dao: EntrenamientoDao,
    val context: CoroutineDispatcher = Dispatchers.IO
) : EntrenamientoRepository {
    override fun getEntrenamiento(idRutina: Long): Flow<EntrenamientoDB> {
        return dao.getEntrenamiento(idRutina)
    }

    override fun getStepEntrenamiento(idRutina: Long): Flow<List<StepEntrenamientoDB>> {
        return dao.getStepEntrenamiento(idRutina)
    }

    override fun getMaterialEntrenamiento(idRutina: Long): Flow<List<MaterialEntrenamientoDB>> {
        return dao.getMaterialEntrenamiento(idRutina)
    }
}