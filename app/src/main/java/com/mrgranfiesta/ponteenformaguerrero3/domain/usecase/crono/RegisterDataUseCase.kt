package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.crono

import androidx.room.Transaction
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono.PairIdDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.ConfMaterialSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.EjercicioMaterialCrossSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.MaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.RutinaRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.SnapshotCronoRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.EntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.MaterialEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.EntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.CompletableFuture
import javax.inject.Inject
import kotlin.collections.forEach
import kotlin.collections.map

class RegisterDataUseCase @Inject constructor(
    private val rutinaRepo: RutinaRepository,
    private val ejercicioRepo: EjercicioRepository,
    private val materialRepo: MaterialRepository,
    private val snapshotRepo: SnapshotCronoRepository
) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(
        entrenamientoDB: EntrenamientoDto,
        stepDB: List<StepEntrenamientoDto>,
        materialDB: List<MaterialEntrenamientoDto>
    ): Long {
        val resultRutinaId = CompletableFuture<Long>()
        ioScope.launch {
            val resultEjercicioId = CompletableFuture<List<Long>>()
            val resultMaterialId = CompletableFuture<List<Long>>()
            val resultStepId = CompletableFuture<List<Long>>()
            val resultPairEjercicio = CompletableFuture<List<PairIdDB>>()
            val resultPairMaterial = CompletableFuture<List<PairIdDB>>()

            insertEjercicio(
                idEjercicioList = stepDB.map { it.idEjercicio },
                resultEjercicioId = resultEjercicioId
            )
            insertRutina(
                idRutina = entrenamientoDB.idRutina,
                resultRutinaId = resultRutinaId
            )
            insertMaterial(
                idMaterialList = materialDB.map { it.idMaterial },
                resultMaterialId = resultMaterialId
            )
            insertStep(
                idRutinaSnapshot = resultRutinaId.get(),
                listIdEjercicioSnapshot = resultEjercicioId.get(),
                stepDB = stepDB,
                resultStepIdList = resultStepId,
                resultPairIdEjercicio = resultPairEjercicio
            )
            insertConfMaterial(
                listIdMaterial = resultMaterialId.get(),
                listIdStepSnapshot = resultStepId.get(),
                materialDB = materialDB,
                resultPairIdMaterial = resultPairMaterial
            )
            insertEjercicioMaterialCross(
                pairMaterial = resultPairMaterial.get(),
                pairEjercicio = resultPairEjercicio.get(),
                stepDB = stepDB,
                materialDB = materialDB
            )
        }
        return resultRutinaId.get()
    }

    @Transaction
    private fun insertRutina(
        idRutina: Long,
        resultRutinaId: CompletableFuture<Long>
    ) {
        ioScope.launch {
            val rutina = rutinaRepo.getByPkNoFlow(idRutina)
            val idRutina = snapshotRepo.insertRutinaSnapshot(
                EntityMapper.rutinaEntityToSnapshot(
                    idRutinaSnapshot = 0,
                    obj = rutina
                )
            )
            resultRutinaId.complete(idRutina)
        }
    }

    @Transaction
    private fun insertEjercicio(
        idEjercicioList: List<Long>,
        resultEjercicioId: CompletableFuture<List<Long>>
    ) {
        ioScope.launch {
            val ejercicioEntityList = ejercicioRepo.getListByPkList(idEjercicioList)
            val ejercicioSnapshotList = ejercicioEntityList
                .distinctBy { it.idEjercicio }
                .map {
                    EntityMapper.toEjercicioSnapshotEntity(
                        idEjercicioSnapshot = 0,
                        obj = it
                    )
                }
            val ids = snapshotRepo.insertEjercicioListSnapshot(ejercicioSnapshotList)
            resultEjercicioId.complete(ids)
        }
    }

    @Transaction
    private fun insertMaterial(
        idMaterialList: List<Long>,
        resultMaterialId: CompletableFuture<List<Long>>
    ) {
        ioScope.launch {
            val materialDB = materialRepo.getListByPkListNoFlow(idMaterialList.distinct())
            val materialSnapshot = materialDB
                .distinctBy { it.idMaterial }
                .map {
                    EntityMapper.toMaterialSnapshotEntity(
                        idMaterialSnapshot = 0,
                        obj = it
                    )
                }
            val ids = snapshotRepo.insertMaterialListSnapshot(materialSnapshot)
            resultMaterialId.complete(ids)
        }
    }

    @Transaction
    private fun insertStep(
        idRutinaSnapshot: Long,
        listIdEjercicioSnapshot: List<Long>,
        stepDB: List<StepEntrenamientoDto>,
        resultStepIdList: CompletableFuture<List<Long>>,
        resultPairIdEjercicio: CompletableFuture<List<PairIdDB>>
    ) {
        ioScope.launch {
            val pairEjercicio = snapshotRepo.getEjercicioListPairIdSnpashotByPk(listIdEjercicioSnapshot)

            val stepSnapshot = stepDB.mapNotNull { step ->
                pairEjercicio.find { it.idOrigin == step.idEjercicio }?.let { pair ->
                    EntityMapper.toStepSnapshotEntity(
                        obj = step,
                        idEjercicioSnapshot = pair.idSnapshot,
                        idRutinaSnapshot = idRutinaSnapshot,
                        idStepSnapshot = 0
                    )
                }
            }
            val ids = snapshotRepo.insertStepListSnapshot(stepSnapshot)
            resultStepIdList.complete(ids)
            resultPairIdEjercicio.complete(pairEjercicio)
        }
    }

    @Transaction
    private fun insertConfMaterial(
        listIdStepSnapshot: List<Long>,
        listIdMaterial: List<Long>,
        materialDB: List<MaterialEntrenamientoDto>,
        resultPairIdMaterial: CompletableFuture<List<PairIdDB>>
    ) {
        ioScope.launch {
            val pairStep = snapshotRepo.getStepListPairIdSnpashotByPk(listIdStepSnapshot)
            val pairMaterial = snapshotRepo.getMaterialListPairIdSnpashotByPk(listIdMaterial)

            val materialSnapshot = materialDB
                .filter { it.isMaterialWeight }
                .mapNotNull { material ->
                    pairStep.find { it.idOrigin == material.idStep }?.let { pairStep ->
                        pairMaterial.find { it.idOrigin == material.idMaterial }?.let { pairMaterial ->
                            ConfMaterialSnapshotEntity(
                                idConfMaterialSnapshot = 0,
                                idConfMaterial = material.idConfMaterial,
                                idMaterialSnapshot = pairMaterial.idSnapshot,
                                idStepSnapshot = pairStep.idSnapshot,
                                confValue = material.confValue
                            )
                        }
                    }
                }
            snapshotRepo.insertConfMaterialListSnapshot(materialSnapshot)
            resultPairIdMaterial.complete(pairMaterial)
        }
    }

    @Transaction
    private fun insertEjercicioMaterialCross(
        pairEjercicio : List<PairIdDB>,
        pairMaterial : List<PairIdDB>,
        materialDB: List<MaterialEntrenamientoDto>,
        stepDB: List<StepEntrenamientoDto>
    ) {
        ioScope.launch {
            val stepToEjercicioMap =
                stepDB.distinctBy { it.idEjercicio }
                    .associateBy({ it.idStep }, { it.idEjercicio })
            val snapshotList: MutableList<EjercicioMaterialCrossSnapshotEntity> = mutableListOf()

            materialDB.forEach { material ->
                val idEjercicio = stepToEjercicioMap[material.idStep]
                val pairMaterial = pairMaterial.find {
                    it.idOrigin == material.idMaterial
                }

                val pairEjercicio = idEjercicio?.let { idEjercicio ->
                    pairEjercicio.find { it.idOrigin == idEjercicio }
                }
                if (pairMaterial != null && pairEjercicio != null) {
                    snapshotList.add(
                        EjercicioMaterialCrossSnapshotEntity(
                            idMaterialSnapshot = pairMaterial.idSnapshot,
                            idEjercicioSnapshot = pairEjercicio.idSnapshot
                        )
                    )
                }
            }
            snapshotRepo.insertEjercicioMaterialCrossListSnapshot(snapshotList.toList())
        }
    }
}