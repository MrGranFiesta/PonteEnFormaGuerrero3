package com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot.SnapshotDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono.PairIdDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.ConfMaterialSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.EjercicioMaterialCrossSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.EjercicioSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.MaterialSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.RutinaSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.StepSnapshotEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SnapshotCronoRepositoryImpl @Inject constructor(
    private val dao: SnapshotDao,
    val context: CoroutineDispatcher = Dispatchers.IO
) : SnapshotCronoRepository {
    override suspend fun insertRutinaSnapshot(rutina: RutinaSnapshotEntity) : Long {
        return withContext(context) {
            dao.insertRutinaSnapshot(rutina)
        }
    }

    /// -------------------------------- ///

    override suspend fun insertEjercicioListSnapshot(ejercicioList: List<EjercicioSnapshotEntity>) : List<Long> {
        return withContext(context) {
            dao.insertEjercicioListSnapshot(ejercicioList)
        }
    }

    override suspend fun getEjercicioListPairIdSnpashotByPk(listIdSnapshot: List<Long>) : List<PairIdDB> {
        return dao.getEjercicioListPairIdSnpashotByPk(listIdSnapshot)
    }

    /// ------------------------------------ ////

    override suspend fun insertStepListSnapshot(stepList: List<StepSnapshotEntity>) : List<Long> {
        return withContext(context) {
            dao.insertStepSnapshot(stepList)
        }
    }

    override suspend fun getStepListPairIdSnpashotByPk(listIdSnapshot: List<Long>) : List<PairIdDB> {
        return dao.getStepListPairIdSnpashotByPk(listIdSnapshot)
    }
    /// ------------------------------------ ///

    override suspend fun insertMaterialListSnapshot(materialList: List<MaterialSnapshotEntity>) : List<Long> {
        return withContext(context) {
            dao.insertMaterialSnapshot(materialList)
        }
    }

    override suspend fun getMaterialListPairIdSnpashotByPk(listIdSnapshot: List<Long>) : List<PairIdDB> {
        return dao.getMaterialListPairIdSnpashotByPk(listIdSnapshot)
    }

    /// ------------------------------------ ///

    override suspend fun insertConfMaterialListSnapshot(confMaterialList: List<ConfMaterialSnapshotEntity>) : List<Long> {
        return withContext(context) {
            dao.insertConfMaterialListSnapshot(confMaterialList)
        }
    }

    /// ------------------------------------ ///

    override suspend fun insertEjercicioMaterialCrossListSnapshot(crossList: List<EjercicioMaterialCrossSnapshotEntity>) : List<Long> {
        return withContext(context) {
            dao.insertEjercicioMaterialCrossSnapshot(crossList)
        }
    }

    /// ------------------------- ///

    override suspend fun deleteRutinaByIdPk(idRutinaSnapshot : Long) {
        withContext(context) {
            dao.deleteRutinaByIdPk(idRutinaSnapshot)
        }
    }

    override suspend fun deleteEjercicioByIdRutina(idRutinaSnapshot : Long) {
        withContext(context) {
            dao.deleteEjercicioByIdRutina(idRutinaSnapshot)
        }
    }

    override suspend fun deleteMaterialByIdRutina(idRutinaSnapshot : Long) {
        withContext(context) {
            dao.deleteMaterialByIdRutina(idRutinaSnapshot)
        }
    }

}