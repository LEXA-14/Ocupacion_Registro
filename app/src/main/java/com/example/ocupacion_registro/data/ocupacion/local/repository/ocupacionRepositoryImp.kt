package com.example.ocupacion_registro.data.ocupacion.local.repository

import Ocupacion
import com.example.ocupacion_registro.data.ocupacion.local.ocupacionDao
import com.example.ocupacion_registro.domain.ocupacion.repository.OcupacionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ocupacionEntity

class ocupacionRepositoryImp @Inject constructor(
    private  val localDataSource: ocupacionDao
) : OcupacionRepository{

    override suspend fun ObserveOcupacion(): Flow<List<Ocupacion>> {
        return localDataSource.observeAll().map {
            entities -> entities.map{it.toDomain()}
        }
    }

    override suspend fun getOcupacion(id: Int): Ocupacion? {
        localDataSource.getById(id)?.toDomain()
    }

    override suspend fun upsert(ocupacion: Ocupacion): Int {
        localDataSource.upsert(ocupacion.toEntity())
        return ocupacion.ocupacionId?:0
    }

    override suspend fun delete(id: Int) {
        localDataSource.deleteById(id)
    }

    override suspend fun exists(id: Int): Boolean {
        localDataSource.exists(id)
    }
}

fun ocupacion.toDomain():Ocupacion=Ocupacion(
    ocupacionId = ocupacionId,
    descripcion = descripcion,
    sueldo = sueldo
)

//Mappers
fun ocupacion.toEntity():ocupacionEntity=ocupacionEntity(
    ocupacionId=ocupacionId,
    descripcion=descripcion,
    sueldo=sueldo
)
