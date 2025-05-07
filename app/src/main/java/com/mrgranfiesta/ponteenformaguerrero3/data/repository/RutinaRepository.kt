package com.mrgranfiesta.ponteenformaguerrero3.data.repository

import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.rutina.RowRutinaDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.rutina.RutinaInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.RutinaEntity
import kotlinx.coroutines.flow.Flow

interface RutinaRepository {
    fun getRowRutinaAll(idUser : Long): Flow<List<RowRutinaDB>>
    fun getRutinaInfoDBByPkFlow(idRutina: Long): Flow<RutinaInfoDB>
    suspend fun getByPkNoFlow(idRutina: Long): RutinaEntity
    suspend fun insert(rutinaEntity: RutinaEntity) : Long
    suspend fun update(rutinaEntity: RutinaEntity) : Int
    suspend fun delete(rutinaEntity: RutinaEntity) : Int
}