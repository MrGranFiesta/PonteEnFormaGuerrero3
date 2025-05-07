package com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.MaterialDao
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.MaterialEntity
import javax.inject.Inject
import kotlin.collections.forEach

class GenerateMaterial @Inject constructor(
    private val dao: MaterialDao
) {
    suspend operator fun invoke() {
        getDefaultMaterial().forEach { mat ->
            dao.insert(mat)
        }
    }

    private fun getDefaultMaterial() : List<MaterialEntity> {
        return listOf(
            MaterialEntity(
                idMaterial = 1,
                idUser = 1,
                nombre = "Mancuerna",
                isMaterialWeight = true,
                descripcion = "Ejemplo 1",
                nameImg = ""
            ),
            MaterialEntity(
                idMaterial = 2,
                idUser = 1,
                nombre = "Banco",
                isMaterialWeight = false,
                descripcion = "Ejemplo 2",
                nameImg = "mat2_000_000"
            ),
            MaterialEntity(
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