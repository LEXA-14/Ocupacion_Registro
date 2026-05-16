package com.example.ocupacion_registro.domain.empleado.repository

import com.example.ocupacion_registro.domain.empleado.model.Empleado
import kotlinx.coroutines.flow.Flow

interface empleadoRepository {

     fun observeAllEmpl(): Flow<List<Empleado>>

    suspend fun getByIdEmpl(id: Int)
    suspend fun eliminarByIdEmpl(id: Int)
    suspend fun upsertEmpl(Empleado: Empleado)
    suspend fun eliminarEmpl(Empleado: Empleado)

}