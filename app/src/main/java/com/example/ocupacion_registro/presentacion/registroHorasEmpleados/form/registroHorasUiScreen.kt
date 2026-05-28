package com.example.ocupacion_registro.presentacion.registroHorasEmpleados.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun registroHorasScreen(
    viewModel: registroHorasViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) onBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Horas") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atras"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Info del empleado
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = state.nombreEmpleado,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Sueldo base: ${state.sueldoEmpleado} DOP",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            OutlinedTextField(
                value = state.horasExtras,
                onValueChange = { viewModel.onEvent(registroHorasUIEvent.OnHorasExtrasChange(it)) },
                label = { Text("Horas Extras") },
                isError = state.horasExtrasError != null,
                supportingText = state.horasExtrasError?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.horasNocturnas,
                onValueChange = { viewModel.onEvent(registroHorasUIEvent.OnHorasNocturnasChange(it)) },
                label = { Text("Horas Nocturnas") },
                isError = state.horasNocturnasError != null,
                supportingText = state.horasNocturnasError?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
                modifier = Modifier.fillMaxWidth()
            )


            Text(
                text = "* Las horas nocturnas están incluidas dentro de las horas extras",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )


            state.totalExtra?.let { total ->
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Pago extra",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = "${"%.2f".format(total)} DOP",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Sueldo final: ${"%.2f".format(state.sueldoEmpleado + total)} DOP",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // Error general
            state.error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { viewModel.onEvent(registroHorasUIEvent.OnCalcular) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Calcular")
                }


                Button(
                    onClick = { viewModel.onEvent(registroHorasUIEvent.OnGuardar) },
                    modifier = Modifier.weight(1f),
                    enabled = state.totalExtra != null && !state.isLoading
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp))
                    } else {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}

