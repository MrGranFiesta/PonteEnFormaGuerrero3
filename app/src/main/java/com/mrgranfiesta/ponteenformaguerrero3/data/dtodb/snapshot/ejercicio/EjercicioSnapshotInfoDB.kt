package com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.snapshot.ejercicio

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import kotlinx.parcelize.Parcelize

@Parcelize
data class EjercicioSnapshotInfoDB(
    var idEjercicioSnapshot: Long = 0,
    var idEjercicio: Long,
    var idUser: Long,
    var nombre: String,
    var descripcion: String,
    var musculoSet: Set<Musculo>,
    var nivel: Nivel,
    var isSimetria: Boolean,
    var nameImg: String,
    var rol : Rol
) : Parcelable
