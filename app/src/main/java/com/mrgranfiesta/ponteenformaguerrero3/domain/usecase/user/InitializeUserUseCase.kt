package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.UserRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.UserBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.EntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class InitializeUserUseCase @Inject constructor(
    private val repo : UserRepository
){
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(user: UserBean) : Deferred<Boolean> {
        return ioScope.async {
            try {
                repo.insert(EntityMapper.toUserEntity(user))
                return@async true
            } catch (_: Exception) {
                return@async false
            }
        }
    }
}