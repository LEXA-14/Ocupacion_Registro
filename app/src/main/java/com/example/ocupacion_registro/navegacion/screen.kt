package com.example.ocupacion_registro.navegacion


import kotlinx.serialization.Serializable

sealed class screen {
     @Serializable
     data object ocupacionList: screen()

    @Serializable
    data class ocupacionForm(val ocupacionId: Int=0): screen()
}