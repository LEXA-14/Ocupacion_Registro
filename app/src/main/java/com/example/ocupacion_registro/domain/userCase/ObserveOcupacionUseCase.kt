package com.example.ocupacion_registro.domain.userCase
import Ocupacion
import com.example.ocupacion_registro.domain.ocupacion.repository.OcupacionRepository
import kotlinx.coroutines.flow.Flow

class ObserveOcupacionUseCase(
    private val repository: OcupacionRepository
) {
    operator fun invoke(): Flow<List<Ocupacion>> = repository.observeAll()
}