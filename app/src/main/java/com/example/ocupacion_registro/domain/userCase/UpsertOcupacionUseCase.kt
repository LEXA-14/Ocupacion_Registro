package com.example.ocupacion_registro.domain.userCase


import Ocupacion
import com.example.ocupacion_registro.domain.ocupacion.repository.OcupacionRepository
import com.example.ocupacion_registro.domain.validacion.validateDescripcion
import com.example.ocupacion_registro.domain.validacion.validateSueldo
import javax.inject.Inject

class UpsertOcupacionUseCase @Inject constructor(
    private val repository: OcupacionRepository
) {
    suspend operator fun invoke(ocupacion: Ocupacion): Result<Int> {

        // Normalizar descripcion antes de validar
        val descripcionNormalizada = ocupacion.descripcion.trim().lowercase()
        val ocupacionNormalizada = ocupacion.copy(descripcion = descripcionNormalizada)

        // Validar descripcion
        val descripcionResult = validateDescripcion(descripcionNormalizada)
        if (!descripcionResult.isValid) {
            return Result.failure(IllegalArgumentException(descripcionResult.error))
        }

        // Validar sueldo
        val sueldoResult = validateSueldo(ocupacionNormalizada.sueldo.toString())
        if (!sueldoResult.isValid) {
            return Result.failure(IllegalArgumentException(sueldoResult.error))
        }

        // Validar duplicado por descripcion (ya normalizada)
        val existeDuplicado = repository.existsByDescripcion(
            descripcion = descripcionNormalizada,
            excludeId = ocupacionNormalizada.ocupacionId
        )
        if (existeDuplicado) {
            return Result.failure(IllegalArgumentException("Ya existe una ocupación con esa descripción"))
        }

        return runCatching { repository.upsert(ocupacionNormalizada) }
    }
}