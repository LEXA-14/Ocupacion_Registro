package com.example.ocupacion_registro.data.ocupacion.local
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ocupacion_registro.data.ocupacion.local.ocupacionDao

@Database(
    entities = [ocupacionEntity::class],
    version=1
)

abstract class ocupacionDatabase:RoomDatabase(){
    abstract fun ocupacionDao(): ocupacionDao
}
