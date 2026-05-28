package com.example.ocupacion_registro.presentacion.empleado.lista

sealed class empleadoListaUiEvent{

    object loadEmpl: empleadoListaUiEvent()
    object refreshEmpl: empleadoListaUiEvent()
    object clearMessageEmpl: empleadoListaUiEvent()
    object createNewEmpl: empleadoListaUiEvent()
    data class editEmpl(val id:Int): empleadoListaUiEvent()
    data class deleteEmpl(val id:Int): empleadoListaUiEvent()
    data class showMessageEmpl(val message: String?): empleadoListaUiEvent()
    data class registroHoras(val id: Int) : empleadoListaUiEvent()


}

