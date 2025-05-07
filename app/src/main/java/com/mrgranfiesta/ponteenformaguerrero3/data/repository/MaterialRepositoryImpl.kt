package com.mrgranfiesta.ponteenformaguerrero3.data.repository

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.MaterialDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.material.MaterialInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.material.RowMaterialDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.MaterialEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MaterialRepositoryImpl @Inject constructor(
    private val dao: MaterialDao,
    val context: CoroutineDispatcher = Dispatchers.IO
) : MaterialRepository {
    override fun getRowMaterialDBAll(idUser : Long): Flow<List<RowMaterialDB>> {
        return dao.getRowMaterialAll(idUser)
    }

    override fun getRowMaterialByPkList(idMaterialList: List<Long>): Flow<List<RowMaterialDB>> {
        return dao.getRowMaterialByPkList(idMaterialList)
    }

    override suspend fun getListByPkListNoFlow(idMaterialList: List<Long>): List<MaterialEntity> {
        return dao.getListByPkListNoFlow(idMaterialList)
    }

    override fun getMaterialInfoByPk(idMaterial: Long): Flow<MaterialInfoDB> {
        return dao.getMaterialInfoByPk(idMaterial)
    }

    override fun getRowMaterialByIdEjercicio(idEjercicio: Long): Flow<List<RowMaterialDB>> {
        return dao.getEjercicioInfoMaterial(idEjercicio)
    }

    override fun getRowMaterialByIdRutina(idRutina: Long): Flow<List<RowMaterialDB>> {
        return dao.getRowMaterialByIdRutina(idRutina)
    }

    override fun getListNoFlowByIdEjercicio(idEjercicio: Long): List<MaterialEntity> {
        return dao.getListNoFlowByIdEjercicio(idEjercicio)
    }

    override suspend fun getListNameImgByIdUser(idUser : Long) : List<String> {
        return dao.getListNameImgByIdUser(idUser)
    }

    override suspend fun insert(materialEntity: MaterialEntity) = withContext(context) {
        dao.insert(materialEntity)
    }

    override suspend fun update(materialEntity: MaterialEntity) = withContext(context) {
        dao.update(materialEntity)
    }

    override suspend fun delete(materialEntity: MaterialEntity) = withContext(context) {
        dao.delete(materialEntity)
    }
}