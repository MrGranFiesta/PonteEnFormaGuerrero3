package com.mrgranfiesta.ponteenformaguerrero3.data.repository

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.StatDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.stat.StatRutinaInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.stat.StatRutinaSearchDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.StatEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StatRepositoryImpl @Inject constructor(
    val daoStat: StatDao
    ) : StatRepository {
    override fun getAllStatRutina(idUser : Long): Flow<List<StatRutinaSearchDB>> {
        return daoStat.getAllStatRutina(idUser)
    }

    override fun getByPkStatRutina(id: Long) : Flow<StatRutinaInfoDB> = daoStat.getByPkStatRutina(id)

    override suspend fun insert(statEntity: StatEntity): Long = daoStat.insert(statEntity)
}