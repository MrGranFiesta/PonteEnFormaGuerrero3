package com.mrgranfiesta.ponteenformaguerrero3.domain.factory

import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Rol
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.TipoEsfuerzo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.CategoryFaqs
import com.mrgranfiesta.ponteenformaguerrero3.domain.dtobean.faqs.Faq
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.AppScreem

class ListManager {
    companion object {
        fun getMusculoListCheck(): List<Musculo> = listOf<Musculo>(
            Musculo.ANTEBRAZO,
            Musculo.ABDOMINALES,
            Musculo.BICEPS,
            Musculo.ESPALDA,
            Musculo.GLUTEOS,
            Musculo.HOMBRO,
            Musculo.OBLICUOS,
            Musculo.PECHO,
            Musculo.PIERNA,
            Musculo.TRAPECIO,
            Musculo.TRICEPS
        )


        fun getNivelList(): List<Nivel> = listOf(
            Nivel.DIFICIL,
            Nivel.MEDIO,
            Nivel.FACIL,
            Nivel.NINGUNO
        )

        fun getDrawerOpt(rol: Rol): List<AppScreem> {
            val drawerList = ArrayList<AppScreem>()
            drawerList.add(AppScreem.EjercicioListScreem)
            drawerList.add(AppScreem.RutinaListScreem)
            drawerList.add(AppScreem.MaterialListScreem)
            drawerList.add(AppScreem.StatListScreem)
            if (Rol.isEditable(rol)) {
                drawerList.add(AppScreem.CurrentUserInfo)
            }
            drawerList.add(AppScreem.FAQsList)
            return drawerList
        }

        fun getListStepUnid(): List<String> = listOf(
            TipoEsfuerzo.REPETICION.unidad,
            TipoEsfuerzo.CRONO.unidad
        )

        fun getFaqsFactory(): List<Faq> = listOf(
            Faq(
                id = 1,
                category = CategoryFaqs.USER,
                question = "¿Cómo iniciar sesión?"
            ),
            Faq(
                id = 2,
                category = CategoryFaqs.USER,
                question = "¿Cómo crear una cuenta de usuario?"
            ),
            Faq(
                id = 3,
                category = CategoryFaqs.USER,
                question = "¿Cómo entrar como usuario invitado?"
            ),
            Faq(
                id = 4,
                category = CategoryFaqs.USER,
                question = "¿Cómo ver la información de mi usuario?"
            ),
            Faq(
                id = 5,
                category = CategoryFaqs.USER,
                question = "¿Cómo editar la contraseña?"
            ),
            Faq(
                id = 6,
                category = CategoryFaqs.USER,
                question = "¿Cómo editar la foto de perfil?"
            ),
            Faq(
                id = 7,
                category = CategoryFaqs.USER,
                question = "¿Cómo editar el nombre de usuario?"
            ),
            Faq(
                id = 8,
                category = CategoryFaqs.USER,
                question = "¿Qué ventajas tiene ser premium?"
            ),
            Faq(
                id = 9,
                category = CategoryFaqs.USER,
                question = "¿Cómo suscribirse al plan premium?"
            ),
            Faq(
                id = 10,
                category = CategoryFaqs.USER,
                question = "¿Cómo cancelar una suscripción premium?"
            ),
            Faq(
                id = 11,
                category = CategoryFaqs.EJERCICIO,
                question = "¿Cómo agregar un ejercicio?"
            ),
            Faq(
                id = 12,
                category = CategoryFaqs.EJERCICIO,
                question = "¿Cómo modificar un ejercicio?"
            ),
            Faq(
                id = 13,
                category = CategoryFaqs.EJERCICIO,
                question = "¿Cómo borrar un ejercicio?"
            ),
            Faq(
                id = 14,
                category = CategoryFaqs.EJERCICIO,
                question = "¿Cómo visualizar un ejercicio?"
            ),
            Faq(
                id = 15,
                category = CategoryFaqs.EJERCICIO,
                question = "¿Cómo agregar o eliminar un material a un ejercicio?"
            ),
            Faq(
                id = 16,
                category = CategoryFaqs.MATERIAL,
                question = "¿Cómo agregar un material?"
            ),
            Faq(
                id = 17,
                category = CategoryFaqs.MATERIAL,
                question = "¿Cómo modificar un material?"
            ),
            Faq(
                id = 18,
                category = CategoryFaqs.MATERIAL,
                question = "¿Cómo borrar un material?"
            ),
            Faq(
                id = 19,
                category = CategoryFaqs.MATERIAL,
                question = "¿Cómo visualizar un material?"
            ),
            Faq(
                id = 20,
                category = CategoryFaqs.RUTINA,
                question = "¿Cómo agregar una rutina?"
            ),
            Faq(
                id = 21,
                category = CategoryFaqs.RUTINA,
                question = "¿Cómo modificar una rutina?"
            ),
            Faq(
                id = 22,
                category = CategoryFaqs.RUTINA,
                question = "¿Cómo borrar una rutina?"
            ),
            Faq(
                id = 23,
                category = CategoryFaqs.RUTINA,
                question = "¿Cómo visualizar una rutina?"
            ),
            Faq(
                id = 24,
                category = CategoryFaqs.RUTINA,
                question = "¿Cómo agregar y configurar un ejercicio en una rutina?"
            ),
            Faq(
                id = 25,
                category = CategoryFaqs.RUTINA,
                question = "¿Cómo eliminar un paso de una rutina?"
            ),
            Faq(
                id = 26,
                category = CategoryFaqs.RUTINA,
                question = "¿Cómo modificar un paso de una rutina?"
            ),
            Faq(
                id = 27,
                category = CategoryFaqs.RUTINA,
                question = "¿Cómo reordenar los ejercicios de una rutina?"
            ),
            Faq(
                id = 28,
                category = CategoryFaqs.CRONO,
                question = "¿Cómo funciona el crónometro?"
            ),
            Faq(
                id = 29,
                category = CategoryFaqs.CRONO,
                question = "¿Cómo navegar entre ejercicios?"
            ),
            Faq(
                id = 30,
                category = CategoryFaqs.CRONO,
                question = "¿Cómo ver materiales de una rutina en ejecución?"
            ),
            Faq(
                id = 31,
                category = CategoryFaqs.CRONO,
                question = "¿Que pasa cuando salgo de la app y estoy en mitad de un entrenamiento?"
            ),
            Faq(
                id = 32,
                category = CategoryFaqs.STATS,
                question = "¿Cómo se generan las estadísticas?"
            ),
            Faq(
                id = 33,
                category = CategoryFaqs.STATS,
                question = "¿Cómo borrar una estadística?"
            ),
            Faq(
                id = 34,
                category = CategoryFaqs.STATS,
                question = "¿Cómo visualizar una estadísticas?"
            )
        )
    }
}