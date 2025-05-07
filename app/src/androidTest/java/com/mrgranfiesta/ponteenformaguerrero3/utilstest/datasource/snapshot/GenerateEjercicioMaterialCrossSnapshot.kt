package com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource.snapshot

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot.SnapshotDao
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.EjercicioMaterialCrossSnapshotEntity
import javax.inject.Inject

class GenerateEjercicioMaterialCrossSnapshot @Inject constructor(
    private val dao: SnapshotDao
) {
    suspend operator fun invoke() {
        dao.insertEjercicioMaterialCrossSnapshot(getDefaultCrossSnapshot())

    }

    private fun getDefaultCrossSnapshot(): List<EjercicioMaterialCrossSnapshotEntity> {
        return listOf(
            EjercicioMaterialCrossSnapshotEntity(
                idEjercicioSnapshot = 1,
                idMaterialSnapshot = 1,
            ),
            EjercicioMaterialCrossSnapshotEntity(
                idEjercicioSnapshot = 2,
                idMaterialSnapshot = 2
            )
        )
    }
}