package com.mrgranfiesta.ponteenformaguerrero3.data.dao

import com.mrgranfiesta.ponteenformaguerrero3.data.entity.ConfMaterialEntity
import com.mrgranfiesta.ponteenformaguerrero3.utilstest.GenerateDataDefault
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ConfMaterialDaoTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var confMaterialDao: ConfMaterialDao

    @Inject
    lateinit var generateDataDefault: GenerateDataDefault

    @Before
    fun createDb() {
        hiltRule.inject()
        generateDataDefault()
    }

    @Test
    fun testGetListNoFlowByIdStep(){
        val result = confMaterialDao.getListNoFlowByIdStep(1)
        assertEquals(true, result.isNotEmpty())
    }

    @Test
    fun testInsert() = runTest {
        val confMaterial = ConfMaterialEntity(
            idConfMaterial = 4,
            idMaterial = 1,
            idStep = 1,
            confValue = 1.0
        )
        val result = confMaterialDao.insert(confMaterial)
        assertEquals(4, result)
    }

    @Test
    fun testUpdate() = runTest {
        val confMaterial = ConfMaterialEntity(
            idConfMaterial = 1,
            idMaterial = 1,
            idStep = 1,
            confValue = 10.0
        )
        val result = confMaterialDao.update(confMaterial)
        assertEquals(1, result)
    }
}