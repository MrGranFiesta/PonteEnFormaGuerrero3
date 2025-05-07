package com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot

import com.mrgranfiesta.ponteenformaguerrero3.data.dtodb.crono.PairIdDB
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.ConfMaterialSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.EjercicioMaterialCrossSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.EjercicioSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.MaterialSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.RutinaSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.data.entity.snapshot.StepSnapshotEntity
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.utilstest.GenerateDataDefault
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class SnapshotDaoTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var snapshotDao: SnapshotDao

    @Inject
    lateinit var generateDataDefault: GenerateDataDefault

    @Before
    fun createDb() {
        hiltRule.inject()
        generateDataDefault()
    }

    @Test
    fun testInsertRutinaSnapshot() = runTest {
        val rutinaSnapshot = RutinaSnapshotEntity(
            idRutina = 4,
            idUser = 1,
            nombre = "Flexiones Dificiles",
            musculoSet = setOf(Musculo.HOMBRO, Musculo.BICEPS),
            nivel = Nivel.DIFICIL,
            descanso = 30,
            descripcion = "Ejemplo 4",
            isPremium = true
        )

        val result = snapshotDao.insertRutinaSnapshot(rutinaSnapshot)
        Assert.assertEquals(4, result)
    }

    @Test
    fun testInsertEjercicioListSnapshot() = runTest {
        val ejercicioSnapshotEntity = EjercicioSnapshotEntity(
            idEjercicioSnapshot = 4,
            idEjercicio = 1,
            idUser = 1,
            nombre = "Flexiones",
            isSimetria = true,
            musculoSet = setOf(Musculo.HOMBRO, Musculo.BICEPS),
            nivel = Nivel.FACIL,
            descripcion = "Ejemplo 1",
            nameImg = ""
        )

        val result = snapshotDao.insertEjercicioListSnapshot(listOf(ejercicioSnapshotEntity))
        Assert.assertEquals(listOf(4L), result)
    }

    @Test
    fun testGetEjercicioListPairIdSnpashotByPk() = runTest {
        val result = snapshotDao.getEjercicioListPairIdSnpashotByPk(listOf(1))
        Assert.assertEquals(listOf(PairIdDB(1L, 1L)), result)
    }

    @Test
    fun testInsertStepSnapshot() = runTest {
        val stepSnapshotEntity = StepSnapshotEntity(
            idStepSnapshot = 6,
            idStep = 1,
            idEjercicioSnapshot = 1,
            idRutinaSnapshot = 1,
            cantidad = 5,
            serie = 1,
            ordenExecution = 1,
            tipo = TipoEsfuerzo.REPETICION
        )

        val result = snapshotDao.insertStepSnapshot(listOf(stepSnapshotEntity))
        Assert.assertEquals(listOf(6L), result)
    }

    @Test
    fun testGetStepListPairIdSnpashotByPk() = runTest {
        val result = snapshotDao.getStepListPairIdSnpashotByPk(listOf(1))
        Assert.assertEquals(listOf(PairIdDB(1L, 1L)), result)
    }

    @Test
    fun testInsertMaterialSnapshot() = runTest {
        val materialSnapshotEntity = MaterialSnapshotEntity(
            idMaterialSnapshot = 4,
            idMaterial = 1,
            idUser = 1,
            nombre = "Mancuerna",
            isMaterialWeight = true,
            descripcion = "Ejemplo 1",
            nameImg = ""
        )
        val result = snapshotDao.insertMaterialSnapshot(listOf(materialSnapshotEntity))
        Assert.assertEquals(listOf(4L), result)
    }

    @Test
    fun testGetMaterialListPairIdSnpashotByPk() = runTest {
        val result = snapshotDao.getMaterialListPairIdSnpashotByPk(listOf(1))
        Assert.assertEquals(listOf(PairIdDB(1L, 1L)), result)
    }

    @Test
    fun testInsertEjercicioMaterialCrossSnapshot() = runTest {
        val crossEntity = EjercicioMaterialCrossSnapshotEntity(
            idEjercicioSnapshot = 3,
            idMaterialSnapshot = 3
        )
        val result = snapshotDao.insertEjercicioMaterialCrossSnapshot(listOf(crossEntity))
        Assert.assertEquals(listOf(3L), result)
    }

    @Test
    fun testInsertConfMaterialListSnapshot() = runTest {
        val confMaterialSnapshotEntity = ConfMaterialSnapshotEntity(
            idConfMaterialSnapshot = 4,
            idConfMaterial = 1,
            idMaterialSnapshot = 1,
            idStepSnapshot = 4,
            confValue = 1.0
        )
        val result = snapshotDao.insertConfMaterialListSnapshot(listOf(confMaterialSnapshotEntity))
        Assert.assertEquals(listOf(4L), result)
    }

    @Test
    fun testDeleteRutinaByIdPk() = runTest {
        val result = snapshotDao.deleteRutinaByIdPk(1)
        Assert.assertEquals(1, result)
    }

    @Test
    fun testDeleteEjercicioByIdRutina() = runTest {
        val result = snapshotDao.deleteEjercicioByIdRutina(1)
        Assert.assertEquals(1, result)
    }

    @Test
    fun testDeleteMaterialByIdRutina() = runTest {
        val result = snapshotDao.deleteMaterialByIdRutina(1)
        Assert.assertEquals(1, result)
    }
}