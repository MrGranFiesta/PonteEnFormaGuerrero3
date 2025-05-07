package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejerciciomaterialcross

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioMaterialCrossRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.EjercicioMaterialCrossBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.EntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class InitializeEjecicioMaterialCrossUseCase @Inject constructor(
    private val repo : EjercicioMaterialCrossRepository
) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(cross: EjercicioMaterialCrossBean) : Deferred<Long> {
       return ioScope.async {
           repo.insert(EntityMapper.toEjercicioMaterialCrossEntity(cross))
       }
    }
}