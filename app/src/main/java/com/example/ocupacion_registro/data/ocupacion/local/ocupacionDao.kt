package com.example.ocupacion_registro.data.ocupacion.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ocupacionEntity

@Dao
interface ocupacionDao {

    @Upsert
    suspend fun upsert(entity:ocupacionEntity)

    @Delete
    suspend fun delete(entity: ocupacionEntity)

    @Query("SELECT * FROM ocupacion order by ocupacionId desc")
    fun observeAll(): Flow<List<ocupacionEntity>>

    @Query("SELECT * FROM ocupacion where ocupacionId= :id")
    suspend fun getById(id:Int):ocupacionEntity?

    @Query("Delete from ocupacion where ocupacionId=:id")
    suspend fun deleteById(id:Int)

    @Query("SELECT EXISTS(SELECT 1 FROM ocupacion where ocupacionId=:id)")
    suspend fun exists(id:Int): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM ocupacion WHERE descripcion = :descripcion AND ocupacionId != :excludeId)")
    suspend fun existsByDescripcion(descripcion: String, excludeId: Int): Boolean


}