package com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource.snapshot

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot.SnapshotDao
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.ConfMaterialSnapshotEntity
import javax.inject.Inject

class GenerateConfMaterialSnapshot @Inject constructor(
    private val dao: SnapshotDao
) {
    suspend operator fun invoke() {
        dao.insertConfMaterialListSnapshot(getDefaultEjercicioSnapshot())
    }

    private fun getDefaultEjercicioSnapshot(): List<ConfMaterialSnapshotEntity> {
        return return listOf(
            ConfMaterialSnapshotEntity(
                idConfMaterialSnapshot = 1,
                idConfMaterial = 1,
                idMaterialSnapshot = 1,
                idStepSnapshot = 1,
                confValue = 1.0
            ),
            ConfMaterialSnapshotEntity(
                idConfMaterialSnapshot = 2,
                idConfMaterial = 2,
                idMaterialSnapshot = 1,
                idStepSnapshot = 4,
                confValue = 2.0
            ),
            ConfMaterialSnapshotEntity(
                idConfMaterialSnapshot = 3,
                idConfMaterial = 3,
                idMaterialSnapshot = 1,
                idStepSnapshot = 5,
                confValue = 3.0
            )
        )
    }
}