package com.mrgranfiesta.ponteenformaguerrero3.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.EjercicioMaterialCrossEntity

@Dao
interface EjercicioMaterialCrossDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ejercicioMaterialCross: EjercicioMaterialCrossEntity) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(ejercicioMaterialCross: List<EjercicioMaterialCrossEntity>)

    @Delete
    suspend fun deleteList(ejercicioMaterialCross: List<EjercicioMaterialCrossEntity>)
}