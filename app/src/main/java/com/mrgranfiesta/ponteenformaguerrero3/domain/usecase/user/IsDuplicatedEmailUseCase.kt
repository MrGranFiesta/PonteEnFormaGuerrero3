package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.CompletableFuture
import javax.inject.Inject

class IsDuplicatedEmailUseCase @Inject constructor(
    private val repo : UserRepository
) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(email : String) : Boolean {
        val result = CompletableFuture<Boolean>()
        ioScope.launch {
            result.complete(repo.isEmailDuplicated(email))
        }
        return result.get()
    }
}