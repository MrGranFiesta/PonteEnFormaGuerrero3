package com.mrgranfiesta.ponteenformaguerrero3.data.dao

import com.mrgranfiesta.ponteenformaguerrero3.data.entity.UserEntity
import com.mrgranfiesta.ponteenformaguerrero3.utilstest.GenerateDataDefault
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class UserDaoTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var userDao: UserDao

    @Inject
    lateinit var generateDataDefault : GenerateDataDefault

    @Before
    fun createDb() {
        hiltRule.inject()
        generateDataDefault()
    }

    @Test
    @Throws(Exception::class)
    fun testGetPhotoUri() = runTest {
        assertEquals("profile_1234", userDao.getPhotoUri(1))
    }

    @Test
    @Throws(Exception::class)
    fun testIsEmailExists() = runTest {
        assertEquals(true, userDao.isEmailExists("guest@gmail.com"))
    }

    @Test
    @Throws(Exception::class)
    fun testIsNotCorrectPassword() = runTest {
        assertEquals(
            false, userDao.isNotCorrectPassword(
                idUser = 1,
                password = "1234"
            )
        )
    }

    @Test
    @Throws(Exception::class)
    fun loginUserLoginUser() = runTest {
        assertEquals(
            true, userDao.loginUser(
                email = "guest@gmail.com",
                password = "1234"
            ) != null
        )
    }

    @Test
    @Throws(Exception::class)
    fun testInsert() = runTest {
        val userEntity = UserEntity(
            idUser = 5,
            rol = Rol.STANDAR_USER,
            username = "Juan",
            password = "1234",
            email = "jun@gmail.com",
            nameImg = "profile_7890"
        )
        val result = userDao.insert(
            user = userEntity
        )

        assertEquals(5, result)
    }

    @Test
    @Throws(Exception::class)
    fun testUpdateNameImgByIdUser() = runTest {
        val newNameImg = "new_profile"
        userDao.updateNameImgByIdUser(
            idUser = 1,
            nameImg = newNameImg
        )

        val result = userDao.loginUser(
            email = "guest@gmail.com",
            password = "1234"
        )?.nameImg

        assertEquals(newNameImg, result)
    }

    @Test
    @Throws(Exception::class)
    fun testUpdateRolByIdUser() = runTest {
        val newNameImg = "new_profile"
        userDao.updateNameImgByIdUser(
            idUser = 1,
            nameImg = newNameImg
        )

        val result = userDao.loginUser(
            email = "guest@gmail.com",
            password = "1234"
        )?.nameImg

        assertEquals(newNameImg, result)
    }

    @Test
    @Throws(Exception::class)
    fun testUpdateUsernameByIdUser() = runTest {
        val newUsername = "Juan"
        userDao.updateUsernameByIdUser(
            idUser = 1,
            username = newUsername
        )

        val result = userDao.loginUser(
            email = "guest@gmail.com",
            password = "1234"
        )?.username

        assertEquals(newUsername, result)
    }

    @Test
    @Throws(Exception::class)
    fun testUpdatePasswordByIdUser() = runTest {
        val newPassword = "password"
        userDao.updatePasswordByIdUser(
            idUser = 1,
            password = newPassword
        )

        val result = userDao.isNotCorrectPassword(
            idUser = 1,
            password = "1234"
        )

        assertEquals(true, result)
    }

    @Test
    @Throws(Exception::class)
    fun testDeleteById() = runTest {
        userDao.deleteById(id = 1)

        val result = userDao.loginUser(
            email = "guest@gmail.com",
            password = "1234"
        )

        assertEquals(null, result)
    }

}