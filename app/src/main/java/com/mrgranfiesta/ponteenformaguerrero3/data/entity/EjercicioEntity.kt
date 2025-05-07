package com.mrgranfiesta.ponteenformaguerrero3.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(
    tableName = "ejercicio",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["idUser"],
            childColumns = ["idUser"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index(value = ["idEjercicio"], unique = true),
        Index(value = ["idUser"])
    ]
)
data class EjercicioEntity(
    @ColumnInfo(name = "idEjercicio")
    @PrimaryKey(autoGenerate = true)
    var idEjercicio: Long = 0,
    @ColumnInfo(name = "idUser")
    var idUser: Long,
    @ColumnInfo(name = "nombre")
    var nombre: String,
    @ColumnInfo(name = "isSimetria")
    var isSimetria: Boolean,
    @ColumnInfo(name = "musculoSet")
    var musculoSet: Set<Musculo>,
    @ColumnInfo(name = "nivel")
    var nivel: Nivel,
    @ColumnInfo(name = "descripcion")
    var descripcion: String,
    @ColumnInfo(name = "nameImg")
    var nameImg: String
) : Parcelable