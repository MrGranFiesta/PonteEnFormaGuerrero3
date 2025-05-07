package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.EjercicioBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.EntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class InitializeEjercicioUseCase @Inject constructor(
    private val repo : EjercicioRepository
){
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(ejercicio: EjercicioBean) : Deferred<Boolean> {
        return ioScope.async {
            try {
                repo.insert(
                    EntityMapper.toEjercicioEntity(
                        rol = Rol.INIT_DATA_USER,
                        obj = ejercicio
                    )
                )
                return@async true
            } catch (_: Exception) {
                return@async false
            }
        }
    }
}