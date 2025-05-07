package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.mrgranfiesta.ponteenformaguerrero3.data.filesystem.StorageFileService
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.UserRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.UserBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_PROFILE_USER_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.CypherSHA256
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.EntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(
    private val repo : UserRepository
) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(context : Context, user : UserBean) {
        ioScope.launch {
            user.password = CypherSHA256.hashPassword(user.password)
            val nameFile = StorageFileService.insertImg(
                context = context,
                galeryUri = user.photoUri,
                dir = DIR_PROFILE_USER_IMG
            )
            if (nameFile.isNotEmpty()) {
                val pathFile = "$DIR_PROFILE_USER_IMG/$nameFile"
                val uri = File(context.getExternalFilesDir(null), pathFile).toUri()
                user.photoUri = uri
            } else {
                user.photoUri = Uri.EMPTY
            }
            repo.insert(EntityMapper.toUserEntity(user))
        }
    }
}