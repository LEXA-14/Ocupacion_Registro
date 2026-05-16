package com.example.ocupacion_registro.domain.empleado.repository

import com.example.ocupacion_registro.domain.empleado.model.Empleado
import kotlinx.coroutines.flow.Flow

interface empleadoRepository {

     fun observeAllEmpl(): Flow<List<Empleado>>

    suspend fun getByIdEmpl(id: Int): Empleado?
    suspend fun eliminarByIdEmpl(id: Int)
    suspend fun upsertEmpl(Empleado: Empleado): Int
    suspend fun eliminarEmpl(Empleado: Empleado)

}