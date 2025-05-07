package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.mrgranfiesta.ponteenformaguerrero3.data.datastore.UserDataStore
import com.mrgranfiesta.ponteenformaguerrero3.data.filesystem.StorageFileService
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.UserRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_PROFILE_USER_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.UriUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class UpdateUserPhotoUriUseCase @Inject constructor(
    private val repo : UserRepository,
    private val userDataStore: UserDataStore
) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(
        context: Context,
        idUser : Long,
        photoUri : Uri,
        deleteImgUri : Uri
    ) {
        val nameImg = StorageFileService.updateImg(
            context = context,
            galeryUri = photoUri,
            dir = DIR_PROFILE_USER_IMG,
            deleteImgUri = deleteImgUri
        )
        if (nameImg.isNotEmpty()) {
            val pathFile = "$DIR_PROFILE_USER_IMG/$nameImg"
            val uri = File(context.getExternalFilesDir(null), pathFile).toUri()
            ioScope.launch {
                repo.updatePhotoUri(
                    idUser = idUser,
                    nameImg = UriUtils.getNameByUri(uri)
                )
                userDataStore.updatePhotoUri(uri)
                CurrentUser.user?.let {
                    it.photoUri = UriUtils.getUriResource(
                        dir = DIR_PROFILE_USER_IMG,
                        nameImg = nameImg
                    )
                }
            }
        }
    }
}