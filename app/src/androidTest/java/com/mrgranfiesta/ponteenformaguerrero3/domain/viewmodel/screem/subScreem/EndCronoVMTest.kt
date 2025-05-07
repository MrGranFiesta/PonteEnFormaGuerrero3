package com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.subScreem

import androidx.test.platform.app.InstrumentationRegistry
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class EndCronoVMTest {
    lateinit var viewModel: EndCronoVM

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = EndCronoVM()
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun testInitDataCorrect() {
        val statusField = viewModel.javaClass.getDeclaredField("status")
        statusField.isAccessible = true

        assertTrue(statusField.getBoolean(viewModel))

        viewModel.soundVictory(
            context = InstrumentationRegistry.getInstrumentation().targetContext
        )

        assertFalse(statusField.getBoolean(viewModel))
    }
}