package com.example.ocupacion_registro.presentacion.empleado.lista

import com.example.ocupacion_registro.domain.empleado.model.Empleado

data class empleadoListaUiState (
    val isLoading: Boolean,
    val empleados:List<Empleado> =emptyList(),
    val messageEmpl:String?=null,
    val navigateToEditIdEmpl: Int?=null,
    val navigateToCreateEmpl: Boolean=false,
    val error: String?=null
)
