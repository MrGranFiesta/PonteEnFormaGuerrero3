package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.crono

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EntrenamientoRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.EntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.DtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetEntrenamientoUseCase @Inject constructor(
    private val repo : EntrenamientoRepository
) {
    operator fun invoke(idRutina: Long): Flow<EntrenamientoDto> {
        return repo.getEntrenamiento(idRutina).map {
            DtoMapper.toEntrenamientoDto(it)
        }
    }
}