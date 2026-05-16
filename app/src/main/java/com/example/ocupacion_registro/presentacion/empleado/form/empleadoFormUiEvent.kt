package com.example.ocupacion_registro.presentacion.empleado.form

import com.example.ocupacion_registro.presentacion.ocupacion.form.OcupacionFormUiEvent

sealed interface empleadoFormUiEvent {

    data class Load(val id: Int?) : empleadoFormUiEvent
    data class DescripcionChanged(val value: String) : empleadoFormUiEvent
    data class SueldoChanged(val value: String) : empleadoFormUiEvent
    data object Save : empleadoFormUiEvent
    data object Delete : empleadoFormUiEvent
}