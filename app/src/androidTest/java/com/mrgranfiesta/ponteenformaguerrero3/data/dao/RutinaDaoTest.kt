package com.mrgranfiesta.ponteenformaguerrero3.data.dao

import com.mrgranfiesta.ponteenformaguerrero3.data.entity.RutinaEntity
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
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
class RutinaDaoTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var rutinaDao: RutinaDao

    @Inject
    lateinit var generateDataDefault: GenerateDataDefault

    @Before
    fun createDb() {
        hiltRule.inject()
        generateDataDefault()
    }

    @Test
    fun testGetRowRutinaAll() = runTest {
        val result = rutinaDao.getRowRutinaAll(1).firstOrNull()
        assertTrue(result?.isNotEmpty() == true)
    }

    @Test
    fun testGetRutinaInfoDBByPkFlow() = runTest {
        val result = rutinaDao.getRutinaInfoDBByPkFlow(1).firstOrNull()
        assertTrue(result != null)
    }

    @Test
    fun test() = runTest {
        val result = rutinaDao.getRutinaInfoDBByPkFlow(1).firstOrNull()
        assertTrue(result != null)
    }

    @Test
    fun testInsert() = runTest {
        val rutinaEntity = RutinaEntity(
            idRutina = 4,
            idUser = 1,
            nombre = "Brazos fuertes 3",
            musculoSet = setOf(Musculo.HOMBRO, Musculo.BICEPS),
            nivel = Nivel.FACIL,
            calentamiento = AnswerState.ASK_LATER,
            movArticular = AnswerState.YES,
            estiramiento = AnswerState.NO,
            descanso = 20,
            descripcion = "Ejemplo 1",
            isPremium = true
        )
        val result = rutinaDao.insert(rutinaEntity)
        assertEquals(4, result)
    }

    @Test
    fun testUpdate() = runTest {
        val rutinaEntity = RutinaEntity(
            idRutina = 1,
            idUser = 1,
            nombre = "Brazos fuertes",
            musculoSet = setOf(Musculo.HOMBRO, Musculo.BICEPS),
            nivel = Nivel.FACIL,
            calentamiento = AnswerState.YES,
            movArticular = AnswerState.NO,
            estiramiento = AnswerState.ASK_LATER,
            descanso = 1,
            descripcion = "Ejemplo 1",
            isPremium = true
        )
        val result = rutinaDao.update(rutinaEntity)
        assertEquals(1, result)
    }

    @Test
    fun testDelete() = runTest {
        val rutinaEntity = RutinaEntity(
            idRutina = 1,
            idUser = 1,
            nombre = "Brazos fuertes2",
            musculoSet = setOf(Musculo.HOMBRO, Musculo.BICEPS),
            nivel = Nivel.FACIL,
            calentamiento = AnswerState.YES,
            movArticular = AnswerState.NO,
            estiramiento = AnswerState.ASK_LATER,
            descanso = 30,
            descripcion = "Ejemplo 1",
            isPremium = true
        )
        val result = rutinaDao.delete(rutinaEntity)
        assertEquals(1, result)
    }
}