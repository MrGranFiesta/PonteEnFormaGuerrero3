package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.MaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.DtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.Long

class GetMaterialByIdListUseCase @Inject constructor(
    private val repo : MaterialRepository
) {
    operator fun invoke(idsMaterial: List<Long>): Flow<List<RowMaterialDto>> {
        return repo.getRowMaterialByPkList(idsMaterial).map { list ->
            list.map { obj ->
                DtoMapper.toRowMaterialDto(obj)
            }
        }
    }
}