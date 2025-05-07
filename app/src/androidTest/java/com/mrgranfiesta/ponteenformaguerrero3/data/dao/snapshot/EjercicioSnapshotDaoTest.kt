package com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot

import com.mrgranfiesta.ponteenformaguerrero3.utilstest.GenerateDataDefault
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import javax.inject.Inject

@HiltAndroidTest
class EjercicioSnapshotDaoTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var ejercicioSnapshotDao: EjercicioSnapshotDao

    @Inject
    lateinit var generateDataDefault: GenerateDataDefault

    @Before
    fun createDb() {
        hiltRule.inject()
        generateDataDefault()
    }

    @Test
    fun testGetEjercicioSnapshotInfoByPkFlow() = runTest {
        val result = ejercicioSnapshotDao.getEjercicioSnapshotInfoByPkFlow(1).firstOrNull()
        assertTrue(result != null)
    }

    @Test
    fun testGetNameImgByRutinaId() = runTest {
        val result = ejercicioSnapshotDao.getNameImgByRutinaId(1)
        assertTrue(result == listOf(""))
    }

    @Test
    fun testIsExistNameImgReturnTrue() = runTest {
        val result = ejercicioSnapshotDao.isExistNameImg("ejer_200_000")
        assertTrue(result)
    }

    @Test
    fun testIsExistNameImgReturnFalse() = runTest {
        val result = ejercicioSnapshotDao.isExistNameImg("NO_EXIST")
        assertFalse(result)
    }

    @Test
    fun testIsNotExistNameImgReturnFalse() = runTest {
        val result = ejercicioSnapshotDao.isNotExistNameImg("ejer_200_000")
        assertFalse(result)
    }

    @Test
    fun testIsNotExistNameImgReturnTrue() = runTest {
        val result = ejercicioSnapshotDao.isNotExistNameImg("NO_EXIST")
        assertTrue(result)
    }

    @Test
    fun testisNotExistNameImgGlobalReturnTrue() = runTest {
        val result = ejercicioSnapshotDao.isNotExistNameImgGlobal("NO_EXIST")
        assertTrue(result)
    }

    @Test
    fun testisNotExistNameImgGlobalReturnFalse() = runTest {
        val result = ejercicioSnapshotDao.isNotExistNameImgGlobal("ejer_200_000")
        assertFalse(result)
    }
}