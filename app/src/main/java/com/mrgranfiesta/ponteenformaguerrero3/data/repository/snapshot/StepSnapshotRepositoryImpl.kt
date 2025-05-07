package com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot.ConfMaterialSnapshotDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot.StepSnapshotDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.step.StepSnapshotInfoDB
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StepSnapshotRepositoryImpl @Inject constructor(
    private val daoStep: StepSnapshotDao,
    private val daoConfMaterial: ConfMaterialSnapshotDao,
    val context: CoroutineDispatcher = Dispatchers.IO
) : StepSnapshotRepository {
    override fun getStepSnapshotInfo(idStat: Long): Flow<List<StepSnapshotInfoDB>> = flow {
        val stepList = daoStep.getStepSnapshotInfoByIdStat(idStat)
        val confMaterial = daoConfMaterial.getMaterialWithConfListByIdStep(
            idStepSnapshot = stepList.map { it.idStepSnapshot }
        )
        val stepSnapshotInfoList = stepList.map { step ->
            StepSnapshotInfoDB(
                idStep = step.idStep,
                idStepSnapshot = step.idStepSnapshot,
                idEjercicioSnapshot = step.idEjercicioSnapshot,
                idEjercicio = step.idEjercicio,
                idRutinaSnapshot = step.idRutinaSnapshot,
                cantidad = step.cantidad,
                serie = step.serie,
                ordenExecution = step.ordenExecution,
                tipo = step.tipo,
                rol = step.rol,
                musculoSet = step.musculoSet,
                confMaterialList = confMaterial.filter {
                    it.idStep == step.idStep && it.idStepSnapshot == step.idStepSnapshot
                }
            )
        }
        emit(stepSnapshotInfoList)
    }.flowOn(context)
}