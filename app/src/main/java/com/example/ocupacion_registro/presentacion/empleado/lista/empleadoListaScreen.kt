package com.example.ocupacion_registro.presentacion.empleado.lista

import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ocupacion_registro.domain.empleado.model.Empleado



import com.example.ocupacion_registro.presentacion.ocupacion.list.ocupacionListaUiEvent


@Composable
fun empleadoListaScreen(
    viewModel:empleadoListaViewModel = hiltViewModel(),
    onAddEmpleado: () -> Unit,
    onNavigateToEdit:(Int)->Unit,
    onBack:()-> Unit
){val state by viewModel.state.collectAsStateWithLifecycle()
    empleadoListaBody(
        state = state,
        onEvent = { event ->
            when (event) {
                is ocupacionListaUiEvent.Edit -> onNavigateToEdit(event.id)
                ocupacionListaUiEvent.CreateNew -> onAddEmpleado()
                else -> viewModel.onEvent(event)
            }
        },
        onAddEmpleado = onAddEmpleado,
        onBack=onBack
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun empleadoListaBody(
    state: empleadoListaUiState,
    onEvent: (empleadoListaUiEvent) -> Unit,
    onAddEmpleado: () -> Unit,
    onBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.messageEmpl) {
        state.messageEmpl?.let { message ->
            snackbarHostState.showSnackbar(message)
            onEvent(empleadoListaUiEvent.clearMessageEmpl)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("Empleados")},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atras"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddEmpleado,
                modifier = Modifier.testTag("fab_add")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar Empleado"
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag("loading")
                )
            } else {
                if (state.empleados.isEmpty()) {
                    Text(
                        text = "No hay Empleados",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("empty_message"),
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = state.empleados,
                            key = { it.empleadoId }
                        ) { empleado ->
                            empleadoItem(
                                empleado = empleado,
                                onDelete = {
                                    onEvent(empleadoListaUiEvent.deleteEmpl(empleado.empleadoId))
                                },
                                onClick = {
                                    onEvent(empleadoListaUiEvent.editEmpl(empleado.empleadoId))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun empleadoItem(
    empleado: Empleado,
    onDelete: () -> Unit,
    onClick: ()-> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        onClick=onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = empleado.nombres,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${empleado.fechaIngreso} ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${empleado.sexo}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "${empleado.sueldo} DOP",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )

            }
            IconButton( onClick = onClick){
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar Empleado")
            }
            IconButton(
                onClick = onDelete,
                modifier = Modifier.testTag("btn_delete_${empleado.empleadoId}")
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar Empleado"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun empleadoListaBodyPreview() {
    MaterialTheme {
        val state = empleadoListaUiState(
            isLoading = false,
            empleados = listOf(
                Empleado(empleadoId = 1, nombres = "Leudy Jaquez", fechaIngreso = "05-02-2006", sexo = 'F', sueldo = 90000.0),
                Empleado(empleadoId = 2, nombres = "Maria Juana", fechaIngreso = "03-03-2025", sexo = 'F', sueldo = 75000.0)
            )
        )
        empleadoListaBody(
            state,
            {},
            {},
            onBack = {}

            )
    }
}
