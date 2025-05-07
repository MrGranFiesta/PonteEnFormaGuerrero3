package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components

import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.AnswerState
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

class TextFieldAnswerVMTest {
    private lateinit var viewModel: TextFieldAnswerVM

    @Before
    fun onBefore() {
        viewModel = TextFieldAnswerVM()
    }

    @Test
    fun testInitDataStatusCorrect() = runTest {
        val cursorField = TextFieldAnswerVM::class.declaredMemberProperties.first { it.name == "cursor" }
        cursorField.isAccessible = true

        viewModel.initData(AnswerState.ASK_LATER)
        val cursorValue = cursorField.get(viewModel) as Int
        assertNotEquals(0, cursorValue)

        val expectedCursor = AnswerState.getListOpt().indexOf(AnswerState.ASK_LATER)
        assertEquals(expectedCursor, cursorValue)

        viewModel.initData(AnswerState.NO)

        val cursorValue2 = cursorField.get(viewModel) as Int
        val expectedCursor2 = AnswerState.getListOpt().indexOf(AnswerState.NO)
        assertNotEquals(expectedCursor2, cursorValue2)
    }

    @Test
    fun testChangeLeft() = runTest {
        viewModel.initData(AnswerState.ASK_LATER)

        var answer : AnswerState? = null
        viewModel.changeLeft { answer = it }
        assertEquals(AnswerState.NO, answer)
        viewModel.changeLeft { answer = it }
        assertEquals(AnswerState.YES, answer)
        viewModel.changeLeft { answer = it }
        assertEquals(AnswerState.ASK_LATER, answer)
        viewModel.changeLeft { answer = it }
        assertEquals(AnswerState.NO, answer)
    }

    @Test
    fun testChangeRight() = runTest {
        viewModel.initData(AnswerState.ASK_LATER)

        var answer : AnswerState? = null
        viewModel.changeLeft { answer = it }
        assertEquals(AnswerState.NO, answer)
        viewModel.changeLeft { answer = it }
        assertEquals(AnswerState.YES, answer)
        viewModel.changeLeft { answer = it }
        assertEquals(AnswerState.ASK_LATER, answer)
        viewModel.changeLeft { answer = it }
        assertEquals(AnswerState.NO, answer)
    }
}