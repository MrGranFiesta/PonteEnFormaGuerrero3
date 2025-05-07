package com.mrgranfiesta.ponteenformaguerrero3.domain.utils

import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Before
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeParseException

class DateUtilsTest {

    @Before
    fun onBefore() {
        mockkObject(StringUtils)
        mockkObject(StringFormat)
    }

    @After
    fun onAfter() {
        unmockkAll()
    }

    @Test
    fun `test getSecondsToMiliseconds convierte correctamente los segundos a milisegundos`(){
        assertEquals(1000L, DateUtils.getSecondsToMiliseconds(1))
    }

    @Test
    fun `test getMinusFormat formatea correctamente la duracion entre dos fechas`(){
        // Given
        val initTime = LocalDateTime.of(2023, 1, 1, 10, 0, 0)
        val endTime = LocalDateTime.of(2023, 1, 1, 12, 30, 45)

        // When
        val result = DateUtils.getMinusFormat(initTime, endTime)

        // Then
        assertEquals("02 : 30 : 45", result)
    }

    @Test
    fun `test toStringFormat con LocalDateTime formatea correctamente`() {
        // Given
        val date = LocalDateTime.of(2023, 5, 15, 14, 30, 25)
        val format = "yyyy-MM-dd HH:mm:ss"

        // When
        val result = DateUtils.toStringFormat(date, format)

        // Then
        assertEquals("2023-05-15 14:30:25", result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test toStringFormat con LocalDateTime y formato invalido debe lanzar excepcion`() {
        // Given
        val date = LocalDateTime.of(2023, 5, 15, 14, 30, 25)
        val invalidFormat = "invalid-format"

        // When & Then
        DateUtils.toStringFormat(date, invalidFormat)
    }

    @Test
    fun `test toStringFormat con LocalDate formatea correctamente`() {
        // Given
        val date = LocalDate.of(2023, 5, 15)
        val format = "yyyy-MM-dd"

        // When
        val result = DateUtils.toStringFormat(date, format)

        // Then
        assertEquals("2023-05-15", result)
    }

    @Test
    fun `test toStringFormat con LocalDate y formato personalizado`() {
        // Given
        val date = LocalDate.of(2023, 5, 15)
        val format = "dd/MM/yyyy"

        // When
        val result = DateUtils.toStringFormat(date, format)

        // Then
        assertEquals("15/05/2023", result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test toStringFormat con LocalDate y formato invalido debe lanzar excepcion`() {
        // Given
        val date = LocalDate.of(2023, 5, 15)
        val invalidFormat = "invalid-format"

        // When & Then
        DateUtils.toStringFormat(date, invalidFormat)
    }

    @Test
    fun `test getLocalDate parsea correctamente una fecha valida`() {
        // Given
        val format = "yyyy-MM-dd"
        val dateString = "2023-05-15"

        // When
        val result = DateUtils.getLocalDate(format, dateString)

        // Then
        assertEquals(LocalDate.of(2023, 5, 15), result)
    }

    @Test
    fun `test getLocalDate con formato personalizado`() {
        // Given
        val format = "dd/MM/yyyy"
        val dateString = "15/05/2023"

        // When
        val result = DateUtils.getLocalDate(format, dateString)

        // Then
        assertEquals(LocalDate.of(2023, 5, 15), result)
    }

    @Test(expected = DateTimeParseException::class)
    fun `test getLocalDate con fecha que no coincide con el formato debe lanzar excepcion`() {
        // Given
        val format = "yyyy-MM-dd"
        val invalidDateString = "15/05/2023"

        // When & Then
        DateUtils.getLocalDate(format, invalidDateString)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test getLocalDate con formato invalido debe lanzar excepcion`() {
        // Given
        val invalidFormat = "invalid-format"
        val dateString = "2023-05-15"

        // When & Then
        DateUtils.getLocalDate(invalidFormat, dateString)
    }

    @Test
    fun `test isNotValidFormat introducir valor valido`() {
        // Given
        val format = "dd/MM/yyyy"
        val dateString = "15/05/2023"

        // When
        val bool = DateUtils.isNotValidFormat(dateString, format)

        // Then
        assertEquals(false, bool)
    }

    @Test
    fun `test isNotValidFormat introducir valor no valido`() {
        // Given
        val invalidFormat = "dd/MM/yyyy"
        val dateString = "invalid-date"

        // When
        val bool = DateUtils.isNotValidFormat(dateString, invalidFormat)

        // Then
        assertEquals(true, bool)
    }

    @Test
    fun `test getMillisToLocalDateTime convierte correctamente milisegundos a formato de fecha`() {
        // Given
        val millis = 1621094400000L // 2021-05-15 18:00:00 UTC
        val format = "yyyy-MM-dd HH:mm:ss"

        // When
        val result = DateUtils.getMillisToLocalDateTime(format, millis)

        // Then
        assertEquals("2021-05-15 18:00:00", result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test getMillisToLocalDateTime con formato invalido debe lanzar excepcion`() {
        // Given
        val millis = 1621094400000L
        val invalidFormat = "invalid-format"

        // When & Then
        DateUtils.getMillisToLocalDateTime(invalidFormat, millis)
    }

    @Test
    fun `test getStringToMillis convierte correctamente fecha a milisegundos`() {
        // Given
        val format = "yyyy-MM-dd"
        val dateString = "2021-05-15"

        val expectedMillis = LocalDate.of(2021, 5, 15)
            .atStartOfDay()
            .atZone(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()

        // When
        val result = DateUtils.getStringToMillis(format, dateString)

        // Then
        assertEquals(expectedMillis, result)
    }

    @Test
    fun `test getStringToMillis con fecha invalida debe retornar null`() {
        // Given
        val format = "yyyy-MM-dd"
        val invalidDateString = "fecha-invalida"

        // When
        val result = DateUtils.getStringToMillis(format, invalidDateString)

        // Then
        assertEquals(null, result)
    }

    @Test
    fun `test getStringToMillis con formato que no coincide con la fecha debe retornar null`() {
        // Given
        val format = "yyyy-MM-dd"
        val invalidDateString = "15/05/2021"

        // When
        val result = DateUtils.getStringToMillis(format, invalidDateString)

        // Then
        assertEquals(null, result)
    }
}