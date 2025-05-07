package com.mrgranfiesta.ponteenformaguerrero3.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "ejercicioMaterialCross",
    primaryKeys = ["idEjercicio", "idMaterial"],
    foreignKeys = [
        ForeignKey(
            entity = EjercicioEntity::class,
            parentColumns = ["idEjercicio"],
            childColumns = ["idEjercicio"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MaterialEntity::class,
            parentColumns = ["idMaterial"],
            childColumns = ["idMaterial"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["idMaterial"]),
        Index(value = ["idEjercicio"])
    ]
)
data class EjercicioMaterialCrossEntity(
    @ColumnInfo(name = "idEjercicio")
    val idEjercicio: Long,
    @ColumnInfo(name = "idMaterial")
    val idMaterial: Long
) : Parcelable
