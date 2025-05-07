package com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.user

import android.os.Parcelable
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.MaxLenght
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.NotEmpty
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.SecuredCharSQL
import kotlinx.parcelize.Parcelize

@Parcelize
data class PasswordValidDto(
    @NotEmpty("antigua contraseña")
    @SecuredCharSQL("antigua contraseña")
    @MaxLenght(20, "antigua contraseña")
    val passwordOld : String,
    @NotEmpty("nueva contraseña")
    @SecuredCharSQL("nueva contraseña")
    @MaxLenght(20, "nueva contraseña")
    val passwordNew : String,
    @NotEmpty("confirmar contraseña")
    @SecuredCharSQL("confirmar contraseña")
    @MaxLenght(20, "confirmar contraseña")
    val passwordNewConfirm : String
) : Parcelable