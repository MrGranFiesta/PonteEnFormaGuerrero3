package com.mrgranfiesta.ponteenformaguerrero3.data.dao

import com.mrgranfiesta.ponteenformaguerrero3.data.entity.EjercicioEntity
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.utilstest.GenerateDataDefault
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class EjercicioDaoTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var ejercicioDao: EjercicioDao

    @Inject
    lateinit var generateDataDefault: GenerateDataDefault

    @Before
    fun createDb() {
        hiltRule.inject()
        generateDataDefault()
    }

    @Test
    fun testGetEjercicioListDB() = runTest {
        val result = ejercicioDao.getEjercicioListDB(1).first()
        assertEquals(true, result.isNotEmpty())
    }

    @Test
    fun testGetEjercicioNameAndPhoto() = runTest {
        val result = ejercicioDao.getEjercicioNameAndPhoto(1).firstOrNull()
        assertTrue(result != null)
    }

    @Test
    fun testGetEjercicioInfo() = runTest {
        val result = ejercicioDao.getEjercicioInfo(1).firstOrNull()
        assertTrue(result != null)
    }

    @Test
    fun testGetListByPkList() = runTest {
        val result = ejercicioDao.getListByPkList(listOf(1)).firstOrNull()
        assertTrue(result != null)
    }

    @Test
    fun testGetListNameImgByIdUser() = runTest {
        val result = ejercicioDao.getListNameImgByIdUser(1)
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun testInsert() = runTest {
        val ejercicioEntity = EjercicioEntity(
            idEjercicio = 4,
            idUser = 1,
            nombre = "Abdominal Media",
            isSimetria = true,
            musculoSet = setOf(Musculo.ABDOMINALES),
            nivel = Nivel.MEDIO,
            descripcion = "Ejemplo 4",
            nameImg = ""
        )
        val result = ejercicioDao.insert(ejercicioEntity)
        assertEquals(4, result)
    }

    @Test
    fun testUpdate() = runTest {
        val ejercicioEntity = EjercicioEntity(
            idEjercicio = 1,
            idUser = 1,
            nombre = "Flexiones Media",
            isSimetria = true,
            musculoSet = setOf(Musculo.HOMBRO, Musculo.BICEPS),
            nivel = Nivel.MEDIO,
            descripcion = "Ejemplo 1",
            nameImg = ""
        )
        val result = ejercicioDao.update(ejercicioEntity)
        assertEquals(1, result)
    }

    @Test
    fun testDelete() = runTest {
        val ejercicioEntity = EjercicioEntity(
            idEjercicio = 1,
            idUser = 1,
            nombre = "Flexiones",
            isSimetria = true,
            musculoSet = setOf(Musculo.HOMBRO, Musculo.BICEPS),
            nivel = Nivel.FACIL,
            descripcion = "Ejemplo 1",
            nameImg = ""
        )
        val result = ejercicioDao.delete(ejercicioEntity)
        assertEquals(1, result)
    }
}