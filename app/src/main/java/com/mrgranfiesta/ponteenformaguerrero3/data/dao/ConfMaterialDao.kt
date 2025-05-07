package com.mrgranfiesta.ponteenformaguerrero3.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.ConfMaterialEntity

@Dao
interface ConfMaterialDao {
    @Query("""Select * from confMaterial cms where cms.idStep = :idStep""")
    fun getListNoFlowByIdStep(idStep: Long): List<ConfMaterialEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(confMaterialEntity: ConfMaterialEntity): Long

    @Update
    suspend fun update(confMaterialEntity: ConfMaterialEntity) : Int
}