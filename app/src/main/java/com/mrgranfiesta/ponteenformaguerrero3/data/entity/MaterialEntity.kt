package com.mrgranfiesta.ponteenformaguerrero3.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "material",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["idUser"],
            childColumns = ["idUser"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index(value = ["idMaterial"], unique = true),
        Index(value = ["idUser"])
    ]
)
data class MaterialEntity(
    @ColumnInfo(name = "idMaterial")
    @PrimaryKey(autoGenerate = true)
    var idMaterial: Long = 0,
    @ColumnInfo(name = "idUser")
    var idUser: Long,
    @ColumnInfo(name = "nombre")
    var nombre: String,
    @ColumnInfo(name = "isMaterialWeight")
    var isMaterialWeight: Boolean,
    @ColumnInfo(name = "descripcion")
    var descripcion: String,
    @ColumnInfo(name = "nameImg")
    var nameImg: String
) : Parcelable
