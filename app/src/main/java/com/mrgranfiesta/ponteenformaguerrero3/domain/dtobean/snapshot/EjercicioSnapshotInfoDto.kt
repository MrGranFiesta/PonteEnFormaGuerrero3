package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.snapshot

import android.net.Uri
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class EjercicioSnapshotInfoDto(
    var idEjercicioSnapshot: Long = 0,
    var idEjercicio: Long,
    var idUser: Long,
    var nombre: String,
    var descripcion: String,
    var musculoSet: Set<Musculo>,
    var nivel: Nivel,
    var isSimetria: Boolean,
    var photoUri: Uri
) : Parcelable