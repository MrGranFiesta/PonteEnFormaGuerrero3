package com.mrgranfiesta.ponteenformaguerrero3.domain.core.vtf

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Before

class DateVisualTransformationTest {
    @RelaxedMockK
    private lateinit var dataVisualTransformation : DateVisualTransformation

    @Before
    fun setUp() {
        dataVisualTransformation = DateVisualTransformation()
    }

    @Test
    fun `test la fecha es correcta`() {
        val input = AnnotatedString("12345678")
        val result = dataVisualTransformation.filter(input)
        assertEquals("12/34/5678", result.text.text)
    }

    @Test
    fun `test introducir una fecha menor a 8 caracteres`() {
        val input = AnnotatedString("1234")
        val result = dataVisualTransformation.filter(input)
        assertEquals("12/34/", result.text.text)
    }

    @Test
    fun `test introducir una cadena en blanco`() {
        val input = AnnotatedString("")
        val result = dataVisualTransformation.filter(input)
        assertEquals("", result.text.text)
    }

    @Test
    fun `test introducir una fecha mayor a 8 caracteres`() {
        val input = AnnotatedString("123456789")
        val result = dataVisualTransformation.filter(input)
        assertEquals("12/34/5678", result.text.text)
    }

    @Test
    fun `test introducir caractereres ilegales`() {
        val input = AnnotatedString("a1b2c3d4")
        val result = dataVisualTransformation.filter(input)
        assertEquals("a1/b2/c3d4", result.text.text)
    }

    @Test
    fun `test mappeo de la posicion del cursor original al trasformado`() {
        val method = dataVisualTransformation::class.java.getDeclaredMethod("getOffsetMapping")
        method.isAccessible = true

        val mapping = method.invoke(dataVisualTransformation) as OffsetMapping
        assertEquals(0, mapping.originalToTransformed(0))
        assertEquals(1, mapping.originalToTransformed(1))
        assertEquals(3, mapping.originalToTransformed(2))
        assertEquals(4, mapping.originalToTransformed(3))
        assertEquals(6, mapping.originalToTransformed(4))
        assertEquals(7, mapping.originalToTransformed(5))
        assertEquals(8, mapping.originalToTransformed(6))
        assertEquals(9, mapping.originalToTransformed(7))
        assertEquals(10, mapping.originalToTransformed(8))
    }

    @Test
    fun `test mappeo de la posicion del cursor trasformado al original`() {
        val method = dataVisualTransformation::class.java.getDeclaredMethod("getOffsetMapping")
        method.isAccessible = true

        val mapping = method.invoke(dataVisualTransformation) as OffsetMapping
        assertEquals("aaa", 0, mapping.transformedToOriginal(0))
        assertEquals(1, mapping.transformedToOriginal(1))
        assertEquals(2, mapping.transformedToOriginal(2))
        assertEquals(2, mapping.transformedToOriginal(3))
        assertEquals(3, mapping.transformedToOriginal(4))
        assertEquals(4, mapping.transformedToOriginal(5))
        assertEquals(4, mapping.transformedToOriginal(6))
        assertEquals(5, mapping.transformedToOriginal(7))
        assertEquals(6, mapping.transformedToOriginal(8))
        assertEquals(7, mapping.transformedToOriginal(9))
        assertEquals(8, mapping.transformedToOriginal(10))
        assertEquals(8, mapping.transformedToOriginal(11))
    }

}