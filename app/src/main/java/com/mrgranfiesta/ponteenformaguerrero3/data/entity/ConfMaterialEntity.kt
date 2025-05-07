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
    tableName = "confMaterial",
    foreignKeys = [
        ForeignKey(
            entity = MaterialEntity::class,
            parentColumns = ["idMaterial"],
            childColumns = ["idMaterial"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = StepEntity::class,
            parentColumns = ["idStep"],
            childColumns = ["idStep"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index(value = ["idConfMaterial"], unique = true),
        Index(value = ["idMaterial"]),
        Index(value = ["idStep"])
    ]
)
data class ConfMaterialEntity(
    @ColumnInfo(name = "idConfMaterial")
    @PrimaryKey(autoGenerate = true)
    var idConfMaterial: Long = 0,
    @ColumnInfo(name = "idMaterial")
    var idMaterial: Long = 0,
    @ColumnInfo(name = "idStep")
    var idStep: Long = 0,
    @ColumnInfo(name = "confValue")
    var confValue: Double
) : Parcelable
