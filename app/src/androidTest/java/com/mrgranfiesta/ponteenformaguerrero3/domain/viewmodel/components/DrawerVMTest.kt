package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.net.toUri
import androidx.test.platform.app.InstrumentationRegistry
import com.mrgranfiesta.ponteenformaguerrero3.data.datastore.UserDataStore
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.user.UserWithRolDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.factory.ListManager
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DrawerVMTest {
    private lateinit var viewModel: DrawerVM

    lateinit var userDataStore: UserDataStore

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun onBefore() {
        Dispatchers.setMain(testDispatcher)
        userDataStore = UserDataStore(
            context = InstrumentationRegistry.getInstrumentation().targetContext
        )
        viewModel = DrawerVM(userDataStore)
        mockkObject(CurrentUser)
        mockkObject(ListManager)
        CurrentUser.user = null
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun testInitDataNoLoggin() = runTest {
        every { CurrentUser.user } returns UserWithRolDto(
            idUser = 1L,
            username = "testUser",
            email = "test@email.com",
            photoUri = "content://image.jpg".toUri(),
            rol = Rol.INIT_DATA_USER
        )
        every { CurrentUser.user?.rol } returns null
        viewModel.initData()

        val result = viewModel.optList.first()
        Assert.assertTrue(result.isEmpty())
    }

    @Test
    fun testInitDataLoggin() = runTest {
        every { CurrentUser.user } returns UserWithRolDto(
            idUser = 1L,
            username = "testUser",
            email = "test@email.com",
            photoUri = "content://image.jpg".toUri(),
            rol = Rol.INIT_DATA_USER
        )
        every { CurrentUser.user?.rol } returns Rol.INIT_DATA_USER
        every { ListManager.getDrawerOpt(any()) } returns listOf(
            AppScreem.EjercicioListScreem,
            AppScreem.RutinaListScreem,
            AppScreem.MaterialListScreem
        )
        viewModel.initData()

        val result = viewModel.optList.first()
        Assert.assertTrue(result.isNotEmpty())
    }
}