package com.mrgranfiesta.ponteenformaguerrero3.data.repository

import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.ejercicio.EjercicioInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.ejercicio.EjercicioNameAndPhotoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.ejercicio.RowEjercicioDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.EjercicioEntity
import kotlinx.coroutines.flow.Flow

interface EjercicioRepository {
    fun getRowEjercicio(idUser : Long) : Flow<List<RowEjercicioDB>>
    fun getEjercicioNameAndPhotoDB(idEjercicio: Long) : Flow<EjercicioNameAndPhotoDB>
    fun getEjercicioInfo(idEjercicio: Long): Flow<EjercicioInfoDB>
    suspend fun getListByPkList(idEjercicioSet: List<Long>): List<EjercicioEntity>
    suspend fun getListNameImgByIdUser(idUser: Long): List<String>
    suspend fun insert(ejercicioEntity: EjercicioEntity): Long
    suspend fun update(ejercicioEntity: EjercicioEntity) : Int
    suspend fun delete(ejercicioEntity: EjercicioEntity) : Int
}