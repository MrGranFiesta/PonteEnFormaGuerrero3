package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.DtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetEjercicioInfoByIdUseCase @Inject constructor(
    private val repo : EjercicioRepository
) {
    operator fun invoke(idEjercicio : Long) : Flow<EjercicioInfoDto> {
        return repo.getEjercicioInfo(idEjercicio).map { obj ->
            DtoMapper.toEjercicioInfoDto(obj)
        }
    }
}