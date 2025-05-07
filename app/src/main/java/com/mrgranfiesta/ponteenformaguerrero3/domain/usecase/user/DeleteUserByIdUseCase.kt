package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.user

import android.content.Context
import com.mrgranfiesta.ponteenformaguerrero3.data.filesystem.StorageFileService
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.MaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.UserRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_EJERCICIO_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_MATERIAL_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_PROFILE_USER_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.UriUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeleteUserByIdUseCase @Inject constructor(
    private val repoUser : UserRepository,
    private val repoEjercicio : EjercicioRepository,
    private val repoMaterial : MaterialRepository,
) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(
        idUser : Long,
        context : Context
    ) {
        ioScope.launch {
            deleteProfileImg(context, idUser)
            deleteEjercicioImg(context, idUser)
            deleteMaterialImg(context, idUser)
            repoUser.deleteById(idUser)
        }
    }

    private suspend fun deleteProfileImg(
        context : Context,
        idUser : Long
    ){
        val profileImg = UriUtils.getUriResource(
            dir = DIR_PROFILE_USER_IMG,
            nameImg = repoUser.getNameImg(idUser)
        )
        StorageFileService.deleteImg(
            dir = DIR_PROFILE_USER_IMG,
            context = context,
            deleteImgUri = profileImg
        )
    }

    private suspend fun deleteEjercicioImg(
        context : Context,
        idUser : Long
    ){
        val listEjercicioImg = repoEjercicio.getListNameImgByIdUser(idUser).map {
            UriUtils.getUriResource(
                dir = DIR_EJERCICIO_IMG,
                nameImg = it
            )
        }
        listEjercicioImg.forEach {
            StorageFileService.deleteImg(
                dir = DIR_EJERCICIO_IMG,
                context = context,
                deleteImgUri = it
            )
        }
    }

    private suspend fun deleteMaterialImg(
        context : Context,
        idUser : Long
    ){
        val listMaterialImg = repoMaterial.getListNameImgByIdUser(idUser).map {
            UriUtils.getUriResource(
                dir = DIR_MATERIAL_IMG,
                nameImg = it
            )
        }
        listMaterialImg.forEach {
            StorageFileService.deleteImg(
                dir = DIR_MATERIAL_IMG,
                context = context,
                deleteImgUri = it
            )
        }
    }
}