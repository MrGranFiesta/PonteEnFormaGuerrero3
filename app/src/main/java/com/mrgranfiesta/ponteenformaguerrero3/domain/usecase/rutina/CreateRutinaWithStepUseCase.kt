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
import kotlin.collections.forEach

class CreateRutinaWithStepUseCase @Inject constructor(
    private val rutinaRepo : RutinaRepository,
    private val stepRepo : StepRepository,
    private val confMaterialRepo: ConfMaterialRepository
) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(
        rutina : RutinaInfoDto,
        stepList: List<RowStepFormWithConfMaterialDto>
    ) {
        ioScope.launch {
            val idRutina = rutinaRepo.insert(EntityMapper.toRutinaEntity(rutina))
            stepList.forEachIndexed { index, step ->
                step.ordenExecution = index
                step.idRutina = idRutina
            }
            stepList.forEach { step ->
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
        }
    }
}