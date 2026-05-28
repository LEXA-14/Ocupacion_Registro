package com.example.ocupacion_registro.data.ocupacion.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ocupacion_registro.data.empleado.local.empleadoDao
import com.example.ocupacion_registro.data.empleado.local.empleadoEntity
import com.example.ocupacion_registro.data.ocupacion.local.Converters
import com.example.ocupacion_registro.data.ocupacion.local.ocupacionDao
import com.example.ocupacion_registro.data.registroHorasEmpleado.registroHorasEmpleadoDao
import com.example.ocupacion_registro.data.registroHorasEmpleado.registroHorasEmpleadoEntity

@TypeConverters(Converters::class)

@Database(
    entities = [ocupacionEntity::class,
    empleadoEntity::class,
        registroHorasEmpleadoEntity::class],
    version=8,
    exportSchema = false
)

abstract class ocupacionDatabase:RoomDatabase(){
    abstract fun ocupacionDao(): ocupacionDao
    abstract fun empleadoDao(): empleadoDao
    abstract fun registroHorasEmpleadoDao(): registroHorasEmpleadoDao
}
