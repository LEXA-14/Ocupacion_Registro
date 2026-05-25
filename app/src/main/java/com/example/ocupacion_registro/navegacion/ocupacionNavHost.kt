package com.example.ocupacion_registro.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ocupacion_registro.presentacion.empleado.form.empleadoFormScreen
import com.example.ocupacion_registro.presentacion.empleado.lista.empleadoListaScreen
import com.example.ocupacion_registro.presentacion.ocupacion.form.OcupacionFormScreen
import com.example.ocupacion_registro.presentacion.ocupacion.list.OcupacionListScreen


@Composable
fun ocupacionNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = screen.ocupacionList
    ) {

        composable<screen.ocupacionList> {

            OcupacionListScreen(

                onAddOcupacion = {
                    navController.navigate(screen.ocupacionForm(ocupacionId = 0))
                },
                onNavigateToEdit = { id ->
                    navController.navigate(screen.ocupacionForm(ocupacionId = id))
                },
                onNavigateToEmpleados = {
                    navController.navigate(screen.empleadoLista)
                    }
            )
        }

        composable<screen.ocupacionForm> { backStackEntry ->

            OcupacionFormScreen(

                onBack = {
                    navController.navigateUp()
                }
            )
        }


        composable<screen.empleadoLista> {
            empleadoListaScreen(
                onAddEmpleado = {
                    navController.navigate(screen.empleadoForm(empleadoId = 0))
                },
                onNavigateToEdit = { id ->
                    navController.navigate(screen.empleadoForm(empleadoId = id))
                },
                onBack = {
                    navController.navigateUp()
                }


            )
        }

        composable<screen.empleadoForm> {
            empleadoFormScreen(
                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }}

