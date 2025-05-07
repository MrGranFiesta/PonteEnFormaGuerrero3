package com.mrgranfiesta.ponteenformaguerrero3.data.repository

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.EjercicioDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.ejercicio.EjercicioInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.ejercicio.EjercicioNameAndPhotoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.ejercicio.RowEjercicioDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.EjercicioEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EjercicioRepositoryImpl @Inject constructor(
    private val dao: EjercicioDao,
    val context: CoroutineDispatcher = Dispatchers.IO
) : EjercicioRepository {
    override fun getRowEjercicio(idUser : Long): Flow<List<RowEjercicioDB>> {
        return dao.getEjercicioListDB(idUser)
    }

    override fun getEjercicioNameAndPhotoDB(idEjercicio: Long): Flow<EjercicioNameAndPhotoDB> {
        return dao.getEjercicioNameAndPhoto(idEjercicio)
    }

    override fun getEjercicioInfo(idEjercicio: Long): Flow<EjercicioInfoDB> {
        return dao.getEjercicioInfo(idEjercicio)
    }

    override suspend fun getListByPkList(idEjercicioSet: List<Long>): List<EjercicioEntity> {
        return dao.getListByPkList(idEjercicioSet)
    }

    override suspend fun getListNameImgByIdUser(idUser : Long) : List<String> {
        return dao.getListNameImgByIdUser(idUser)
    }

    override suspend fun insert(ejercicioEntity: EjercicioEntity): Long = withContext(context) {
        dao.insert(ejercicioEntity)
    }

    override suspend fun update(ejercicioEntity: EjercicioEntity) = withContext(context) {
        dao.update(ejercicioEntity)
    }

    override suspend fun delete(ejercicioEntity: EjercicioEntity) = withContext(context) {
        dao.delete(ejercicioEntity)
    }
}