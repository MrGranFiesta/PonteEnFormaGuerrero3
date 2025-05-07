package com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot

import androidx.room.Dao
import androidx.room.Query
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.step.StepSnapshotWithMuscleDB

@Dao
fun interface StepSnapshotDao {

    @Query(
        """SELECT 
            sts.idStep, 
            sts.idStepSnapshot,
            sts.idEjercicioSnapshot,
            ejs.idEjercicio,
            sts.idRutinaSnapshot,
            sts.cantidad, 
            sts.serie, 
            sts.ordenExecution, 
            sts.tipo, 
            u.rol,
            ejs.musculoSet
        FROM stat stt
        INNER JOIN stepSnapshot sts ON sts.idRutinaSnapshot = stt.idRutinaSnapshot
        INNER JOIN ejercicioSnapshot ejs ON sts.idEjercicioSnapshot = ejs.idEjercicioSnapshot
        JOIN user u ON u.idUser = ejs.idUser
        WHERE stt.idStat = :idStat
        ORDER BY sts.ordenExecution
    """
    )
    suspend fun getStepSnapshotInfoByIdStat(idStat : Long) : List<StepSnapshotWithMuscleDB>
}