package com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.PARAMS_ID
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.CurrentUser
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.ConfChangePassword
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.ConfCreateAcount
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.ConfCronoScreem
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.ConfCurrentUserInfo
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.ConfEjercicioForm
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.ConfEjercicioInfo
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.ConfEjercicioInfoNoOption
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.ConfEjercicioList
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.ConfMaterialForm
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.ConfMaterialInfo
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.ConfMaterialList
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.ConfRutinaForm
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.ConfRutinaInfo
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.ConfRutinaList
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.ConfSelectEjercicioList
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.ConfSelectMaterial
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.ConfStartRutinaQuestion
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.ConfStatEjercicioInfo
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.ConfStatList
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.ConfStatRutinaInfo
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.FAQResponse
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.FAQsList
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.LoginScreem

@Composable
fun NavigationHost(
    navController: NavHostController,
    snackbarHost: SnackbarHostState,
    currentUser: CurrentUser = CurrentUser
) {
    NavHost(
        navController = navController,
        startDestination = if (currentUser.user == null) {
            AppScreem.Login.route
        } else {
            AppScreem.RutinaListScreem.route
        }
    ) {
        composable(
            route = AppScreem.Login.route
        ) {
            LoginScreem(
                snackbarHost = snackbarHost,
                navController = navController
            )

        }

        composable(
            route = AppScreem.CreateAcount.route
        ) {
            ConfCreateAcount(
                snackbarHost = snackbarHost,
                navController = navController
            )
        }

        composable(
            route = AppScreem.EjercicioListScreem.route
        ) {
            ConfEjercicioList(navController)
        }

        composable(
            route = AppScreem.EjercicioInfoScreem.route,
            arguments = listOf(navArgument(name = PARAMS_ID) {
                type = NavType.StringType
            })
        ) {
            ConfEjercicioInfo(
                navController = navController,
                navBackStackEntry = it
            )
        }

        composable(
            route = AppScreem.EjercicioInfoScreemNoOption.route,
            arguments = listOf(navArgument(name = PARAMS_ID) {
                type = NavType.StringType
            })
        ) {
            ConfEjercicioInfoNoOption(it)
        }

        composable(
            route = AppScreem.EjercicioFormScreem.route,
            arguments = listOf(navArgument(name = PARAMS_ID)
            {
                type = NavType.StringType
            })
        ) {
            ConfEjercicioForm(
                navController = navController,
                navBackStackEntry = it,
                snackbarHost = snackbarHost
            )
        }

        composable(
            route = AppScreem.RutinaListScreem.route
        ) {
            ConfRutinaList(
                navController = navController,
                snackbarHost = snackbarHost
            )
        }

        composable(
            route = AppScreem.RutinaInfoScreem.route,
            arguments = listOf(navArgument(name = PARAMS_ID)
            {
                type = NavType.StringType
            })
        ) {
            ConfRutinaInfo(
                navController = navController,
                navBackStackEntry = it,
                snackbarHost = snackbarHost
            )
        }

        composable(
            route = AppScreem.RutinaFormScreem.route,
            arguments = listOf(navArgument(name = PARAMS_ID)
            {
                type = NavType.StringType
            })
        ) {
            ConfRutinaForm(
                navController = navController,
                snackbarHost = snackbarHost,
                navBackStackEntry = it
            )
        }

        composable(
            route = AppScreem.EjercicioListStepCreateScreem.route
        ) {
            ConfSelectEjercicioList(navController)
        }

        composable(
            route = AppScreem.StartRutinaQuestionScreem.route,
            arguments = listOf(navArgument(name = PARAMS_ID) {
                type = NavType.StringType
            })
        ) {
            ConfStartRutinaQuestion(
                navController = navController,
                navBackStackEntry = it
            )
        }

        composable(
            route = AppScreem.MaterialListScreem.route
        ) {
            ConfMaterialList(navController)
        }

        composable(
            route = AppScreem.MaterialInfoScreem.route,
            arguments = listOf(navArgument(name = PARAMS_ID) {
                type = NavType.StringType
            })
        ) {
            ConfMaterialInfo(
                navController = navController,
                navBackStackEntry = it
            )
        }

        composable(
            route = AppScreem.MaterialFormScreem.route,
            arguments = listOf(navArgument(name = PARAMS_ID) {
                type = NavType.StringType
            })
        ) {
            ConfMaterialForm(
                navController = navController,
                snackbarHost = snackbarHost,
                navBackStackEntry = it
            )
        }

        composable(
            route = AppScreem.MaterialFormScreem.route,
            arguments = listOf(navArgument(name = PARAMS_ID) {
                type = NavType.StringType
            })
        ) {
            ConfMaterialForm(
                navController = navController,
                snackbarHost = snackbarHost,
                navBackStackEntry = it
            )
        }

        composable(
            route = AppScreem.SelectMaterialScreem.route
        ) {
            ConfSelectMaterial(
                navController = navController,
                navBackStackEntry = it
            )
        }

        composable(
            route = AppScreem.CronoScreem.route
        ) {
            ConfCronoScreem(
                navController = navController,
                navBackStackEntry = it
            )
        }

        composable(
            route = AppScreem.StatListScreem.route
        ) {
            ConfStatList(navController)
        }

        composable(
            route = AppScreem.StatRutinaInfoScreem.route,
            arguments = listOf(
                navArgument(name = PARAMS_ID) {
                    type = NavType.StringType
                }
            )
        ) {
            ConfStatRutinaInfo(
                navController = navController,
                navBackStackEntry = it
            )
        }

        composable(
            route = AppScreem.StatEjercicioInfoScreem.route,
            arguments = listOf(
                navArgument(name = PARAMS_ID) {
                    type = NavType.StringType
                }
            )
        ) {
            ConfStatEjercicioInfo(
                navController = navController,
                navBackStackEntry = it
            )
        }

        composable(
            route = AppScreem.FAQsList.route
        ) {
            FAQsList(navController)
        }

        composable(
            route = AppScreem.FAQResponse.route
        ) {
            FAQResponse(navBackStackEntry = it)
        }

        composable(
            route = AppScreem.CurrentUserInfo.route
        ) {
            ConfCurrentUserInfo(navController)
        }

        composable(
            route = AppScreem.ChangePassword.route
        ) {
            ConfChangePassword(navController, snackbarHost)
        }
    }
}