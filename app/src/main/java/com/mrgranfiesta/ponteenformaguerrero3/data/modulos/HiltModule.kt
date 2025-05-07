package com.mrgranfiesta.ponteenformaguerrero3.data.modulos

import android.content.Context
import androidx.room.Room
import com.mrgranfiesta.ponteenformaguerrero3.data.db.DatabaseApp
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.ConfMaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.ConfMaterialRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.ConfMaterialSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.ConfMaterialSnapshotRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioMaterialCrossRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioMaterialCrossRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.EjercicioSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.EjercicioSnapshotRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EntrenamientoRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EntrenamientoRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.MaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.MaterialRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.RutinaRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.RutinaRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.SnapshotCronoRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.SnapshotCronoRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.StatRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.StatRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.StepRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.StepRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.UserRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.UserRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.MaterialSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.MaterialSnapshotRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.StepSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.StepSnapshotRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltModule {
    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context): DatabaseApp {
        return Room.databaseBuilder(
            context,
            DatabaseApp::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun ejercicioRepository(db: DatabaseApp): EjercicioRepository =
        EjercicioRepositoryImpl(db.EjercicioDao())

    @Provides
    @Singleton
    fun rutinaRepository(db: DatabaseApp): RutinaRepository =
        RutinaRepositoryImpl(db.RutinaDao())

    @Provides
    @Singleton
    fun materialRepository(db: DatabaseApp): MaterialRepository =
        MaterialRepositoryImpl(db.MaterialDao())

    @Provides
    @Singleton
    fun stepRepository(db: DatabaseApp): StepRepository =
        StepRepositoryImpl(
            db.StepDao()
        )

    @Provides
    @Singleton
    fun ejercicioMaterialCrossRepository(db: DatabaseApp): EjercicioMaterialCrossRepository =
        EjercicioMaterialCrossRepositoryImpl(db.EjercicioMaterialCrossDao())

    @Provides
    @Singleton
    fun confMaterialRepository(db: DatabaseApp): ConfMaterialRepository =
        ConfMaterialRepositoryImpl(db.ConfMaterialDao())

    @Provides
    @Singleton
    fun entrenamientoRepository(db: DatabaseApp): EntrenamientoRepository =
        EntrenamientoRepositoryImpl(db.EntrenamientoDao())

    @Provides
    @Singleton
    fun snapshotRepository(db: DatabaseApp): SnapshotCronoRepository =
        SnapshotCronoRepositoryImpl(db.SnapshotDao())

    @Provides
    @Singleton
    fun statRepository(db: DatabaseApp): StatRepository =
        StatRepositoryImpl(
            daoStat = db.StatDao()
        )

    @Provides
    @Singleton
    fun stepSnapshotRepository(db: DatabaseApp): StepSnapshotRepository =
        StepSnapshotRepositoryImpl(
            daoStep = db.StepSnapshotDao(),
            daoConfMaterial = db.ConfMaterialSnapshotDao()
        )

    @Provides
    @Singleton
    fun ejercicioSnapshotRepository(
        db: DatabaseApp
    ) : EjercicioSnapshotRepository = EjercicioSnapshotRepositoryImpl(db.EjercicioSnapshotDao())


    @Provides
    @Singleton
    fun userRepository(
        db: DatabaseApp
    ) : UserRepository = UserRepositoryImpl(db.UserDao())

    @Provides
    @Singleton
    fun confMaterialSnapshotRepository(
        db: DatabaseApp
    ) : ConfMaterialSnapshotRepository = ConfMaterialSnapshotRepositoryImpl(db.ConfMaterialSnapshotDao())

    @Provides
    @Singleton
    fun materialSnapshotRepository(
        db: DatabaseApp
    ) : MaterialSnapshotRepository = MaterialSnapshotRepositoryImpl(db.MaterialSnapshotDao())
}