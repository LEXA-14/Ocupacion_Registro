package com.example.ocupacion_registro.domain.ocupacion.repository

import Ocupacion
import kotlinx.coroutines.flow.Flow

interface OcupacionRepository {

    fun observeAll(): Flow<List<Ocupacion>>
    suspend fun getOcupacion(id:Int):Ocupacion?
    suspend fun upsert(ocupacion: Ocupacion):Int
    suspend fun delete(id: Int)
    suspend fun exists(id:Int): Boolean
    suspend fun existsByDescripcion(descripcion: String, excludeId: Int): Boolean


}