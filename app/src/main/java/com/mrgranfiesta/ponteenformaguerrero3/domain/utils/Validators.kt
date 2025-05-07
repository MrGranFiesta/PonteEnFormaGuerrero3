package com.mrgranfiesta.ponteenformaguerrero3.domain.utils

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.ejercicio.EjercicioInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.MaterialInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.rutina.RutinaInfoDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.step.RowStepFormWithConfMaterialDto

class Validators {
    companion object {
        fun isChangedValuesEjercicio(
            ejercicioOrigen: EjercicioInfoDto,
            ejercicioUpdate: EjercicioInfoDto,
            materialOrigen: Set<RowMaterialDto>,
            materialUpdate: Set<RowMaterialDto>
        ): Boolean {
            if (ejercicioUpdate.isSimetria != ejercicioOrigen.isSimetria) {
                return true
            }
            if (!ejercicioUpdate.descripcion.trim().contentEquals(ejercicioOrigen.descripcion)) {
                return true
            }
            if (ejercicioUpdate.nivel != ejercicioOrigen.nivel) {
                return true
            }
            if (!ejercicioUpdate.nombre.trim().contentEquals(ejercicioOrigen.nombre)) {
                return true
            }
            if (this.isNotEqualsContent(ejercicioUpdate.musculoSet, ejercicioOrigen.musculoSet)) {
                return true
            }
            val uriPathUpd = ejercicioOrigen.photoUri.path
            val uriPathOri = ejercicioUpdate.photoUri.path
            if (!uriPathUpd.contentEquals(uriPathOri)) {
                return true
            }
            val materialOrigenId = materialOrigen.map { it.idMaterial }
            val materialUpdateId = materialUpdate.map { it.idMaterial }
            return this.isNotEqualsContent(materialUpdateId, materialOrigenId)
        }

        fun isChangedValuesRutina(
            rutinaOrigen: RutinaInfoDto,
            rutinaUpdate: RutinaInfoDto,
            stepOrigen: List<RowStepFormWithConfMaterialDto>,
            stepUpdate: List<RowStepFormWithConfMaterialDto>
        ): Boolean {
            if (!rutinaUpdate.nombre.trim().contentEquals(rutinaOrigen.nombre)) {
                return true
            }
            if (!rutinaUpdate.descripcion.trim().contentEquals(rutinaOrigen.descripcion)) {
                return true
            }
            if (this.isNotEqualsContent(rutinaUpdate.musculoSet, rutinaOrigen.musculoSet)) {
                return true
            }
            if (rutinaUpdate.nivel != rutinaOrigen.nivel) {
                return true
            }
            if (rutinaUpdate.calentamiento != rutinaOrigen.calentamiento) {
                return true
            }
            if (rutinaUpdate.estiramiento != rutinaOrigen.estiramiento) {
                return true
            }
            if (rutinaUpdate.movArticular != rutinaOrigen.movArticular) {
                return true
            }
            if (rutinaUpdate.descanso != rutinaOrigen.descanso) {
                return true
            }
            return stepOrigen != stepUpdate
        }

        fun isChangedValuesMaterial(
            materialOrigen: MaterialInfoDto,
            materialUpdate: MaterialInfoDto
        ): Boolean {
            if (!materialUpdate.descripcion.trim().contentEquals(materialOrigen.descripcion)) {
                return true
            }
            if (!materialUpdate.nombre.trim().contentEquals(materialOrigen.nombre)) {
                return true
            }
            val uriPathUpd = materialUpdate.photoUri.path
            val uriPathOri = materialOrigen.photoUri.path
            if (!uriPathUpd.contentEquals(uriPathOri)) {
                return true
            }
            return materialUpdate.isMaterialWeight != materialOrigen.isMaterialWeight
        }

        fun <T> isNotEqualsContent(
            set1: Collection<T>,
            set2: Collection<T>
        ): Boolean {
            if (set1.size != set2.size) {
                return true
            }
            return !set1.containsAll(set2)
        }

        fun <T> isContent(
            set: Collection<T>,
            item: T
        ): Boolean {
            set.forEach {
                if (it == item) {
                    return true
                }
            }
            return false
        }

        fun <T> isIntersectContent(
            list1: Collection<T>,
            list2: Collection<T>
        ) = list1.intersect(list2.toSet()).isNotEmpty()

    }
}