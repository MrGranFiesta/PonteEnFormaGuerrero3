package com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.ejercicio

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.room.Transaction
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.EjercicioMaterialCrossEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.filesystem.StorageFileService
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioMaterialCrossRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepository
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_EJERCICIO_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper.EntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class CreateEjercicioUseCase @Inject constructor(
    private val ejercicioRepo : EjercicioRepository,
    private val crossRepo: EjercicioMaterialCrossRepository
){
    private val ioScope = CoroutineScope(Dispatchers.IO)

    @Transaction
    operator fun invoke(
        ejercicio: EjercicioInfoDto,
        materialList: Set<RowMaterialDto>,
        context: Context
    ) {
        ioScope.launch {
            val nameFile = StorageFileService.insertImg(
                context = context,
                galeryUri = ejercicio.photoUri,
                dir = DIR_EJERCICIO_IMG
            )
            if (nameFile.isNotEmpty()) {
                val pathFile = "$DIR_EJERCICIO_IMG/$nameFile"
                val uri = File(context.getExternalFilesDir(null), pathFile).toUri()
                ejercicio.photoUri = uri
            } else {
                ejercicio.photoUri = Uri.EMPTY
            }
            val idEjercicio = ejercicioRepo.insert(
                EntityMapper.toEjercicioEntity(ejercicio)
            )
            val ejercicioMaterialCrossList = materialList.map {
                EjercicioMaterialCrossEntity(
                    idEjercicio = idEjercicio,
                    idMaterial = it.idMaterial
                )
            }
            crossRepo.insertList(ejercicioMaterialCrossList)
        }
    }
}