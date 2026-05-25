package com.example.ocupacion_registro.data.ocupacion.local
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ocupacion_registro.data.empleado.local.empleadoDao
import com.example.ocupacion_registro.data.empleado.local.empleadoEntity
import com.example.ocupacion_registro.data.ocupacion.local.ocupacionDao

@Database(
    entities = [ocupacionEntity::class,
    empleadoEntity::class],
    version=4
)

abstract class ocupacionDatabase:RoomDatabase(){
    abstract fun ocupacionDao(): ocupacionDao
    abstract fun empleadoDao(): empleadoDao
}
