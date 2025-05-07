package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.ConfMaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.RutinaRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.StepRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RutinaInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.step.RowStepFormWithConfMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.EntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateRutinaWithStepUseCase @Inject constructor(
    private val rutinaRepo : RutinaRepository,
    private val stepRepo : StepRepository,
    private val confMaterialRepo: ConfMaterialRepository
) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(
        rutina : RutinaInfoDto,
        stepListUpdate: List<RowStepFormWithConfMaterialDto>,
        stepListDelete: List<RowStepFormWithConfMaterialDto>
    ) {
        ioScope.launch {
            rutinaRepo.update(EntityMapper.toRutinaEntity(rutina))
            stepListUpdate.forEachIndexed { index, step ->
                step.ordenExecution = index
            }
            val stepListInsertFilter = stepListUpdate.filter { it.idStep == 0L }
            val stepListUpdateFilter = stepListUpdate.filter { it.idStep != 0L }

            stepListInsertFilter.map { it.idRutina = rutina.idRutina }

            stepListInsertFilter.forEach { step ->
                val idStep = stepRepo.insert(EntityMapper.toStepEntity(step))
                if (step.confMaterialList.isNotEmpty()) {
                    val confMaterialList = step.confMaterialList.onEach { it.idStep = idStep }
                        .map { confBean ->
                            EntityMapper.toConfMaterialEntity(confBean)
                        }
                    confMaterialList.forEach { confEntity ->
                        confMaterialRepo.insert(confEntity)
                    }
                }
            }

            stepListUpdateFilter.forEach { step ->
                stepRepo.update(EntityMapper.toStepEntity(step))
                if (step.confMaterialList.isNotEmpty()) {

                    val confMaterialList = step.confMaterialList.onEach { it.idStep = step.idStep }
                        .map { confBean ->
                            EntityMapper.toConfMaterialEntity(confBean)
                        }
                    confMaterialList.forEach { confEntity ->
                        if (confEntity.idConfMaterial == 0L) {
                            confMaterialRepo.insert(confEntity)
                        } else {
                            confMaterialRepo.update(confEntity)
                        }
                    }
                }
            }
            stepListDelete.forEach { step ->
                stepRepo.delete(EntityMapper.toStepEntity(step))
            }
        }
    }
}