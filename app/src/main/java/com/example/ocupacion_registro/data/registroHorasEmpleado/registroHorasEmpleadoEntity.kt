package com.example.ocupacion_registro.data.registroHorasEmpleado

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.ocupacion_registro.data.empleado.local.empleadoEntity
import com.example.ocupacion_registro.data.ocupacion.local.Converters
import java.time.LocalDate



@TypeConverters(Converters::class)

@Entity(
    tableName = "registro_horas_empleado",
    foreignKeys = [ForeignKey(
        entity = empleadoEntity::class,
        parentColumns = ["empleadoId"],
        childColumns = ["empleadoId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("empleadoId")]
)

data class registroHorasEmpleadoEntity
    (
    @PrimaryKey(autoGenerate=true)
           val registroId:Int=0,
            val empleadoId:Int,
            val horasExtras: Double,
            val horasNocturnas: Double,
            val fecha: LocalDate

            )