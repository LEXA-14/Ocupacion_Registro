package com.example.ocupacion_registro.presentacion.empleado.list

sealed class empleadoListUiEvent{

    object loadEmpl: empleadoListUiEvent()
    object refreshEmpl: empleadoListUiEvent()
    object clearMessageEmpl: empleadoListUiEvent()
    object createNewEmpl: empleadoListUiEvent()
    data class editEmpl(val id:Int): empleadoListUiEvent()
    data class deleteEmpl(val id:Int): empleadoListUiEvent()
    data class showMessageEmpl(val message: String?): empleadoListUiEvent()


}

