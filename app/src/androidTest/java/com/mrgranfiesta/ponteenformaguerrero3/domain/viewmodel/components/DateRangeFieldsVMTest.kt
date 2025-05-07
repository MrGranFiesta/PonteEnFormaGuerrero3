package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse

class DateRangeFieldsVMTest {
    private lateinit var viewModel: DateRangeFieldsVM

    @Before
    fun onBefore() {
        viewModel = DateRangeFieldsVM()
    }

    @Test
    fun testSetInitDateIsDigits() = runTest {
        viewModel.setInitDate("1234")

        val result = viewModel.initDate.first()

        assertEquals("1234", result)
    }

    @Test
    fun testSetInitDateIsNotDigits() = runTest {
        viewModel.setInitDate("1234a")

        val result = viewModel.initDate.first()

        assertEquals("", result)
    }

    @Test
    fun testSetEndDateIsDigits() = runTest {
        viewModel.setEndDate("1234")

        val result = viewModel.endDate.first()

        assertEquals("1234", result)
    }

    @Test
    fun testSetEndDateIsNotDigits() = runTest {
        viewModel.setEndDate("1234a")

        val result = viewModel.endDate.first()

        assertEquals("", result)
    }

    @Test
    fun testIsAfterReturnTrue() {
        val result = viewModel.isAfter(
            initDate = "12032015",
            endDate = "12042015"
        )
        assertFalse(result)
    }

    @Test
    fun testIsAfterReturnFalse() {
        val result = viewModel.isAfter(
            initDate = "12042015",
            endDate = "12032015"
        )
        assertTrue(result)
    }
}