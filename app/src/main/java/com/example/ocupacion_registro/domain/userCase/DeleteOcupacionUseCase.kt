package com.example.ocupacion_registro.domain.userCase

import com.example.ocupacion_registro.domain.ocupacion.repository.OcupacionRepository
import javax.inject.Inject

class DeleteOcupacionUseCase  @Inject constructor(
    private val repository: OcupacionRepository)
{
    suspend  operator fun invoke(id: Int)=repository.delete(id)
}