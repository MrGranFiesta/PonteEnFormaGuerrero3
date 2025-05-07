package com.mrgranfiesta.ponteenformaguerrero3.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.material.MaterialInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.material.RowMaterialDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.MaterialEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MaterialDao {
    @Query("""
        Select 
            mt.idMaterial,
            mt.idUser,
            mt.nombre,
            mt.nameImg,
            u.rol
        from material mt
        join user u ON u.idUser = mt.idUser
        WHERE mt.idUser = :idUser 
        OR u.rol = 1""")
    fun getRowMaterialAll(idUser : Long): Flow<List<RowMaterialDB>>

    @Query("""
        SELECT 
            mt.idMaterial,
            mt.idUser,
            mt.nombre,
            mt.nameImg,
            u.rol
        FROM material mt
        JOIN user u ON u.idUser = mt.idUser
        WHERE mt.idMaterial IN (:idMaterialList)
    """)
    fun getRowMaterialByPkList(idMaterialList: List<Long>) : Flow<List<RowMaterialDB>>


    @Query("""
        SELECT DISTINCT
            mt.idMaterial,
            mt.idUser,
            mt.nombre,
            mt.nameImg,
            u.rol
        FROM rutina rt
        INNER JOIN step st ON rt.idRutina = st.idRutina
        INNER JOIN ejercicioMaterialCross emc ON st.idEjercicio = emc.idEjercicio
        INNER JOIN material mt ON mt.idMaterial = emc.idMaterial
        JOIN user u ON u.idUser = mt.idUser
        WHERE rt.idRutina = :idRutina
    """)
    fun getRowMaterialByIdRutina(idRutina : Long) : Flow<List<RowMaterialDB>>

    @Query("SELECT * FROM material mt WHERE mt.idMaterial IN (:idMaterialList)")
    suspend fun getListByPkListNoFlow(idMaterialList: List<Long>): List<MaterialEntity>

    @Query("""
        SELECT
            mt.idMaterial,
            mt.idUser,
            mt.nombre,
            mt.isMaterialWeight,
            mt.descripcion,
            mt.nameImg,
            u.rol
        FROM material mt
        JOIN user u ON u.idUser = mt.idUser
        where mt.idMaterial = :idMaterial
    """)
    fun getMaterialInfoByPk(idMaterial: Long) : Flow<MaterialInfoDB>

    @Query(
        """SELECT 
            mt.idMaterial,
            mt.idUser,
            mt.nombre,
            mt.nameImg,
            u.rol
        FROM material mt
        JOIN user u ON u.idUser = mt.idUser
        INNER JOIN ejercicioMaterialCross emc ON mt.idMaterial = emc.idMaterial 
        WHERE emc.idEjercicio = :idEjercicio"""
    )
    fun getEjercicioInfoMaterial(idEjercicio: Long): Flow<List<RowMaterialDB>>

    @Query("""SELECT mt.*
        FROM material mt
        INNER JOIN ejercicioMaterialCross emc ON mt.idMaterial = emc.idMaterial 
        WHERE emc.idEjercicio = :idEjercicio""")
    fun getListNoFlowByIdEjercicio(idEjercicio: Long): List<MaterialEntity>

    @Query("""
        SELECT mt.nameImg
        FROM material mt
        WHERE mt.idUser = :idUser
    """)
    suspend fun getListNameImgByIdUser(idUser : Long) : List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(materialEntity: MaterialEntity) : Long

    @Update
    suspend fun update(materialEntity: MaterialEntity) : Int

    @Delete
    suspend fun delete(materialEntity: MaterialEntity) : Int
}