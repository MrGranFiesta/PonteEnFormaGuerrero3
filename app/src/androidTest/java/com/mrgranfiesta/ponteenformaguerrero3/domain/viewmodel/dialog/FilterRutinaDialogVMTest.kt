package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.dialog

import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class FilterRutinaDialogVMTest {
    lateinit var viewModel: FilterRutinaDialogVM

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = FilterRutinaDialogVM()
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun testInitDataCorrect() = runTest {
        val result1 = setOf(Musculo.ABDOMINALES)
        viewModel.initData(
            musculoSet = result1,
            nivelSet = setOf(Nivel.MEDIO)
        )

        val musculoSetTemp1 = viewModel.musculoSetTemp.first()
        assertEquals(result1, musculoSetTemp1)

        val result2 = setOf(Musculo.PECHO)

        viewModel.initData(
            musculoSet = result2,
            nivelSet = setOf(Nivel.FACIL)
        )

        val musculoSetTemp2 = viewModel.musculoSetTemp.first()
        assertNotEquals(result2, musculoSetTemp2)
    }
}