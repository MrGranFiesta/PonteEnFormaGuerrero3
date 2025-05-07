package com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "confMaterialSnapshot",
    foreignKeys = [
        ForeignKey(
            entity = MaterialSnapshotEntity::class,
            parentColumns = ["idMaterialSnapshot"],
            childColumns = ["idMaterialSnapshot"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = StepSnapshotEntity::class,
            parentColumns = ["idStepSnapshot"],
            childColumns = ["idStepSnapshot"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["idConfMaterialSnapshot"]),
        Index(value = ["idMaterialSnapshot"]),
        Index(value = ["idStepSnapshot"])
    ]
)
data class ConfMaterialSnapshotEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idConfMaterialSnapshot")
    var idConfMaterialSnapshot: Long = 0,
    @ColumnInfo(name = "idMaterialSnapshot")
    var idMaterialSnapshot: Long,
    @ColumnInfo(name = "idStepSnapshot")
    var idStepSnapshot: Long,
    @ColumnInfo(name = "idConfMaterial")
    var idConfMaterial: Long,
    @ColumnInfo(name = "confValue")
    var confValue: Double
) : Parcelable