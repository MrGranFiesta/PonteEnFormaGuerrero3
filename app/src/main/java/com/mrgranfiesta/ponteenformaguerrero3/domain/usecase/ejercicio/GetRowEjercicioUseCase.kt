package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.RowEjercicioDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.DtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRowEjercicioUseCase @Inject constructor(
    private val repo : EjercicioRepository
) {
    operator fun invoke() : Flow<List<RowEjercicioDto>> {
        val idUser = CurrentUser.user?.idUser ?: 0
        return repo.getRowEjercicio(idUser).map { list ->
            list.map { obj ->
                DtoMapper.toRowEjercicioDto(obj)
            }
        }
    }
}