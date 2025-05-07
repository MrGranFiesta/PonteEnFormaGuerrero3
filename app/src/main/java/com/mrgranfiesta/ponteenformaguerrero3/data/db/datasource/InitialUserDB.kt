package com.mrgranfiesta.ponteenformaguerrero3.data.db.datasource

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.UserBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.CypherSHA256

class InitialUserDB {
    companion object {
        fun getListDataDefaultUser(): List<UserBean> {
            return listOf(
                UserBean(
                    idUser = 1,
                    rol = Rol.INIT_DATA_USER,
                    username = "Init",
                    password = CypherSHA256.hashPassword("1234"),
                    email = "init@gmail.com",
                    photoUri = Uri.EMPTY
                ),
                UserBean(
                    idUser = 2,
                    rol = Rol.GUEST,
                    username = "Invitado",
                    password = CypherSHA256.hashPassword("1234"),
                    email = "guest@gmail.com",
                    photoUri = Uri.EMPTY
                )
            )
        }
    }
}