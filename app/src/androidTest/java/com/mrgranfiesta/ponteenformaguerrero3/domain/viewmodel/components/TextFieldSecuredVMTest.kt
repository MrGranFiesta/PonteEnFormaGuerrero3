package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_ERROR_CHARACTER_FIELD
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.LABEL_NOT_EMPTY
import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.RegexExpresion
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TextFieldSecuredVMTest {
    private lateinit var viewModel: TextFieldSecuredVM

    @Before
    fun onBefore() {
        viewModel = TextFieldSecuredVM()
    }

    @Test
    fun testIsHasErrorIsFinish() {
        val firstIteration = viewModel.isHasError(
            txt = "",
            mask = RegexExpresion.SECURED_SQL,
            isActivePermitIsEmpty = true
        )
        assertFalse(firstIteration)

        viewModel.onCompleteFirstIteration()

        val secondIteration = viewModel.isHasError(
            txt = "",
            mask = RegexExpresion.SECURED_SQL,
            isActivePermitIsEmpty = true
        )
        assertTrue(secondIteration)
    }

    @Test
    fun testIsHasErrorIsEmpty() {
        viewModel.onCompleteFirstIteration()
        val result = viewModel.isHasError(
            txt = "",
            mask = RegexExpresion.SECURED_SQL,
            isActivePermitIsEmpty = true
        )
        assertTrue(result)
    }

    @Test
    fun testIsHasErrorIsEmptyNoActiveCheck() {
        viewModel.onCompleteFirstIteration()
        val result = viewModel.isHasError(
            txt = "",
            mask = RegexExpresion.SECURED_SQL,
            isActivePermitIsEmpty = false
        )
        assertFalse(result)
    }

    @Test
    fun testIsHasErrorContentChatNotPermit() {
        viewModel.onCompleteFirstIteration()
        val result = viewModel.isHasError(
            txt = "|!@#$",
            mask = RegexExpresion.SECURED_SQL,
            isActivePermitIsEmpty = false
        )
        assertTrue(result)
    }

    @Test
    fun testIsHasErrorContentCorrect() {
        viewModel.onCompleteFirstIteration()
        val result = viewModel.isHasError(
            txt = "1234",
            mask = RegexExpresion.SECURED_SQL,
            isActivePermitIsEmpty = false
        )
        assertFalse(result)
    }

    @Test
    fun testGetTextErrorIsFinishIteration() {
        val result = viewModel.getTextError(
            txt = "1234",
            mask = RegexExpresion.SECURED_SQL,
            isActivePermitIsEmpty = false,
            isFinishFirstIteration = false
        )
        assertEquals("", result)
    }

    @Test
    fun testGetTextErrorEmpty() {
        viewModel.onCompleteFirstIteration()
        val result = viewModel.getTextError(
            txt = "",
            mask = RegexExpresion.SECURED_SQL,
            isActivePermitIsEmpty = true,
            isFinishFirstIteration = true
        )
        assertEquals(LABEL_NOT_EMPTY, result)
    }

    @Test
    fun testGetTextErrorContentChatNotPermit() {
        viewModel.onCompleteFirstIteration()
        val result = viewModel.getTextError(
            txt = "|!@#$",
            mask = RegexExpresion.SECURED_SQL,
            isActivePermitIsEmpty = true,
            isFinishFirstIteration = true
        )
        assertEquals(LABEL_ERROR_CHARACTER_FIELD, result)
    }
}