package com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "stepSnapshot",
    foreignKeys = [
        ForeignKey(
            entity = EjercicioSnapshotEntity::class,
            parentColumns = ["idEjercicioSnapshot"],
            childColumns = ["idEjercicioSnapshot"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RutinaSnapshotEntity::class,
            parentColumns = ["idRutinaSnapshot"],
            childColumns = ["idRutinaSnapshot"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["idStepSnapshot"]),
        Index(value = ["idRutinaSnapshot"]),
        Index(value = ["idEjercicioSnapshot"])
    ]
)
data class StepSnapshotEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idStepSnapshot")
    var idStepSnapshot: Long = 0,
    @ColumnInfo(name = "idStep")
    var idStep: Long,
    @ColumnInfo(name = "idEjercicioSnapshot")
    var idEjercicioSnapshot: Long,
    @ColumnInfo(name = "idRutinaSnapshot")
    var idRutinaSnapshot: Long,
    @ColumnInfo(name = "cantidad")
    var cantidad: Int,
    @ColumnInfo(name = "serie")
    var serie: Int,
    @ColumnInfo(name = "ordenExecution")
    var ordenExecution: Int,
    @ColumnInfo(name = "tipo")
    var tipo: TipoEsfuerzo
) : Parcelable