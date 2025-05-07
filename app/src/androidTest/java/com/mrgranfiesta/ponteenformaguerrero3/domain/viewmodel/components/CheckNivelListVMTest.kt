package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.compose.ui.state.ToggleableState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CheckNivelListVMTest {
    lateinit var viewModel: CheckNivelListVM

    @Before
    fun onBefore() {
        viewModel = CheckNivelListVM()
    }

    @Test
    fun testInitDataParentCheckOn() = runTest {
        var nivelList = Nivel.entries.toSet()
        viewModel.initData(nivelList)

        val parentCheckState = viewModel.parentCheckState.first()

        Assert.assertEquals(ToggleableState.On, parentCheckState)
    }

    @Test
    fun testInitDataParentCheckIndeterminate() = runTest {
        var nivelList = setOf(Nivel.MEDIO)
        viewModel.initData(nivelList)

        val parentCheckState = viewModel.parentCheckState.first()

        Assert.assertEquals(ToggleableState.Indeterminate, parentCheckState)
    }

    @Test
    fun testInitDataParentCheckOff() = runTest {
        var nivelList = setOf<Nivel>()
        viewModel.initData(nivelList)

        val parentCheckState = viewModel.parentCheckState.first()

        Assert.assertEquals(ToggleableState.Off, parentCheckState)
    }

    @Test
    fun testOnCheckTriStateToOn() = runTest {
        var nivelList = setOf<Nivel>()
        viewModel.setParentCheckState(ToggleableState.Off)

        viewModel.onCheckTriState { nivelList = it }

        val parentCheckState = viewModel.parentCheckState.first()

        Assert.assertEquals(ToggleableState.On, parentCheckState)
        Assert.assertTrue(nivelList.size == Nivel.entries.toSet().size)
    }

    @Test
    fun testOnCheckTriStateToOff() = runTest {
        var nivelList = setOf<Nivel>()
        viewModel.setParentCheckState(ToggleableState.On)

        viewModel.onCheckTriState { nivelList = it }

        val parentCheckState = viewModel.parentCheckState.first()

        Assert.assertEquals(ToggleableState.Off, parentCheckState)
        Assert.assertTrue(nivelList.isEmpty())
    }
}