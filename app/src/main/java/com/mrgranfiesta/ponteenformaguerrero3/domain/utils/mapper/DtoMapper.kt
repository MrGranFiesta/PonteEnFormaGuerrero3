package com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper


import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.confmaterial.RowConfMaterialFormDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.confmaterial.RowConfMaterialInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono.EntrenamientoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono.MaterialEntrenamientoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono.StepEntrenamientoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.ejercicio.EjercicioInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.ejercicio.EjercicioNameAndPhotoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.ejercicio.RowEjercicioDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.material.MaterialInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.material.RowMaterialDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.rutina.RowRutinaDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.rutina.RutinaInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.confmaterial.MaterialWithConfSnapshotDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.ejercicio.EjercicioSnapshotInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.step.MaterialWithConfSnapshotStepDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.step.StepSnapshotInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.stat.StatRutinaInfoDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.stat.StatRutinaSearchDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.step.StepFormWithConfMaterialDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.step.StepInfoWithConfMaterialDB
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.user.UserWithRolDB
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_EJERCICIO_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_MATERIAL_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_PROFILE_USER_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.EntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.MaterialEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.dialog.MaterialNameDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioNameAndPhotoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.RowEjercicioDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.MaterialInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialFormWithConfDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialInfoWithConfDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialWithConfDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RowRutinaDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RutinaInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot.EjercicioSnapshotInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot.MaterialWithConfSnapshotDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot.StatConfMaterialSnapshotDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot.StepSnapshotInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.stat.StatRutinaInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.stat.StatRutinaSearchDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.step.RowStepFormWithConfMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.step.RowStepInfoWithConfMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.user.UserWithRolDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.objects.AnswerStateLoading
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.UriUtils
import kotlin.Long

