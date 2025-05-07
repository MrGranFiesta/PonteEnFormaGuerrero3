package com.mrgranfiesta.ponteenformaguerrero3.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mrgranfiesta.ponteenformaguerrero3.data.dao.ConfMaterialDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dao.EjercicioDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dao.EjercicioMaterialCrossDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dao.EntrenamientoDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dao.MaterialDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dao.RutinaDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dao.StatDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dao.StepDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dao.UserDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot.ConfMaterialSnapshotDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot.EjercicioSnapshotDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot.MaterialSnapshotDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot.SnapshotDao
import com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot.StepSnapshotDao
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.ConfMaterialEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.EjercicioEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.EjercicioMaterialCrossEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.MaterialEntity
//import com.mrgranfiesta.ponteenformaguerrero3.data.entity.RolEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.RutinaEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.StatEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.StepEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.UserEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.ConfMaterialSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.EjercicioMaterialCrossSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.EjercicioSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.MaterialSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.RutinaSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.StepSnapshotEntity

@TypeConverters(Converter::class)
@Database(
    entities = [
        UserEntity::class,
        EjercicioEntity::class,
        StepEntity::class,
        RutinaEntity::class,
        MaterialEntity::class,
        EjercicioMaterialCrossEntity::class,
        ConfMaterialEntity::class,
        RutinaSnapshotEntity::class,
        EjercicioSnapshotEntity::class,
        StepSnapshotEntity::class,
        MaterialSnapshotEntity::class,
        EjercicioMaterialCrossSnapshotEntity::class,
        ConfMaterialSnapshotEntity::class,
        StatEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class DatabaseApp : RoomDatabase() {
    abstract fun UserDao(): UserDao
    abstract fun ConfMaterialDao(): ConfMaterialDao
    abstract fun ConfMaterialSnapshotDao(): ConfMaterialSnapshotDao
    abstract fun EjercicioDao(): EjercicioDao
    abstract fun EjercicioMaterialCrossDao(): EjercicioMaterialCrossDao
    abstract fun EjercicioSnapshotDao(): EjercicioSnapshotDao
    abstract fun EntrenamientoDao(): EntrenamientoDao
    abstract fun MaterialDao(): MaterialDao
    abstract fun MaterialSnapshotDao(): MaterialSnapshotDao
    abstract fun RutinaDao(): RutinaDao
    abstract fun SnapshotDao(): SnapshotDao
    abstract fun StatDao(): StatDao
    abstract fun StepDao(): StepDao
    abstract fun StepSnapshotDao(): StepSnapshotDao
}