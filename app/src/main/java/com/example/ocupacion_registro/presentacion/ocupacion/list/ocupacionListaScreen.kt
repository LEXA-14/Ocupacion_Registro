package com.example.ocupacion_registro.presentacion.ocupacion.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.material.icons.Icons
import com.example.ocupacion_registro.domain.ocupacion.model.Ocupacion
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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle



@Composable
fun OcupacionListScreen(
    viewModel:ocupacionListaViewModel = hiltViewModel(),
    onAddOcupacion: () -> Unit,
    onNavigateToEdit:(Int)->Unit,
    onNavigateToEmpleados: () -> Unit,
    onNavigateToRegistroHoras:()-> Unit
){val state by viewModel.state.collectAsStateWithLifecycle()
    OcupacionListBody(
        state = state,
        onEvent = { event ->
            when (event) {
                is ocupacionListaUiEvent.Edit -> onNavigateToEdit(event.id)
                ocupacionListaUiEvent.CreateNew -> onAddOcupacion()
                else -> viewModel.onEvent(event)
            }
        },
        onAddOcupacion = onAddOcupacion,
        onNavigateToEmpleados=onNavigateToEmpleados,
        onNavigateToRegistroHoras = onNavigateToRegistroHoras
    )

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OcupacionListBody(
    state: ocupacionListUiState,
    onEvent: (ocupacionListaUiEvent) -> Unit,
    onAddOcupacion: () -> Unit,
    onNavigateToEmpleados: () -> Unit,
    onNavigateToRegistroHoras: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ocupaciones") },
                actions = {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Empleados") },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                            onClick = {
                                menuExpanded = false
                                onNavigateToEmpleados()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Registro de Horas") },
                            leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                            onClick = {
                                menuExpanded = false
                                onNavigateToRegistroHoras()
                            }
                        )
                    }
                }
            )
        },

        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
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

                if (state.ocupaciones.isEmpty()) {
                    Text(
                        text = "No hay Ocupaciones",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("empty_message"),
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    AnimatedVisibility(
                        visible = state.ocupaciones.isNotEmpty(),
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(
                                items = state.ocupaciones,
                                key = { it.ocupacionId }
                            ) { ocupacion ->
                                OcupacionItem(
                                    ocupacion = ocupacion,
                                    onDelete = {
                                        onEvent(ocupacionListaUiEvent.Delete(ocupacion.ocupacionId))
                                    },
                                    onClick = {
                                        onEvent(ocupacionListaUiEvent.Edit(ocupacion.ocupacionId))
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OcupacionItem(
    ocupacion: Ocupacion,
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
                    text = ocupacion.descripcion,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${ocupacion.sueldo} DOP",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
             IconButton( onClick = onClick){
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar Ocupacion")
            }
            IconButton(
                onClick = onDelete,
                modifier = Modifier.testTag("btn_delete_${ocupacion.ocupacionId}")
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar Ocupacion"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OcupacionListBodyPreview() {
    MaterialTheme {
        val state = ocupacionListUiState(
            isLoading = false,
            ocupaciones = listOf(
                Ocupacion(ocupacionId = 1, descripcion = "Medico", sueldo = 90000.0),
                Ocupacion(ocupacionId = 2, descripcion = "Ingeniero", sueldo = 75000.0)
            )
        )
        OcupacionListBody(state,
            {},
            {},
            onNavigateToEmpleados = {},
            onNavigateToRegistroHoras = {}

        )
    }
}