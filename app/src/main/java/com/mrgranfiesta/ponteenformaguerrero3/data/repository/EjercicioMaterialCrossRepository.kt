package com.mrgranfiesta.ponteenformaguerrero3.data.repository

import com.mrgranfiesta.ponteenformaguerrero3.data.entity.EjercicioMaterialCrossEntity

interface EjercicioMaterialCrossRepository {
    suspend fun insert(ejercicioMaterialCross: EjercicioMaterialCrossEntity) : Long
    suspend fun insertList(ejercicioMaterialCross: List<EjercicioMaterialCrossEntity>)
    suspend fun deleteList(ejercicioMaterialCross: List<EjercicioMaterialCrossEntity>)
}