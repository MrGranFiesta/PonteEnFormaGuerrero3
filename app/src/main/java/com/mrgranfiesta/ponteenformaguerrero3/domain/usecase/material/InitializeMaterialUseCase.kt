package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.MaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.MaterialBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.EntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class InitializeMaterialUseCase @Inject constructor(
    private val repo: MaterialRepository
) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(material: MaterialBean): Deferred<Boolean> {
        return ioScope.async {
            try {
                repo.insert(
                    EntityMapper.toMaterialEntity(
                        rol = Rol.INIT_DATA_USER,
                        obj = material
                    )
                )
                return@async true
            } catch (_: Exception) {
                return@async false
            }
        }
    }
}