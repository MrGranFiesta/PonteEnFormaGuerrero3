package com.mrgranfiesta.ponteenformaguerrero3.data.repository

import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.step.StepFormWithConfMaterialDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.step.StepInfoWithConfMaterialDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.StepEntity
import kotlinx.coroutines.flow.Flow

interface StepRepository {
    fun getStepInfoWithMaterialConfListByIdRutina(idRutina: Long): Flow<List<StepInfoWithConfMaterialDB>>
    fun getStepFormWithMaterialConfListByIdRutina(idRutina: Long): Flow<List<StepFormWithConfMaterialDB>>
    suspend fun insert(stepEntity: StepEntity): Long
    suspend fun update(stepEntity: StepEntity): Int
    suspend fun delete(stepEntity: StepEntity): Int
}