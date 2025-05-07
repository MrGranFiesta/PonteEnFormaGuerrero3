package com.mrgranfiesta.ponteenformaguerrero3.domain.singleton

import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.user.UserWithRolDto

object CurrentUser {
    var user : UserWithRolDto? = null

    fun logout() {
        user = null
    }
}