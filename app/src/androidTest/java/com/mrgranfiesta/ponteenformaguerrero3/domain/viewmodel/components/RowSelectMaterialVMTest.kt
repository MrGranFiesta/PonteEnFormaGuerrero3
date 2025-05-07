package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertTrue

class RowSelectMaterialVMTest {
    private lateinit var viewModel: RowSelectMaterialVM

    @Before
    fun onBefore() {
        viewModel = RowSelectMaterialVM()
    }

    @Test
    fun testInitDataStatusCorrect() = runTest {
        viewModel.initData(true)

        assertTrue(viewModel.isChecked.first())

        viewModel.initData(false)
        assertTrue(viewModel.isChecked.first())

    }
}