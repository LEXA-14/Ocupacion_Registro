package com.example.ocupacion_registro.data.empleado.local.mappers

import com.example.ocupacion_registro.data.empleado.local.empleadoEntity
import com.example.ocupacion_registro.domain.empleado.model.Empleado

object empleadoMappers {

    fun empleadoEntity.toDomain(): Empleado= Empleado(

        empleadoId = this.empleadoId,
        nombres = nombres,
        fechaIngreso =fechaIngreso,
        sexo =sexo,
        sueldo =sueldo,
        sueldoFinal = sueldoFinal
        )

    fun Empleado.toEntity(): empleadoEntity=
        empleadoEntity(
            empleadoId=empleadoId,
            nombres=nombres,
            fechaIngreso=fechaIngreso,
            sexo=sexo,
            sueldo=sueldo,
            sueldoFinal=sueldoFinal
        )
    }

