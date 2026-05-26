package com.example.ocupacion_registro.domain.registroHoras.validaciones

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.ocupacion_registro.domain.empleado.model.Empleado
import java.time.LocalDate

data class ValidationResultRegistroHoras(
    val isValid: Boolean,
    val error: String?
)

fun validateHorasExtras(horasExtras: Double): ValidationResultRegistroHoras {
    return when {
        horasExtras < 0 -> ValidationResultRegistroHoras(false, "Las horas extras no pueden ser negativas")
        else -> ValidationResultRegistroHoras(true, null)
    }
}

fun validateHorasNocturnas(horasNocturnas: Double): ValidationResultRegistroHoras {
    return when {
        horasNocturnas < 0 -> ValidationResultRegistroHoras(false, "Las horas nocturnas no pueden ser negativas")
        else -> ValidationResultRegistroHoras(true, null)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun validateFecha(fecha: LocalDate): ValidationResultRegistroHoras {
    return when {
        fecha.isAfter(LocalDate.now()) -> ValidationResultRegistroHoras(false, "La fecha no puede ser futura")
        else -> ValidationResultRegistroHoras(true, null)
    }
}

fun validateEmpleadoExiste(empleado: Empleado?): ValidationResultRegistroHoras {
    return when {
        empleado == null -> ValidationResultRegistroHoras(false, "El empleado no existe")
        else -> ValidationResultRegistroHoras(true, null)
    }
}