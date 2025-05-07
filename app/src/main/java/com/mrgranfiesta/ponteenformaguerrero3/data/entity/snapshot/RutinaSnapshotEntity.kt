package com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.UserEntity
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "rutinaSnapshot",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["idUser"],
            childColumns = ["idUser"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index(value = ["idRutinaSnapshot"]),
        Index(value = ["idUser"])
    ]
)
data class RutinaSnapshotEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idRutinaSnapshot")
    var idRutinaSnapshot: Long = 0,
    @ColumnInfo(name = "idRutina")
    var idRutina: Long,
    @ColumnInfo(name = "idUser")
    var idUser: Long,
    @ColumnInfo(name = "nombre")
    var nombre: String,
    @ColumnInfo(name = "descripcion")
    var descripcion: String,
    @ColumnInfo(name = "musculoSet")
    var musculoSet: Set<Musculo>,
    @ColumnInfo(name = "nivel")
    var nivel: Nivel,
    @ColumnInfo("isPremium")
    var isPremium : Boolean,
    @ColumnInfo(name = "descanso")
    var descanso: Int
) : Parcelable