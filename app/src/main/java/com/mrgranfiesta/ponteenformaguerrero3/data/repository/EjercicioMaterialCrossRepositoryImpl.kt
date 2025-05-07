package com.mrgranfiesta.ponteenformaguerrero3.data.repository

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.EjercicioMaterialCrossDao
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.EjercicioMaterialCrossEntity
import javax.inject.Inject

class EjercicioMaterialCrossRepositoryImpl @Inject constructor(
    private val dao: EjercicioMaterialCrossDao
) : EjercicioMaterialCrossRepository {
    override suspend fun insert(ejercicioMaterialCross: EjercicioMaterialCrossEntity) : Long {
        return dao.insert(ejercicioMaterialCross)
    }

    override suspend fun insertList(ejercicioMaterialCross: List<EjercicioMaterialCrossEntity>) {
        return dao.insertList(ejercicioMaterialCross)
    }

    override suspend fun deleteList(ejercicioMaterialCross: List<EjercicioMaterialCrossEntity>) {
        return dao.deleteList(ejercicioMaterialCross)
    }
}