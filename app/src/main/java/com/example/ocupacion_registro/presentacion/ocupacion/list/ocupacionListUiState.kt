package com.example.ocupacion_registro.presentacion.ocupacion.list

import Ocupacion

data class ocupacionListUiState (
    val isLoading: Boolean=false,
    val ocupaciones:List<Ocupacion> =emptyList(),
    val message:String?=null,
    val navigateToCreate: Boolean=false,
    val navigateToEditId:Int?=null,
    val error: String?=null
)
