package com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot

import androidx.room.Dao
import androidx.room.Query
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.confmaterial.MaterialWithConfSnapshotDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.step.MaterialWithConfSnapshotStepDB
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfMaterialSnapshotDao {
    @Query("""
        SELECT 
            COALESCE(c.idConfMaterialSnapshot, 0) AS idConfMaterialSnapshot,
            COALESCE(c.idConfMaterial , 0) AS idConfMaterial,
            mts.idMaterialSnapshot,
            mts.idMaterial,
            sts.idStepSnapshot,
            sts.idStep,
            COALESCE(c.confValue, 0) AS confValue,
            mts.nombre,
            mts.isMaterialWeight,
            mts.nameImg,
            u.rol
        FROM stepSnapshot sts
        JOIN ejercicioMaterialCrossSnapshot emcs ON emcs.idEjercicioSnapshot = sts.idEjercicioSnapshot
        JOIN materialSnapshot mts ON emcs.idMaterialSnapshot = mts.idMaterialSnapshot
        JOIN user u ON u.idUser = mts.idUser
        LEFT JOIN confMaterialSnapshot c ON mts.idMaterialSnapshot = c.idMaterialSnapshot
            AND c.idStepSnapshot = sts.idStepSnapshot
        WHERE sts.idStepSnapshot IN (:idStepSnapshot)
    """)
    suspend fun getMaterialWithConfListByIdStep(idStepSnapshot: List<Long>): List<MaterialWithConfSnapshotStepDB>

    @Query("""
        SELECT 
            COALESCE(cms.idConfMaterialSnapshot, 0) AS idConfMaterialSnapshot,
            COALESCE(cms.idConfMaterial, 0) AS idConfMaterial,
            mts.idMaterialSnapshot,
            mts.idMaterial,
            ejs.idEjercicioSnapshot,
            ejs.idEjercicio,
            COALESCE(cms.confValue, 0) AS confValue,
            mts.nombre,
            mts.isMaterialWeight,
            mts.nameImg,
            u.rol
        FROM ejercicioSnapshot ejs
        JOIN ejercicioMaterialCrossSnapshot emcs ON emcs.idEjercicioSnapshot = ejs.idEjercicioSnapshot
        JOIN materialSnapshot mts ON emcs.idMaterialSnapshot = mts.idMaterialSnapshot
        JOIN user u ON u.idUser = mts.idUser
        LEFT JOIN confMaterialSnapshot cms ON mts.idMaterialSnapshot = cms.idMaterialSnapshot
        WHERE ejs.idEjercicioSnapshot = :idEjercicioSnapshot
    """)
    fun getMaterialWithConfByIdEjercicio(idEjercicioSnapshot: Long): Flow<List<MaterialWithConfSnapshotDB>>
}