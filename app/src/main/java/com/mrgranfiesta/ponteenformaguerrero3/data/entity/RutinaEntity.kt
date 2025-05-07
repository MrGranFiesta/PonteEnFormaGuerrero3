package com.mrgranfiesta.ponteenformaguerrero3.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "rutina",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["idUser"],
            childColumns = ["idUser"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index(value = ["idRutina"], unique = true),
        Index(value = ["idUser"])
    ]
)
data class RutinaEntity(
    @ColumnInfo(name = "idRutina")
    @PrimaryKey(autoGenerate = true)
    var idRutina: Long = 0,
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
    @ColumnInfo(name = "calentamiento")
    var calentamiento: AnswerState,
    @ColumnInfo(name = "estiramiento")
    var estiramiento: AnswerState,
    @ColumnInfo(name = "movArticular")
    var movArticular: AnswerState,
    @ColumnInfo(name = "descanso")
    var descanso: Int,
    @ColumnInfo(name = "isPremium")
    var isPremium: Boolean
) : Parcelable