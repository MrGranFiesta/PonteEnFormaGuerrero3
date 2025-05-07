package com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot.EjercicioSnapshotDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.ejercicio.EjercicioSnapshotInfoDB
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EjercicioSnapshotRepositoryImpl @Inject constructor(
    private val dao: EjercicioSnapshotDao
) : EjercicioSnapshotRepository {
    override fun getEjercicioSnapshotInfoByPk(idEjercicioSnapshot : Long): Flow<EjercicioSnapshotInfoDB> {
        return dao.getEjercicioSnapshotInfoByPkFlow(idEjercicioSnapshot)
    }

    override fun getEjercicioSnapshotInfoNulleableByPk(idEjercicioSnapshot : Long): Flow<EjercicioSnapshotInfoDB?> {
        return dao.getEjercicioSnapshotInfoNulleableByPkFlow(idEjercicioSnapshot)
    }

    override suspend fun getNameImgByRutinaId(idRutina : Long) : List<String> {
        return dao.getNameImgByRutinaId(idRutina)
    }

    override suspend fun isUseNameImg(nameImg: String): Boolean {
        return dao.isExistNameImg(nameImg)
    }

    override suspend fun isNotUseNameImg(nameImg: String): Boolean {
        return dao.isNotExistNameImg(nameImg)
    }

    override suspend fun isNotUseNameImgGlobal(nameImg: String) : Boolean {
        return dao.isNotExistNameImgGlobal(nameImg)
    }
}