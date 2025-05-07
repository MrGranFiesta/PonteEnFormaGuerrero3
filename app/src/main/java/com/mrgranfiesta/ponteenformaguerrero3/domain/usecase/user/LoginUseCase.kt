package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.UserRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.user.UserWithRolDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.CypherSHA256
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.DtoMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.CompletableFuture
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repo : UserRepository
) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(
        email : String,
        password : String
    ) : UserWithRolDto? {
        if (email == "init@gmail.com"){
            return null
        }
        val result = CompletableFuture<UserWithRolDto?>()
        ioScope.launch {
            val passwordCypher = CypherSHA256.hashPassword(password)
            result.complete(DtoMapper.toUserWithRolDBtoDto(repo.loginUser(email, passwordCypher)))
        }
        return result.get()
    }
}