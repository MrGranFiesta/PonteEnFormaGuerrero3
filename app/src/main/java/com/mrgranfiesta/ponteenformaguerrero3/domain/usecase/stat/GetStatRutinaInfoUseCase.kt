package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.stat

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.StatRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.stat.StatRutinaInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.DtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetStatRutinaInfoUseCase @Inject constructor(
    private val repo: StatRepository
) {
    operator fun invoke(id : Long) : Flow<StatRutinaInfoDto> {
        return repo.getByPkStatRutina(id).map {
            DtoMapper.toStatRutinaInfoDto(it)
        }
    }
}