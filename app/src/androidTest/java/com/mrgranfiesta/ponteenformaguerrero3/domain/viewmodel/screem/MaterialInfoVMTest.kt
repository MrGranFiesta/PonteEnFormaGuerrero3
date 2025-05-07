package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.DeleteMaterialUseCase
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.GetMaterialByIdUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MaterialInfoVMTest {
    lateinit var viewModel: MaterialInfoVM

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @RelaxedMockK
    lateinit var getMaterialByIdUseCase: GetMaterialByIdUseCase

    @RelaxedMockK
    lateinit var deleteMaterialUseCase: DeleteMaterialUseCase

    @Before
    fun onBefore() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        mockkObject(CurrentUser)
        viewModel = MaterialInfoVM(
            getMaterialByIdUseCase = getMaterialByIdUseCase,
            deleteMaterialUseCase = deleteMaterialUseCase
        )
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun testInitDataCorrect() = runTest {
        val statusField = viewModel.javaClass.getDeclaredField("status")
        statusField.isAccessible = true

        assertTrue(statusField.getBoolean(viewModel))

        viewModel.initData(1)

        assertFalse(statusField.getBoolean(viewModel))
    }
}