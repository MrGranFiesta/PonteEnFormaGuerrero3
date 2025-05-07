package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CountdownVMTest {
    private lateinit var viewModel: CountdownVM

    @Before
    fun onBefore() {
        viewModel = CountdownVM()
    }

    @Test
    fun testSetEndTimeIsNotNegative() = runTest {
        viewModel.setEndTime(-1000)

        val result = viewModel.endTime.first()

        Assert.assertTrue(result >= 0)
    }

    @Test
    fun testSetEndTimeIsEqualsZero() = runTest {
        viewModel.setEndTime(0)

        val result = viewModel.endTime.first()

        Assert.assertEquals(result, 0)
    }

    @Test
    fun testSetEndTimeIsPositive() = runTest {
        viewModel.setEndTime(1000)

        val result = viewModel.endTime.first()

        Assert.assertEquals(result, 1_000)
    }

    @Test
    fun testMinusEndTimeMinIsNegative() = runTest {
        viewModel.setEndTime(4000)

        viewModel.minusEndTimeMin(5000)

        val result = viewModel.endTime.first()

        Assert.assertTrue(result > 0)
    }

    @Test
    fun testMinusEndTimeMinIsEqualsZero() = runTest {
        viewModel.setEndTime(5000)

        viewModel.minusEndTimeMin(5000)

        val result = viewModel.endTime.first()

        Assert.assertEquals(result, 1_000)
    }

    @Test
    fun testMinusEndTimeMinIsPositive() = runTest {
        viewModel.setEndTime(5000)

        viewModel.minusEndTimeMin(1000)

        val result = viewModel.endTime.first()

        Assert.assertTrue(result > 0)
    }

    @Test
    fun testPlusEndTimeIsLimit() = runTest {
        viewModel.setEndTime(5000)

        viewModel.plusEndTime(1_000_000_000_000)

        val result = viewModel.endTime.first()

        Assert.assertTrue(result < 1_000_000_000_000)
    }

    @Test
    fun testPlusEndTimeIsCorrect() = runTest {
        viewModel.setEndTime(5000)

        viewModel.plusEndTime(5000)

        val result = viewModel.endTime.first()

        Assert.assertEquals(result, 10_000)
    }

    @Test
    fun testIterationTimeSetEndNoInitialize() = runTest {
        var finish = false

        viewModel.iterationTime(
            actionFinish = { finish = true },
            launcher = this
        )

        advanceTimeBy(5000)

        val result = viewModel.endTime.first()

        Assert.assertEquals(-1, result)
        Assert.assertFalse(finish)
    }

    @Test
    fun testIterationTimeSetEndNotActive() = runTest {
        var finish = false
        viewModel.setActive(false)

        viewModel.iterationTime(
            actionFinish = { finish = true },
            launcher = this
        )

        advanceTimeBy(5000)

        val result = viewModel.endTime.first()

        Assert.assertEquals(-1L, result)
        Assert.assertFalse(finish)
    }

    @Test
    fun testIterationTimeMinusTime() = runTest {
        var finish = false
        viewModel.setActive(true)
        viewModel.setEndTime(3000)

        viewModel.iterationTime(
            actionFinish = { finish = true },
            launcher = this
        )

        advanceTimeBy(5000)

        val result = viewModel.endTime.first()

        Assert.assertEquals(2000, result)
        Assert.assertFalse(finish)
    }

    @Test
    fun testIterationTimeTimeEqualsZero() = runTest {
        var isFinish = false
        viewModel.setEndTime(0)

        viewModel.iterationTime(
            actionFinish = { isFinish = true },
            launcher = this
        )

        advanceTimeBy(5000)

        val result = viewModel.endTime.first()

        Assert.assertEquals(-1, result)
        Assert.assertTrue(isFinish)
    }

    @Test
    fun testInitDataCorrect() = runTest {
        viewModel.initData(
            initMiliseconds = 2000,
            autoPlay = true
        )

        val endTime = viewModel.endTime.first()
        val isActive = viewModel.isActive.first()

        Assert.assertEquals(2000, endTime)
        Assert.assertTrue(isActive)
    }

    @Test
    fun testInitDataNotFirstIteration() = runTest {
        viewModel.setEndTime(10_000)
        viewModel.initData(
            initMiliseconds = 2000,
            autoPlay = true
        )

        val endTime = viewModel.endTime.first()

        Assert.assertNotEquals(2000, endTime)
    }
}