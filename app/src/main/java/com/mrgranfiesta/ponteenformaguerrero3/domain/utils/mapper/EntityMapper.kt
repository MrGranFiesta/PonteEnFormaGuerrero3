package com.mrgranfiesta.ponteenformaguerrero3.domain.utils.mapper

import com.mrgranfiesta.ponteenformaguerrero3.data.entity.ConfMaterialEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.EjercicioEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.EjercicioMaterialCrossEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.MaterialEntity
//import com.mrgranfiesta.ponteenformaguerrero3.data.entity.RolEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.RutinaEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.StatEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.StepEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.UserEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.EjercicioSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.MaterialSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.RutinaSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.StepSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.ConfMaterialBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.EjercicioBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.EjercicioMaterialCrossBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.MaterialBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.RutinaBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.StatBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.StepBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.UserBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.crono.StepEntrenamientoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.MaterialInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialFormWithConfDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RutinaInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.step.RowStepFormWithConfMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.UriUtils

class EntityMapper {
    companion object {
        fun toEjercicioEntity(
            rol: Rol,
            obj: EjercicioBean
        ): EjercicioEntity {
            val nameImg = UriUtils.getNameByUri(
                photoUri = obj.photoUri,
                rol = rol
            )
            return EjercicioEntity(
                idEjercicio = obj.idEjercicio,
                idUser = obj.idUser,
                nombre = obj.nombre,
                isSimetria = obj.isSimetria,
                musculoSet = obj.musculoSet,
                nivel = obj.nivel,
                descripcion = obj.descripcion,
                nameImg = nameImg
            )
        }

        fun toRutinaEntity(obj: RutinaBean) = RutinaEntity(
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
            isPremium = obj.isPremium
        )

        fun toRutinaEntity(obj: RutinaInfoDto) = RutinaEntity(
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
            isPremium = false
        )

        fun toStepEntity(obj: StepBean) = StepEntity(
            idStep = obj.idStep,
            idEjercicio = obj.idEjercicio,
            idRutina = obj.idRutina,
            cantidad = obj.cantidad,
            serie = obj.serie,
            tipo = obj.tipo,
            ordenExecution = obj.ordenExecution
        )

        fun toMaterialEntity(
            rol: Rol,
            obj: MaterialBean
        ): MaterialEntity {
            val nameImg = UriUtils.getNameByUri(
                photoUri = obj.photoUri,
                rol = rol
            )
            return MaterialEntity(
                idMaterial = obj.idMaterial,
                idUser = obj.idUser,
                nombre = obj.nombre,
                isMaterialWeight = obj.isMaterialWeight,
                descripcion = obj.descripcion,
                nameImg = nameImg
            )
        }

        fun toEjercicioMaterialCrossEntity(
            obj: EjercicioMaterialCrossBean
        ) = EjercicioMaterialCrossEntity(
            idMaterial = obj.idMaterial,
            idEjercicio = obj.idEjercicio,
        )

        fun toEjercicioEntity(obj: EjercicioInfoDto): EjercicioEntity {
            val nameImg = UriUtils.getNameByUri(
                photoUri = obj.photoUri,
                rol = obj.rol
            )
            return EjercicioEntity(
                idEjercicio = obj.idEjercicio,
                idUser = obj.idUser,
                nombre = obj.nombre,
                isSimetria = obj.isSimetria,
                musculoSet = obj.musculoSet,
                nivel = obj.nivel,
                descripcion = obj.descripcion,
                nameImg = nameImg
            )
        }

        fun toConfMaterialEntity(obj: ConfMaterialBean) = ConfMaterialEntity(
            idConfMaterial = obj.idConfMaterial,
            idMaterial = obj.idMaterial,
            idStep = obj.idStep,
            confValue = obj.confValue
        )

        fun toUserEntity(user: UserBean) = UserEntity(
            idUser = user.idUser,
            rol = user.rol,
            username = user.username,
            password = user.password,
            email = user.email,
            nameImg = UriUtils.getNameByUri(user.photoUri)
        )

        fun toEjercicioSnapshotEntity(
            idEjercicioSnapshot: Long,
            obj: EjercicioEntity
        ) = EjercicioSnapshotEntity(
            idEjercicioSnapshot = idEjercicioSnapshot,
            idEjercicio = obj.idEjercicio,
            idUser = obj.idUser,
            nombre = obj.nombre,
            isSimetria = obj.isSimetria,
            musculoSet = obj.musculoSet,
            nivel = obj.nivel,
            nameImg = obj.nameImg,
            descripcion = obj.descripcion
        )

        fun toMaterialEntity(obj: MaterialInfoDto): MaterialEntity {
            val nameImg = UriUtils.getNameByUri(
                photoUri = obj.photoUri,
                rol = obj.rol
            )
            return MaterialEntity(
                idMaterial = obj.idMaterial,
                idUser = obj.idUser,
                nombre = obj.nombre,
                isMaterialWeight = obj.isMaterialWeight,
                descripcion = obj.descripcion,
                nameImg = nameImg
            )
        }

        fun toStepSnapshotEntity(
            obj: StepEntrenamientoDto,
            idEjercicioSnapshot: Long,
            idRutinaSnapshot: Long,
            idStepSnapshot: Long
        ) = StepSnapshotEntity(
            idStepSnapshot = idStepSnapshot,
            idStep = obj.idStep,
            idEjercicioSnapshot = idEjercicioSnapshot,
            idRutinaSnapshot = idRutinaSnapshot,
            serie = obj.serie,
            tipo = obj.tipo,
            cantidad = obj.cantidad,
            ordenExecution = obj.ordenExecution
        )

        fun toMaterialSnapshotEntity(
            idMaterialSnapshot: Long,
            obj: MaterialEntity
        ) = MaterialSnapshotEntity(
            idMaterialSnapshot = idMaterialSnapshot,
            idMaterial = obj.idMaterial,
            idUser = obj.idUser,
            nombre = obj.nombre,
            isMaterialWeight = obj.isMaterialWeight,
            descripcion = obj.descripcion,
            nameImg = obj.nameImg
        )

        fun rutinaEntityToSnapshot(
            idRutinaSnapshot: Long,
            obj: RutinaEntity
        ) = RutinaSnapshotEntity(
            idRutinaSnapshot = idRutinaSnapshot,
            idRutina = obj.idRutina,
            idUser = obj.idUser,
            nombre = obj.nombre,
            descripcion = obj.descripcion,
            musculoSet = obj.musculoSet,
            nivel = obj.nivel,
            isPremium = obj.isPremium,
            descanso = obj.descanso
        )

        fun toConfMaterialEntity(
            obj: RowMaterialFormWithConfDto
        ) = ConfMaterialEntity(
            idConfMaterial = obj.idConfMaterial,
            idMaterial = obj.idMaterial,
            idStep = obj.idStep,
            confValue = obj.confValue
        )

        fun toStatEntity(obj: StatBean): StatEntity = StatEntity(
            idStat = obj.idStatHistory,
            idRutinaSnapshot = obj.idRutinaSnapshot,
            idRutina = obj.idRutina,
            idUser = obj.idUser,
            dateInit = obj.dateInit,
            dateEnd = obj.dateEnd,
            isCalentamientoDone = obj.isCalentamientoDone,
            isMovArticularDone = obj.isMovArticularDone,
            isEstiramientoDone = obj.isEstiramientoDone,
            isCompleted = obj.isCompleted
        )

        fun toStepEntity(obj: RowStepFormWithConfMaterialDto) = StepEntity(
            idStep = obj.idStep,
            idEjercicio = obj.idEjercicio,
            idRutina = obj.idRutina,
            cantidad = obj.cantidad,
            serie = obj.serie,
            ordenExecution = obj.ordenExecution,
            tipo = obj.tipo
        )
    }
}