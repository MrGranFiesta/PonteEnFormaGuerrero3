package com.mrgranfiesta.ponteenformaguerrero3.domain.bean

import android.net.Uri
import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.FormatEmail
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.MaxLenght
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.NotEmpty
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.SecuredCharEmailSQL
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.SecuredCharSQL
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserBean(
    var idUser: Long = 0,
    var rol : Rol,
    @NotEmpty("nombre")
    @SecuredCharSQL("nombre")
    @MaxLenght(20, "nombre")
    var username : String,
    @NotEmpty("contraseña")
    @MaxLenght(20, "contraseña")
    var password : String,
    @NotEmpty("email")
    @SecuredCharEmailSQL("email")
    @FormatEmail("email")
    @MaxLenght(50, "email")
    var email : String,
    var photoUri : Uri
) : Parcelable
