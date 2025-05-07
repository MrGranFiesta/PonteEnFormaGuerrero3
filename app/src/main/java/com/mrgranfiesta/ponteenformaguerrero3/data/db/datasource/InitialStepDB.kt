package com.mrgranfiesta.ponteenformaguerrero3.data.db.datasource

import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.StepBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo

class InitialStepDB {
    companion object {
        fun getListDataDefaultStep(): List<StepBean> {
            return listOf(
                //Flexiones Basicas
                StepBean(
                    idStep = 10_000_000,
                    idRutina = 2_000_000,
                    idEjercicio = 1_999_964,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 1
                ),
                StepBean(
                    idStep = 9_999_999,
                    idRutina = 2_000_000,
                    idEjercicio = 1_999_998,
                    cantidad = 30,
                    serie = 3,
                    tipo = TipoEsfuerzo.CRONO,
                    ordenExecution = 2
                ),
                //Flexiones Media
                StepBean(
                    idStep = 9_999_998,
                    idRutina = 1_999_999,
                    idEjercicio = 1_999_964,
                    cantidad = 12,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 1
                ),
                StepBean(
                    idStep = 9_999_998,
                    idRutina = 1_999_999,
                    idEjercicio = 1_999_999,
                    cantidad = 12,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 1
                ),
                StepBean(
                    idStep = 9_999_997,
                    idRutina = 1_999_999,
                    idEjercicio = 1_999_977,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 2
                ),
                StepBean(
                    idStep = 9_999_996,
                    idRutina = 1_999_999,
                    idEjercicio = 1_999_976,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 3
                ),
                StepBean(
                    idStep = 9_999_995,
                    idRutina = 1_999_999,
                    idEjercicio = 1_999_978,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 4
                ),
                StepBean(
                    idStep = 9_999_994,
                    idRutina = 1_999_999,
                    idEjercicio = 1_999_998,
                    cantidad = 45,
                    serie = 3,
                    tipo = TipoEsfuerzo.CRONO,
                    ordenExecution = 5
                ),
                //Flexiones Dificil
                StepBean(
                    idStep = 9_999_993,
                    idRutina = 1_999_998,
                    idEjercicio = 1_999_977,
                    cantidad = 12,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 1
                ),
                StepBean(
                    idStep = 9_999_992,
                    idRutina = 1_999_998,
                    idEjercicio = 1_999_976,
                    cantidad = 12,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 2
                ),
                StepBean(
                    idStep = 9_999_991,
                    idRutina = 1_999_998,
                    idEjercicio = 1_999_978,
                    cantidad = 12,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 3
                ),
                StepBean(
                    idStep = 9_999_989,
                    idRutina = 1_999_998,
                    idEjercicio = 1_999_973,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 5
                ),
                StepBean(
                    idStep = 9_999_988,
                    idRutina = 1_999_998,
                    idEjercicio = 1_999_965,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 6
                ),
                StepBean(
                    idStep = 9_999_987,
                    idRutina = 1_999_998,
                    idEjercicio = 1_999_998,
                    cantidad = 60,
                    serie = 3,
                    tipo = TipoEsfuerzo.CRONO,
                    ordenExecution = 7
                ),
                //Abdominales basicos
                StepBean(
                    idStep = 9_999_986,
                    idRutina = 1_999_997,
                    idEjercicio = 1_999_999,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 1
                ),
                StepBean(
                    idStep = 9_999_985,
                    idRutina = 1_999_997,
                    idEjercicio = 1_999_998,
                    cantidad = 30,
                    serie = 3,
                    tipo = TipoEsfuerzo.CRONO,
                    ordenExecution = 2
                ),
                StepBean(
                    idStep = 9_999_984,
                    idRutina = 1_999_997,
                    idEjercicio = 1_999_992,
                    cantidad = 30,
                    serie = 3,
                    tipo = TipoEsfuerzo.CRONO,
                    ordenExecution = 3
                ),
                //Abdominales medio
                StepBean(
                    idStep = 9_999_983,
                    idRutina = 1_999_996,
                    idEjercicio = 1_999_999,
                    cantidad = 12,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 1
                ),
                StepBean(
                    idStep = 9_999_982,
                    idRutina = 1_999_996,
                    idEjercicio = 1_999_998,
                    cantidad = 60,
                    serie = 3,
                    tipo = TipoEsfuerzo.CRONO,
                    ordenExecution = 2
                ),
                StepBean(
                    idStep = 9_999_981,
                    idRutina = 1_999_996,
                    idEjercicio = 1_999_992,
                    cantidad = 60,
                    serie = 3,
                    tipo = TipoEsfuerzo.CRONO,
                    ordenExecution = 3
                ),
                StepBean(
                    idStep = 9_999_980,
                    idRutina = 1_999_996,
                    idEjercicio = 1_999_974,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 4
                ),
                //Abdominales dificil
                StepBean(
                    idStep = 9_999_979,
                    idRutina = 1_999_995,
                    idEjercicio = 1_999_999,
                    cantidad = 20,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 1
                ),
                StepBean(
                    idStep = 9_999_978,
                    idRutina = 1_999_995,
                    idEjercicio = 1_999_998,
                    cantidad = 90,
                    serie = 3,
                    tipo = TipoEsfuerzo.CRONO,
                    ordenExecution = 2
                ),
                StepBean(
                    idStep = 9_999_977,
                    idRutina = 1_999_995,
                    idEjercicio = 1_999_992,
                    cantidad = 90,
                    serie = 3,
                    tipo = TipoEsfuerzo.CRONO,
                    ordenExecution = 3
                ),
                StepBean(
                    idStep = 9_999_976,
                    idRutina = 1_999_995,
                    idEjercicio = 1_999_974,
                    cantidad = 12,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 4
                ),
                //Pierna Basica
                StepBean(
                    idStep = 9_999_975,
                    idRutina = 1_999_994,
                    idEjercicio = 1_999_981,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 1
                ),
                StepBean(
                    idStep = 9_999_974,
                    idRutina = 1_999_994,
                    idEjercicio = 1_999_996,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 2
                ),
                StepBean(
                    idStep = 9_999_973,
                    idRutina = 1_999_994,
                    idEjercicio = 1_999_996,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 3
                ),
                StepBean(
                    idStep = 9_999_972,
                    idRutina = 1_999_994,
                    idEjercicio = 1_999_994,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 4
                ),
                StepBean(
                    idStep = 9_999_971,
                    idRutina = 1_999_994,
                    idEjercicio = 1_999_995,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 5
                ),
                //Pierna Media
                StepBean(
                    idStep = 9_999_970,
                    idRutina = 1_999_993,
                    idEjercicio = 1_999_990,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 1
                ),
                StepBean(
                    idStep = 9_999_969,
                    idRutina = 1_999_993,
                    idEjercicio = 1_999_996,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 2
                ),
                StepBean(
                    idStep = 9_999_968,
                    idRutina = 1_999_993,
                    idEjercicio = 1_999_982,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 3
                ),
                StepBean(
                    idStep = 9_999_967,
                    idRutina = 1_999_993,
                    idEjercicio = 1_999_994,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 4
                ),
                StepBean(
                    idStep = 9_999_966,
                    idRutina = 1_999_993,
                    idEjercicio = 1_999_995,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 5
                ),
                //Pierna Dificil
                StepBean(
                    idStep = 9_999_965,
                    idRutina = 1_999_992,
                    idEjercicio = 1_999_990,
                    cantidad = 12,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 1
                ),
                StepBean(
                    idStep = 9_999_964,
                    idRutina = 1_999_992,
                    idEjercicio = 1_999_996,
                    cantidad = 12,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 2
                ),
                StepBean(
                    idStep = 9_999_963,
                    idRutina = 1_999_992,
                    idEjercicio = 1_999_982,
                    cantidad = 12,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 3
                ),
                StepBean(
                    idStep = 9_999_962,
                    idRutina = 1_999_992,
                    idEjercicio = 1_999_994,
                    cantidad = 12,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 4
                ),
                StepBean(
                    idStep = 9_999_961,
                    idRutina = 1_999_992,
                    idEjercicio = 1_999_995,
                    cantidad = 12,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 5
                ),
                //Dominadas facil
                StepBean(
                    idStep = 9_999_960,
                    idRutina = 1_999_991,
                    idEjercicio = 1_999_963,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 1
                ),
                StepBean(
                    idStep = 9_999_959,
                    idRutina = 1_999_991,
                    idEjercicio = 1_999_997,
                    cantidad = 30,
                    serie = 3,
                    tipo = TipoEsfuerzo.CRONO,
                    ordenExecution = 2
                ),
                StepBean(
                    idStep = 9_999_958,
                    idRutina = 1_999_991,
                    idEjercicio = 1_999_980,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 3
                ),
                //Muscle Up
                StepBean(
                    idStep = 9_999_957,
                    idRutina = 1_999_990,
                    idEjercicio = 1_999_979,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 1
                ),
                StepBean(
                    idStep = 9_999_956,
                    idRutina = 1_999_990,
                    idEjercicio = 1_999_980,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 2
                ),
                //Preparación para bandera
                StepBean(
                    idStep = 9_999_955,
                    idRutina = 1_999_989,
                    idEjercicio = 1_999_970,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 1
                ),
                StepBean(
                    idStep = 9_999_954,
                    idRutina = 1_999_989,
                    idEjercicio = 1_999_969,
                    cantidad = 30,
                    serie = 3,
                    tipo = TipoEsfuerzo.CRONO,
                    ordenExecution = 2
                ),
                //Bandera
                StepBean(
                    idStep = 9_999_953,
                    idRutina = 1_999_988,
                    idEjercicio = 1_999_970,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 1
                ),
                StepBean(
                    idStep = 9_999_952,
                    idRutina = 1_999_988,
                    idEjercicio = 1_999_969,
                    cantidad = 30,
                    serie = 3,
                    tipo = TipoEsfuerzo.CRONO,
                    ordenExecution = 2
                ),
                StepBean(
                    idStep = 9_999_951,
                    idRutina = 1_999_988,
                    idEjercicio = 1_999_993,
                    cantidad = 30,
                    serie = 3,
                    tipo = TipoEsfuerzo.CRONO,
                    ordenExecution = 3
                ),
                //dominada media
                StepBean(
                    idStep = 9_999_950,
                    idRutina = 1_999_987,
                    idEjercicio = 1_999_970,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 1
                ),
                StepBean(
                    idStep = 9_999_949,
                    idRutina = 1_999_987,
                    idEjercicio = 1_999_975,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 2
                ),
                StepBean(
                    idStep = 9_999_948,
                    idRutina = 1_999_987,
                    idEjercicio = 1_999_980,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 3
                ),
                //Dominada dificil
                StepBean(
                    idStep = 9_999_947,
                    idRutina = 1_999_986,
                    idEjercicio = 1_999_993,
                    cantidad = 30,
                    serie = 3,
                    tipo = TipoEsfuerzo.CRONO,
                    ordenExecution = 1
                ),
                StepBean(
                    idStep = 9_999_946,
                    idRutina = 1_999_986,
                    idEjercicio = 1_999_968,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 2
                ),
                StepBean(
                    idStep = 9_999_945,
                    idRutina = 1_999_986,
                    idEjercicio = 1_999_967,
                    cantidad = 8,
                    serie = 3,
                    tipo = TipoEsfuerzo.REPETICION,
                    ordenExecution = 3
                ),
            )
        }
    }
}