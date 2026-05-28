package com.example.ocupacion_registro.presentacion.registroHorasEmpleados.form

 data class registroHoraUIState
     (
  val empleadoId: Int = 0,
  val nombreEmpleado: String = "",
  val sueldoEmpleado: Double = 0.0,
  val horasExtras: String = "",
  val horasNocturnas: String = "",
  val totalExtra: Double? = null,
  val horasExtrasError: String? = null,
  val horasNocturnasError: String? = null,
  val isLoading: Boolean = false,
  val isSuccess: Boolean = false,
  val error: String? = null
)
