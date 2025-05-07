package com.mrgranfiesta.ponteenformaguerrero3.domain.utils

import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.Black
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.White
import org.junit.Test

class CastingClassTest {

    @Test
    fun `test colorByNivel no cambiar el color por el nivel`(){
        assert(White == CastingClass.colorByNivel(Nivel.NINGUNO))
        assert(White == CastingClass.colorByNivel(Nivel.FACIL))
        assert(Black == CastingClass.colorByNivel(Nivel.MEDIO))
        assert(White == CastingClass.colorByNivel(Nivel.DIFICIL))
    }

    @Test
    fun `test unidadToTipoEsfuerzo castea bien a tipo de esfuerzo repeticion`(){
        assert(TipoEsfuerzo.REPETICION == CastingClass.unidadToTipoEsfuerzo("Repeticiones"))
    }

    @Test
    fun `test unidadToTipoEsfuerzo castea bien a tipo de esfuerzo crono`(){
        assert(TipoEsfuerzo.CRONO == CastingClass.unidadToTipoEsfuerzo("Segundos"))
    }

    @Test
    fun `test unidadToTipoEsfuerzo castea bien a tipo de esfuerzo por defecto`(){
        assert(TipoEsfuerzo.REPETICION == CastingClass.unidadToTipoEsfuerzo("1234"))
    }
}