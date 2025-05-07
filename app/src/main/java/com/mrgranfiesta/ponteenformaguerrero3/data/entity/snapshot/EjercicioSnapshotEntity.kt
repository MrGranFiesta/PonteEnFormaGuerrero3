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
    tableName = "ejercicioSnapshot",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["idUser"],
            childColumns = ["idUser"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index(value = ["idEjercicioSnapshot"]),
        Index(value = ["idUser"])
    ]
)
data class EjercicioSnapshotEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idEjercicioSnapshot")
    var idEjercicioSnapshot: Long = 0,
    @ColumnInfo(name = "idEjercicio")
    var idEjercicio: Long,
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