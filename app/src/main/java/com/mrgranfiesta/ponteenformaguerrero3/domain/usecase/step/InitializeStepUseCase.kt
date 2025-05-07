package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.step

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.StepRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.StepBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.EntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class InitializeStepUseCase @Inject constructor(
    private val repo : StepRepository
){
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(step: StepBean) : Deferred<Boolean> {
        return ioScope.async {
            try {
                repo.insert(EntityMapper.toStepEntity(step))
                return@async true
            } catch (_: Exception) {
                return@async false
            }
        }
    }
}