package com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot

import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.ejercicio.EjercicioSnapshotInfoDB
import kotlinx.coroutines.flow.Flow

interface EjercicioSnapshotRepository {
    fun getEjercicioSnapshotInfoByPk(idEjercicioSnapshot : Long): Flow<EjercicioSnapshotInfoDB>
    fun getEjercicioSnapshotInfoNulleableByPk(idEjercicioSnapshot : Long): Flow<EjercicioSnapshotInfoDB?>
    suspend fun getNameImgByRutinaId(idRutina: Long) : List<String>
    suspend fun isUseNameImg(nameImg : String) : Boolean
    suspend fun isNotUseNameImg(nameImg : String) : Boolean
    suspend fun isNotUseNameImgGlobal(nameImg : String) : Boolean
}