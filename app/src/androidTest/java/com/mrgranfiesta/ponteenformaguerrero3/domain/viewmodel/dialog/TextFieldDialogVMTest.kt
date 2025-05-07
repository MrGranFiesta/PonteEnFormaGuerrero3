package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.dialog

import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class TextFieldDialogVMTest {
    lateinit var viewModel: TextFieldDialogVM

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = TextFieldDialogVM()
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun testInitDataCorrect() = runTest {
        val result1 = "Juan"
        viewModel.initData(result1)

        val textDialog1 = viewModel.textDialog.first()
        assertEquals(result1, textDialog1)

        val result2 = "Francisco"

        viewModel.initData(result2)

        val textDialog2 = viewModel.textDialog.first()
        assertNotEquals(result2, textDialog2)
    }
}