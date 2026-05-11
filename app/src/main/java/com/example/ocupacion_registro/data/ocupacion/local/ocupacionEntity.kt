import androidx.room.Entity


@Entity(tableName="ocupacion")
data class ocupacionEntity(
    @PrimaryKey(autogenerate=true)
    val ocupacionId:Int=0,
    val descripcion:String,
    val sueldo:Double
)