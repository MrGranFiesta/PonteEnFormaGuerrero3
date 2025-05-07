package com.mrgranfiesta.ponteenformaguerrero3.utilstest.datasource

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.UserDao
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.UserEntity
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import javax.inject.Inject

class GenerateUser @Inject constructor(
    private val dao: UserDao
) {
    suspend operator fun invoke() {
        getDefaultUser().forEach { user ->
            dao.insert(user)
        }
    }

    private fun getDefaultUser() : List<UserEntity> {
        return listOf(
            UserEntity(
                idUser = 1,
                rol = Rol.GUEST,
                username = "Guest",
                password = "1234",
                email = "guest@gmail.com",
                nameImg = "profile_1234"
            ),
            UserEntity(
                idUser = 2,
                rol = Rol.STANDAR_USER,
                username = "standar",
                password = "1234",
                email = "standar@gmail.com",
                nameImg = "profile_5678"
            ),
            UserEntity(
                idUser = 3,
                rol = Rol.INIT_DATA_USER,
                username = "init",
                password = "1234",
                email = "init@gmail.com",
                nameImg = "profile_9012"
            ),
            UserEntity(
                idUser = 4,
                rol = Rol.PREMIUN_USER,
                username = "premium",
                password = "1234",
                email = "pre@gmail.com",
                nameImg = "profile_3456"
            )
        )
    }
}