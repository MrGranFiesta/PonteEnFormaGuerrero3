package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.dialog

import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class SelectMusculoDialogVMTest {
    lateinit var viewModel: SelectMusculoDialogVM

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = SelectMusculoDialogVM()
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun testInitDataCorrect() = runTest {
        val result1 = setOf(Musculo.ABDOMINALES)
        viewModel.initData(result1)

        val musculoSetTemp1 = viewModel.musculoSet.first()
        assertEquals(result1, musculoSetTemp1)

        val result2 = setOf(Musculo.PECHO)

        viewModel.initData(result2)

        val musculoSetTemp2 = viewModel.musculoSet.first()
        assertNotEquals(result2, musculoSetTemp2)
    }
}