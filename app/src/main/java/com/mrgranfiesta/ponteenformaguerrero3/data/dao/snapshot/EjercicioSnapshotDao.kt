package com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot

import androidx.room.Dao
import androidx.room.Query
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.ejercicio.EjercicioSnapshotInfoDB
import kotlinx.coroutines.flow.Flow


@Dao
interface EjercicioSnapshotDao {
    @Query("""
            SELECT 
            ejs.idEjercicioSnapshot,
            ejs.idEjercicio,
            ejs.idUser,
            ejs.nombre,
            ejs.descripcion,
            ejs.musculoSet,
            ejs.nivel,
            ejs.isSimetria,
            ejs.nameImg,
            u.rol
            FROM ejercicioSnapshot ejs
            JOIN user u ON u.idUser = ejs.idUser
            WHERE ejs.idEjercicioSnapshot = :idEjercicioSnapshot
            """)
    fun getEjercicioSnapshotInfoByPkFlow(idEjercicioSnapshot : Long): Flow<EjercicioSnapshotInfoDB>

    @Query("""
            SELECT 
            ejs.idEjercicioSnapshot,
            ejs.idEjercicio,
            ejs.idUser,
            ejs.nombre,
            ejs.descripcion,
            ejs.musculoSet,
            ejs.nivel,
            ejs.isSimetria,
            ejs.nameImg,
            u.rol
            FROM ejercicioSnapshot ejs
            JOIN user u ON u.idUser = ejs.idUser
            WHERE ejs.idEjercicioSnapshot = :idEjercicioSnapshot
            """)
    fun getEjercicioSnapshotInfoNulleableByPkFlow(idEjercicioSnapshot : Long): Flow<EjercicioSnapshotInfoDB?>

    @Query("""
        SELECT DISTINCT ejs.nameImg
        FROM stepSnapshot sts
        JOIN ejercicioSnapshot ejs ON sts.idEjercicioSnapshot = ejs.idEjercicioSnapshot
        JOIN user u ON u.idUser = ejs.idUser
        WHERE sts.idRutinaSnapshot = :idRutinaSnapshot
        AND u.rol != 1
    """)
    suspend fun getNameImgByRutinaId(idRutinaSnapshot : Long) : List<String>

    @Query("""
            SELECT EXISTS(
                SELECT 1 
                FROM ejercicioSnapshot ejs
                WHERE ejs.nameImg = :nameImg
            )
            """)
    fun isExistNameImg(nameImg : String): Boolean

    @Query("""
            SELECT NOT EXISTS(
                SELECT 1 
                FROM ejercicioSnapshot ejs
                WHERE ejs.nameImg = :nameImg
            )
            """)
    suspend fun isNotExistNameImg(nameImg : String): Boolean

    @Query("""
        SELECT NOT EXISTS (
            SELECT 1 
            FROM ejercicio ej 
            WHERE ej.nameImg = :nameImg
        ) OR NOT EXISTS (
            SELECT 1 
            FROM ejercicioSnapshot ejs 
            WHERE ejs.nameImg = :nameImg
        )
    """)
    suspend fun isNotExistNameImgGlobal(nameImg : String) : Boolean
}