package com.example.ocupacion_registro.data.ocupacion.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="ocupacion")
data class ocupacionEntity(
    @PrimaryKey(autoGenerate=true)
    val ocupacionId:Int=0,
    val descripcion:String,
    val sueldo:Double
)