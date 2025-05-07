package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CountUpVMTest {
    private lateinit var viewModel: CountUpVM

    @Before
    fun onBefore() {
        viewModel = CountUpVM()
    }

    @Test
    fun testSetAcumulatorTimeIsNotNegative() = runTest {
        viewModel.setAcumulatorTime(-1000)

        val result = viewModel.acumulatorTime.first()

        Assert.assertTrue(result >= 0)
    }

    @Test
    fun testSetAcumulatorTimeIsEqualsZero() = runTest {
        viewModel.setAcumulatorTime(0)

        val result = viewModel.acumulatorTime.first()

        Assert.assertEquals(result, 0)
    }

    @Test
    fun testSetAcumulatorTimeIsPositive() = runTest {
        viewModel.setAcumulatorTime(1000)

        val result = viewModel.acumulatorTime.first()

        Assert.assertEquals(result, 1_000)
    }

    @Test
    fun testInitDataCorrect() = runTest {
        viewModel.initData(
            initMiliseconds = 2000,
            autoPlay = true
        )

        val acumulatorTime = viewModel.acumulatorTime.first()
        val isActive = viewModel.isActive.first()

        Assert.assertEquals(2000, acumulatorTime)
        Assert.assertTrue(isActive)
    }

    @Test
    fun testInitDataNotFirstIteration() = runTest {
        viewModel.setAcumulatorTime(10_000)
        viewModel.initData(
            initMiliseconds = 2000,
            autoPlay = true
        )

        val acumulatorTime = viewModel.acumulatorTime.first()

        Assert.assertNotEquals(2000, acumulatorTime)
    }

    @Test
    fun testIterationTimeIsPlusTime() = runTest {
        viewModel.setAcumulatorTime(1_000)
        viewModel.setActive(true)

        viewModel.iterationTime(
            actionByTime = {},
            timeAction = 1_000,
            launcher = this
        )

        advanceTimeBy(5000)

        val acumulatorTime = viewModel.acumulatorTime.first()

        Assert.assertEquals(2000, acumulatorTime)
    }

    @Test
    fun testIterationTimeExecutionActionExtra() = runTest {
        var isExecuteActionExtre = false
        viewModel.setAcumulatorTime(10_000)
        viewModel.setActive(true)

        viewModel.iterationTime(
            actionByTime = { isExecuteActionExtre = true },
            timeAction = 1_000,
            launcher = this
        )

        advanceTimeBy(5000)

        Assert.assertTrue(isExecuteActionExtre)
    }

    @Test
    fun testIterationTimeNotSecondExecutionActionExtra() = runTest {
        var isFirstExecuteActionExtre = false
        viewModel.setAcumulatorTime(20_000)
        viewModel.setActive(true)

        viewModel.iterationTime(
            actionByTime = { isFirstExecuteActionExtre = true },
            timeAction = 10_000,
            launcher = this
        )

        advanceTimeBy(5000)

        Assert.assertTrue(isFirstExecuteActionExtre)

        var isSecondExecuteActionExtre = false
        viewModel.iterationTime(
            actionByTime = { isSecondExecuteActionExtre = true },
            timeAction = 10_000,
            launcher = this
        )

        advanceTimeBy(5000)

        Assert.assertFalse(isSecondExecuteActionExtre)
    }
}