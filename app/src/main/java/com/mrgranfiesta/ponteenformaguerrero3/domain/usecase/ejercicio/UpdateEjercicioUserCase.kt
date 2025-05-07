package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.room.Transaction
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.EjercicioMaterialCrossEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.filesystem.StorageFileService
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioMaterialCrossRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.EjercicioSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_EJERCICIO_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.UriUtils
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.EntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class UpdateEjercicioUserCase @Inject constructor(
    private val ejercicioRepo : EjercicioRepository,
    private val ejercicioSnapshotRepo : EjercicioSnapshotRepository,
    private val crossRepo: EjercicioMaterialCrossRepository
){
    private val ioScope = CoroutineScope(Dispatchers.IO)

    @Transaction
    operator fun invoke(
        ejercicio: EjercicioInfoDto,
        deleteImgUri: Uri,
        materialListOrigin: Set<RowMaterialDto>,
        materialListUpdate: Set<RowMaterialDto>,
        context: Context
    ){
        ioScope.launch {
            if (deleteImgUri.path != ejercicio.photoUri.path) {
                val deleteNameFile = UriUtils.getNameByUri(deleteImgUri)
                val nameFile = if (ejercicioSnapshotRepo.isUseNameImg(deleteNameFile)) {
                    StorageFileService.insertImg(
                        context = context,
                        galeryUri = ejercicio.photoUri,
                        dir = DIR_EJERCICIO_IMG
                    )
                } else {
                    StorageFileService.updateImg(
                        context = context,
                        galeryUri = ejercicio.photoUri,
                        dir = DIR_EJERCICIO_IMG,
                        deleteImgUri = deleteImgUri
                    )
                }
                val pathFile = "$DIR_EJERCICIO_IMG/$nameFile"
                val uri = File(context.getExternalFilesDir(null), pathFile).toUri()
                ejercicio.photoUri = uri
            }
            ejercicioRepo.update(
                EntityMapper.toEjercicioEntity(ejercicio)
            )
            val materialListAdd = materialListUpdate.filter { upd ->
                materialListOrigin.none { ori -> ori.idMaterial == upd.idMaterial }
            }
            val materialListDel = materialListOrigin.filter { ori ->
                materialListUpdate.none { upd -> upd.idMaterial == ori.idMaterial }
            }

            val ejercicioMaterialCrossListAdd = materialListAdd.map {
                EjercicioMaterialCrossEntity(
                    idEjercicio = ejercicio.idEjercicio,
                    idMaterial = it.idMaterial
                )
            }
            val ejercicioMaterialCrossListDel = materialListDel.map {
                EjercicioMaterialCrossEntity(
                    idEjercicio = ejercicio.idEjercicio,
                    idMaterial = it.idMaterial
                )
            }
            crossRepo.insertList(ejercicioMaterialCrossListAdd)
            crossRepo.deleteList(ejercicioMaterialCrossListDel)
        }
    }
}