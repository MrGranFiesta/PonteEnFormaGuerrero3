package com.mrgranfiesta.ponteenformaguerrero3.domain.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EjercicioMaterialCrossBean(
    val idEjercicio: Long,
    val idMaterial: Long
) : Parcelable
