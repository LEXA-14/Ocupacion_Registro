package com.example.ocupacion_registro.data.empleado.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface empleadoDao {

    @Upsert
    suspend fun Crear(entity: empleadoEntity)


    @Delete
    suspend fun Eliminar(entity: empleadoEntity)

    @Query("Delete from empleado where empleadoId= :id ")
    suspend fun EliminarId(id:Int)

    @Query("select * from empleado where empleadoId = :id")
    suspend fun ListarId(id: Int)

    @Query("select * from empleado")
    suspend fun ObserveAllEmpl(): Flow<List<empleadoEntity>>

    @Query("select * from empleado where empleadoId =:id")
    suspend fun getByIdEmpl(id: Int)
}