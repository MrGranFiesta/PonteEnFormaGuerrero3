package com.mrgranfiesta.ponteenformaguerrero3.domain.factory

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.EntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog.MaterialNameDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog.StepEditDialogDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioNameAndPhotoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.MaterialInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RutinaInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot.EjercicioSnapshotInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.stat.StatRutinaInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.user.UserWithRolDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.AnswerStateLoading
import java.time.LocalDateTime

class ObjectFactory {
    companion object {
        fun getDefaultEjercicioNameAndPhotoDto() = EjercicioNameAndPhotoDto(
            idEjercicio = 0,
            nombre = "",
            photoUri = Uri.EMPTY
        )


        fun getDefaultEjercicioInfoDtoBean() = EjercicioInfoDto(
            idEjercicio = 0,
            idUser = 0L,
            nombre = "",
            isSimetria = false,
            musculoSet = mutableSetOf(),
            nivel = Nivel.NINGUNO,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.STANDAR_USER
        )


        fun getEjercicioInfoDtoDefault() = EjercicioInfoDto(
            idEjercicio = 0,
            idUser = 0L,
            nombre = "",
            isSimetria = false,
            musculoSet = mutableSetOf(),
            nivel = Nivel.NINGUNO,
            descripcion = "",
            photoUri = Uri.EMPTY,
            rol = Rol.STANDAR_USER
        )

        fun getDefaultRutinaInfo() = RutinaInfoDto(
            idRutina = 0,
            idUser = 0,
            nombre = "",
            descripcion = "",
            musculoSet = mutableSetOf(),
            nivel = Nivel.NINGUNO,
            calentamiento = AnswerState.YES,
            movArticular = AnswerState.YES,
            estiramiento = AnswerState.YES,
            descanso = 5,
            rol = Rol.STANDAR_USER
        )

        fun getDefaultStepEditDialogDto() = StepEditDialogDto(
            idStep = 0L,
            idEjercicio = 0L,
            cantidad = 1,
            serie = 1,
            tipo = TipoEsfuerzo.REPETICION,
            confMaterialList = listOf()
        )

        fun getListMaterialNameDto(): List<MaterialNameDto> = listOf()

        fun getDefaultEntrenamiento() = EntrenamientoDto(
            idRutina = 0,
            calentamiento = AnswerStateLoading.ON_LOADING,
            estiramiento = AnswerState.ASK_LATER,
            movArticular = AnswerState.ASK_LATER,
            descanso = 0,
            musculoSet = mutableSetOf(),
        )

        fun getDefaultStatRutinaInfoDtoBean() = StatRutinaInfoDto(
            idRutinaSnapshot = 0,
            idRutina = 0,
            dateInit = LocalDateTime.now(),
            dateEnd = LocalDateTime.now(),
            isCalentamientoDone = false,
            isMovArticularDone = false,
            isEstiramientoDone = false,
            isCompleted = false,
            nivel = Nivel.NINGUNO,
            musculoSet = mutableSetOf(),
            nombre = "",
            descripcion = ""
        )

        fun getDefaultEjercicioSnapshotInfo() = EjercicioSnapshotInfoDto(
            idEjercicioSnapshot = 0L,
            idEjercicio = 0L,
            idUser = 0L,
            nombre = "",
            descripcion = "",
            musculoSet = emptySet(),
            nivel = Nivel.NINGUNO,
            isSimetria = true,
            photoUri = Uri.EMPTY
        )

        fun getDefaultMaterialInfo() = MaterialInfoDto(
            idMaterial = 0,
            idUser = 0,
            nombre = "",
            isMaterialWeight = false,
            descripcion = "",
            rol = Rol.STANDAR_USER,
            photoUri = Uri.EMPTY
        )

        fun getGuestUser() = UserWithRolDto(
            idUser = 3,
            username = "Guest",
            email = "guest@gmail.com",
            photoUri = Uri.EMPTY,
            rol = Rol.GUEST
        )
    }
}