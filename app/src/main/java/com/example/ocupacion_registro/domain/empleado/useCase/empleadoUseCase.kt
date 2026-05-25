package com.example.ocupacion_registro.domain.empleado.useCase

import com.example.ocupacion_registro.domain.empleado.model.Empleado
import com.example.ocupacion_registro.domain.empleado.repository.empleadoRepository
import com.example.ocupacion_registro.domain.empleado.validacionesEmpleado.validateNombresEmpleado
import com.example.ocupacion_registro.domain.empleado.validacionesEmpleado.validateSexoEmpleado
import com.example.ocupacion_registro.domain.empleado.validacionesEmpleado.validateSueldoEmpleado
import javax.inject.Inject

class deleteEmpleadoUseCase @Inject constructor(
    private val repository: empleadoRepository
){
    suspend operator fun invoke(id: Int)=repository.eliminarByIdEmpl(id)
}

class upsertEmpleadoUseCase @Inject constructor(
    private val repository: empleadoRepository
) {
    suspend operator fun invoke(empleado: Empleado): Result<Int> {

        val nombresNormalizado = empleado.nombres.trim().lowercase()
        val empleadoNormalizado = empleado.copy(nombres = nombresNormalizado)

        // Validar nombres
        val nombresResult = validateNombresEmpleado(nombresNormalizado)
        if (!nombresResult.isValid) {
            return Result.failure(IllegalArgumentException(nombresResult.error))
        }

        // Validar sueldo
        val sueldoResult = validateSueldoEmpleado(empleadoNormalizado.sueldo)
        if (!sueldoResult.isValid) {
            return Result.failure(IllegalArgumentException(sueldoResult.error))
        }

        // Validar sexo
        val sexoResult = validateSexoEmpleado(empleadoNormalizado.sexo)
        if (!sexoResult.isValid) {
            return Result.failure(IllegalArgumentException(sexoResult.error))
        }

        return runCatching { repository.upsertEmpl(empleadoNormalizado) }
    }
}

class getEmpleadoUseCase @Inject constructor(
    private val repository: empleadoRepository){

    suspend operator fun invoke(id: Int)=repository.getByIdEmpl(id)
}

class observeEmpleadoUseCase @Inject constructor(
    private val repository: empleadoRepository){

    suspend operator fun invoke()=repository.observeAllEmpl()
}

