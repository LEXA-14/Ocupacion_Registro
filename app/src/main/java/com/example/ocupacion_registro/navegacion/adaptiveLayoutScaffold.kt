package com.example.ocupacion_registro.navegacion

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShortNavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun AdaptiveAppScaffold(
    navController: NavHostController,
    widthSizeClass: WindowWidthSizeClass,
    content: @Composable (PaddingValues) -> Unit
) {
    val navItems = listOf(
        NavItem(
            label    = "Ocupaciones",
            icon     = Icons.Default.Work,
            route    = "com.example.ocupacion_registro.navegacion.screen.ocupacionList",
            routeObj = screen.ocupacionList
        ),
        NavItem(
            label    = "Empleados",
            icon     = Icons.Default.People,
            route    = "com.example.ocupacion_registro.navegacion.screen.empleadoLista",
            routeObj = screen.empleadoLista
        ),
        NavItem(
            label    = "Registros",
            icon     = Icons.Default.Schedule,
            route    = "com.example.ocupacion_registro.navegacion.screen.registroHorasLista",
            routeObj = screen.registroHorasLista
        )
    )

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    when (widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        navItems.forEach { item ->
                            NavigationBarItem(
                                selected = currentRoute?.startsWith(item.route) == true,
                                onClick  = {
                                    navController.navigate(item.routeObj) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState    = true
                                    }
                                },
                                icon  = { Icon(item.icon, contentDescription = item.label) },
                                label = { Text(item.label) }
                            )
                        }
                    }
                }
            ) { padding -> content(padding) }
        }

        else -> {
            Row(Modifier.fillMaxSize()) {
                NavigationRail {
                    Spacer(Modifier.weight(1f))
                    navItems.forEach { item ->
                        NavigationRailItem(
                            selected = currentRoute?.startsWith(item.route) == true,
                            onClick  = {
                                navController.navigate(item.routeObj) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState    = true
                                }
                            },
                            icon  = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) }
                        )
                    }
                    Spacer(Modifier.weight(1f))
                }
                Scaffold { padding -> content(padding) }
            }
        }
    }
}

data class NavItem(
    val label:    String,
    val icon:     ImageVector,
    val route:    String,
    val routeObj: Any
)

