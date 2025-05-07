package com.mrgranfiesta.ponteenformaguerrero3.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.step.StepFormWithConfMaterialTempDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.step.StepInfoWithConfMaterialTempDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.StepEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StepDao {
    @Query("""
        SELECT 
            st.idStep,
            st.idEjercicio,
            st.cantidad,
            st.serie,
            st.ordenExecution,
            st.tipo,
            mt.idMaterial,
            mt.nombre,
            mt.isMaterialWeight,
            mt.nameImg,
            u.rol,
            cm.idConfMaterial,
            cm.confValue
        FROM step st
        INNER JOIN ejercicio ej ON st.idEjercicio = ej.idEjercicio
        LEFT JOIN ejercicioMaterialCross emc ON ej.idEjercicio = emc.idEjercicio
        LEFT JOIN material mt ON emc.idMaterial = mt.idMaterial
        LEFT JOIN confMaterial cm ON st.idStep = cm.idStep AND mt.idMaterial = cm.idMaterial
        LEFT JOIN user u ON u.idUser = mt.idUser
        WHERE st.idRutina = :idRutina
        ORDER BY st.ordenExecution, mt.idMaterial
    """)
    fun getStepInfoWithConfMaterialByRutinaId(idRutina: Long): Flow<List<StepInfoWithConfMaterialTempDB>>

    @Query("""
        SELECT 
            st.idStep,
            st.idRutina,
            st.idEjercicio,
            st.cantidad,
            st.serie,
            st.ordenExecution,
            st.tipo,
            mt.idMaterial,
            mt.nombre,
            mt.isMaterialWeight,
            mt.nameImg,
            u.rol,
            cm.idConfMaterial,
            cm.confValue
        FROM step st
        INNER JOIN ejercicio ej ON st.idEjercicio = ej.idEjercicio
        LEFT JOIN ejercicioMaterialCross emc ON ej.idEjercicio = emc.idEjercicio
        LEFT JOIN material mt ON emc.idMaterial = mt.idMaterial
        LEFT JOIN confMaterial cm ON st.idStep = cm.idStep AND mt.idMaterial = cm.idMaterial
        LEFT JOIN user u ON u.idUser = mt.idUser
        WHERE st.idRutina = :idRutina
        ORDER BY st.ordenExecution, mt.idMaterial
    """)
    fun getStepFormWithConfMaterialByRutinaId(idRutina: Long): Flow<List<StepFormWithConfMaterialTempDB>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(stepEntity: StepEntity): Long

    @Update
    suspend fun update(stepEntity: StepEntity) : Int

    @Delete
    suspend fun delete(stepEntity: StepEntity) : Int
}