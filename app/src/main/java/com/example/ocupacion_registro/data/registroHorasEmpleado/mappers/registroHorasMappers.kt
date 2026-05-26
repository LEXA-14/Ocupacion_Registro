package com.example.ocupacion_registro.data.registroHorasEmpleado.mappers

import com.example.ocupacion_registro.data.registroHorasEmpleado.registroHorasEmpleadoEntity
import com.example.ocupacion_registro.domain.registroHoras.model.registroHorasEmpleado

object registroHorasMappers {



    fun registroHorasEmpleadoEntity.toDomain() = registroHorasEmpleado(
        registroId = registroId,
        empleadoId = empleadoId,
        horasExtras = horasExtras,
        horasNocturnas = horasNocturnas,
        fecha = fecha
    )


    fun registroHorasEmpleado.toEntity() = registroHorasEmpleadoEntity(
        registroId = registroId,
        empleadoId = empleadoId,
        horasExtras = horasExtras,
        horasNocturnas = horasNocturnas,
        fecha = fecha
    )
}