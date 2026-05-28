package com.example.ocupacion_registro.presentacion.registroHorasEmpleados.form

sealed class registroHorasUIEvent {

        data class OnEmpleadoIdChange(val id: Int) : registroHorasUIEvent()
        data class OnHorasExtrasChange(val horas: String) : registroHorasUIEvent()
        data class OnHorasNocturnasChange(val horas: String) : registroHorasUIEvent()
        data object OnCalcular : registroHorasUIEvent()
        data object OnGuardar : registroHorasUIEvent()
        data object OnDismissError : registroHorasUIEvent()

}