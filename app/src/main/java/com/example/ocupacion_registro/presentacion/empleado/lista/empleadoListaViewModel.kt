package com.example.ocupacion_registro.presentacion.empleado.lista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ocupacion_registro.domain.empleado.useCase.deleteEmpleadoUseCase
import com.example.ocupacion_registro.domain.empleado.useCase.observeEmpleadoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class empleadoListaViewModel @Inject constructor(
    private val deleteEmpleadoUseCase: deleteEmpleadoUseCase,
    private val observeEmpleadoUseCase: observeEmpleadoUseCase)
    : ViewModel(){

        private val _stateEmpl= MutableStateFlow(empleadoListaUiState(true))

    val state: StateFlow<empleadoListaUiState> = _stateEmpl.asStateFlow()

    init {
        loadEmpleado()
    }

    fun onEvent(event: empleadoListaUiEvent){
        when (event) {
            empleadoListaUiEvent.loadEmpl-> loadEmpleado()
            empleadoListaUiEvent.refreshEmpl-> loadEmpleado()
            is empleadoListaUiEvent.deleteEmpl-> onDeleteEmpl(event.id)
            is empleadoListaUiEvent.showMessageEmpl -> _stateEmpl.update { it.copy(messageEmpl = event.message) }
            empleadoListaUiEvent.clearMessageEmpl -> _stateEmpl.update { it.copy(messageEmpl = null) }
            empleadoListaUiEvent.createNewEmpl -> _stateEmpl.update { it.copy(navigateToCreateEmpl = true) }
            is empleadoListaUiEvent.editEmpl -> _stateEmpl.update { it.copy(navigateToEditIdEmpl = event.id) }
            is empleadoListaUiEvent.registroHoras -> {}
        }
    }
    fun loadEmpleado(){
        viewModelScope.launch {
            _stateEmpl.update { it.copy(isLoading = true) }
            observeEmpleadoUseCase().collectLatest { list->
                _stateEmpl.update { it.copy(isLoading = false, empleados = list, messageEmpl= null) }
            }
        }
    }

    private fun onDeleteEmpl(id:Int){
        viewModelScope.launch {
            deleteEmpleadoUseCase(id)
            onEvent(empleadoListaUiEvent.showMessageEmpl("Eliminado"))
        }
    }
}




