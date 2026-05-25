package com.example.ocupacion_registro.domain.empleado.validacionesEmpleado

data class ValidationResultEmpleado(
    val isValid: Boolean,
    val error: String?
)

fun validateNombresEmpleado(nombres: String): ValidationResultEmpleado{
    return when {
        nombres.isBlank()-> ValidationResultEmpleado(false,"El nombre no puede estar vacio")
        (nombres.length)<3 -> ValidationResultEmpleado(false,"Los nombres deben de tener mas de 3 caracteres")
        else -> ValidationResultEmpleado(true, null)
    }
}

fun validateSueldoEmpleado(sueldo: Double): ValidationResultEmpleado{
    return when{
        sueldo<=0 -> ValidationResultEmpleado(false,"Sueldo no puede ser 0 o menor que 0")
        else -> ValidationResultEmpleado(true,null)
    }
}

fun validateSexoEmpleado(sexo:Char): ValidationResultEmpleado{
    return when{
        sexo!='F' && sexo!='M' -> ValidationResultEmpleado(false,"El sexo solo puede ser Femenino (F) o Masculino(M)")
        else -> ValidationResultEmpleado(true,null)
    }
}