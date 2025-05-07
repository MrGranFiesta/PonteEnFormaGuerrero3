package com.mrgranfiesta.ponteenformaguerrero3.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.VERSION_DATASTORE
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onEmpty

val Context.dataStoreVersion by preferencesDataStore(
    name = VERSION_DATASTORE
)

class VersionDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val USER_VERSION_KEY = intPreferencesKey("version_user")
        val MATERIAL_VERSION_KEY = intPreferencesKey("version_material")
        val EJERCICIO_VERSION_KEY = intPreferencesKey("version_ejercicio")
        val RUTINA_VERSION_KEY = intPreferencesKey("version_rutina")
        val EJERCICIO_MATERIAL_CROSS_VERSION_KEY =
            intPreferencesKey("version_ejercicio_material_cross")
        val STEP_VERSION_KEY = intPreferencesKey("version_step")
        val CONF_MATERIAL_VERSION_KEY = intPreferencesKey("version_conf_material")
    }

    suspend fun updateUserVersion(newVersion: Int) {
        updateVersion(USER_VERSION_KEY, newVersion)
    }

    suspend fun updateMaterialVersion(newVersion: Int) {
        updateVersion(MATERIAL_VERSION_KEY, newVersion)
    }

    suspend fun updateEjercicioVersion(newVersion: Int) {
        updateVersion(EJERCICIO_VERSION_KEY, newVersion)
    }

    suspend fun updateRutinaVersion(newVersion: Int) {
        updateVersion(RUTINA_VERSION_KEY, newVersion)
    }

    suspend fun updateEjercicioMaterialCrossVersion(newVersion: Int) {
        updateVersion(EJERCICIO_MATERIAL_CROSS_VERSION_KEY, newVersion)
    }

    suspend fun updateStepVersion(newVersion: Int) {
        updateVersion(STEP_VERSION_KEY, newVersion)
    }

    suspend fun updateConfMaterialVersion(newVersion: Int) {
        updateVersion(CONF_MATERIAL_VERSION_KEY, newVersion)
    }

    private suspend fun updateVersion(key: Preferences.Key<Int>, newVersion: Int) {
        context.dataStoreVersion.edit { preferences ->
            preferences[key] = newVersion
        }
    }

    suspend fun getUserVersion() = readVersion(USER_VERSION_KEY)
    suspend fun getMaterialVersion() = readVersion(MATERIAL_VERSION_KEY)
    suspend fun getEjercicioVersion() = readVersion(EJERCICIO_VERSION_KEY)
    suspend fun getRutinaVersion() = readVersion(RUTINA_VERSION_KEY)
    suspend fun getEjercicioMaterialCrossVersion() = readVersion(EJERCICIO_MATERIAL_CROSS_VERSION_KEY)
    suspend fun getStepVersion() = readVersion(STEP_VERSION_KEY)
    suspend fun getConfMaterialVersion() = readVersion(CONF_MATERIAL_VERSION_KEY)

    private suspend fun readVersion(
        key: Preferences.Key<Int>
    ): Int {
        val preferences = context.dataStoreVersion.data
            .onEmpty { emit(emptyPreferences()) }
            .catch { emit(emptyPreferences()) }
            .firstOrNull() ?: emptyPreferences()
        return preferences[key] ?: 0
    }

    suspend fun clearData() {
        context.dataStoreVersion.edit { preferences ->
            preferences.clear()
        }
    }
}