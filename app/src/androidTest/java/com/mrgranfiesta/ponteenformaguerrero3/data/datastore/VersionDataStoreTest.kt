package com.mrgranfiesta.ponteenformaguerrero3.data.datastore

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class VersionDataStoreTest {
    private lateinit var versionDataStore: VersionDataStore
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun onBefore() {
        versionDataStore = VersionDataStore(context)
    }

    @After
    fun onAfter() = runTest {
        versionDataStore.clearData()
    }


    @Test
    fun testCurrenVersionUpdate() = runTest {
        versionDataStore.updateUserVersion(1)
        versionDataStore.updateMaterialVersion(1)
        versionDataStore.updateEjercicioVersion(1)
        versionDataStore.updateRutinaVersion(1)
        versionDataStore.updateEjercicioMaterialCrossVersion(1)
        versionDataStore.updateStepVersion(1)
        versionDataStore.updateConfMaterialVersion(1)

        val userVersion = versionDataStore.getUserVersion()
        val materialVersion = versionDataStore.getMaterialVersion()
        val ejercicioVersion = versionDataStore.getEjercicioVersion()
        val rutinaVersion = versionDataStore.getRutinaVersion()
        val crossVersion = versionDataStore.getEjercicioMaterialCrossVersion()
        val stepVersion = versionDataStore.getStepVersion()
        val confMaterialVersion = versionDataStore.getConfMaterialVersion()

        assertEquals(1, userVersion)
        assertEquals(1, materialVersion)
        assertEquals(1, ejercicioVersion)
        assertEquals(1, rutinaVersion)
        assertEquals(1, crossVersion)
        assertEquals(1, stepVersion)
        assertEquals(1, confMaterialVersion)
    }
}