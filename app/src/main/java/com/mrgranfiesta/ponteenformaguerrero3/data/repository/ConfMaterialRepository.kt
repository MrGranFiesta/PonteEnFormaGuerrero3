package com.mrgranfiesta.ponteenformaguerrero3.data.repository

import com.mrgranfiesta.ponteenformaguerrero3.data.entity.ConfMaterialEntity

interface ConfMaterialRepository {
    fun getListNoFlowByIdStep(idStep: Long): List<ConfMaterialEntity>
    suspend fun insert(confMaterial: ConfMaterialEntity): Long
    suspend fun update(confMaterial: ConfMaterialEntity) : Int
}