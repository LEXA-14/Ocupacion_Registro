package com.example.ocupacion_registro.presentacion.ocupacion.list

import android.icu.number.Scale
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ocupacionListScreen (
    viewModel: ocupacionListaViewModel=hiltViewModel(),
    onAddOcupacion: ()-> Unit
)
{
    val state by viewModel.state.collectAsStateWithLifecycle()
    ocupacionListaBody(
        state:ocupacionListaUiState,
        onEvent:ocupacionListaUiEvent)->Unit,
    onAddOcupacion:()->Unit)

    {
        val snackbarHostState= remember {SnackbarHostState()}
        LaunchedEffect(state.message) {
            state.message?.let{message->snackbarHostState.showSnackbar(message)
                onEvent(ocupacionListaUiEvent.ClearMessage)
        }
    }
        Scaffold (
            snackbarHostState={snackbarHostState(snackbarHostState)},
            floatingActionButton={
                FloatingActionButton(
                    onClick = onAddOcupacion,
                    modifier = Modifier.testTag("fab_add")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar Ocupacion"
                    )
                }
            }
        ){
            padding->
            Box(
                modifier = Modifier.padding(padding).fillMaxSize()
            ){
                if(state.isLoading){
                    CircularProgressIndicator(
                        modifier = Modifier
                    )
                }
            }
        }
}