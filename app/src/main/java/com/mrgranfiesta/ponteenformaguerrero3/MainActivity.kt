package com.mrgranfiesta.ponteenformaguerrero3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.core.viewmodel.LifecicleActionVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.screem.SplashCreatedScreenVM
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.Drawer
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.FloatingButton
import com.mrgranfiesta.ponteenformaguerrero3.presentation.components.TopBar
import com.mrgranfiesta.ponteenformaguerrero3.presentation.navigations.NavigationHost
import com.mrgranfiesta.ponteenformaguerrero3.presentation.screem.SplashCreatedScreen
import com.mrgranfiesta.ponteenformaguerrero3.ui.theme.PonteEnFormaGuerrero3Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PonteEnFormaGuerrero3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    override fun onPause() {
        LifecicleActionVM.getLifecicleActionVM().executionOnPause()
        super.onPause()
    }

    override fun onResume() {
        LifecicleActionVM.getLifecicleActionVM().executionOnResume()
        super.onResume()
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM(),
    splashCreatedScreenVM: SplashCreatedScreenVM = hiltViewModel()
) {
    val isNotFinish by splashCreatedScreenVM.isNotFinish.collectAsState()

    if (isNotFinish) {
        SplashCreatedScreen()
    } else {
        val isGestures by topBarVM.isGestures.collectAsState()

        val navController = rememberNavController()
        val snackbarHost = remember { SnackbarHostState() }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Drawer(
                        scope = scope,
                        drawerState = drawerState,
                        navController = navController,
                    )
                }
            },
            scrimColor = MaterialTheme.colorScheme.background,
            gesturesEnabled = isGestures
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                snackbarHost = {
                    SnackbarHost(snackbarHost, snackbar = { data ->
                        Snackbar(
                            snackbarData = data,
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.onSurface,
                        )
                    })
                },
                topBar = {
                    TopBar(
                        scope = scope,
                        drawerState = drawerState,
                        navController = navController,
                    )
                },
                floatingActionButton = { FloatingButton() }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    NavigationHost(
                        navController = navController,
                        snackbarHost = snackbarHost
                    )
                }
            }
        }
    }
}