package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio

import android.content.Context
import androidx.room.Transaction
import com.mrgranfiesta.ponteenformaguerrero3.data.filesystem.StorageFileService
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.EjercicioSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_EJERCICIO_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.UriUtils
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.EntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeleteEjercicioUseCase @Inject constructor(
    private val ejercicioRepo: EjercicioRepository,
    private val ejercicioSnapshotRepo: EjercicioSnapshotRepository,
) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    @Transaction
    operator fun invoke(
        ejercicio: EjercicioInfoDto,
        context: Context
    ) {
        ioScope.launch {
            val deleteNameImg = UriUtils.getNameByUri(
                photoUri = ejercicio.photoUri,
                rol = ejercicio.rol
            )
            if (ejercicioSnapshotRepo.isNotUseNameImg(deleteNameImg)) {
                StorageFileService.deleteImg(context, ejercicio.photoUri, DIR_EJERCICIO_IMG)
            }
            ejercicioRepo.delete(
                EntityMapper.toEjercicioEntity(ejercicio)
            )
        }
    }
}