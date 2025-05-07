package com.mrgranfiesta.ponteenformaguerrero3.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "user",
    indices = [
        Index(value = ["idUser"], unique = true)
    ]
)
data class UserEntity (
    @ColumnInfo(name = "idUser")
    @PrimaryKey(autoGenerate = true)
    var idUser: Long = 0,
    @ColumnInfo(name = "rol")
    var rol : Rol,
    @ColumnInfo("username")
    var username : String,
    @ColumnInfo("password")
    var password : String,
    @ColumnInfo("email")
    var email : String,
    @ColumnInfo("nameImg")
    var nameImg : String
) : Parcelable