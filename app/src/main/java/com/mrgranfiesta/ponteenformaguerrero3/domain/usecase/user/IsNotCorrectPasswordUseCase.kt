package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.UserRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.CypherSHA256
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.CompletableFuture
import javax.inject.Inject

class IsNotCorrectPasswordUseCase @Inject constructor(
    private val repo: UserRepository
) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(password: String): Boolean {
        val idUser = CurrentUser.user?.idUser
        return if (idUser != null) {
            val result = CompletableFuture<Boolean>()
            ioScope.launch {
                result.complete(
                    repo.isNotCorrectPassword(
                        idUser = idUser,
                        password = CypherSHA256.hashPassword(password)
                    )
                )
            }
            result.get()
        } else {
            true
        }
    }
}