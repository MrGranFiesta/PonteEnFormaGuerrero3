package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.compose.ui.state.ToggleableState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse

class CheckMusculoListItemVMTest {
    lateinit var viewModel: CheckMusculoListItemVM

    @Before
    fun onBefore() {
        viewModel = CheckMusculoListItemVM()
    }

    @Test
    fun testOnCheckTextCheckedIsTrue() = runTest {
        viewModel.onCheckText(
            isCheck = true,
            musculo = Musculo.PECHO,
            onChangeMusculoSet = { },
            musculosList = emptySet<Musculo>(),
            changeGlobalCheck = { }
        )

        val initialState = viewModel.isChecked.first()

        assertTrue(initialState)
    }

    @Test
    fun testOnCheckTextCheckedIsFalse() = runTest {
        viewModel.onCheckText(
            isCheck = false,
            musculo = Musculo.PECHO,
            onChangeMusculoSet = { },
            musculosList = emptySet<Musculo>(),
            changeGlobalCheck = { }
        )

        val initialState = viewModel.isChecked.first()

        assertFalse(initialState)
    }

    @Test
    fun testOnCheckTextAddMusculo() {
        var muscleList = setOf(Musculo.PECHO)

        viewModel.onCheckText(
            isCheck = true,
            musculo = Musculo.ABDOMINALES,
            onChangeMusculoSet = { muscleList = it },
            musculosList = emptySet<Musculo>(),
            changeGlobalCheck = { }
        )

        assertTrue(muscleList.contains(Musculo.ABDOMINALES))
    }

    @Test
    fun testOnCheckTextRemoveMusculo() {
        var muscleList = setOf(Musculo.PECHO, Musculo.ABDOMINALES)

        viewModel.onCheckText(
            isCheck = false,
            musculo = Musculo.ABDOMINALES,
            onChangeMusculoSet = { muscleList = it },
            musculosList = emptySet<Musculo>(),
            changeGlobalCheck = { }
        )

        assertFalse(muscleList.contains(Musculo.ABDOMINALES))
    }

    @Test
    fun testOnCheckTextToggleGlobalIsOn() {
        var muscleList = Musculo.entries.toSet()

        var globalCheckState: ToggleableState? = null

        viewModel.onCheckText(
            isCheck = true,
            musculo = Musculo.ABDOMINALES,
            onChangeMusculoSet = { },
            musculosList = muscleList,
            changeGlobalCheck = { globalCheckState = it }
        )

        assertEquals(ToggleableState.On, globalCheckState)
    }

    @Test
    fun testOnCheckTextToggleGlobalIsIndeterminate() {
        var muscleList = setOf(Musculo.PECHO, Musculo.ABDOMINALES)

        var globalCheckState: ToggleableState? = null

        viewModel.onCheckText(
            isCheck = true,
            musculo = Musculo.ABDOMINALES,
            onChangeMusculoSet = { },
            musculosList = muscleList,
            changeGlobalCheck = { globalCheckState = it }
        )

        assertEquals(ToggleableState.Indeterminate, globalCheckState)
    }

    @Test
    fun testOnCheckTextToggleGlobalIsOff() {
        var muscleList = setOf<Musculo>()

        var globalCheckState: ToggleableState? = null

        viewModel.onCheckText(
            isCheck = true,
            musculo = Musculo.ABDOMINALES,
            onChangeMusculoSet = { },
            musculosList = muscleList,
            changeGlobalCheck = { globalCheckState = it }
        )

        assertEquals(ToggleableState.Off, globalCheckState)
    }
}