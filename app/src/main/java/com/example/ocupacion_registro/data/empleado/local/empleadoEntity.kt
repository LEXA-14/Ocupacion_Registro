package com.example.ocupacion_registro.data.empleado.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName="Empleado")
data class empleadoEntity (
    @PrimaryKey(autoGenerate = true)
    val empleadoId:Int=0,
    val nombres: String,
    val fechaIngreso: String,
    val sexo: Char,
    val sueldo: Double,
    val sueldoFinal: Double?=sueldo

)
