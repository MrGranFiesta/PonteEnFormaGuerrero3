package com.mrgranfiesta.ponteenformaguerrero3.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.user.UserWithRolDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.UserEntity
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol

@Dao
interface UserDao {
    @Query("""
        SELECT
            u.nameImg
        FROM USER u
        WHERE u.idUser = :idUser
    """)
    suspend fun getPhotoUri(idUser : Long) : String

    @Query(
        """
        SELECT EXISTS(
            SELECT 1 
            FROM user 
            WHERE email = :email
        )
    """
    )
    suspend fun isEmailExists(email: String): Boolean

    @Query(
        """
        SELECT NOT EXISTS(
            SELECT 1 
            FROM user 
            WHERE idUser = :idUser
            AND password = :password
        )
    """
    )
    suspend fun isNotCorrectPassword(idUser: Long, password: String) : Boolean

    @Query(
        """
        SELECT 
            u.idUser,
            u.rol,
            u.username,
            u.email,
            u.nameImg
        FROM USER u
        WHERE u.email = :email 
        AND u.password = :password
    """
    )
    suspend fun loginUser(
        email: String,
        password: String
    ): UserWithRolDB?

    @Insert
    suspend fun insert(user: UserEntity) : Long

    @Query("UPDATE user SET nameImg = :nameImg WHERE idUser = :idUser")
    suspend fun updateNameImgByIdUser(
        idUser: Long,
        nameImg: String
    )

    @Query("UPDATE user SET rol = :rol WHERE idUser = :idUser")
    suspend fun updateRolByIdUser(
        idUser: Long,
        rol: Rol
    )

    @Query("UPDATE user SET username = :username WHERE idUser = :idUser")
    suspend fun updateUsernameByIdUser(
        idUser: Long,
        username: String
    )

    @Query("UPDATE user SET password = :password WHERE idUser = :idUser")
    suspend fun updatePasswordByIdUser(
        idUser: Long,
        password: String
    )

    @Query("DELETE FROM user WHERE idUser = :id")
    suspend fun deleteById(id: Long)
}