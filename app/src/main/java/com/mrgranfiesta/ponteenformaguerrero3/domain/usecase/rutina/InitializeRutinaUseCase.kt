package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.rutina

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.RutinaRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.RutinaBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.EntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class InitializeRutinaUseCase @Inject constructor(
    private val repo : RutinaRepository
){
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(rutina: RutinaBean) : Deferred<Boolean> {
        return ioScope.async {
            try {
                repo.insert(EntityMapper.toRutinaEntity(rutina))
                return@async true
            } catch (_: Exception) {
                return@async false
            }
        }
    }
}