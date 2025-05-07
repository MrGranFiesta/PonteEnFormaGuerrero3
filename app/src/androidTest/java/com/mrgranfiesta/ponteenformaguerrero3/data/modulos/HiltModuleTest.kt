package com.mrgranfiesta.ponteenformaguerrero3.data.modulos

import android.content.Context
import androidx.room.Room
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
import com.mrgranfiesta.ponteenformaguerrero3.data.db.DatabaseApp
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.ConfMaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.ConfMaterialRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioMaterialCrossRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioMaterialCrossRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EjercicioRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EntrenamientoRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.EntrenamientoRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.MaterialRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.MaterialRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.RutinaRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.RutinaRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.StatRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.StatRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.StepRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.StepRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.UserRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.UserRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.ConfMaterialSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.ConfMaterialSnapshotRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.EjercicioSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.EjercicioSnapshotRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.MaterialSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.MaterialSnapshotRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.SnapshotCronoRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.SnapshotCronoRepositoryImpl
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.StepSnapshotRepository
import com.mrgranfiesta.ponteenformaguerrero3.data.repository.snapshot.StepSnapshotRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [HiltModule::class]
)
@Module
class HiltModuleTest {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): DatabaseApp {
        return Room.inMemoryDatabaseBuilder(
            context = context,
            klass = DatabaseApp::class.java
        ).allowMainThreadQueries().build()
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
    ): EjercicioSnapshotRepository = EjercicioSnapshotRepositoryImpl(db.EjercicioSnapshotDao())


    @Provides
    @Singleton
    fun userRepository(
        db: DatabaseApp
    ): UserRepository = UserRepositoryImpl(db.UserDao())

    @Provides
    @Singleton
    fun confMaterialSnapshotRepository(
        db: DatabaseApp
    ): ConfMaterialSnapshotRepository =
        ConfMaterialSnapshotRepositoryImpl(db.ConfMaterialSnapshotDao())

    @Provides
    @Singleton
    fun materialSnapshotRepository(
        db: DatabaseApp
    ): MaterialSnapshotRepository = MaterialSnapshotRepositoryImpl(db.MaterialSnapshotDao())

    @Singleton
    @Provides
    fun provideUserDao(database: DatabaseApp): UserDao {
        return database.UserDao()
    }

    @Singleton
    @Provides
    fun provideConfMaterialDao(database: DatabaseApp): ConfMaterialDao {
        return database.ConfMaterialDao()
    }

    @Singleton
    @Provides
    fun provideEjercicioDao(database: DatabaseApp): EjercicioDao {
        return database.EjercicioDao()
    }

    @Singleton
    @Provides
    fun provideEjercicioMaterialCrossDao(database: DatabaseApp): EjercicioMaterialCrossDao {
        return database.EjercicioMaterialCrossDao()
    }

    @Singleton
    @Provides
    fun provideEntrenamientoDao(database: DatabaseApp): EntrenamientoDao {
        return database.EntrenamientoDao()
    }

    @Singleton
    @Provides
    fun provideMaterialDao(database: DatabaseApp): MaterialDao {
        return database.MaterialDao()
    }

    @Singleton
    @Provides
    fun provideRutinaDao(database: DatabaseApp): RutinaDao {
        return database.RutinaDao()
    }

    @Singleton
    @Provides
    fun provideStatDao(database: DatabaseApp): StatDao {
        return database.StatDao()
    }

    @Singleton
    @Provides
    fun provideStepDao(database: DatabaseApp): StepDao {
        return database.StepDao()
    }

    @Singleton
    @Provides
    fun provideConfMaterialSnapshotDao(database: DatabaseApp): ConfMaterialSnapshotDao {
        return database.ConfMaterialSnapshotDao()
    }

    @Singleton
    @Provides
    fun provideEjercicioSnapshotDao(database: DatabaseApp): EjercicioSnapshotDao {
        return database.EjercicioSnapshotDao()
    }

    @Singleton
    @Provides
    fun provideMaterialSnapshotDao(database: DatabaseApp): MaterialSnapshotDao {
        return database.MaterialSnapshotDao()
    }

    @Singleton
    @Provides
    fun provideSnapshotDao(database: DatabaseApp): SnapshotDao {
        return database.SnapshotDao()
    }

    @Singleton
    @Provides
    fun provideStepSnapshotDao(database: DatabaseApp): StepSnapshotDao {
        return database.StepSnapshotDao()
    }
}