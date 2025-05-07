package com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "ejercicioMaterialCrossSnapshot",
    primaryKeys = ["idMaterialSnapshot", "idEjercicioSnapshot"],
    foreignKeys = [
        ForeignKey(
            entity = EjercicioSnapshotEntity::class,
            parentColumns = ["idEjercicioSnapshot"],
            childColumns = ["idEjercicioSnapshot"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MaterialSnapshotEntity::class,
            parentColumns = ["idMaterialSnapshot"],
            childColumns = ["idMaterialSnapshot"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["idMaterialSnapshot"]),
        Index(value = ["idEjercicioSnapshot"])
    ]
)
data class EjercicioMaterialCrossSnapshotEntity(
    @ColumnInfo(name = "idEjercicioSnapshot")
    val idEjercicioSnapshot: Long,
    @ColumnInfo(name = "idMaterialSnapshot")
    val idMaterialSnapshot: Long
) : Parcelable
