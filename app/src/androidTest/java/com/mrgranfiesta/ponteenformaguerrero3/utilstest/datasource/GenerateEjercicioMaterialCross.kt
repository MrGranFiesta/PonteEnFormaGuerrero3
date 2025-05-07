package com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.EjercicioMaterialCrossDao
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.EjercicioMaterialCrossEntity
import javax.inject.Inject

class GenerateEjercicioMaterialCross @Inject constructor(
    private val dao: EjercicioMaterialCrossDao
) {
    suspend operator fun invoke() {
        getDefaultCross().forEach { cross ->
            dao.insert(cross)
        }
    }

    private fun getDefaultCross(): List<EjercicioMaterialCrossEntity> {
        return listOf(
            EjercicioMaterialCrossEntity(
                idEjercicio = 1,
                idMaterial = 1
            ),
            EjercicioMaterialCrossEntity(
                idEjercicio = 2,
                idMaterial = 2
            ),
            EjercicioMaterialCrossEntity(
                idEjercicio = 3,
                idMaterial = 3
            )
        )
    }
}