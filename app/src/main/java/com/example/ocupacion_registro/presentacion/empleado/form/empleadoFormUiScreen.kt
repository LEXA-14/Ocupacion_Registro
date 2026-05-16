package com.example.ocupacion_registro.presentacion.empleado.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun empleadoFormScreen(
    viewModel: empleadoFormViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.saved) {
        if (state.saved) onBack()
    }

    LaunchedEffect(state.deleted) {
        if (state.deleted) onBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isNew) "Nuevo Empleado" else "Editar Empleado") },
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

            OutlinedTextField(
                value = state.nombres ?: "",
                onValueChange = { viewModel.onEvent(empleadoFormUiEvent.DescripcionChanged(it)) },
                label = { Text("Nombres") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.sueldo?.toString() ?: "",
                onValueChange = { viewModel.onEvent(empleadoFormUiEvent.SueldoChanged(it)) },
                label = { Text("Sueldo (DOP)") },
                modifier = Modifier.fillMaxWidth()
            )

            DatePickerField(
                selectedDate = state.fechaIngreso ?: "",
                onDateSelected = { viewModel.onEvent(empleadoFormUiEvent.FechaChanged(it)) }
            )

            SexoSelector(
                selectedSexo = state.sexo?.toString() ?: "",
                onSexoSelected = { viewModel.onEvent(empleadoFormUiEvent.SexoChanged(it.first())) }
            )

            Button(
                onClick = { viewModel.onEvent(empleadoFormUiEvent.Save) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isSaved
            ) {
                Text("Guardar")
            }
        }
    }
}

@Composable
fun DatePickerField(
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val formatted = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
            onDateSelected(formatted)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    OutlinedTextField(
        value = selectedDate.ifEmpty { "Seleccionar fecha" },
        onValueChange = {},
        readOnly = true,
        label = { Text("Fecha de Ingreso") },
        trailingIcon = {
            IconButton(onClick = { datePickerDialog.show() }) {
                Icon(Icons.Default.DateRange, contentDescription = "Fecha")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SexoSelector(
    selectedSexo: String,
    onSexoSelected: (String) -> Unit
) {
    Column {
        Text("Sexo", style = MaterialTheme.typography.labelMedium)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedSexo == "M",
                    onClick = { onSexoSelected("M") }
                )
                Text("Masculino")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedSexo == "F",
                    onClick = { onSexoSelected("F") }
                )
                Text("Femenino")
            }
        }
    }
}