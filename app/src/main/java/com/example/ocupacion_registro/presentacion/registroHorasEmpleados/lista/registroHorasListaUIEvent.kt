package com.example.ocupacion_registro.presentacion.registroHorasEmpleados.lista

 sealed class registroHorasListaUIEvent {
        object Load : registroHorasListaUIEvent()
        data class Delete(val id: Int) : registroHorasListaUIEvent()
        object ClearMessage : registroHorasListaUIEvent()
    }
