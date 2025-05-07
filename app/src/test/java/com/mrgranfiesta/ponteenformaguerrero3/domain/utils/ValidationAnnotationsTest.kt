package com.mrgranfiesta.ponteenformaguerrero3.domain.utils

import androidx.compose.material3.SnackbarHostState
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.FormatEmail
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.MaxLenght
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.NotEmpty
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.SecuredCharEmailSQL
import com.mrgranfiesta.ponteenformaguerrero3.domain.annotatios.SecuredCharSQL
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ValidationAnnotationsTest {
    @RelaxedMockK
    private lateinit var snackbarHostState: SnackbarHostState

    private lateinit var testScope: CoroutineScope

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        testScope = TestScope(UnconfinedTestDispatcher())
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    data class AnotationTest(
        @FormatEmail("email")
        @SecuredCharEmailSQL("email")
        val email: String,
        @MaxLenght(15, "nombre")
        @NotEmpty("nombre")
        @SecuredCharSQL("nombre")
        val name: String
    )

    @Test
    fun `test isValid termina bien`() {
        val test = AnotationTest(
            email = "prueba@correo.com",
            name = "usuario"
        )
        assertTrue(
            ValidationAnnotations.isValid(
                bean = test,
                snackbarHost = snackbarHostState,
                viewModelScope = testScope
            )
        )
    }

    @Test
    fun `test isValid devuelve cada anotacion`() {
        val list = listOf(
            Pair(
                "@FormatEmail",
                AnotationTest(
                    email = "prueba#prueba@correo.com.correo",
                    name = "usuario"
                )
            ),
            Pair(
                "@SecuredCharEmailSQL",
                AnotationTest(
                    email = "prueba#prueba@correo.com",
                    name = "usuario"
                )
            ),
            Pair(
                "@MaxLenght",
                AnotationTest(
                    email = "prueba@correo.com",
                    name = "usuario laaaargo"
                )
            ),
            Pair(
                "@NotEmpty",
                AnotationTest(
                    email = "prueba@correo.com",
                    name = ""
                )
            ),
            Pair(
                "@SecuredCharSQL",
                AnotationTest(
                    email = "prueba@correo.com",
                    name = "usuario@#~€&"
                )
            )
        )
        list.forEach {
            assertEquals(
                "Test ${it.first}",
                false,
                ValidationAnnotations.isValid(
                    bean = it.second,
                    snackbarHost = snackbarHostState,
                    viewModelScope = testScope
                )
            )
        }
    }
}