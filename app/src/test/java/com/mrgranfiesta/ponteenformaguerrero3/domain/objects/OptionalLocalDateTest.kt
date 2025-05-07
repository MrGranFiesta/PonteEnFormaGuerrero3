package com.mrgranfiesta.ponteenformaguerrero3.domain.objects

import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.DateUtils
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class OptionalLocalDateTest {

    @Test
    fun `test getOptionalByString control de excepcion por malformato de fecha`() {
        //Given
        mockkObject(DateUtils)

        //When
        val result = OptionalLocalDate.getOptionalByString("ddMMyyyy", "invalid-format")
        val bool = result is OptionalLocalDate.Empty

        //Verify
        assertEquals(true, bool)
        verify {
            DateUtils.getLocalDate(
                format = any(),
                date = any()
            )
        }
    }

    @Test
    fun `test getOptionalByString retorna Date`() {
        val bool =
            OptionalLocalDate.getOptionalByString("ddMMyyyy", "11021234") is OptionalLocalDate.Date
        assertEquals(true, bool)
    }

    @Test
    fun `test getStringDateOrEmpty retornar una cadena vacia de un Empty`() {
        val optionalDate = OptionalLocalDate.Empty
        val bool = optionalDate.getStringDateOrEmpty("ddMMyyyy").isEmpty()
        assertEquals(true, bool)
    }

    @Test
    fun `test getStringDateOrEmpty obtener una fecha`() {
        val optionalDate = OptionalLocalDate.getOptionalByString("ddMMyyyy", "11021234")
        val bool = optionalDate.getStringDateOrEmpty("ddMMyyyy").isNotEmpty()
        assertEquals(true, bool)
    }

    @Test
    fun `test getStringDateOrEmpty retornar una cadena vacia por una excepcion de malformado`() {
        mockkObject(DateUtils)

        val optionalDate = OptionalLocalDate.getOptionalByString("ddMMyyyy", "11021234")
        val bool = optionalDate.getStringDateOrEmpty("invalid-format").isEmpty()

        assertEquals(true, bool)
        verify {
            DateUtils.toStringFormat(any<LocalDate>(), any())
        }
    }

    @Test
    fun `test isBefore la fecha es anterior`() {
        val optionalDate1 = OptionalLocalDate.getOptionalByString("ddMMyyyy", "11021234")
        val optionalDate2 = OptionalLocalDate.getOptionalByString("ddMMyyyy", "12021234")
        assertEquals(true, optionalDate1.isBefore(optionalDate2))
    }

    @Test
    fun `test isBefore la fecha no es anterior`() {
        val optionalDate1 = OptionalLocalDate.getOptionalByString("ddMMyyyy", "12021234")
        val optionalDate2 = OptionalLocalDate.getOptionalByString("ddMMyyyy", "11021234")
        assertEquals(false, optionalDate1.isBefore(optionalDate2))
    }

    @Test
    fun `test isBefore optional operador es empty devuelve false`() {
        val optionalDate1 = OptionalLocalDate.Empty
        val optionalDate2 = OptionalLocalDate.getOptionalByString("ddMMyyyy", "11021234")
        assertEquals(false, optionalDate1.isBefore(optionalDate2))
    }

    @Test
    fun `test isBefore el optional del aegumento es empty devuelve false`() {
        val optionalDate1 = OptionalLocalDate.getOptionalByString("ddMMyyyy", "11021234")
        val optionalDate2 = OptionalLocalDate.Empty
        assertEquals(false, optionalDate1.isBefore(optionalDate2))
    }
}