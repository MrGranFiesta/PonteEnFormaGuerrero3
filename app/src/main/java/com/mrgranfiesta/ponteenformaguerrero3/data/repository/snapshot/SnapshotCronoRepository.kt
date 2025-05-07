package com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot

import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono.PairIdDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.ConfMaterialSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.EjercicioMaterialCrossSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.EjercicioSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.MaterialSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.RutinaSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.StepSnapshotEntity

interface SnapshotCronoRepository {
    suspend fun insertRutinaSnapshot(rutina: RutinaSnapshotEntity) : Long

    /// ------------------------- ///

    suspend fun insertEjercicioListSnapshot(ejercicioList: List<EjercicioSnapshotEntity>) : List<Long>
    suspend fun getEjercicioListPairIdSnpashotByPk(listIdSnapshot: List<Long>): List<PairIdDB>

    /// ------------------------- ///

    suspend fun insertStepListSnapshot(stepList: List<StepSnapshotEntity>) : List<Long>
    suspend fun getStepListPairIdSnpashotByPk(listIdSnapshot: List<Long>): List<PairIdDB>

    /// ------------------------- ///

    suspend fun insertMaterialListSnapshot(materialList: List<MaterialSnapshotEntity>) : List<Long>
    suspend fun getMaterialListPairIdSnpashotByPk(listIdSnapshot: List<Long>): List<PairIdDB>
    /// ------------------------- ///

    suspend fun insertConfMaterialListSnapshot(confMaterialList: List<ConfMaterialSnapshotEntity>) : List<Long>

    /// ------------------------- ///

    suspend fun insertEjercicioMaterialCrossListSnapshot(crossList: List<EjercicioMaterialCrossSnapshotEntity>) : List<Long>

    /// ------------------------- ///

    suspend fun deleteRutinaByIdPk(idRutinaSnapshot : Long)
    suspend fun deleteEjercicioByIdRutina(idRutinaSnapshot : Long)
    suspend fun deleteMaterialByIdRutina(idRutinaSnapshot : Long)
}