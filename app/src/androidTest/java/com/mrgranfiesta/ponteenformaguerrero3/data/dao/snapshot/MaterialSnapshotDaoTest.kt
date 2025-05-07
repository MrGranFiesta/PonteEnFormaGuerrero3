package com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot

import com.mrgranfiesta.ponteenformaguerrero3.utilstest.GenerateDataDefault
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MaterialSnapshotDaoTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var materialSnapshotDao: MaterialSnapshotDao

    @Inject
    lateinit var generateDataDefault: GenerateDataDefault

    @Before
    fun createDb() {
        hiltRule.inject()
        generateDataDefault()
    }

    @Test
    fun testIsExistNameImgReturnTrue() = runTest {
        val result = materialSnapshotDao.isExistNameImg("mat2_000_000")
        assertTrue(result)
    }

    @Test
    fun testIsExistNameImgReturnFalse() = runTest {
        val result = materialSnapshotDao.isExistNameImg("NO_EXISTS")
        assertFalse(result)
    }

    @Test
    fun testIsNotExistNameImgReturnTrue() = runTest {
        val result = materialSnapshotDao.isExistNameImg("mat2_000_000")
        assertTrue(result)
    }

    @Test
    fun testIsNotExistNameImgReturnFalse() = runTest {
        val result = materialSnapshotDao.isExistNameImg("NO_EXISTS")
        assertFalse(result)
    }

    @Test
    fun testGetNameImgByIdRutina() = runTest {
        val result = materialSnapshotDao.getNameImgByIdRutina(2)
        assertEquals(listOf("mat2_000_000"), result)
    }

    @Test
    fun testIsNotExistNameImgGlobalReturnTrue() = runTest {
        val result = materialSnapshotDao.isNotExistNameImgGlobal("NO_EXISTS")
        assertTrue(result)
    }

    @Test
    fun testIsNotExistNameImgGlobalReturnFalse() = runTest {
        val result = materialSnapshotDao.isNotExistNameImgGlobal("mat2_000_000")
        assertFalse(result)
    }
}