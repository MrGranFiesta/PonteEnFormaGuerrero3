package com.mrgranfiesta.ponteenformaguerrero3.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.IconTopBar
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar.SEARCH
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar.SEARCH_FILTER
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.ModeTopBar.TITLE
import com.mrgranfiesta.ponteenformaguerrero3.domain.singleton.TopBarVMSingle
import com.mrgranfiesta.ponteenformaguerrero3.domain.viewmodel.components.TopBarVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    scope: CoroutineScope,
    drawerState: DrawerState,
    navController: NavController,
    topBarVM: TopBarVM = TopBarVMSingle.getTopBarVM()
) {
    val mode by topBarVM.modeTopBar.collectAsState()
    val iconTopBar by topBarVM.iconTopBar.collectAsState()
    val title by topBarVM.titleBar.collectAsState()
    val actionIconList by topBarVM.actionIconList.collectAsState()
    val actionExtra by topBarVM.actionExtra.collectAsState()

    val interationIconNav = topBarVM.interationIconNav

    LaunchedEffect(interationIconNav) {
        interationIconNav.interactions.collectLatest { interaction ->
            topBarVM.actionNavIcon(
                interaction = interaction,
                navController = navController,
                drawerState = drawerState,
                scope = scope
            )
        }
    }

    TopAppBar(
        modifier = Modifier.padding(
            bottom = 2.dp
        ),
        title = {
            when (mode) {
                SEARCH, SEARCH_FILTER -> {
                    SearchAppBar(mode)
                }

                TITLE -> {
                    Text(
                        textAlign = TextAlign.Center,
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        navigationIcon = {
            if (iconTopBar != IconTopBar.NONE) {
                IconButton(
                    onClick = {
                        topBarVM.generateAction(
                            scope = scope,
                            drawerState = drawerState,
                            navController = navController,
                            actionExtra = actionExtra
                        )
                    },
                    interactionSource = interationIconNav
                ) {
                    Icon(
                        imageVector = topBarVM.generateImg(iconTopBar),
                        contentDescription = topBarVM.generateContentDescription()
                    )
                }
            }
        },
        actions = {
            actionIconList.forEach {
                val interionSource = MutableInteractionSource()
                LaunchedEffect(interionSource) {
                    interionSource.interactions.collectLatest { interaction ->
                        topBarVM.actionListIcons(
                            interaction = interaction,
                            action = it.action
                        )
                    }
                }
                IconButtonWithTooltip(
                    tooltipText = it.tooltipText,
                    onClick = { },
                    interactionSource = interionSource
                ) {
                    Icon(imageVector = it.icon, contentDescription = it.name)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}