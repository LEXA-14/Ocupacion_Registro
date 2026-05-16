package com.example.ocupacion_registro.presentacion.empleado.list

import com.example.ocupacion_registro.domain.empleado.model.Empleado

data class empleadoListUiState (
    val isLoading: Boolean,
    val empleados:List<Empleado> =emptyList(),
    val navigateToEdit: Boolean,
    val navigateToCreate: Boolean,
    val error: Error,
)
