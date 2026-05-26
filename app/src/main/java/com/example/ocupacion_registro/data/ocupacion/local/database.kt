package com.example.ocupacion_registro.data.ocupacion.local
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ocupacion_registro.data.empleado.local.empleadoDao
import com.example.ocupacion_registro.data.empleado.local.empleadoEntity
import com.example.ocupacion_registro.data.ocupacion.local.ocupacionDao
import com.example.ocupacion_registro.data.registroHorasEmpleado.registroHorasEmpleadoDao
import com.example.ocupacion_registro.data.registroHorasEmpleado.registroHorasEmpleadoEntity

@Database(
    entities = [ocupacionEntity::class,
    empleadoEntity::class,
        registroHorasEmpleadoEntity::class],
    version=5
)

abstract class ocupacionDatabase:RoomDatabase(){
    abstract fun ocupacionDao(): ocupacionDao
    abstract fun empleadoDao(): empleadoDao
    abstract fun registroHorasEmpleadoDao(): registroHorasEmpleadoDao
}
