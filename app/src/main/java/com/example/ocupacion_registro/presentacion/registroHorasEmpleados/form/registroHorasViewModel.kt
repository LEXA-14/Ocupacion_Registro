package com.example.ocupacion_registro.presentacion.registroHorasEmpleados.form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ocupacion_registro.domain.empleado.repository.empleadoRepository
import com.example.ocupacion_registro.domain.registroHoras.model.registroHorasEmpleado
import com.example.ocupacion_registro.domain.registroHoras.useCase.CalcularSueldoFinalUseCase
import com.example.ocupacion_registro.domain.registroHoras.validaciones.validateHorasExtras
import com.example.ocupacion_registro.domain.registroHoras.validaciones.validateHorasNocturnas
import java.time.LocalDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class registroHorasViewModel @Inject constructor(
    private val calcularSueldoFinalUseCase: CalcularSueldoFinalUseCase,
    private val empleadoRepository: empleadoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(registroHoraUIState())
    val state = _state.asStateFlow()

    init {
        val empleadoId=savedStateHandle.get<Int>("empleadoId") ?:0
        loadEmpleado(empleadoId)
    }

    fun onEvent(event: registroHorasUIEvent) {
        when (event) {
            is registroHorasUIEvent.OnEmpleadoIdChange -> loadEmpleado(event.id)

            is registroHorasUIEvent.OnHorasExtrasChange -> {
                _state.update { it.copy(
                    horasExtras = event.horas,
                    horasExtrasError = validateHorasExtras(event.horas.toDoubleOrNull() ?: 0.0).error
                )}
            }

            is registroHorasUIEvent.OnHorasNocturnasChange -> {
                val horasExtras = _state.value.horasExtras.toDoubleOrNull() ?: 0.0
                _state.update { it.copy(
                    horasNocturnas = event.horas,
                    horasNocturnasError = validateHorasNocturnas(
                        event.horas.toDoubleOrNull() ?: 0.0,
                        horasExtras
                    ).error
                )}
            }

            is registroHorasUIEvent.OnCalcular -> calcular()
            is registroHorasUIEvent.OnGuardar -> guardar()
            is registroHorasUIEvent.OnDismissError -> _state.update { it.copy(error = null) }
        }
    }

    private fun loadEmpleado(id: Int) {
        viewModelScope.launch {
            val empleado = empleadoRepository.getByIdEmpl(id)
            if (empleado != null) {
                _state.update { it.copy(
                    empleadoId = empleado.empleadoId,
                    nombreEmpleado = empleado.nombres,
                    sueldoEmpleado = empleado.sueldo
                )}
            }
        }
    }

    private fun calcular() {
        viewModelScope.launch {
            val state = _state.value
            val horas = registroHorasEmpleado(
                empleadoId = state.empleadoId,
                horasExtras = state.horasExtras.toDoubleOrNull() ?: 0.0,
                horasNocturnas = state.horasNocturnas.toDoubleOrNull() ?: 0.0,
                fecha = LocalDate.now(),
                registroId = 0
            )

            val result = calcularSueldoFinalUseCase(horas)
            result.fold(
                onSuccess = { total ->
                    _state.update { it.copy(totalExtra = total, error = null) }
                },
                onFailure = { e ->
                    _state.update { it.copy(error = e.message) }
                }
            )
        }
    }

    private fun guardar() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val state = _state.value
            val horas = registroHorasEmpleado(
                registroId = 0,
                empleadoId     = state.empleadoId,
                horasExtras    = state.horasExtras.toDoubleOrNull() ?: 0.0,
                horasNocturnas = state.horasNocturnas.toDoubleOrNull() ?: 0.0,
                fecha          = LocalDate.now()
            )
            calcularSueldoFinalUseCase(horas).fold(
                onSuccess = { _state.update { it.copy(isLoading = false, isSuccess = true) } },
                onFailure = { e -> _state.update { it.copy(isLoading = false, error = e.message) } }
            )
        }
    }
}