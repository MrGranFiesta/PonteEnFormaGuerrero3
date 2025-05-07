package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina


import com.mrgranfiesta.ponteenformaguerrero3.data.repository.RutinaRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RutinaInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.DtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRutinaByIdUseCase @Inject constructor(
    private val repo : RutinaRepository
) {
    operator fun invoke(idRutina : Long) : Flow<RutinaInfoDto> {
        return repo.getRutinaInfoDBByPkFlow(idRutina).map { obj ->
            DtoMapper.toRutinaInfoDto(obj)
        }
    }
}