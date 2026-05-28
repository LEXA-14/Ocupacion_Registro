package com.example.ocupacion_registro.domain.empleado.model

data class Empleado
    (
    val empleadoId: Int =0,
    val nombres: String,
    val fechaIngreso: String,
    val sexo: Char,
    val sueldo: Double,
    val sueldoFinal: Double?
            )