package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioNameAndPhotoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.DtoMapper
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetEjercicioNameAndPhotoByIdUseCase @Inject constructor(
    private val repo : EjercicioRepository
) {
    operator fun invoke(idEjercicio : Long) : Flow<EjercicioNameAndPhotoDto> {
        return repo.getEjercicioNameAndPhotoDB(idEjercicio).map { obj ->
            DtoMapper.toEjercicioNameAndPhotoDto(obj)
        }
    }
}