package com.mrgranfiesta.ponteenformaguerrero3.data.entity

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
    tableName = "step",
    foreignKeys = [
        ForeignKey(
            entity = EjercicioEntity::class,
            parentColumns = ["idEjercicio"],
            childColumns = ["idEjercicio"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = RutinaEntity::class,
            parentColumns = ["idRutina"],
            childColumns = ["idRutina"],
            onDelete = ForeignKey.CASCADE,

            )
    ],
    indices = [
        Index(value = ["idStep"], unique = true),
        Index(value = ["idRutina"]),
        Index(value = ["idEjercicio"])
    ]
)
data class StepEntity(
    @ColumnInfo(name = "idStep")
    @PrimaryKey(autoGenerate = true)
    var idStep: Long = 0,
    @ColumnInfo(name = "idEjercicio")
    var idEjercicio: Long,
    @ColumnInfo(name = "idRutina")
    var idRutina: Long,
    @ColumnInfo(name = "cantidad")
    var cantidad: Int,
    @ColumnInfo(name = "serie")
    var serie: Int,
    @ColumnInfo(name = "ordenExecution")
    var ordenExecution: Int,
    @ColumnInfo(name = "tipo")
    var tipo: TipoEsfuerzo
) : Parcelable