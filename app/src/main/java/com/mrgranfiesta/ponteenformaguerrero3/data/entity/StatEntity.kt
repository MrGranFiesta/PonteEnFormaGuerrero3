package com.mrgranfiesta.ponteenformaguerrero3.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.RutinaSnapshotEntity
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
@Entity(
    tableName = "stat",
    foreignKeys = [
        ForeignKey(
            entity = RutinaEntity::class,
            parentColumns = ["idRutina"],
            childColumns = ["idRutina"],
            onDelete = ForeignKey.SET_NULL,
        ),
        ForeignKey(
            entity = RutinaSnapshotEntity::class,
            parentColumns = ["idRutinaSnapshot"],
            childColumns = ["idRutinaSnapshot"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["idUser"],
            childColumns = ["idUser"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index(value = ["idStat"], unique = true),
        Index(value = ["idRutina"]),
        Index(value = ["idRutinaSnapshot"]),
        Index(value = ["idUser"])
    ]
)
data class StatEntity(
    @ColumnInfo(name = "idStat")
    @PrimaryKey(autoGenerate = true)
    var idStat: Long = 0,
    @ColumnInfo(name = "idRutinaSnapshot")
    var idRutinaSnapshot: Long,
    @ColumnInfo(name = "idRutina")
    var idRutina: Long?,
    @ColumnInfo(name = "idUser")
    var idUser: Long,
    @ColumnInfo(name = "dateInit")
    var dateInit: LocalDateTime,
    @ColumnInfo(name = "dateEnd")
    var dateEnd: LocalDateTime,
    @ColumnInfo(name = "isCalentamientoDone")
    var isCalentamientoDone: Boolean,
    @ColumnInfo(name = "isMovArticularDone")
    var isMovArticularDone: Boolean,
    @ColumnInfo(name = "isEstiramientoDone")
    var isEstiramientoDone: Boolean,
    @ColumnInfo(name = "isCompleted")
    var isCompleted: Boolean
) : Parcelable
