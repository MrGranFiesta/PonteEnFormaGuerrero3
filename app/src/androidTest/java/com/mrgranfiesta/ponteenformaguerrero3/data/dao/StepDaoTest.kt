package com.mrgranfiesta.ponteenformaguerrero3.data.dao

import com.mrgranfiesta.ponteenformaguerrero3.data.entity.StepEntity
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.utilstest.GenerateDataDefault
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class StepDaoTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var stepDao: StepDao

    @Inject
    lateinit var generateDataDefault: GenerateDataDefault

    @Before
    fun createDb() {
        hiltRule.inject()
        generateDataDefault()
    }

    @Test
    fun testGetStepInfoWithConfMaterialByRutinaId() = runTest {
        val result = stepDao.getStepInfoWithConfMaterialByRutinaId(1).firstOrNull()
        assertTrue(result?.isNotEmpty() == true)
    }

    @Test
    fun testGetStepFormWithConfMaterialByRutinaId() = runTest {
        val result = stepDao.getStepFormWithConfMaterialByRutinaId(1).firstOrNull()
        assertTrue(result?.isNotEmpty() == true)
    }

    @Test
    fun testInsert() = runTest {
        val stepEntity = StepEntity(
            idStep = 6,
            idEjercicio = 1,
            idRutina = 1,
            cantidad = 5,
            serie = 1,
            ordenExecution = 2,
            tipo = TipoEsfuerzo.REPETICION
        )
        val result = stepDao.insert(stepEntity)
        assertEquals(6, result)
    }

    @Test
    fun testUpdate() = runTest {
        val stepEntity = StepEntity(
            idStep = 1,
            idEjercicio = 1,
            idRutina = 1,
            cantidad = 7,
            serie = 6,
            ordenExecution = 10,
            tipo = TipoEsfuerzo.REPETICION
        )
        val result = stepDao.update(stepEntity)
        assertEquals(1, result)
    }

    @Test
    fun testDelete() = runTest {
        val stepEntity = StepEntity(
            idStep = 1,
            idEjercicio = 1,
            idRutina = 1,
            cantidad = 5,
            serie = 1,
            ordenExecution = 1,
            tipo = TipoEsfuerzo.REPETICION
        )
        val result = stepDao.delete(stepEntity)
        assertEquals(1, result)
    }
}