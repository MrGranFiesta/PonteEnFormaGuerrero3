package com.mrgranfiesta.ponteenformaguerrero3.data.repository

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.RutinaDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.rutina.RowRutinaDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.rutina.RutinaInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.RutinaEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RutinaRepositoryImpl @Inject constructor(
    private val dao: RutinaDao,
    val context: CoroutineDispatcher = Dispatchers.IO
) : RutinaRepository {
    override fun getRowRutinaAll(idUser : Long): Flow<List<RowRutinaDB>> {
        return dao.getRowRutinaAll(idUser)
    }

    override suspend fun getByPkNoFlow(idRutina: Long): RutinaEntity = dao.getByPkNoFlow(idRutina)

    override fun getRutinaInfoDBByPkFlow(idRutina: Long): Flow<RutinaInfoDB> {
        return dao.getRutinaInfoDBByPkFlow(idRutina)
    }

    override suspend fun insert(rutinaEntity: RutinaEntity): Long = withContext(context) {
            dao.insert(rutinaEntity)
        }

    override suspend fun update(rutinaEntity: RutinaEntity) = withContext(context) {
        dao.update(rutinaEntity)
    }

    override suspend fun delete(rutinaEntity: RutinaEntity) = withContext(context) {
        dao.delete(rutinaEntity)
    }
}