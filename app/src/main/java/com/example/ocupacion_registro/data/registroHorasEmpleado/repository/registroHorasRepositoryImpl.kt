package com.example.ocupacion_registro.data.registroHorasEmpleado.repository

import com.example.ocupacion_registro.data.registroHorasEmpleado.mappers.registroHorasMappers.toDomain
import com.example.ocupacion_registro.data.registroHorasEmpleado.mappers.registroHorasMappers.toEntity
import com.example.ocupacion_registro.data.registroHorasEmpleado.registroHorasEmpleadoDao
import com.example.ocupacion_registro.domain.registroHoras.model.registroHorasEmpleado
import com.example.ocupacion_registro.domain.registroHoras.repository.registroHorasRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.sql.DataSource

class registroHorasRepositoryImpl @Inject constructor(
    private val localDataSource: registroHorasEmpleadoDao
): registroHorasRepository {
    override fun observeAllHoras(): Flow<List<registroHorasEmpleado>> =
        localDataSource.observeAll().map { list -> list.filterNotNull().map { it.toDomain() } }



    override suspend fun getByIdHoras(id: Int): registroHorasEmpleado? =
        localDataSource.getbyId(id)?.toDomain()


    override suspend fun eliminarByIdHoras(id: Int) {
        val registro=localDataSource.getbyId(id)?:return
        localDataSource.deleteById(registro)
    }

    override suspend fun upsertHoras(horas: registroHorasEmpleado): Int {
        localDataSource.upsert(horas.toEntity())
        return horas.registroId
    }

    override suspend fun eliminarHoras(horas: registroHorasEmpleado) {
        localDataSource.deleteById(horas.toEntity())
    }

    override fun observeByEmpleado(empleadoId: Int): Flow<List<registroHorasEmpleado>> =
        localDataSource.observeByEmpleado(empleadoId).map { list -> list.map { it.toDomain() } }
}