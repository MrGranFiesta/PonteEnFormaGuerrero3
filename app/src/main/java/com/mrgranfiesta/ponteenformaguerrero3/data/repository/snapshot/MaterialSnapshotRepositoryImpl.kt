package com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot.MaterialSnapshotDao
import javax.inject.Inject

class MaterialSnapshotRepositoryImpl @Inject constructor(
    private val dao: MaterialSnapshotDao
) : MaterialSnapshotRepository {
    override suspend fun isUseNameImg(nameImg : String) : Boolean {
        return dao.isExistNameImg(nameImg)
    }

    override suspend fun isNotUseNameImg(nameImg: String): Boolean {
        return dao.isNotExistNameImg(nameImg)
    }

    override suspend fun getNameImgByRutinaId(idRutina: Long): List<String> {
        return dao.getNameImgByIdRutina(idRutina)
    }

    override suspend fun isNotUseNameImgGlobal(nameImg : String) : Boolean {
        return dao.isNotExistNameImgGlobal(nameImg)
    }
}