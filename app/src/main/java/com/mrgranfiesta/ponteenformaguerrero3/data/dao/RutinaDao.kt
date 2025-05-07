package com.mrgranfiesta.ponteenformaguerrero3.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.rutina.RowRutinaDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.rutina.RutinaInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.RutinaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RutinaDao {
    @Query("""
        Select 
            rt.idRutina,
            rt.idUser,
            rt.nombre,
            rt.nivel,
            rt.musculoSet,
            rt.isPremium
        from rutina rt
        JOIN user u ON u.idUser = rt.idUser
        WHERE rt.idUser = :idUser
        OR u.idUser = 1
    """)
    fun getRowRutinaAll(idUser : Long): Flow<List<RowRutinaDB>>

    @Query("""
        SELECT
            rt.idRutina,
            rt.idUser,
            rt.nombre,
            rt.descripcion,
            rt.musculoSet,
            rt.nivel,
            rt.calentamiento,
            rt.estiramiento,
            rt.movArticular,
            rt.descanso,
            u.rol
        FROM rutina rt
        JOIN user u ON u.idUser = rt.idUser
        WHERE rt.idRutina = :idRutina
    """)
    fun getRutinaInfoDBByPkFlow(idRutina : Long) : Flow<RutinaInfoDB>

    @Query("Select * from rutina rt where rt.idRutina = :idRutina")
    suspend fun getByPkNoFlow(idRutina: Long): RutinaEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(rutinaEntity: RutinaEntity) : Long

    @Update
    suspend fun update(rutinaEntity: RutinaEntity) : Int

    @Delete
    suspend fun delete(rutinaEntity: RutinaEntity) : Int
}