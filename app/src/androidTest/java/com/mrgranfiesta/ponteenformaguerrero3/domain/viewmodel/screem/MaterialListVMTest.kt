package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem

import android.net.Uri
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.material.RowMaterialDto
import com.mrgranfiesta.ponteenformaguerrero3.domain.usecase.material.GetMaterialAllUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MaterialListVMTest {
    lateinit var viewModel: MaterialListVM

    @RelaxedMockK
    private lateinit var getMaterialAllUseCase: GetMaterialAllUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = MaterialListVM(getMaterialAllUseCase)
    }

    @After
    fun onAfter() {
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
            materialListDB = getSetMaterial()
        )

        val result = viewModel.materialList.first().isEmpty()
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterSearchText() = runTest {
        viewModel.initData(
            searchText = "Disco",
            materialListDB = getSetMaterial()
        )

        val result = viewModel.materialList.first().isNotEmpty()
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterSearchTextAutocomplete() = runTest {
        viewModel.initData(
            searchText = "Mancuerna",
            materialListDB = getSetMaterial()
        )

        val result = viewModel.materialList.first().size == 2
        assertTrue(result)
    }

    @Test
    fun testInitDateFilterSearchTextRE() = runTest {
        viewModel.initData(
            searchText = "*cuer*",
            materialListDB = getSetMaterial()
        )

        val result = viewModel.materialList.first().size == 2
        assertTrue(result)
    }
}