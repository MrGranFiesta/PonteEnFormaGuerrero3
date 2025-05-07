package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user

import com.mrgranfiesta.ponteenformaguerrero3.data.datastore.UserDataStore
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.UserRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateUserUsernameUseCase @Inject constructor(
    private val repo: UserRepository,
    private val userDataStore: UserDataStore
) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(
        idUser: Long,
        username: String
    ) {
        ioScope.launch {
            repo.updateUsername(
                idUser = idUser,
                username = username
            )
            userDataStore.updateUsername(username)
            CurrentUser.user?.let {
                it.username = username
            }
        }
    }
}