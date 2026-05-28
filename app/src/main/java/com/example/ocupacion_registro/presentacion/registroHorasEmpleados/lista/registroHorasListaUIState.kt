package com.example.ocupacion_registro.presentacion.registroHorasEmpleados.lista

import com.example.ocupacion_registro.domain.empleado.model.Empleado
import com.example.ocupacion_registro.domain.registroHoras.model.registroHorasEmpleado

 data class registroHorasListaUIState (

    val isLoading: Boolean = false,
    val registros: List<registroHorasEmpleado> = emptyList(),
    val empleados: List<Empleado> = emptyList(),
    val message: String? = null,
    val error: String? = null
    )
