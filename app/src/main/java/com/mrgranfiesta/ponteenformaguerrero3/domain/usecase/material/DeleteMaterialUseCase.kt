package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material

import android.content.Context
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
import javax.inject.Inject

class DeleteMaterialUseCase @Inject constructor(
    private val materialRepo : MaterialRepository,
    private val materialSnapshotRepo : MaterialSnapshotRepository
) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(
        context: Context,
        material: MaterialInfoDto
    ) {
        ioScope.launch {
            val deleteNameImg = UriUtils.getNameByUri(
                photoUri = material.photoUri,
                rol = material.rol
            )
            if (materialSnapshotRepo.isNotUseNameImg(deleteNameImg)) {
                StorageFileService.deleteImg(context, material.photoUri, DIR_MATERIAL_IMG)
            }
            materialRepo.delete(EntityMapper.toMaterialEntity(material))
        }
    }
}