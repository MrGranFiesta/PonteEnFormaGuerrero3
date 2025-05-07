package com.mrgranfiesta.ponteenformaguerrero3.data.datastore

import android.content.Context
import android.net.Uri
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DIR_PROFILE_USER_IMG
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.USER_DATASTORE
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.user.UserWithRolDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.UriUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onEmpty

val Context.dataStoreUser by preferencesDataStore(
    name = USER_DATASTORE
)

class UserDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val USER_ID_KEY = longPreferencesKey("user_id")
        val ROL_ID_KEY = intPreferencesKey("rol_id")
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val USER_NAME_IMG_KEY = stringPreferencesKey("user_name_img")
    }

    suspend fun saveUser(user: UserWithRolDto) {
        context.dataStoreUser.edit { preferences ->
            preferences[USER_ID_KEY] = user.idUser
            preferences[ROL_ID_KEY] = user.rol.id
            preferences[USER_NAME_KEY] = user.username
            preferences[USER_EMAIL_KEY] = user.email
            preferences[USER_NAME_IMG_KEY] = UriUtils.getNameByUri(user.photoUri)
        }
    }

    suspend fun updatePhotoUri(photoUri: Uri) {
        context.dataStoreUser.edit { preferences ->
            preferences[USER_NAME_IMG_KEY] = UriUtils.getNameByUri(photoUri)
        }
    }

    suspend fun updateUsername(username: String) {
        context.dataStoreUser.edit { preferences ->
            preferences[USER_NAME_KEY] = username
        }
    }

    suspend fun updateRol(rol: Rol) {
        context.dataStoreUser.edit { preferences ->
            preferences[ROL_ID_KEY] = rol.id
        }
    }

    suspend fun cleanUser() {
        context.dataStoreUser.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun getUserWithRol(): UserWithRolDto? {
        val idUser = readData(USER_ID_KEY)
        val idRol = readData(ROL_ID_KEY)
        val username = readData(USER_NAME_KEY)
        val email = readData(USER_EMAIL_KEY)
        val nameImg = readData(USER_NAME_IMG_KEY)

        if (idUser == null
            || idRol == null
            || username.isNullOrBlank()
            || email.isNullOrBlank()
            || nameImg == null
        ) {
            return null
        }
        val rol = Rol.entries.find { it.id == idRol } ?: return null
        return UserWithRolDto(
            idUser = idUser,
            username = username,
            email = email,
            photoUri = UriUtils.getUriResource(DIR_PROFILE_USER_IMG, nameImg),
            rol = rol
        )
    }

    private suspend fun <T> readData(
        key: Preferences.Key<T>
    ): T? {
        val preferences = context.dataStoreUser.data
            .onEmpty { emit(emptyPreferences()) }
            .catch { emit(emptyPreferences()) }
            .firstOrNull() ?: emptyPreferences()
        return preferences[key]
    }

    suspend fun clearData() {
        context.dataStoreUser.edit { preferences ->
            preferences.clear()
        }
    }
}