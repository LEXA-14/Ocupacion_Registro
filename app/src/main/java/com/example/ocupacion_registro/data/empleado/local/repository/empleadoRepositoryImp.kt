package com.example.ocupacion_registro.data.empleado.local.repository

import com.example.ocupacion_registro.data.empleado.local.empleadoDao
import com.example.ocupacion_registro.domain.empleado.repository.empleadoRepository
import javax.inject.Inject

class empleadoRepositoryImp  @Inject constructor(
    private val localDataSource: empleadoDao
) : empleadoRepository {

}