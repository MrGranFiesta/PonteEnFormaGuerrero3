package com.mrgranfiesta.ponteenformaguerrero3.data.repository

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.ConfMaterialDao
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.ConfMaterialEntity
import javax.inject.Inject

class ConfMaterialRepositoryImpl @Inject constructor(
    private val dao: ConfMaterialDao
) : ConfMaterialRepository {
    override fun getListNoFlowByIdStep(idStep: Long): List<ConfMaterialEntity> =
        dao.getListNoFlowByIdStep(idStep)

    override suspend fun insert(confMaterial: ConfMaterialEntity): Long {
        return dao.insert(confMaterial)
    }

    override suspend fun update(confMaterial: ConfMaterialEntity): Int {
        return dao.update(confMaterial)
    }
}