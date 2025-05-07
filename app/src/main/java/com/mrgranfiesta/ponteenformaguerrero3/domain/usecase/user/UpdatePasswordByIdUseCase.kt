package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user

import com.mrgranfiesta.ponteenformaguerrero3.data.repository.UserRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.CypherSHA256
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdatePasswordByIdUseCase @Inject constructor(
    private val repo : UserRepository
) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(password : String) {
        ioScope.launch {
            CurrentUser.user?.let {
                repo.updatePassword(
                    password = CypherSHA256.hashPassword(password),
                    idUser = it.idUser
                )
            }

        }
    }
}