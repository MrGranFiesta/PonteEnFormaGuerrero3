package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.stat

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.StatRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.StatBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.EntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.CompletableFuture
import javax.inject.Inject

class CreateStatUseCase @Inject constructor(
    private val repo: StatRepository
) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(stat: StatBean): Long {
        val resultIdStat = CompletableFuture<Long>()
        ioScope.launch {
            resultIdStat.complete(repo.insert(EntityMapper.toStatEntity(stat)))
        }
        return resultIdStat.get()
    }
}