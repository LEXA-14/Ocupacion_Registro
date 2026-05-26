package com.example.ocupacion_registro.domain.registroHoras.repository

import com.example.ocupacion_registro.domain.registroHoras.model.registroHorasEmpleado
import kotlinx.coroutines.flow.Flow

interface registroHorasRepository {
    fun observeAllHoras(): Flow<List<registroHorasEmpleado>>
    fun observeByEmpleado(empleadoId: Int): Flow<List<registroHorasEmpleado>>

    suspend fun getByIdHoras(id: Int): registroHorasEmpleado?
    suspend fun eliminarByIdHoras(id: Int)
    suspend fun upsertHoras(horas: registroHorasEmpleado): Int
    suspend fun eliminarHoras(horas: registroHorasEmpleado)
}