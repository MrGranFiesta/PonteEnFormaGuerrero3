package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.step

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.StepRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.step.RowStepFormWithConfMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.DtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetStepFormByIdRutinaUseCase @Inject constructor(
    private val repo : StepRepository
) {
    operator fun invoke(idRutina : Long) : Flow<List<RowStepFormWithConfMaterialDto>> {
        return repo.getStepFormWithMaterialConfListByIdRutina(idRutina).map { list ->
            list.map { step ->
                DtoMapper.toRowStepFormWithConfMaterialDto(step)
            }
        }
    }
}