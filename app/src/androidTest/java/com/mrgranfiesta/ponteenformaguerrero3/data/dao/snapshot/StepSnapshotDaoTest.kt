package com.mrgranfiesta.ponteenformaguerrero3.data.dao.snapshot

import com.mrgranfiesta.ponteenformaguerrero3.utilstest.GenerateDataDefault
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertTrue
import javax.inject.Inject

@HiltAndroidTest
class StepSnapshotDaoTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var stepSnapshotDao: StepSnapshotDao

    @Inject
    lateinit var generateDataDefault: GenerateDataDefault

    @Before
    fun createDb() {
        hiltRule.inject()
        generateDataDefault()
    }

    @Test
    fun testGetStepSnapshotInfoByIdStat() = runTest {
        val result = stepSnapshotDao.getStepSnapshotInfoByIdStat(1)
        assertTrue(result.isNotEmpty())
    }
}