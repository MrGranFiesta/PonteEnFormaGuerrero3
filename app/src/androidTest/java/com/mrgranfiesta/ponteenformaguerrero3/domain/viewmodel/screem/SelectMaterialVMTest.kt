package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.PARAMS_MATERIAL_INITIAL_SELECT
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.GetMaterialAllUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class SelectMaterialVMTest {
    lateinit var viewModel: SelectMaterialVM

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @RelaxedMockK
    private lateinit var getMaterialAllUseCase: GetMaterialAllUseCase

    @RelaxedMockK
    private lateinit var navController: NavController

    @RelaxedMockK
    private lateinit var navBackStackEntry: NavBackStackEntry

    @RelaxedMockK
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun onBefore() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel = SelectMaterialVM(getMaterialAllUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    private fun getSetMaterial() = mutableSetOf(
        RowMaterialDto(
            idMaterial = 1,
            idUser = 1,
            nombre = "Banco",
            photoUri = Uri.EMPTY
        ),
        RowMaterialDto(
            idMaterial = 2,
            idUser = 1,
            nombre = "Mancuerna 1",
            photoUri = Uri.EMPTY
        ),
        RowMaterialDto(
            idMaterial = 3,
            idUser = 1,
            nombre = "Mancuerna 2",
            photoUri = Uri.EMPTY
        ),
        RowMaterialDto(
            idMaterial = 4,
            idUser = 1,
            nombre = "Disco",
            photoUri = Uri.EMPTY
        ),
        RowMaterialDto(
            idMaterial = 5,
            idUser = 1,
            nombre = "|@#~€",
            photoUri = Uri.EMPTY
        ),
    )

    @Test
    fun testInitDateIlegalSearchText() = runTest {
        viewModel.initData(
            searchText = "|@#~€",
            navController = navController,
            navBackStackEntry = navBackStackEntry,
            materialListDB = getSetMaterial()
        )

        val result = viewModel.materialList.first().isEmpty()
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterSearchText() = runTest {
        every { navController.previousBackStackEntry } returns navBackStackEntry
        every { navController.previousBackStackEntry?.savedStateHandle } returns savedStateHandle
        every {
            navController
                .previousBackStackEntry
                ?.savedStateHandle
                ?.get<Set<Long>>(PARAMS_MATERIAL_INITIAL_SELECT)
        } returns setOf(1, 2, 3)

        viewModel.initData(
            searchText = "Disco",
            navController = navController,
            navBackStackEntry = navBackStackEntry,
            materialListDB = getSetMaterial()
        )

        val result = viewModel.materialList.first().isNotEmpty()
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterSearchTextAutocomplete() = runTest {
        every { navController.previousBackStackEntry } returns navBackStackEntry
        every { navController.previousBackStackEntry?.savedStateHandle } returns savedStateHandle
        every {
            navController
                .previousBackStackEntry
                ?.savedStateHandle
                ?.get<Set<Long>>(PARAMS_MATERIAL_INITIAL_SELECT)
        } returns setOf(1, 2, 3)

        viewModel.initData(
            searchText = "Mancuerna",
            navController = navController,
            navBackStackEntry = navBackStackEntry,
            materialListDB = getSetMaterial()
        )

        val result = viewModel.materialList.first().size == 2
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterSearchTextRE() = runTest {
        every { navController.previousBackStackEntry } returns navBackStackEntry
        every { navController.previousBackStackEntry?.savedStateHandle } returns savedStateHandle
        every {
            navController
                .previousBackStackEntry
                ?.savedStateHandle
                ?.get<Set<Long>>(PARAMS_MATERIAL_INITIAL_SELECT)
        } returns setOf(1, 2, 3)

        viewModel.initData(
            searchText = "*cuer*",
            navController = navController,
            navBackStackEntry = navBackStackEntry,
            materialListDB = getSetMaterial()
        )

        val result = viewModel.materialList.first().size == 2
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterMaterialSelecter() = runTest {
        every { navController.previousBackStackEntry } returns navBackStackEntry
        every { navController.previousBackStackEntry?.savedStateHandle } returns savedStateHandle
        every {
            navController
                .previousBackStackEntry
                ?.savedStateHandle
                ?.get<Set<Long>>(PARAMS_MATERIAL_INITIAL_SELECT)
        } returns setOf(1, 2, 3)


        viewModel.initData(
            searchText = "",
            navController = navController,
            navBackStackEntry = navBackStackEntry,
            materialListDB = getSetMaterial()
        )

        val result = viewModel.materialListSelected.first().isNotEmpty()

        assertTrue(result)
    }

    @Test
    fun testOnCheckActionCorrect() = runTest {
        val materialRow = RowMaterialDto(
            idMaterial = 1,
            idUser = 1,
            nombre = "Banco",
            photoUri = Uri.EMPTY
        )

        viewModel.onCheckAction(
            check = true,
            material = materialRow
        )

        val resultSelect = viewModel.materialListSelected.first().any { it == 1L }
        advanceTimeBy(5000)
        assertTrue(resultSelect)

        advanceTimeBy(5000)

        viewModel.onCheckAction(
            check = false,
            material = materialRow
        )
        advanceTimeBy(5000)
        val resultDeselect = viewModel.materialListSelected.first().any { it == 1L }
        advanceTimeBy(5000)
        assertFalse(resultDeselect)
    }
}