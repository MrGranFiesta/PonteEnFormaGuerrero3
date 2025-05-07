package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.mrgranfiesta.ponteenformaguerrero3.data.filesystem.StorageFileService
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.MaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.MaterialSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_MATERIAL_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.MaterialInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.UriUtils
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.EntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class UpdateMaterialUseCase @Inject constructor(
    private val repoMaterial: MaterialRepository,
    private val repoMaterialSnapshot: MaterialSnapshotRepository
) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(
        context: Context,
        deleteImgUri: Uri,
        material: MaterialInfoDto
    ) {
        ioScope.launch {
            if (deleteImgUri.path != material.photoUri.path) {
                val deleteNameFile = UriUtils.getNameByUri(deleteImgUri)
                val nameFile = if (repoMaterialSnapshot.isUseNameImg(deleteNameFile)){
                    StorageFileService.insertImg(
                        context = context,
                        galeryUri = material.photoUri,
                        dir = DIR_MATERIAL_IMG
                    )
                } else {
                    StorageFileService.updateImg(
                        context = context,
                        galeryUri = material.photoUri,
                        dir = DIR_MATERIAL_IMG,
                        deleteImgUri = deleteImgUri
                    )
                }
                val pathFile = "$DIR_MATERIAL_IMG/$nameFile"
                val uri = File(context.getExternalFilesDir(null), pathFile).toUri()
                material.photoUri = uri
            }
            repoMaterial.update(EntityMapper.toMaterialEntity(material))
        }
    }
}