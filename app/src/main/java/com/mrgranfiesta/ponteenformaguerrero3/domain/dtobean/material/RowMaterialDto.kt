package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material

import android.net.Uri
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class RowMaterialDto (
     var idMaterial: Long,
     var idUser : Long,
     var nombre: String,
     var photoUri: Uri
): Parcelable