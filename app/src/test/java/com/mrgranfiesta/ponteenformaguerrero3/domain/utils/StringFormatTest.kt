package com.mrgranfiesta.ponteenformaguerrero3.domain.utils

import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import org.junit.Test
import org.junit.Assert.assertEquals

class StringFormatTest {

    @Test
    fun `test musculosSetToString convierte a musculos a texto`(){
        val set = setOf(Musculo.OBLICUOS, Musculo.PIERNA, Musculo.PECHO)
        val result = StringUtils.musculosSetToString(set)

        assertEquals("OBLICUOS, PIERNA, PECHO", result)
    }

    @Test
    fun `test musculosSetToString lista de musculos vacia`(){
        val set = setOf<Musculo>()
        val result = StringUtils.musculosSetToString(set)

        assertEquals(true, result.isEmpty())
    }

    @Test
    fun `test segToMinForrmatter tiempo con min y seg`(){
        val result = StringUtils.segToMinForrmatter(200)
        assertEquals("3 min 20 seg", result)
    }

    @Test
    fun `test segToMinForrmatter tiempo con seg`(){
        val result = StringUtils.segToMinForrmatter(20)
        assertEquals("20 seg", result)
    }

    @Test
    fun `test timeMinDigitalFormat tiempo con min y seg`(){
        val result = StringUtils.timeMinDigitalFormat(200_000)

        assertEquals("03 : 20", result)
    }

    @Test
    fun `test timeMinDigitalFormat tiempo con seg`(){
        val result = StringUtils.timeMinDigitalFormat(20_000)

        assertEquals("20", result)
    }

    @Test
    fun `test timeHourDigitalFormat formatea correctamente las horas, minutos y segundos`() {
        // Given
        val millis = 9_045_000L // 2h 30m 45s

        // When
        val result = StringUtils.timeHourDigitalFormat(millis)

        // Then
        assertEquals("02 : 30 : 45", result)
    }

    @Test
    fun `test timeHourDigitalFormat con valores cero`() {
        // Given
        val millis = 0L

        // When
        val result = StringUtils.timeHourDigitalFormat(millis)

        // Then
        assertEquals("00 : 00 : 00", result)
    }

    @Test
    fun `test timeHourDigitalFormat con valores grandes`() {
        // Given
        val millis = 90_061_000L // 25h 1m 1s

        // When
        val result = StringUtils.timeHourDigitalFormat(millis)

        // Then
        assertEquals("25 : 01 : 01", result)
    }

    @Test
    fun `test toTipoEsfuerzoForrmatter con tipo de esfuerzo crono`() {
        val result = StringUtils.toTipoEsfuerzoForrmatter(200, TipoEsfuerzo.CRONO)
        assertEquals("3 min 20 seg", result)
    }

    @Test
    fun `test toTipoEsfuerzoForrmatter con tipo de esfuerzo repeticion`() {
        val result = StringUtils.toTipoEsfuerzoForrmatter(15, TipoEsfuerzo.REPETICION)
        assertEquals("15 reps", result)
    }
}