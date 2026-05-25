package com.example.ocupacion_registro.presentacion.empleado.form

sealed interface empleadoFormUiEvent {


        data class Load(val id: Int?) : empleadoFormUiEvent
        data class DescripcionChanged(val value: String) : empleadoFormUiEvent
        data class SueldoChanged(val value: String) : empleadoFormUiEvent
        data class FechaChanged(val value: String) : empleadoFormUiEvent  // ✅
        data class SexoChanged(val value: Char) : empleadoFormUiEvent     // ✅
        data object Save : empleadoFormUiEvent
        data object Delete : empleadoFormUiEvent
    }