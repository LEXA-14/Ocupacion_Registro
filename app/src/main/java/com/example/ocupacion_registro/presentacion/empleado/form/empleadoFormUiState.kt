package com.example.ocupacion_registro.presentacion.empleado.form

data class empleadoFormUiState(

 val empleadoId:Int?= null,
 val nombres : String?="",
 val sexo: Char?=null,
 val fechaIngreso: String?="",
 val sueldo: Double?=null,
 val isSaved: Boolean=false,
 val isDeleting: Boolean=false,
 val isNew: Boolean=true,
 val saved: Boolean=false,
 val deleted: Boolean=false,
 val nombresError: String?=null,
 val sueldoError: String?=null,
 val sexoError:String?=null

)
