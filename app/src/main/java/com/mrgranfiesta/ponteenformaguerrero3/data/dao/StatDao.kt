package com.mrgranfiesta.ponteenformaguerrero3.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.stat.StatRutinaInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.stat.StatRutinaSearchDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.StatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StatDao {
    @Query(
        """SELECT 
        stt.idStat,
        stt.idRutinaSnapshot,
        stt.idRutina,
        stt.dateInit,
        stt.dateEnd,
        stt.isCompleted,
        rts.nombre,
        rts.nivel,
        rts.isPremium,
        rts.musculoSet,
        u.rol
        FROM stat stt
        JOIN rutinaSnapshot rts ON stt.idRutinaSnapshot = rts.idRutinaSnapshot
        JOIN user u ON stt.idUser = u.idUser
        WHERE u.idUser = :idUser
        """
    )
    fun getAllStatRutina(idUser : Long): Flow<List<StatRutinaSearchDB>>

    @Query(
        """Select 
        stt.idStat,
        stt.idRutinaSnapshot,
        stt.idRutina,
        stt.dateInit,
        stt.dateEnd,
        stt.iscalentamientoDone,
        stt.isMovArticularDone,
        stt.isEstiramientoDone,
        stt.isCompleted,
        rts.nombre,
        rts.nivel,
        rts.musculoSet,
        rts.descripcion,
        u.rol
        from stat stt
        JOIN rutinaSnapshot rts ON stt.idRutina = rts.idRutina AND stt.idRutinaSnapshot = rts.idRutinaSnapshot
        JOIN user u ON rts.idUser = u.idUser
        where stt.idStat = :idStat"""
    )
    fun getByPkStatRutina(idStat: Long): Flow<StatRutinaInfoDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(statEntity: StatEntity): Long
}