package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.stat

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.StatRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.stat.StatRutinaSearchDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.DtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetStatRutinaSearchUseCase @Inject constructor(
    private val repo: StatRepository
) {
    operator fun invoke() : Flow<List<StatRutinaSearchDto>> {
        val idUser = CurrentUser.user?.idUser ?: 0
        return repo.getAllStatRutina(idUser).map { list ->
            list.map {
                DtoMapper.toStatRutinaSearchDto(it)
            }
        }
    }
}