class DtoMapper {
    companion object {
        fun toEntrenamientoDto(obj: EntrenamientoDB) = EntrenamientoDto(
            idRutina = obj.idRutina,
            calentamiento = AnswerStateLoading.convert(obj.calentamiento),
            estiramiento = obj.estiramiento,
            movArticular = obj.movArticular,
            descanso = obj.descanso,
            musculoSet = obj.musculoSet
        )

        fun toStepEntrenamientoDto(obj: StepEntrenamientoDB): StepEntrenamientoDto {
            val uri = UriUtils.getUriResource(
                dir = DIR_EJERCICIO_IMG,
                nameImg = obj.nameImg,
                rol = obj.rol
            )
            return StepEntrenamientoDto(
                idEjercicio = obj.idEjercicio,
                idStep = obj.idStep,
                nombre = obj.nombre,
                photoUri = uri,
                isSimetria = obj.isSimetria,
                cantidad = obj.cantidad,
                serie = obj.serie,
                ordenExecution = obj.ordenExecution,
                tipo = obj.tipo,
                musculoSet = obj.musculoSet
            )
        }

        fun toMaterialEntrenamientoDto(obj: MaterialEntrenamientoDB): MaterialEntrenamientoDto {
            val uri = UriUtils.getUriResource(
                dir = DIR_MATERIAL_IMG,
                nameImg = obj.nameImg,
                rol = obj.rol
            )
            return MaterialEntrenamientoDto(
                idMaterial = obj.idMaterial,
                idConfMaterial = obj.idConfMaterial,
                idStep = obj.idStep,
                nombre = obj.nombre,
                photoUri = uri,
                isMaterialWeight = obj.isMaterialWeight,
                confValue = obj.confValue
            )
        }

        fun toStepSnapshotInfoDto(obj: StepSnapshotInfoDB) = StepSnapshotInfoDto(
            idStep = obj.idStep,
            idStepSnapshot = obj.idStepSnapshot,
            idEjercicioSnapshot = obj.idEjercicioSnapshot,
            idEjercicio = obj.idEjercicio,
            idRutinaSnapshot = obj.idRutinaSnapshot,
            cantidad = obj.cantidad,
            serie = obj.serie,
            ordenExecution = obj.ordenExecution,
            tipo = obj.tipo,
            musculoSet = obj.musculoSet,
            confMaterialList = obj.confMaterialList.map {
                toStatConfMaterialSnapshotDto(it)
            }
        )

        fun toStatConfMaterialSnapshotDto(obj: MaterialWithConfSnapshotStepDB): StatConfMaterialSnapshotDto {
            val uri = UriUtils.getUriResource(
                dir = DIR_MATERIAL_IMG,
                nameImg = obj.nameImg,
                rol = obj.rol
            )
            return StatConfMaterialSnapshotDto(
                idConfMaterialSnapshot = obj.idConfMaterialSnapshot,
                idConfMaterial = obj.idConfMaterial,
                idMaterialSnapshot = obj.idMaterialSnapshot,
                idMaterial = obj.idMaterial,
                idStepSnapshot = obj.idStepSnapshot,
                idStep = obj.idStep,
                confValue = obj.confValue,
                nombre = obj.nombre,
                isMaterialWeight = obj.isMaterialWeight,
                photoUri = uri
            )
        }

        fun toEjercicioSnapshotInfoDto(obj: EjercicioSnapshotInfoDB): EjercicioSnapshotInfoDto {
            val uri = UriUtils.getUriResource(
                dir = DIR_EJERCICIO_IMG,
                nameImg = obj.nameImg,
                rol = obj.rol
            )
            return EjercicioSnapshotInfoDto(
                idEjercicio = obj.idEjercicio,
                idUser = 0L,
                nombre = obj.nombre,
                isSimetria = obj.isSimetria,
                musculoSet = obj.musculoSet,
                nivel = obj.nivel,
                descripcion = obj.descripcion,
                photoUri = uri
            )
        }

        fun toEjercicioNameAndPhotoDto(obj: EjercicioSnapshotInfoDB): EjercicioNameAndPhotoDto {
            val uri = UriUtils.getUriResource(
                dir = DIR_EJERCICIO_IMG,
                nameImg = obj.nameImg,
                rol = obj.rol
            )
            return EjercicioNameAndPhotoDto(
                idEjercicio = obj.idEjercicio,
                nombre = obj.nombre,
                photoUri = uri
            )
        }

        fun toMaterialWithConfSnapshotDto(
            obj: MaterialWithConfSnapshotDB,
        ): MaterialWithConfSnapshotDto {
            val uri = UriUtils.getUriResource(
                dir = DIR_MATERIAL_IMG,
                nameImg = obj.nameImg,
                rol = obj.rol
            )
            return MaterialWithConfSnapshotDto(
                idEjercicioSnapshot = obj.idEjercicioSnapshot,
                idEjercicio = obj.idEjercicio,
                idMaterialSnapshot = obj.idMaterialSnapshot,
                idMaterial = obj.idMaterial,
                idConfMaterialSnapshot = obj.idConfMaterialSnapshot,
                idConfMaterial = obj.idConfMaterial,
                confValue = obj.confValue,
                nombre = obj.nombre,
                isMaterialWeight = obj.isMaterialWeight,
                photoUri = uri
            )
        }

        fun toRowEjercicioDto(obj: RowEjercicioDB): RowEjercicioDto {
            val uri = UriUtils.getUriResource(
                dir = DIR_EJERCICIO_IMG,
                nameImg = obj.nameImg,
                rol = obj.rol
            )
            return RowEjercicioDto(
                idEjercicio = obj.idEjercicio,
                nombre = obj.nombre,
                musculoSet = obj.musculoSet,
                nivel = obj.nivel,
                photoUri = uri
            )
        }

        fun toRowMaterialDto(obj: RowMaterialDB): RowMaterialDto {
            val uri = UriUtils.getUriResource(
                dir = DIR_MATERIAL_IMG,
                nameImg = obj.nameImg,
                rol = obj.rol
            )
            return RowMaterialDto(
                idMaterial = obj.idMaterial,
                idUser = obj.idUser,
                nombre = obj.nombre,
                photoUri = uri
            )
        }

        fun toEjercicioInfoDto(obj: EjercicioInfoDB): EjercicioInfoDto {
            val uri = UriUtils.getUriResource(
                dir = DIR_EJERCICIO_IMG,
                nameImg = obj.nameImg,
                rol = obj.rol
            )
            return EjercicioInfoDto(
                idEjercicio = obj.idEjercicio,
                idUser = obj.idUser,
                nombre = obj.nombre,
                isSimetria = obj.isSimetria,
                musculoSet = obj.musculoSet,
                nivel = obj.nivel,
                descripcion = obj.descripcion,
                photoUri = uri,
                rol = obj.rol
            )
        }

        fun toMaterialInfoDto(obj: MaterialInfoDB): MaterialInfoDto {
            val uri = UriUtils.getUriResource(
                dir = DIR_MATERIAL_IMG,
                nameImg = obj.nameImg,
                rol = obj.rol
            )
            return MaterialInfoDto(
                idMaterial = obj.idMaterial,
                idUser = obj.idUser,
                nombre = obj.nombre,
                isMaterialWeight = obj.isMaterialWeight,
                descripcion = obj.descripcion,
                photoUri = uri,
                rol = obj.rol
            )
        }

        fun toRowRutinaDto(obj: RowRutinaDB) = RowRutinaDto(
            idRutina = obj.idRutina,
            idUser = obj.idUser,
            nombre = obj.nombre,
            nivel = obj.nivel,
            musculoSet = obj.musculoSet,
            isPremium = obj.isPremium
        )

        fun toRutinaInfoDto(obj: RutinaInfoDB) = RutinaInfoDto(
            idRutina = obj.idRutina,
            idUser = obj.idUser,
            nombre = obj.nombre,
            descripcion = obj.descripcion,
            musculoSet = obj.musculoSet,
            nivel = obj.nivel,
            calentamiento = obj.calentamiento,
            estiramiento = obj.estiramiento,
            movArticular = obj.movArticular,
            descanso = obj.descanso,
            rol = obj.rol
        )

        fun toRowMaterialWithConfDto(obj: RowMaterialInfoWithConfDto) =
            RowMaterialWithConfDto(
                photoUri = obj.photoUri,
                nombre = obj.nombre,
                isMaterialWeight = obj.isMaterialWeight,
                confValue = obj.confValue
            )

        fun toRowMaterialWithConfDto(statConfMaterial: StatConfMaterialSnapshotDto): RowMaterialWithConfDto {
            return RowMaterialWithConfDto(
                confValue = statConfMaterial.confValue,
                nombre = statConfMaterial.nombre,
                isMaterialWeight = statConfMaterial.isMaterialWeight,
                photoUri = statConfMaterial.photoUri
            )
        }

        fun toRowStepInfoWithConfMaterialDto(obj: StepInfoWithConfMaterialDB) =
            RowStepInfoWithConfMaterialDto(
                idStep = obj.idStep,
                idEjercicio = obj.idEjercicio,
                cantidad = obj.cantidad,
                serie = obj.serie,
                tipo = obj.tipo,
                ordenExecution = obj.ordenExecution,
                confMaterialList = obj.confMaterial.map {
                    this.toRowMaterialInfoWithConfDto(it)
                }
            )

        fun toRowMaterialInfoWithConfDto(obj: RowConfMaterialInfoDB): RowMaterialInfoWithConfDto {
            val uri = UriUtils.getUriResource(
                dir = DIR_MATERIAL_IMG,
                nameImg = obj.nameImg,
                rol = obj.rol
            )
            return RowMaterialInfoWithConfDto(
                idMaterial = obj.idMaterial,
                confValue = obj.confValue,
                nombre = obj.nombre,
                isMaterialWeight = obj.isMaterialWeight,
                photoUri = uri
            )
        }

        fun toRowStepFormWithConfMaterialDto(obj: StepFormWithConfMaterialDB) =
            RowStepFormWithConfMaterialDto(
                idStep = obj.idStep,
                idRutina = obj.idRutina,
                idEjercicio = obj.idEjercicio,
                cantidad = obj.cantidad,
                serie = obj.serie,
                tipo = obj.tipo,
                ordenExecution = obj.ordenExecution,
                confMaterialList = obj.confMaterial.map {
                    this.toRowMaterialFormWithConfDto(it)
                }
            )

        fun toRowMaterialFormWithConfDto(obj: RowConfMaterialFormDB) =
            RowMaterialFormWithConfDto(
                idMaterial = obj.idMaterial,
                idConfMaterial = obj.idConfMaterial,
                idStep = obj.idStep,
                confValue = obj.confValue,
                nombre = obj.nombre,
                isMaterialWeight = obj.isMaterialWeight
            )


        fun toRowMaterialFormWithConfDto(
            idStep: Long,
            obj: MaterialNameDto
        ) = RowMaterialFormWithConfDto(
            idMaterial = obj.idMaterial,
            idConfMaterial = obj.idConfMaterial,
            idStep = idStep,
            nombre = obj.nombre,
            isMaterialWeight = obj.isMaterialWeight,
            confValue = obj.confValue
        )

        fun toEjercicioNameAndPhotoDto(obj: EjercicioNameAndPhotoDB): EjercicioNameAndPhotoDto {
            val uri = UriUtils.getUriResource(
                dir = DIR_EJERCICIO_IMG,
                nameImg = obj.nameImg,
                rol = obj.rol
            )
            return EjercicioNameAndPhotoDto(
                idEjercicio = obj.idEjercicio,
                nombre = obj.nombre,
                photoUri = uri
            )
        }

        fun toStatRutinaSearchDto(obj: StatRutinaSearchDB) = StatRutinaSearchDto(
            idStat = obj.idStat,
            idRutinaSnapshot = obj.idRutinaSnapshot,
            idRutina = obj.idRutina,
            dateInit = obj.dateInit,
            dateEnd = obj.dateEnd,
            isCompleted = obj.isCompleted,
            nombre = obj.nombre,
            nivel = obj.nivel,
            isPremium = obj.isPremium,
            musculoSet = obj.musculoSet
        )

        fun toStatRutinaInfoDto(obj: StatRutinaInfoDB) = StatRutinaInfoDto(
            idStat = obj.idStat,
            idRutinaSnapshot = obj.idRutinaSnapshot,
            idRutina = obj.idRutina,
            dateInit = obj.dateInit,
            dateEnd = obj.dateEnd,
            isCalentamientoDone = obj.isCalentamientoDone,
            isEstiramientoDone = obj.isEstiramientoDone,
            isMovArticularDone = obj.isMovArticularDone,
            isCompleted = obj.isCompleted,
            nombre = obj.nombre,
            nivel = obj.nivel,
            musculoSet = obj.musculoSet,
            descripcion = obj.descripcion
        )

        fun toRowMaterialWithConfDto(obj: MaterialEntrenamientoDto) =
            RowMaterialWithConfDto(
                photoUri = obj.photoUri,
                nombre = obj.nombre,
                isMaterialWeight = obj.isMaterialWeight,
                confValue = obj.confValue
            )

        fun toUserWithRolDBtoDto(user: UserWithRolDB?): UserWithRolDto? {
            return user?.let {
                UserWithRolDto(
                    idUser = user.idUser,
                    username = user.username,
                    email = user.email,
                    photoUri = UriUtils.getUriResource(DIR_PROFILE_USER_IMG, user.nameImg),
                    rol = user.rol
                )
            }
        }
    }
}