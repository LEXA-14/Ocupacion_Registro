package com.example.ocupacion_registro.domain.ocupacion.userCase
import com.example.ocupacion_registro.domain.ocupacion.model.Ocupacion
import com.example.ocupacion_registro.domain.ocupacion.repository.OcupacionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveOcupacionUseCase @Inject constructor(
    private val repository: OcupacionRepository
) {
    operator fun invoke(): Flow<List<Ocupacion>> = repository.observeAll()
}