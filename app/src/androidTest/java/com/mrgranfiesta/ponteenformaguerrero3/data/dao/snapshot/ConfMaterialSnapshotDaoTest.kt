package com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot

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
class ConfMaterialSnapshotDaoTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var confMaterialSnapshotDao: ConfMaterialSnapshotDao

    @Inject
    lateinit var generateDataDefault: GenerateDataDefault

    @Before
    fun createDb() {
        hiltRule.inject()
        generateDataDefault()
    }

    @Test
    fun testGetMaterialWithConfListByIdStep() = runTest {
        val result = confMaterialSnapshotDao.getMaterialWithConfListByIdStep(listOf(1))
        assertEquals(true, result.isNotEmpty())
    }

    @Test
    fun testGetMaterialWithConfByIdEjercicio() = runTest {
        val result = confMaterialSnapshotDao.getMaterialWithConfByIdEjercicio(1).firstOrNull()
        assertTrue(result?.isNotEmpty() == true)
    }
}