package com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppScreem(
    val route: String,
    val title: String = "",
    val imageVector: ImageVector
) {
    data object Login :
        AppScreem(
            route = "Login",
            title = "Iniciar Sesión",
            imageVector = Icons.AutoMirrored.Filled.DirectionsRun
        ) {
        fun createRoute() = "Login"
    }

    data object CreateAcount :
        AppScreem(
            route = "CreateAcount",
            title = "Crear Cuenta",
            imageVector = Icons.AutoMirrored.Filled.DirectionsRun
        ) {
        fun createRoute() = "CreateAcount"
    }

    data object EjercicioListScreem :
        AppScreem(
            route = "EjercicioList",
            title = "Lista de ejercicios",
            imageVector = Icons.AutoMirrored.Filled.DirectionsRun
        ) {
        fun createRoute() = "EjercicioList"
    }

    data object EjercicioInfoScreem :
        AppScreem(
            route = "EjercicioInfo/{id}",
            imageVector = Icons.Filled.Home
        ) {
        fun createRoute(id: Long) = "EjercicioInfo/$id"
    }

    data object EjercicioInfoScreemNoOption :
        AppScreem(
            route = "EjercicioInfoNoOption/{id}",
            imageVector = Icons.Filled.Home
        ) {
        fun createRoute(id: Long) = "EjercicioInfoNoOption/$id"
    }

    data object EjercicioFormScreem :
        AppScreem(
            route = "EjercicioForm/{id}",
            imageVector = Icons.Filled.Home
        ) {
        fun createRoute(id: Long) = "EjercicioForm/$id"
    }

    data object RutinaListScreem :
        AppScreem(
            route = "RutinaList",
            title = "Lista de rutinas",
            imageVector = Icons.Filled.LocalFireDepartment
        ) {
        fun createRoute() = "RutinaList"
    }


    data object RutinaInfoScreem :
        AppScreem(
            route = "RutinaInfo/{id}",
            imageVector = Icons.Filled.Home
        ) {
        fun createRoute(id: Long) = "RutinaInfo/$id"
    }

    data object RutinaFormScreem :
        AppScreem(
            route = "RutinaForm/{id}",
            imageVector = Icons.Filled.Home
        ) {
        fun createRoute(id: Long) = "RutinaForm/$id"
    }

    data object EjercicioListStepCreateScreem :
        AppScreem(
            route = "EjercicioListStepCreate",
            imageVector = Icons.Filled.Home
        ) {
        fun createRoute() = "EjercicioListStepCreate"
    }

    data object StartRutinaQuestionScreem :
        AppScreem(
            route = "StartRutinaQuestion/{id}",
            imageVector = Icons.Filled.Home
        ) {
        fun createRoute(id: Long) = "StartRutinaQuestion/$id"
    }

    data object MaterialListScreem :
        AppScreem(
            route = "MaterialListScreem",
            title = "Lista de Materiales",
            imageVector = Icons.Filled.FitnessCenter
        ) {
        fun createRoute() = "MaterialList"
    }

    data object MaterialInfoScreem :
        AppScreem(
            route = "MaterialInfo/{id}",
            imageVector = Icons.Filled.Home
        ) {
        fun createRoute(id: Long) = "MaterialInfo/$id"
    }

    data object MaterialFormScreem :
        AppScreem(
            route = "MaterialForm/{id}",
            imageVector = Icons.Filled.Home
        ) {
        fun createRoute(id: Long) = "MaterialForm/$id"
    }

    data object SelectMaterialScreem :
        AppScreem(
            route = "SelectMaterial",
            imageVector = Icons.Filled.Home
        ) {
        fun createRoute() = "SelectMaterial"
    }

    data object CronoScreem :
        AppScreem(
            route = "SelectMaterial/{id}",
            imageVector = Icons.Filled.Home
        ) {
        fun createRoute(id: Long) = "SelectMaterial/$id"
    }

    data object StatListScreem :
        AppScreem(
            route = "StatList",
            title = "Estadisticas",
            imageVector = Icons.Filled.BarChart
        ) {
        fun createRoute() = "StatList"
    }

    data object StatRutinaInfoScreem :
        AppScreem(
            route = "StatRutinaInfo/{id}",
            imageVector = Icons.Filled.Home
        ) {
        fun createRoute(id: Long) = "StatRutinaInfo/$id"
    }

    data object StatEjercicioInfoScreem :
        AppScreem(
            route = "StatEjercicioInfoScreem/{id}",
            imageVector = Icons.Filled.Home
        ) {
        fun createRoute(id: Long) = "StatEjercicioInfoScreem/$id"
    }

    data object FAQsList :
        AppScreem(
            route = "FAQsList",
            title = "FAQs",
            imageVector = Icons.AutoMirrored.Filled.Help
        ) {
        fun createRoute() = "FAQsList"
    }

    data object FAQResponse :
    AppScreem(
        route = "FAQResponse/{id}",
        title = "FAQs",
        imageVector = Icons.AutoMirrored.Filled.Help
    ) {
        fun createRoute(id: Int) = "FAQResponse/$id"
    }

    data object CurrentUserInfo :
        AppScreem(
            route = "UserInfo",
            title = "Perfil",
            imageVector = Icons.Filled.Person
        ) {
        fun createRoute() = "UserInfo"
    }

    data object ChangePassword :
        AppScreem(
            route = "ChangePassword",
            title = "Cambio de contraseña",
            imageVector = Icons.Filled.Person
        ) {
        fun createRoute() = "ChangePassword"
    }
}
