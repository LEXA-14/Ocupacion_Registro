package com.example.ocupacion_registro.data.ocupacion.local.repository

import com.example.ocupacion_registro.domain.ocupacion.model.Ocupacion
import com.example.ocupacion_registro.data.ocupacion.local.ocupacionDao
import com.example.ocupacion_registro.data.ocupacion.local.ocupacionEntity
import com.example.ocupacion_registro.domain.ocupacion.repository.OcupacionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class ocupacionRepositoryImp @Inject constructor(
    private  val localDataSource: ocupacionDao
) : OcupacionRepository{

    override fun observeAll(): Flow<List<Ocupacion>> {
        return localDataSource.observeAll().map {
            entities -> entities.map{it.toDomain()}
        }
    }

    override suspend fun getOcupacion(id: Int): Ocupacion? {
         return localDataSource.getById(id)?.toDomain()
    }

    override suspend fun upsert(ocupacion: Ocupacion): Int {
        localDataSource.upsert(ocupacion.toEntity())
        return ocupacion.ocupacionId?:0
    }

    override suspend fun delete(id: Int) {
        localDataSource.deleteById(id)
    }

    override suspend fun exists(id: Int): Boolean {
         return localDataSource.exists(id)
    }

    override suspend fun existsByDescripcion(descripcion: String, excludeId: Int): Boolean {
        return localDataSource.existsByDescripcion(descripcion,excludeId)
    }
}

//mappers
fun ocupacionEntity.toDomain():Ocupacion=Ocupacion(
    ocupacionId = ocupacionId,
    descripcion = descripcion,
    sueldo = sueldo
)


fun Ocupacion.toEntity():ocupacionEntity=ocupacionEntity(
    ocupacionId=ocupacionId,
    descripcion=descripcion,
    sueldo=sueldo
)
