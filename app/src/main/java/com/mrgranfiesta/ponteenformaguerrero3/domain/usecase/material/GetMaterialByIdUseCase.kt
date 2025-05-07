package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.MaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.MaterialInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.DtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMaterialByIdUseCase @Inject constructor(
    private val repo : MaterialRepository
) {
    operator fun invoke(id: Long): Flow<MaterialInfoDto> {
        return repo.getMaterialInfoByPk(id).map { obj ->
            DtoMapper.toMaterialInfoDto(obj)
        }
    }
}