package com.mrgranfiesta.ponteenformaguerrero3.data.repository

import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.stat.StatRutinaInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.stat.StatRutinaSearchDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.StatEntity
import kotlinx.coroutines.flow.Flow

interface StatRepository {
    fun getAllStatRutina(idUser : Long): Flow<List<StatRutinaSearchDB>>
    fun getByPkStatRutina(id: Long) : Flow<StatRutinaInfoDB>
    suspend fun insert(statEntity: StatEntity): Long
}