package com.mrgranfiesta.ponteenformaguerrero3.data.dao

import com.mrgranfiesta.ponteenformaguerrero3.data.entity.MaterialEntity
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
class MaterialDaoTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var materialDao: MaterialDao

    @Inject
    lateinit var generateDataDefault: GenerateDataDefault

    @Before
    fun createDb() {
        hiltRule.inject()
        generateDataDefault()
    }

    @Test
    fun testGetRowMaterialAll() = runTest {
        val result = materialDao.getRowMaterialAll(1).firstOrNull()
        assertTrue(result?.isNotEmpty() == true)
    }

    @Test
    fun testGetRowMaterialByPkList() = runTest {
        val result = materialDao.getRowMaterialByPkList(listOf(1)).firstOrNull()
        assertTrue(result?.isNotEmpty() == true)
    }

    @Test
    fun testGetRowMaterialByIdRutina() = runTest {
        val result = materialDao.getRowMaterialByIdRutina(1).firstOrNull()
        assertTrue(result?.isNotEmpty() == true)
    }

    @Test
    fun testGetListByPkListNoFlow() = runTest {
        val result = materialDao.getListByPkListNoFlow(listOf(1))
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun testGetMaterialInfoByPk() = runTest {
        val result = materialDao.getMaterialInfoByPk(1).firstOrNull()
        assertTrue(result != null)
    }

    @Test
    fun testGetEjercicioInfoMaterial() = runTest {
        val result = materialDao.getEjercicioInfoMaterial(1).firstOrNull()
        assertTrue(result?.isNotEmpty() == true)
    }

    @Test
    fun testGetListNoFlowByIdEjercicio() = runTest {
        val result = materialDao.getListNoFlowByIdEjercicio(1)
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun testGetListNameImgByIdUser() = runTest {
        val result = materialDao.getListNameImgByIdUser(1)
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun testInsert() = runTest {
        val materialEntity = MaterialEntity(
            idMaterial = 4,
            idUser = 1,
            isMaterialWeight = false,
            nombre = "Disco",
            descripcion = "Ejemplo 4",
            nameImg = ""
        )
        val result = materialDao.insert(materialEntity)
        assertEquals(4, result)
    }

    @Test
    fun testUpdate() = runTest {
        val materialEntity = MaterialEntity(
            idMaterial = 1,
            idUser = 1,
            isMaterialWeight = false,
            nombre = "Barra dominadas",
            descripcion = "Ejemplo 10",
            nameImg = ""
        )
        val result = materialDao.update(materialEntity)
        assertEquals(1, result)
    }

    @Test
    fun testDelete() = runTest {
        val materialEntity = MaterialEntity(
            idMaterial = 1,
            idUser = 1,
            nombre = "Banco",
            isMaterialWeight = false,
            descripcion = "Ejemplo 1",
            nameImg = ""
        )
        val result = materialDao.delete(materialEntity)
        assertEquals(1, result)
    }
}