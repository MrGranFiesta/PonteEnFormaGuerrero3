package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.snapshot

import android.content.Context
import androidx.room.Transaction
import com.mrgranfiesta.ponteenformaguerrero3.data.filesystem.StorageFileService
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.EjercicioSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.MaterialSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.SnapshotCronoRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_EJERCICIO_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_MATERIAL_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.UriUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeleteSnapshotUseCase @Inject constructor(
    @ApplicationContext private val context : Context,
    private val snapshotRepo: SnapshotCronoRepository,
    private val ejercicioSnapshotRepo : EjercicioSnapshotRepository,
    private val materialSnapshotRepo : MaterialSnapshotRepository
) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    @Transaction
    operator fun invoke(idRutinaSnapshot: Long) {
        ioScope.launch {
            val ejercicioNameImg = ejercicioSnapshotRepo.getNameImgByRutinaId(idRutinaSnapshot)
            ejercicioNameImg.forEach {
                if (ejercicioSnapshotRepo.isNotUseNameImgGlobal(it)) {
                    val photoUri = UriUtils.getUriResource(
                        dir = DIR_EJERCICIO_IMG,
                        nameImg = it
                    )
                    StorageFileService.deleteImg(context, photoUri, DIR_EJERCICIO_IMG)
                }
            }
            val materialNameImg = materialSnapshotRepo.getNameImgByRutinaId(idRutinaSnapshot)
            materialNameImg.forEach {
                if (materialSnapshotRepo.isNotUseNameImgGlobal(it)) {
                    val photoUri = UriUtils.getUriResource(
                        dir = DIR_MATERIAL_IMG,
                        nameImg = it
                    )
                    StorageFileService.deleteImg(context, photoUri, DIR_MATERIAL_IMG)
                }
            }
            snapshotRepo.deleteMaterialByIdRutina(idRutinaSnapshot)
            snapshotRepo.deleteEjercicioByIdRutina(idRutinaSnapshot)
            snapshotRepo.deleteRutinaByIdPk(idRutinaSnapshot)
        }
    }
}