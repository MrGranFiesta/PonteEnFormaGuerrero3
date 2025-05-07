package com.mrgranfiesta.ponteenformaguerrero3.data.datastore

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.user.UserWithRolDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class UserDataStoreTest {
    private lateinit var userDataStore: UserDataStore
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun onBefore() {
        userDataStore = UserDataStore(context)
    }

    @After
    fun onAfter() = runTest {
        userDataStore.clearData()
    }

    @Test
    fun testSaveUserCorrect() = runTest {
        val user = UserWithRolDto(
            idUser = 1L,
            username = "testUser",
            email = "test@email.com",
            photoUri = "content://image.jpg".toUri(),
            rol = Rol.INIT_DATA_USER
        )

        userDataStore.saveUser(user)

        val result = userDataStore.getUserWithRol()

        assertNotNull(result)
        assertEquals(user.idUser, result?.idUser)
        assertEquals(user.username, result?.username)
        assertEquals(user.email, result?.email)
        assertEquals(user.rol, result?.rol)
    }

    @Test
    fun testUpdateUserCorrect() = runTest {
        val user = UserWithRolDto(
            idUser = 1L,
            username = "testUser",
            email = "test@email.com",
            photoUri = "content://image.jpg".toUri(),
            rol = Rol.INIT_DATA_USER
        )

        userDataStore.saveUser(user)
        userDataStore.updateUsername("testUser2")
        userDataStore.updateRol(Rol.STANDAR_USER)
        userDataStore.updatePhotoUri(Uri.EMPTY)

        val result = userDataStore.getUserWithRol()

        assertNotNull(result)
        assertEquals(user.idUser, result?.idUser)
        assertNotEquals(user.username, result?.username)
        assertEquals(user.email, result?.email)
        assertNotEquals(user.rol, result?.rol)
    }
}