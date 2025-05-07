package com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot

import androidx.room.Dao
import androidx.room.Query


@Dao
interface MaterialSnapshotDao {
    @Query(
        """
            SELECT EXISTS(
                SELECT 1 
                FROM materialSnapshot mts
                WHERE mts.nameImg = :nameImg
            )
        """
    )
    suspend fun isExistNameImg(nameImg: String): Boolean

    @Query(
        """
            SELECT NOT EXISTS(
                SELECT 1 
                FROM materialSnapshot mts
                WHERE mts.nameImg = :nameImg
            )
        """
    )
    suspend fun isNotExistNameImg(nameImg: String): Boolean

    @Query("""
        SELECT DISTINCT mts.nameImg
        FROM stepSnapshot sts
        JOIN ejercicioMaterialCrossSnapshot emcs ON emcs.idEjercicioSnapshot = sts.idEjercicioSnapshot
        JOIN materialSnapshot mts ON mts.idMaterialSnapshot = emcs.idMaterialSnapshot
        JOIN user u ON u.idUser = mts.idUser
        WHERE sts.idRutinaSnapshot = :idRutinaSnapshot
        AND u.rol != 1
    """)
    suspend fun getNameImgByIdRutina(idRutinaSnapshot : Long) : List<String>

    @Query("""
        SELECT NOT EXISTS (
            SELECT 1 
            FROM material mt 
            WHERE mt.nameImg = :nameImg
        ) OR NOT EXISTS (
            SELECT 1 
            FROM materialSnapshot mts 
            WHERE mts.nameImg = :nameImg
        )
    """)
    suspend fun isNotExistNameImgGlobal(nameImg : String) : Boolean
}