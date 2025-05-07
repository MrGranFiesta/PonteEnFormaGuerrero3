package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.RutinaRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RutinaInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.EntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeleteRutinaUseCase @Inject constructor(
    private val repo : RutinaRepository
) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(rutina : RutinaInfoDto) {
        ioScope.launch {
            repo.delete(EntityMapper.toRutinaEntity(rutina))
        }
    }
}