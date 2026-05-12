package com.example.ocupacion_registro.navegacion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.test.espresso.UiController
import java.util.concurrent.atomic.LongAdder

@Composable

fun ocupacionNavHost(
    navController: NavHostController= rememberNavController()
) {
    NavHost(
        navController=navController,
        startDestination= screen.ocupacionList
    ){
        composable<screen.ocupacionList>{
            screen.ocupacionListScreen(
                onAddOcupacion={
                    navController.navigate(screen.ocupacionForm)
                }
            )

        }
        composable<screen.ocupacionForm>{
            screen.ocupacionFormScreen(
                onBack={
                    navController.navigateUp()
                }
            )
        }
    }
}