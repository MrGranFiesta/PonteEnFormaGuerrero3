package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TextFieldNumberVMTest {
    private lateinit var viewModel: TextFieldNumberVM

    @Before
    fun onBefore() {
        viewModel = TextFieldNumberVM()
    }

    @Test
    fun testOnChangeFocusCorrect() = runTest {
        viewModel.setCantidadTxt("aaa50aaa")
        viewModel.onChangeFocus {}

        val result = viewModel.cantidadTxt.first()
        assertEquals("50", result)
    }

    @Test
    fun testOnChangeFocusEmpty() = runTest {
        viewModel.setCantidadTxt("")
        viewModel.onChangeFocus { }

        val result = viewModel.cantidadTxt.first()
        assertEquals("1", result)
    }

    @Test
    fun testNumerLimitCorrect() {
        val method = viewModel.javaClass.getDeclaredMethod("numberLimit", String::class.java)
        method.isAccessible = true

        val result = method.invoke(
            viewModel, "5"
        ) as Int

        assertEquals(5, result)
    }

    @Test
    fun testNumerLimitZero() {
        val method = viewModel.javaClass.getDeclaredMethod("numberLimit", String::class.java)
        method.isAccessible = true

        val result = method.invoke(
            viewModel, "0"
        ) as Int

        assertEquals(1, result)
    }

    @Test
    fun testCalculateButtonAddMinNumber() {
        val method = viewModel.javaClass.getDeclaredMethod(
            "calculateButtonAdd",
            Int::class.java,
            Int::class.java
        )
        method.isAccessible = true

        val amount = -20
        val accumulator = 1

        val result = method.invoke(
            viewModel, accumulator, amount
        ) as Int

        assertEquals(amount, result)
    }

    @Test
    fun testCalculateButtonAddMaxNumber() {
        val method = viewModel.javaClass.getDeclaredMethod(
            "calculateButtonAdd",
            Int::class.java,
            Int::class.java
        )
        method.isAccessible = true

        val amount = 50
        val accumulator = Int.MAX_VALUE

        val result = method.invoke(
            viewModel, accumulator, amount
        ) as Int

        assertEquals(accumulator, result)
    }

    @Test
    fun testCalculateButtonAddCorrect() {
        val method = viewModel.javaClass.getDeclaredMethod(
            "calculateButtonAdd",
            Int::class.java,
            Int::class.java
        )
        method.isAccessible = true

        val amount = 50
        val accumulator = 50

        val result = method.invoke(
            viewModel, accumulator, amount
        ) as Int

        assertEquals(100, result)
    }

    @Test
    fun testCalculateButtonlessCorrect() {
        val method = viewModel.javaClass.getDeclaredMethod(
            "calculateButtonless",
            Int::class.java,
            Int::class.java
        )
        method.isAccessible = true

        val amount = 1
        val accumulator = 10

        val result = method.invoke(
            viewModel, accumulator, amount
        ) as Int

        assertEquals(9, result)
    }

    @Test
    fun testCalculateButtonlessNumberMin() {
        val method = viewModel.javaClass.getDeclaredMethod(
            "calculateButtonless",
            Int::class.java,
            Int::class.java
        )
        method.isAccessible = true

        val amount = 1
        val accumulator = 10

        val result = method.invoke(
            viewModel, accumulator, amount
        ) as Int

        assertEquals(9, result)
    }

    @Test
    fun testCalculateButtonlessNumberMax() {
        val method = viewModel.javaClass.getDeclaredMethod(
            "calculateButtonless",
            Int::class.java,
            Int::class.java
        )
        method.isAccessible = true

        val amount = 100
        val accumulator = 10

        val result = method.invoke(
            viewModel, accumulator, amount
        ) as Int

        assertEquals(1, result)
    }
}