package com.example.ocupacion_registro.domain.empleado.model

import java.time.LocalDate
import java.time.LocalTime
import java.util.Date

data class Empleado
    (
        val empleadoId:Int=0,
        val nombres: String,
        val fechaIngreso: String,
        val sexo: Char,
        val sueldo: Double
            )