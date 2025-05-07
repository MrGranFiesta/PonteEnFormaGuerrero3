package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material

import android.net.Uri
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.MaxLenght
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.NotEmpty
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.SecuredCharSQL
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class MaterialInfoDto(
    var idMaterial: Long = 0,
    var idUser: Long,
    @MaxLenght(50, "nombre")
    @NotEmpty("nombre")
    @SecuredCharSQL("nombre")
    var nombre: String,
    var isMaterialWeight: Boolean,
    @MaxLenght(500, "descripcion")
    @SecuredCharSQL("descripcion")
    var descripcion: String,
    var photoUri: Uri,
    var rol: Rol
) : Parcelable