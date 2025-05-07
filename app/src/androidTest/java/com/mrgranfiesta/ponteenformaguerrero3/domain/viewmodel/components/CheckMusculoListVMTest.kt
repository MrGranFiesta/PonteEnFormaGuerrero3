package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.compose.ui.state.ToggleableState
import androidx.lifecycle.ViewModel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CheckMusculoListVMTest : ViewModel() {
    lateinit var viewModel: CheckMusculoListVM

    @Before
    fun onBefore() {
        viewModel = CheckMusculoListVM()
    }

    @Test
    fun testInitDataParentCheckOn() = runTest {
        var muscleList = Musculo.entries.toSet()
        viewModel.initData(muscleList)

        val parentCheckState = viewModel.parentCheckState.first()

        assertEquals(ToggleableState.On, parentCheckState)
    }

    @Test
    fun testInitDataParentCheckIndeterminate() = runTest {
        var muscleList = setOf(Musculo.PECHO)
        viewModel.initData(muscleList)

        val parentCheckState = viewModel.parentCheckState.first()

        assertEquals(ToggleableState.Indeterminate, parentCheckState)
    }

    @Test
    fun testInitDataParentCheckOff() = runTest {
        var muscleList = setOf<Musculo>()
        viewModel.initData(muscleList)

        val parentCheckState = viewModel.parentCheckState.first()

        assertEquals(ToggleableState.Off, parentCheckState)
    }

    @Test
    fun testOnCheckTriStateToOn() = runTest {
        var muscleList = setOf<Musculo>()
        viewModel.setParentCheckState(ToggleableState.Off)

        viewModel.onCheckTriState { muscleList = it }

        val parentCheckState = viewModel.parentCheckState.first()

        assertEquals(ToggleableState.On, parentCheckState)
        assertTrue(
            "aaa ${muscleList.size} / ${Musculo.entries.toSet().size}",
            muscleList.size == Musculo.entries.toSet().size
        )
    }

    @Test
    fun testOnCheckTriStateToOff() = runTest {
        var muscleList = setOf<Musculo>()
        viewModel.setParentCheckState(ToggleableState.On)

        viewModel.onCheckTriState { muscleList = it }

        val parentCheckState = viewModel.parentCheckState.first()

        assertEquals(ToggleableState.Off, parentCheckState)
        assertTrue(muscleList.isEmpty())
    }
}