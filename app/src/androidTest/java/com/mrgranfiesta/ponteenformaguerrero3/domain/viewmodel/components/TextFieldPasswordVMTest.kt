package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ERROR_CHARACTER_FIELD
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NOT_EMPTY
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TextFieldPasswordVMTest {
    private lateinit var viewModel: TextFieldPasswordVM

    @Before
    fun onBefore() {
        viewModel = TextFieldPasswordVM()
    }

    @Test
    fun testIsHasErrorIsFinish() {
        val firstIteration = viewModel.isHasError("")
        assertFalse(firstIteration)

        viewModel.onCompleteFirstIteration()

        val secondIteration = viewModel.isHasError("")
        assertTrue(secondIteration)
    }

    @Test
    fun testIsHasErrorIsEmpty() {
        viewModel.onCompleteFirstIteration()
        val result = viewModel.isHasError("")
        assertTrue(result)
    }

    @Test
    fun testIsHasErrorContentChatNotPermit() {
        viewModel.onCompleteFirstIteration()
        val result1 = viewModel.isHasError("|!@#$")
        assertFalse(result1)

        val result2 = viewModel.isHasError("\"\\<>")
        assertTrue(result2)
    }

    @Test
    fun testIsHasErrorContentCorrect() {
        viewModel.onCompleteFirstIteration()
        val result = viewModel.isHasError("1234")
        assertFalse(result)
    }

    @Test
    fun testGetTextErrorIsFinishIteration() {
        val result = viewModel.getTextError(
            txt = "1234",
            isFinishFirstIteration = true
        )
        assertEquals("", result)
    }

    @Test
    fun testGetTextErrorEmpty() {
        viewModel.onCompleteFirstIteration()
        val result = viewModel.getTextError(
            txt = "",
            isFinishFirstIteration = true
        )
        assertEquals(LABEL_NOT_EMPTY, result)
    }

    @Test
    fun testGetTextErrorContentChatNotPermit() {
        viewModel.onCompleteFirstIteration()
        val result = viewModel.getTextError(
            txt = "\"\\<>",
            isFinishFirstIteration = true
        )
        assertEquals(LABEL_ERROR_CHARACTER_FIELD, result)
    }
}