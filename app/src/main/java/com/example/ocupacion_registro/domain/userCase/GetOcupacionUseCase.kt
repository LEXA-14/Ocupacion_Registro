package com.example.ocupacion_registro.domain.userCase

import Ocupacion
import com.example.ocupacion_registro.domain.ocupacion.repository.OcupacionRepository
import javax.inject.Inject

class GetOcupacionUseCase @Inject constructor(
    private val repository: OcupacionRepository
) {
    suspend operator fun invoke(id:Int):Ocupacion?=repository.getOcupacion(id)
}