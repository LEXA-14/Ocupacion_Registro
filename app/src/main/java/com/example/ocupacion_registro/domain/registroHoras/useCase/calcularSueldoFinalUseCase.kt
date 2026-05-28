package com.example.ocupacion_registro.domain.registroHoras.useCase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.ocupacion_registro.domain.empleado.repository.empleadoRepository
import com.example.ocupacion_registro.domain.registroHoras.model.registroHorasEmpleado
import com.example.ocupacion_registro.domain.registroHoras.repository.registroHorasRepository
import com.example.ocupacion_registro.domain.registroHoras.validaciones.validateEmpleadoExiste
import com.example.ocupacion_registro.domain.registroHoras.validaciones.validateFecha
import com.example.ocupacion_registro.domain.registroHoras.validaciones.validateHorasExtras
import com.example.ocupacion_registro.domain.registroHoras.validaciones.validateHorasNocturnas
import javax.inject.Inject
import kotlin.math.min

class CalcularSueldoFinalUseCase @Inject constructor(
    private val empleadoRepository: empleadoRepository,
    private val registroHorasRepository: registroHorasRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(
        horas: registroHorasEmpleado
    ): Result<Double> {
        val empleado = empleadoRepository.getByIdEmpl(horas.empleadoId)


        validateEmpleadoExiste(empleado).let { if (!it.isValid) return Result.failure(Exception(it.error)) }
        validateHorasExtras(horas.horasExtras).let { if (!it.isValid) return Result.failure(Exception(it.error)) }
        validateHorasNocturnas(horas.horasNocturnas).let { if (!it.isValid) return Result.failure(Exception(it.error)) }
        validateHorasNocturnas(horas.horasNocturnas, horas.horasExtras).let { if (!it.isValid) return Result.failure(Exception(it.error)) }
        validateFecha(horas.fecha).let { if (!it.isValid) return Result.failure(Exception(it.error)) }


        val valorHora    = ((empleado!!.sueldo /23.83)/8)
        println("HORA PAGADO " + valorHora)

        val horasMax35=min(horas.horasExtras,24.0)
        var pagoExtra = horasMax35 * (valorHora* 1.35)


        if(horas.horasExtras > 24){
            var horas100=horas.horasExtras-24
            pagoExtra +=horas100 * (valorHora * 2)
        }
        if(horas.horasNocturnas>0){
            pagoExtra +=horas.horasNocturnas *(valorHora*0.15)
        }

        val sueldoFinal=empleado.sueldo + pagoExtra
        registroHorasRepository.upsertHoras(horas)

        empleadoRepository.upsertEmpl(
            empleado.copy(sueldoFinal = sueldoFinal)
        )

        return Result.success(pagoExtra)
    }
}