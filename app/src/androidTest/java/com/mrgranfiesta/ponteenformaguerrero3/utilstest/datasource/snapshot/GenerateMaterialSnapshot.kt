package com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource.snapshot

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot.SnapshotDao
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.MaterialSnapshotEntity
import javax.inject.Inject

class GenerateMaterialSnapshot @Inject constructor(
    private val dao: SnapshotDao
) {
    suspend operator fun invoke() {
        dao.insertMaterialSnapshot(getDefaultMaterialSnapshot())
    }

    private fun getDefaultMaterialSnapshot(): List<MaterialSnapshotEntity> {
        return listOf(
            MaterialSnapshotEntity(
                idMaterialSnapshot = 1,
                idMaterial = 1,
                idUser = 1,
                nombre = "Mancuerna",
                isMaterialWeight = true,
                descripcion = "Ejemplo 1",
                nameImg = ""
            ),
            MaterialSnapshotEntity(
                idMaterialSnapshot = 2,
                idMaterial = 2,
                idUser = 1,
                nombre = "Banco",
                isMaterialWeight = false,
                descripcion = "Ejemplo 2",
                nameImg = "mat2_000_000"
            ),
            MaterialSnapshotEntity(
                idMaterialSnapshot = 3,
                idMaterial = 3,
                idUser = 1,
                nombre = "Comba",
                isMaterialWeight = false,
                descripcion = "Ejemplo 3",
                nameImg = ""
            )
        )
    }
}