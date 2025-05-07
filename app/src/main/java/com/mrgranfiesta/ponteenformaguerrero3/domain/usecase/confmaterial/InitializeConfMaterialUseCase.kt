package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.confmaterial

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.ConfMaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.ConfMaterialBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.EntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class InitializeConfMaterialUseCase @Inject constructor(
    private val repo : ConfMaterialRepository
){
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(confMaterial: ConfMaterialBean): Deferred<Long> {
       return ioScope.async {
           repo.insert(EntityMapper.toConfMaterialEntity(confMaterial))
       }
    }
}