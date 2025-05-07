package com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "materialSnapshot",
    indices = [
        Index(value = ["idMaterialSnapshot"])
    ]
)
data class MaterialSnapshotEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idMaterialSnapshot")
    var idMaterialSnapshot: Long = 0,
    @ColumnInfo(name = "idMaterial")
    var idMaterial: Long,
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
