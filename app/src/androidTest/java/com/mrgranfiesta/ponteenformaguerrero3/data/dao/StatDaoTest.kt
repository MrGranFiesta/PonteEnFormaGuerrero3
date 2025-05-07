package com.mrgranfiesta.ponteenformaguerrero3.data.dao

import com.mrgranfiesta.ponteenformaguerrero3.data.entity.StatEntity
import com.mrgranfiesta.ponteenformaguerrero3.utilstest.GenerateDataDefault
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime
import javax.inject.Inject

@HiltAndroidTest
class StatDaoTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var statDao: StatDao

    @Inject
    lateinit var generateDataDefault: GenerateDataDefault

    @Before
    fun createDb() {
        hiltRule.inject()
        generateDataDefault()
    }

    @Test
    fun testGetAllStatRutina() = runTest {
        val result = statDao.getAllStatRutina(1).firstOrNull()
        assertTrue(result?.isNotEmpty() == true)
    }

    @Test
    fun testGetByPkStatRutina() = runTest {
        val result = statDao.getByPkStatRutina(1).firstOrNull()
        assertTrue(result != null)
    }

    @Test
    fun testInsert() = runTest {
        val statEntity = StatEntity(
            idStat = 4,
            idRutina = null,
            idRutinaSnapshot = 3,
            dateInit = LocalDateTime.of(2023, 1, 1, 10, 0, 0),
            dateEnd = LocalDateTime.of(2023, 1, 1, 12, 0, 0),
            idUser = 1,
            isCalentamientoDone = true,
            isMovArticularDone = true,
            isEstiramientoDone = true,
            isCompleted = true
        )
        val result = statDao.insert(statEntity)
        assertEquals(4, result)
    }
}