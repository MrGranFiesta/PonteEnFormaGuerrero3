package com.mrgranfiesta.ponteenformaguerrero3.data.repository

import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.material.MaterialInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.material.RowMaterialDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.MaterialEntity
import kotlinx.coroutines.flow.Flow

interface MaterialRepository {
    fun getRowMaterialDBAll(idUser : Long): Flow<List<RowMaterialDB>>
    fun getRowMaterialByPkList(idMaterialList: List<Long>): Flow<List<RowMaterialDB>>
    suspend fun getListByPkListNoFlow(idMaterialList: List<Long>): List<MaterialEntity>
    fun getMaterialInfoByPk(idMaterial: Long): Flow<MaterialInfoDB>
    fun getRowMaterialByIdEjercicio(idEjercicio: Long): Flow<List<RowMaterialDB>>
    fun getRowMaterialByIdRutina(idRutina: Long): Flow<List<RowMaterialDB>>
    fun getListNoFlowByIdEjercicio(idEjercicio: Long): List<MaterialEntity>
    suspend fun getListNameImgByIdUser(idUser: Long): List<String>
    suspend fun insert(materialEntity: MaterialEntity) : Long
    suspend fun update(materialEntity: MaterialEntity) : Int
    suspend fun delete(materialEntity: MaterialEntity) : Int
}