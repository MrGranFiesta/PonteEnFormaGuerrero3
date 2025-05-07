package com.mrgranfiesta.ponteenformaguerrero3.data.repository

import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.user.UserWithRolDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.UserEntity
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol

interface UserRepository {
    suspend fun getNameImg(idUser : Long) : String
    suspend fun isEmailDuplicated(email: String): Boolean
    suspend fun isNotCorrectPassword(idUser : Long, password: String): Boolean
    suspend fun loginUser(
        email : String,
        password : String
    ) : UserWithRolDB?
    suspend fun updatePhotoUri(
        idUser : Long,
        nameImg : String
    )
    suspend fun updateUsername(
        idUser : Long,
        username : String
    )
    suspend fun updateRol(
        idUser : Long,
        rol : Rol
    )
    suspend fun updatePassword(
        idUser : Long,
        password : String
    )
    suspend fun insert(user : UserEntity)
    suspend fun deleteById(id : Long)
}