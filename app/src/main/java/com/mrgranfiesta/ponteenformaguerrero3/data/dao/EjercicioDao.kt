package com.mrgranfiesta.ponteenformaguerrero3.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.ejercicio.EjercicioInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.ejercicio.EjercicioNameAndPhotoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.ejercicio.RowEjercicioDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.EjercicioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EjercicioDao {
    @Query("""Select 
        ej.idEjercicio,
        ej.nombre,
        ej.musculoSet,
        ej.nivel,
        ej.nameImg,
        U.rol
        from ejercicio ej
        JOIN user u ON u.idUser = ej.idUser
        WHERE ej.idUser = :idUser
        OR u.rol = 1
        """)
    fun getEjercicioListDB(
        idUser : Long
    ): Flow<List<RowEjercicioDB>>

    @Query("""
        SELECT 
            ej.idEjercicio,
            ej.nombre,
            ej.nameImg,
            U.rol
        FROM Ejercicio ej
        JOIN user u ON u.idUser = ej.idUser
        WHERE ej.idEjercicio = :idEjercicio
    """)
    fun getEjercicioNameAndPhoto(idEjercicio : Long) : Flow<EjercicioNameAndPhotoDB>

    @Query("""
        Select 
         ej.idEjercicio,
         ej.idUser,
         ej.nombre,
         ej.isSimetria,
         ej.musculoSet,
         ej.nivel,
         ej.descripcion,
         ej.nameImg,
         U.rol
        from ejercicio ej
        join user u ON ej.idUser = u.idUser
        where ej.idEjercicio = :idEjercicio""")
    fun getEjercicioInfo(idEjercicio: Long): Flow<EjercicioInfoDB>

    @Query("Select * from ejercicio where idEjercicio IN (:idEjercicioList)")
    suspend fun getListByPkList(idEjercicioList: List<Long>): List<EjercicioEntity>

    @Query("""
        SELECT ej.nameImg
        FROM ejercicio ej
        WHERE ej.idUser = :idUser
    """)
    suspend fun getListNameImgByIdUser(idUser : Long) : List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ejercicioEntity: EjercicioEntity): Long

    @Update
    suspend fun update(ejercicioEntity: EjercicioEntity) : Int

    @Delete
    suspend fun delete(ejercicioEntity: EjercicioEntity) : Int
}