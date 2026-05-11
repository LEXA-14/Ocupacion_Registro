package com.example.ocupacion_registro.presentacion.ocupacion.list

 sealed class ocupacionListaUiEvent {
     object Load: ocupacionListaUiEvent()
     object Refresh: ocupacionListaUiEvent()
     data class Delete(val id: Int): ocupacionListaUiEvent()
     data class ShowMessage(val mensaje:String): ocupacionListaUiEvent()
     object ClearMessage: ocupacionListaUiEvent()
     object CreateNew: ocupacionListaUiEvent()
     data class Edit(val id:Int): ocupacionListaUiEvent()

}
