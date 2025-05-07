package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina


import com.mrgranfiesta.ponteenformaguerrero3.data.repository.RutinaRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RowRutinaDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.DtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRowRutinaUseCase @Inject constructor(
    private val repo : RutinaRepository
) {
    operator fun invoke() : Flow<List<RowRutinaDto>> {
        val idUser = CurrentUser.user?.idUser ?: 0
        return repo.getRowRutinaAll(idUser).map { list ->
            list.map { obj ->
                DtoMapper.toRowRutinaDto(obj)
            }
        }
    }
}