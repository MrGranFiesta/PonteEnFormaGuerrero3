package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TextFieldNumberDoubleVMTest {
    private lateinit var viewModel: TextFieldNumberDoubleVM

    @Before
    fun onBefore() {
        viewModel = TextFieldNumberDoubleVM()
    }

    @Test
    fun testInitData() = runTest {
        viewModel.initData(
            cont = 1.0
        )

        val result = viewModel.cantidadTxt.first()

        assertEquals("1.0", result)

        viewModel.initData(
            cont = 2.0
        )

        assertEquals("1.0", result)
    }

    @Test
    fun testOnChangeTextFieldCorrect() = runTest {
        viewModel.setHasFocus(true)
        viewModel.onChangeTextField(
            it = "aa1.0bb"
        )

        val result = viewModel.cantidadTxt.first()

        assertEquals("1.0", result)
    }

    @Test
    fun testOnChangeTextFieldNotFocus() = runTest {
        viewModel.setHasFocus(false)
        viewModel.setCantidadTxt("2.0")
        viewModel.onChangeTextField(
            it = "1.0"
        )

        val result = viewModel.cantidadTxt.first()

        assertEquals("2.0", result)
    }

    @Test
    fun testOnChangeFocusCorrect() = runTest {
        viewModel.setCantidadTxt("aaa5.0aaa")
        viewModel.onChangeFocus {}

        val result = viewModel.cantidadTxt.first()
        assertEquals("5.0", result)
    }

    @Test
    fun testOnChangeFocusEmpty() = runTest {
        viewModel.setCantidadTxt("")
        viewModel.onChangeFocus { }

        val result = viewModel.cantidadTxt.first()
        assertEquals("1.0", result)
    }

    @Test
    fun testNumerLimitCorrect() {
        val method = viewModel.javaClass.getDeclaredMethod("numberLimit", String::class.java)
        method.isAccessible = true

        val result = method.invoke(
            viewModel, "5.0"
        ) as Double

        assertEquals(5.0, result, 0.0)
    }

    @Test
    fun testNumerLimitZero() {
        val method = viewModel.javaClass.getDeclaredMethod("numberLimit", String::class.java)
        method.isAccessible = true

        val result = method.invoke(
            viewModel, "0.0"
        ) as Double

        assertEquals(0.0, result, 0.0)
    }

    @Test
    fun testNumerLimitIsNotDouble() {
        val method = viewModel.javaClass.getDeclaredMethod("numberLimit", String::class.java)
        method.isAccessible = true

        val result = method.invoke(
            viewModel, "aa.aaa"
        ) as Double

        assertEquals(1.0, result, 0.0)
    }

    @Test
    fun testNumerLimitLength10() {
        val method = viewModel.javaClass.getDeclaredMethod("numberLimit", String::class.java)
        method.isAccessible = true

        val result = method.invoke(
            viewModel, "9999999999.99999"
        ) as Double

        assertEquals(9999999.99, result, 0.0)
    }

    @Test
    fun testNumerLimitNegative() {
        val method = viewModel.javaClass.getDeclaredMethod("numberLimit", String::class.java)
        method.isAccessible = true

        val result = method.invoke(
            viewModel, "-4.00"
        ) as Double

        assertEquals(1.0, result, 0.0)
    }

    @Test
    fun testCalculateButtonAddMinDouble() {
        val method = viewModel.javaClass.getDeclaredMethod(
            "calculateButtonAdd",
            Double::class.java,
            Double::class.java
        )
        method.isAccessible = true

        val amount = -20.0
        val accumulator = 1.0

        val result = method.invoke(
            viewModel, accumulator, amount
        ) as Double

        assertEquals(amount, result, 0.0)
    }

    @Test
    fun testCalculateButtonAddMaxDouble() {
        val method = viewModel.javaClass.getDeclaredMethod(
            "calculateButtonAdd",
            Double::class.java,
            Double::class.java
        )
        method.isAccessible = true

        val amount = 9999999999.99999
        val accumulator = 50.0

        val result = method.invoke(
            viewModel, accumulator, amount
        ) as Double

        assertEquals(accumulator, result, 0.0)
    }

    @Test
    fun testCalculateButtonAddCorrect() {
        val method = viewModel.javaClass.getDeclaredMethod(
            "calculateButtonAdd",
            Double::class.java,
            Double::class.java
        )
        method.isAccessible = true

        val amount = 50.0
        val accumulator = 50.0

        val result = method.invoke(
            viewModel, accumulator, amount
        ) as Double

        assertEquals(100.0, result, 0.0)
    }

    @Test
    fun testCalculateButtonlessCorrect() {
        val method = viewModel.javaClass.getDeclaredMethod(
            "calculateButtonless",
            Double::class.java,
            Double::class.java
        )
        method.isAccessible = true

        val amount = 1.0
        val accumulator = 10.0

        val result = method.invoke(
            viewModel, accumulator, amount
        ) as Double

        assertEquals(9.0, result, 0.0)
    }

    @Test
    fun testCalculateButtonlessDoubleMin() {
        val method = viewModel.javaClass.getDeclaredMethod(
            "calculateButtonless",
            Double::class.java,
            Double::class.java
        )
        method.isAccessible = true

        val amount = 1.0
        val accumulator = 10.0

        val result = method.invoke(
            viewModel, accumulator, amount
        ) as Double

        assertEquals(9.0, result, 0.0)
    }

    @Test
    fun testCalculateButtonlessDoubleMax() {
        val method = viewModel.javaClass.getDeclaredMethod(
            "calculateButtonless",
            Double::class.java,
            Double::class.java
        )
        method.isAccessible = true

        val amount = 100.0
        val accumulator = 10.0

        val result = method.invoke(
            viewModel, accumulator, amount
        ) as Double

        assertEquals(1.0, result, 0.0)
    }
}