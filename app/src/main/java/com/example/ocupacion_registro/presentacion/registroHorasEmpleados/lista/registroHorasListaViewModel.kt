package com.example.ocupacion_registro.presentacion.registroHorasEmpleados.lista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ocupacion_registro.domain.registroHoras.repository.registroHorasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class registroHorasListaViewModel @Inject constructor(
    private val registroHorasRepository: registroHorasRepository
) : ViewModel() {

    private val _state = MutableStateFlow(registroHorasListaUIState(isLoading = true))
    val state: StateFlow<registroHorasListaUIState> = _state.asStateFlow()

    init { load() }

    fun onEvent(event: registroHorasListaUIEvent) {
        when (event) {
            registroHorasListaUIEvent.Load -> load()
            is registroHorasListaUIEvent.Delete -> onDelete(event.id)
            registroHorasListaUIEvent.ClearMessage -> _state.update { it.copy(message = null) }
        }
    }

    private fun load() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            registroHorasRepository.observeAllHoras().collectLatest { list ->
                _state.update { it.copy(isLoading = false, registros = list) }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            registroHorasRepository.eliminarByIdHoras(id)
            _state.update { it.copy(message = "Eliminado") }
        }
    }
}