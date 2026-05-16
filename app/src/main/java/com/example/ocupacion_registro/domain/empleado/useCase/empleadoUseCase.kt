package com.example.ocupacion_registro.domain.empleado.useCase

import com.example.ocupacion_registro.domain.empleado.model.Empleado
import com.example.ocupacion_registro.domain.empleado.repository.empleadoRepository
import javax.inject.Inject

class deleteEmpleadoUseCase @Inject constructor(
    private val repository: empleadoRepository
){
    suspend operator fun invoke(id: Int)=repository.eliminarByIdEmpl(id)
}

class upsertEmpleadoUseCase @Inject constructor(
    private val repository: empleadoRepository
    )   {
    suspend operator fun invoke(empleado: Empleado)=repository.upsertEmpl(empleado)
    }

class getEmpleadoUseCase @Inject constructor(
    private val repository: empleadoRepository){

    suspend operator fun invoke(id: Int)=repository.getByIdEmpl(id)
}

class observeEmpleadoUseCase @Inject constructor(
    private val repository: empleadoRepository){

    suspend operator fun invoke()=repository.observeAllEmpl()
}

