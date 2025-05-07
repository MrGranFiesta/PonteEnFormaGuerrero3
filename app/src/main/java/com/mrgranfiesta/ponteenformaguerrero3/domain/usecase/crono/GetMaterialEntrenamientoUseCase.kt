package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.crono

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EntrenamientoRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.MaterialEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.DtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMaterialEntrenamientoUseCase @Inject constructor(
    private val repo : EntrenamientoRepository
) {
    operator fun invoke(idRutina: Long): Flow<List<MaterialEntrenamientoDto>> {
        return repo.getMaterialEntrenamiento(idRutina).map { list ->
            list.map {
                DtoMapper.toMaterialEntrenamientoDto(it)
            }
        }
    }
}