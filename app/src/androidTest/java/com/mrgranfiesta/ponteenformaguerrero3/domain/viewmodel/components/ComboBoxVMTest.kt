package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ComboBoxVMTest {
    private lateinit var viewModel : ComboBoxVM

    @Before
    fun onBefore() {
        viewModel = ComboBoxVM()
    }

    @Test
    fun testInitDataIsExplandedTrue() = runTest {
        viewModel.setIsExplanded(true)

        viewModel.initData()

        val result = viewModel.icon.first()

        Assert.assertEquals(Icons.Filled.KeyboardArrowUp, result)
    }

    @Test
    fun testInitDataIsExplandedFalse() = runTest {
        viewModel.setIsExplanded(false)

        viewModel.initData()

        val result = viewModel.icon.first()

        Assert.assertEquals(Icons.Filled.KeyboardArrowDown, result)
    }
}