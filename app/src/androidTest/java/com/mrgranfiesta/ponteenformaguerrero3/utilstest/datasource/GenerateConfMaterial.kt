package com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.ConfMaterialDao
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.ConfMaterialEntity
import javax.inject.Inject
import kotlin.collections.forEach

class GenerateConfMaterial @Inject constructor(
    private val dao: ConfMaterialDao
) {
    suspend operator fun invoke() {
        getDefaultConfMaterial().forEach { conf ->
            dao.insert(conf)
        }
    }

    private fun getDefaultConfMaterial() : List<ConfMaterialEntity> {
        return listOf(
            ConfMaterialEntity(
                idConfMaterial = 1,
                idMaterial = 1,
                idStep = 1,
                confValue = 1.0
            ),
            ConfMaterialEntity(
                idConfMaterial = 2,
                idMaterial = 1,
                idStep = 4,
                confValue = 2.0
            ),
            ConfMaterialEntity(
                idConfMaterial = 3,
                idMaterial = 1,
                idStep = 5,
                confValue = 3.0
            )
        )
    }
}