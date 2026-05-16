package com.example.ocupacion_registro.data.empleado.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface empleadoDao {

    @Upsert
    suspend fun upsert(entity: empleadoEntity)


    @Delete
    suspend fun deleteEmpl(entity: empleadoEntity)

    @Query("Delete from empleado where empleadoId= :id ")
    suspend fun deleteIdEmpl(id:Int)

    @Query("select * from empleado where empleadoId = :id")
    suspend fun getByIdEmpl(id: Int)

    @Query("select * from empleado")
    suspend fun observeAllEmpl(): Flow<List<empleadoEntity>>

    @Query("select * from empleado where Nombres =:nombre")
    suspend fun getByNameEmpl(nombre: String)
}