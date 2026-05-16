package com.example.ocupacion_registro.data.empleado.local.repository

import androidx.constraintlayout.helper.widget.Flow
import com.example.ocupacion_registro.data.empleado.local.empleadoDao
import com.example.ocupacion_registro.domain.empleado.repository.empleadoRepository
import javax.inject.Inject

class empleadoRepositoryImp  @Inject constructor(
    private val localDataSource: empleadoDao
) : empleadoRepository {
    override fun observeAllEmpl(): Flow<List<Empleado>> {
        return localDataSource.observeAllEmpl().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getByIdEmpl(id: Int): Empleado? {
        return localDataSource.getByIdEmpl(id)?.toDomain()
    }

    override suspend fun upsertEmpl(empleado: Empleado): Int {
        localDataSource.upsert(empleado.toEntity())
        return empleado.empleadoId ?: 0
    }

    override suspend fun eliminarByIdEmpl(id: Int) {
        localDataSource.deleteIdEmpl(id)
    }

    override suspend fun eliminarEmpl(empleado: Empleado) {
        localDataSource.deleteEmpl(empleado.toEntity())
    }


}