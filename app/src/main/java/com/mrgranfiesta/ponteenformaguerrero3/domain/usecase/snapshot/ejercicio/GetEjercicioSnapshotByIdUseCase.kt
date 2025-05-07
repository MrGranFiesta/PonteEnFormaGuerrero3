package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.snapshot.ejercicio

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.EjercicioSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot.EjercicioSnapshotInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.DtoMapper
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetEjercicioSnapshotByIdUseCase @Inject constructor(
    private val repo: EjercicioSnapshotRepository
) {
    operator fun invoke(idEjercicioSnapshot : Long) : Flow<EjercicioSnapshotInfoDto> {
        return repo.getEjercicioSnapshotInfoByPk(idEjercicioSnapshot).map {
            DtoMapper.toEjercicioSnapshotInfoDto(it)
        }
    }
}