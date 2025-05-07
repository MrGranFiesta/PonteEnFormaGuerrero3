package com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.user

import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol

data class UserWithRolDB(
    var idUser : Long,
    var username : String,
    var email : String,
    var nameImg : String,
    var rol : Rol
)
