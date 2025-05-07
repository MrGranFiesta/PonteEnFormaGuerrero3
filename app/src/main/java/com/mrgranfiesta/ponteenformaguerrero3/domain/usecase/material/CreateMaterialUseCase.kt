package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.mrgranfiesta.ponteenformaguerrero3.data.filesystem.StorageFileService
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.MaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_MATERIAL_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.MaterialInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.EntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class CreateMaterialUseCase @Inject constructor(
    private val repo : MaterialRepository
) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(
        context: Context,
        material: MaterialInfoDto
    ) {
        ioScope.launch {
            val nameFile = StorageFileService.insertImg(
                context = context,
                galeryUri = material.photoUri,
                dir = DIR_MATERIAL_IMG
            )
            if (nameFile.isNotEmpty()) {
                val pathFile = "$DIR_MATERIAL_IMG/$nameFile"
                val uri = File(context.getExternalFilesDir(null), pathFile).toUri()
                material.photoUri = uri
            } else {
                material.photoUri = Uri.EMPTY
            }
            repo.insert(EntityMapper.toMaterialEntity(material))
        }
    }
}