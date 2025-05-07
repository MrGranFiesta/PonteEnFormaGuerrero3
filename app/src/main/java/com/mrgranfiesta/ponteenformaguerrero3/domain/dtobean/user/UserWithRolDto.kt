package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.user

import android.net.Uri
import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserWithRolDto(
    var idUser : Long,
    var username : String,
    var email : String,
    var photoUri : Uri,
    var rol : Rol
) : Parcelable
