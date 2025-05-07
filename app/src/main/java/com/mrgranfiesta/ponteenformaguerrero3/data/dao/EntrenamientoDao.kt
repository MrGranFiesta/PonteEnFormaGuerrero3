package com.mrgranfiesta.ponteenformaguerrero3.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono.EntrenamientoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono.MaterialEntrenamientoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono.StepEntrenamientoDB
import kotlinx.coroutines.flow.Flow

@Dao
interface EntrenamientoDao {
    @Query(
        """Select 
        rt.idRutina,
        rt.calentamiento,
        rt.estiramiento,
        rt.movArticular,
        rt.descanso,
        rt.musculoSet
        From rutina rt
        where rt.idRutina = :idRutina
    """
    )
    fun getEntrenamiento(idRutina: Long): Flow<EntrenamientoDB>

    @Query(
        """Select 
        st.idEjercicio,
        st.idStep,
        ej.nombre,
        ej.nameImg,
        ej.isSimetria,
        u.rol,
        ej.musculoSet,
        st.cantidad,
        st.serie,
        st.ordenExecution,
        st.tipo
        From step st
        JOIN ejercicio ej ON ej.idEjercicio = st.idEjercicio
        JOIN user u ON u.idUser = ej.idUser
        where st.idRutina = :idRutina
        ORDER BY st.ordenExecution
    """
    )
    fun getStepEntrenamiento(idRutina: Long): Flow<List<StepEntrenamientoDB>>

    @Query(
        """Select 
        mt.idMaterial,
        cm.idConfMaterial,
        cm.idStep,
        mt.nombre,
        mt.nameImg,
        mt.isMaterialWeight,
        cm.confValue,
        u.rol
        From material mt
        JOIN confMaterial cm ON cm.idMaterial = mt.idMaterial
        JOIN step st ON st.idStep = cm.idStep 
        JOIN user u ON u.idUser = mt.idUser
        WHERE st.idRutina = :idRutina
    """
    )
    fun getMaterialEntrenamiento(idRutina: Long): Flow<List<MaterialEntrenamientoDB>>
}