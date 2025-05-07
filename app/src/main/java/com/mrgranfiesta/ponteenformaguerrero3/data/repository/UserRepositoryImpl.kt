package com.mrgranfiesta.ponteenformaguerrero3.data.repository

import com.mrgranfiesta.ponteenformaguerrero3.data.dao.UserDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.user.UserWithRolDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.UserEntity
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol

class UserRepositoryImpl(
    private val dao: UserDao
) : UserRepository {
    override suspend fun getNameImg(idUser : Long) : String {
        return dao.getPhotoUri(idUser)
    }

    override suspend fun isEmailDuplicated(email: String): Boolean {
        return dao.isEmailExists(email)
    }

    override suspend fun isNotCorrectPassword(
        idUser: Long,
        password: String
    ): Boolean {
        return dao.isNotCorrectPassword(idUser, password)
    }

    override suspend fun loginUser(email: String, password: String): UserWithRolDB? {
        return dao.loginUser(email, password)
    }

    override suspend fun insert(user: UserEntity) {
        dao.insert(user)
    }

    override suspend fun updatePhotoUri(
        idUser: Long,
        nameImg: String
    ) {
        dao.updateNameImgByIdUser(
            idUser = idUser,
            nameImg = nameImg
        )
    }

    override suspend fun updateRol(
        idUser: Long,
        rol: Rol
    ) {
        dao.updateRolByIdUser(
            idUser = idUser,
            rol = rol
        )
    }

    override suspend fun updateUsername(
        idUser: Long,
        username: String
    ) {
        dao.updateUsernameByIdUser(
            idUser = idUser,
            username = username
        )
    }

    override suspend fun updatePassword(
        idUser: Long,
        password: String
    ) {
        dao.updatePasswordByIdUser(
            idUser = idUser,
            password = password
        )
    }

    override suspend fun deleteById(id: Long) {
        dao.deleteById(id)
    }

}