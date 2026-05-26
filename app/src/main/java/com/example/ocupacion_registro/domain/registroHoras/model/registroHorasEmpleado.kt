package com.example.ocupacion_registro.domain.registroHoras.model

import java.time.LocalDate

data class registroHorasEmpleado (

    val registroId: Int,
    val empleadoId: Int,
    val horasExtras: Double,
    val horasNocturnas: Double,
    val fecha: LocalDate


)
