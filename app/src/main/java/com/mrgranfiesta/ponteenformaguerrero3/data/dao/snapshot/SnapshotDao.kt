package com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono.PairIdDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.ConfMaterialSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.EjercicioMaterialCrossSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.EjercicioSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.MaterialSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.RutinaSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.StepSnapshotEntity

@Dao
interface SnapshotDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRutinaSnapshot(snapshot: RutinaSnapshotEntity) : Long

    /// ------------------------------- ///
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEjercicioListSnapshot(snapshotList: List<EjercicioSnapshotEntity>) : List<Long>

    @Query("""
        Select
            ejs.idEjercicioSnapshot AS idSnapshot,
            ejs.idEjercicio AS idOrigin
        FROM ejercicioSnapshot ejs 
        WHERE ejs.idEjercicioSnapshot IN (:listIdSnapshot)""")
    suspend fun getEjercicioListPairIdSnpashotByPk(listIdSnapshot: List<Long>) : List<PairIdDB>

    /// ------------------------------- ///

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStepSnapshot(snapshotList: List<StepSnapshotEntity>) : List<Long>

    @Query("""
        Select
            sts.idStepSnapshot AS idSnapshot,
            sts.idStep AS idOrigin
        FROM stepSnapshot sts 
        WHERE sts.idStepSnapshot IN (:listIdSnapshot)""")
    suspend fun getStepListPairIdSnpashotByPk(listIdSnapshot: List<Long>) : List<PairIdDB>

    /// ------------------------------- ///

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMaterialSnapshot(snapshot: List<MaterialSnapshotEntity>) : List<Long>

    @Query("""
        Select 
           mts.idMaterialSnapshot AS idSnapshot,
           mts.idMaterial AS idOrigin
        FROM materialSnapshot mts 
        WHERE mts.idMaterialSnapshot IN (:listIdSnapshot)""")
    suspend fun getMaterialListPairIdSnpashotByPk(listIdSnapshot: List<Long>) : List<PairIdDB>

    /// ------------------------------- ///

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEjercicioMaterialCrossSnapshot(snapshotList: List<EjercicioMaterialCrossSnapshotEntity>) : List<Long>

    /// ------------------------------- ///

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertConfMaterialListSnapshot(snapshotList: List<ConfMaterialSnapshotEntity>) : List<Long>

    /// -------------------------------- ///

    @Query("""DELETE FROM rutinaSnapshot WHERE idRutinaSnapshot = :idRutinaSnapshot""")
    suspend fun deleteRutinaByIdPk(idRutinaSnapshot : Long) : Int

    @Query("""DELETE FROM ejercicioSnapshot 
            WHERE idEjercicioSnapshot IN (
                SELECT DISTINCT e.idEjercicioSnapshot 
                FROM rutinasnapshot r
                JOIN stepSnapshot s 
                    ON s.idRutinaSnapshot = r.idRutinaSnapshot
                JOIN ejercicioSnapshot e 
                    ON e.idEjercicioSnapshot = s.idEjercicioSnapshot
                WHERE r.idRutinaSnapshot = :idRutinaSnapshot
            )""")
    suspend fun deleteEjercicioByIdRutina(idRutinaSnapshot : Long) : Int

    @Query("""DELETE FROM materialSnapshot 
        WHERE idMaterialSnapshot IN (
            SELECT DISTINCT m.idMaterialSnapshot
            FROM rutinaSnapshot r
            JOIN stepSnapshot s 
                ON s.idRutinaSnapshot = r.idRutinaSnapshot
            JOIN ejercicioMaterialCrossSnapshot c 
                ON s.idEjercicioSnapshot = c.idEjercicioSnapshot
            JOIN materialSnapshot m 
                ON m.idMaterialSnapshot = c.idMaterialSnapshot
            WHERE r.idRutinaSnapshot = :idRutinaSnapshot
        )""")
    suspend fun deleteMaterialByIdRutina(idRutinaSnapshot : Long) : Int
}