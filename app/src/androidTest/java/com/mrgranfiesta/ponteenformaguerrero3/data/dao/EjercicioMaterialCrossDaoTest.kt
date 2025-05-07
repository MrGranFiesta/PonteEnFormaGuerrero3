package com.mrgranfiesta.ponteenformaguerrero3.data.dao

import com.mrgranfiesta.ponteenformaguerrero3.data.entity.EjercicioMaterialCrossEntity
import com.mrgranfiesta.ponteenformaguerrero3.utilstest.GenerateDataDefault
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class EjercicioMaterialCrossDaoTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var crossDao: EjercicioMaterialCrossDao

    @Inject
    lateinit var generateDataDefault: GenerateDataDefault

    @Before
    fun createDb() {
        hiltRule.inject()
        generateDataDefault()
    }

    @Test
    fun testInsert() = runTest {
        val cross = EjercicioMaterialCrossEntity(
            idEjercicio = 3,
            idMaterial = 2
        )
        crossDao.insert(cross)
    }

    @Test
    fun testInsertList() = runTest {
        val cross = EjercicioMaterialCrossEntity(
            idEjercicio = 3,
            idMaterial = 2
        )
        crossDao.insertList(listOf(cross))
    }

    @Test
    fun testDeleteList() = runTest {
        val cross = EjercicioMaterialCrossEntity(
            idEjercicio = 3,
            idMaterial = 3
        )
        crossDao.deleteList(listOf(cross))
    }
}