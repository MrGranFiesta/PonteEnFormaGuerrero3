package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.snapshot.confmaterial

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.ConfMaterialSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot.MaterialWithConfSnapshotDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.DtoMapper
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class GetMaterialWithConfByIdEjercicioUseCase @Inject constructor(
    private val repo: ConfMaterialSnapshotRepository
) {
    operator fun invoke(idEjercicioSnapshot : Long) : Flow<List<MaterialWithConfSnapshotDto>> {
        return repo.getMaterialWithConfByIdEjercicio(idEjercicioSnapshot).map { list ->
            list.map {
                DtoMapper.toMaterialWithConfSnapshotDto(it)
            }
        }
    }
}