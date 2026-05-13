package com.example.ocupacion_registro.presentacion.ocupacion.list


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ocupacion_registro.domain.userCase.DeleteOcupacionUseCase
import com.example.ocupacion_registro.domain.userCase.ObserveOcupacionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ocupacionListaViewModel @Inject constructor(
    private val observeOcupacionUseCase: ObserveOcupacionUseCase,
    private val deleteOcupacionUseCase: DeleteOcupacionUseCase)
    : ViewModel()
{
        private val _state=MutableStateFlow(ocupacionListUiState(isLoading=true))
    val state: StateFlow<ocupacionListUiState> = _state.asStateFlow()

    init {
        loadOcupacion()
    }

    fun onEvent(event: ocupacionListaUiEvent){
        when (event) {
            ocupacionListaUiEvent.Load -> loadOcupacion()
            ocupacionListaUiEvent.Refresh -> loadOcupacion()
            is ocupacionListaUiEvent.Delete -> onDelete(event.id)
            is ocupacionListaUiEvent.ShowMessage -> _state.update { it.copy(message = event.mensaje) }
            ocupacionListaUiEvent.ClearMessage -> _state.update { it.copy(message = null) }
            ocupacionListaUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is ocupacionListaUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
        }
        }
    fun loadOcupacion(){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            observeOcupacionUseCase().collectLatest { list->
                _state.update { it.copy(isLoading = false, ocupaciones = list, message = null) }
            }
        }
    }

    private fun onDelete(id:Int){
        viewModelScope.launch {
            deleteOcupacionUseCase(id)
            onEvent(ocupacionListaUiEvent.ShowMessage("Eliminado"))
            }
        }
    }




