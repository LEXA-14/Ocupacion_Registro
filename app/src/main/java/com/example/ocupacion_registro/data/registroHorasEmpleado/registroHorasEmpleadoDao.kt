package com.example.ocupacion_registro.data.registroHorasEmpleado

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface registroHorasEmpleadoDao {

    @Upsert
    suspend fun upsert(registroHora: registroHorasEmpleadoEntity)

    @Delete
    suspend fun deleteById(registro: registroHorasEmpleadoEntity)

    @Query("SELECT * from registro_horas_empleado where registroId = :id")
    suspend fun getbyId(id:Int):registroHorasEmpleadoEntity?

    @Query("SELECT * FROM registro_horas_empleado")
    fun observeAll(): Flow<List<registroHorasEmpleadoEntity?>>

}