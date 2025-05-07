package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import androidx.compose.ui.state.ToggleableState
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CheckNivelListItemVMTest {
    lateinit var viewModel: CheckNivelListItemVM

    @Before
    fun onBefore() {
        viewModel = CheckNivelListItemVM()
    }

    @Test
    fun testOnCheckTextCheckedIsTrue() = runTest {
        viewModel.onCheckText(
            isCheck = true,
            nivel = Nivel.FACIL,
            onChangeNivelSet = { },
            nivelList = emptySet<Nivel>(),
            changeGlobalCheck = { }
        )

        val initialState = viewModel.isChecked.first()

        assertTrue(initialState)
    }

    @Test
    fun testOnCheckTextCheckedIsFalse() = runTest {
        viewModel.onCheckText(
            isCheck = false,
            nivel = Nivel.FACIL,
            onChangeNivelSet = { },
            nivelList = emptySet<Nivel>(),
            changeGlobalCheck = { }
        )

        val initialState = viewModel.isChecked.first()

        assertFalse(initialState)
    }

    @Test
    fun testOnCheckTextAddNivel() {
        var nivelList = setOf(Nivel.MEDIO)

        viewModel.onCheckText(
            isCheck = true,
            nivel = Nivel.FACIL,
            onChangeNivelSet = { nivelList = it },
            nivelList = emptySet<Nivel>(),
            changeGlobalCheck = { }
        )

        assertTrue(nivelList.contains(Nivel.FACIL))
    }

    @Test
    fun testOnCheckTextRemoveNivel() {
        var nivelList = setOf(Nivel.MEDIO, Nivel.FACIL)

        viewModel.onCheckText(
            isCheck = false,
            nivel = Nivel.FACIL,
            onChangeNivelSet = { nivelList = it },
            nivelList = emptySet<Nivel>(),
            changeGlobalCheck = { }
        )

        assertFalse(nivelList.contains(Nivel.FACIL))
    }

    @Test
    fun testOnCheckTextToggleGlobalIsOn() {
        var nivelList = Nivel.entries.toSet()

        var globalCheckState: ToggleableState? = null

        viewModel.onCheckText(
            isCheck = true,
            nivel = Nivel.FACIL,
            onChangeNivelSet = { },
            nivelList = nivelList,
            changeGlobalCheck = { globalCheckState = it }
        )

        assertEquals(ToggleableState.On, globalCheckState)
    }

    @Test
    fun testOnCheckTextToggleGlobalIsIndeterminate() {
        var nivelList = setOf(Nivel.MEDIO, Nivel.FACIL)

        var globalCheckState: ToggleableState? = null

        viewModel.onCheckText(
            isCheck = true,
            nivel = Nivel.FACIL,
            onChangeNivelSet = { },
            nivelList = nivelList,
            changeGlobalCheck = { globalCheckState = it }
        )

        assertEquals(ToggleableState.Indeterminate, globalCheckState)
    }

    @Test
    fun testOnCheckTextToggleGlobalIsOff() {
        var nivelList = setOf<Nivel>()

        var globalCheckState: ToggleableState? = null

        viewModel.onCheckText(
            isCheck = true,
            nivel = Nivel.FACIL,
            onChangeNivelSet = { },
            nivelList = nivelList,
            changeGlobalCheck = { globalCheckState = it }
        )

        assertEquals(ToggleableState.Off, globalCheckState)
    }
}