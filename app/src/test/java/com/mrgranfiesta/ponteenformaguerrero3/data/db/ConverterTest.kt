package com.mrgranfiesta.ponteenformaguerrero3.data.db

import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import org.junit.Test
import org.junit.Assert.assertEquals
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo

class ConverterTest {
    val converter = Converter()

    @Test
    fun `test no cambiar el id de ningun Rol`() {
        assertEquals(1, converter.fromRol(Rol.INIT_DATA_USER))
        assertEquals(2, converter.fromRol(Rol.PREMIUN_USER))
        assertEquals(3, converter.fromRol(Rol.STANDAR_USER))
        assertEquals(4, converter.fromRol(Rol.GUEST))
    }

    @Test
    fun `test no cambiar el id de ningun Nivel`() {
        assertEquals(0, converter.fromNivel(Nivel.NINGUNO))
        assertEquals(1, converter.fromNivel(Nivel.FACIL))
        assertEquals(2, converter.fromNivel(Nivel.MEDIO))
        assertEquals(3, converter.fromNivel(Nivel.DIFICIL))
    }

    @Test
    fun `test no cambiar el id de ningun TipoDeEsfuerzo`() {
        assertEquals(0, converter.fromTipoEsfuerzo(TipoEsfuerzo.REPETICION))
        assertEquals(1, converter.fromTipoEsfuerzo(TipoEsfuerzo.CRONO))
    }

    @Test
    fun `test no cambiar el nombre de ningun Musculo`() {
        assertEquals("HOMBRO", Musculo.HOMBRO.name)
        assertEquals("PECHO", Musculo.PECHO.name)
        assertEquals("BICEPS", Musculo.BICEPS.name)
        assertEquals("TRICEPS", Musculo.TRICEPS.name)
        assertEquals("ABDOMINALES", Musculo.ABDOMINALES.name)
        assertEquals("OBLICUOS", Musculo.OBLICUOS.name)
        assertEquals("ESPALDA", Musculo.ESPALDA.name)
        assertEquals("TRAPECIO", Musculo.TRAPECIO.name)
        assertEquals("PIERNA", Musculo.PIERNA.name)
        assertEquals("GLUTEOS", Musculo.GLUTEOS.name)
    }
}