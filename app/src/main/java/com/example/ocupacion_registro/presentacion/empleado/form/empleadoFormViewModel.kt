package com.example.ocupacion_registro.presentacion.empleado.form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.ocupacion_registro.domain.empleado.model.Empleado
import com.example.ocupacion_registro.domain.empleado.useCase.deleteEmpleadoUseCase
import com.example.ocupacion_registro.domain.empleado.useCase.getEmpleadoUseCase
import com.example.ocupacion_registro.domain.empleado.useCase.upsertEmpleadoUseCase
import com.example.ocupacion_registro.navegacion.screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class empleadoFormViewModel @Inject constructor(
    private val getEmpleadoUseCase: getEmpleadoUseCase,
    private val upsertEmpleadoUseCase: upsertEmpleadoUseCase,
    private val deleteEmpleadoUseCase: deleteEmpleadoUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val empleadoId: Int = savedStateHandle.toRoute<screen.empleadoForm>().empleadoId

    private val _state = MutableStateFlow(empleadoFormUiState())
    val state: StateFlow<empleadoFormUiState> = _state.asStateFlow()

    init {
        loadEmpleado(empleadoId)
    }

    fun onEvent(event: empleadoFormUiEvent) {
        when (event) {
            is empleadoFormUiEvent.Load -> loadEmpleado(event.id)
            is empleadoFormUiEvent.DescripcionChanged -> _state.update {
                it.copy(nombres = event.value, nombresError = null)
            }
            is empleadoFormUiEvent.SueldoChanged -> _state.update {
                it.copy(sueldo = event.value.toDoubleOrNull(), sueldoError = null)
            }
            is empleadoFormUiEvent.FechaChanged -> _state.update {
                it.copy(fechaIngreso = event.value)
            }
            is empleadoFormUiEvent.SexoChanged -> _state.update {
                it.copy(sexo = event.value, sexoError = null)
            }
            empleadoFormUiEvent.Save -> onSave()
            empleadoFormUiEvent.Delete -> onDelete()
        }
    }

    private fun loadEmpleado(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, empleadoId = null) }
            return
        }

        viewModelScope.launch {
            val empleado = getEmpleadoUseCase(id)
            if (empleado != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        empleadoId = empleado.empleadoId,
                        nombres = empleado.nombres,
                        sueldo = empleado.sueldo,
                        fechaIngreso = empleado.fechaIngreso,
                        sexo = empleado.sexo
                    )
                }
            } else {
                _state.update { it.copy(isNew = true, empleadoId = null) }
            }
        }
    }

    private fun onSave() {
        viewModelScope.launch {
            _state.update { it.copy(isSaved = true) }

            val empleado = Empleado(
                empleadoId = state.value.empleadoId ?: 0,
                nombres = state.value.nombres ?: "",
                sueldo = state.value.sueldo ?: 0.0,
                fechaIngreso = state.value.fechaIngreso ?: "",
                sexo = state.value.sexo ?: ' ',
                sueldoFinal = state.value.sueldo
            )

            val result = upsertEmpleadoUseCase(empleado)
            result.onSuccess { newId ->
                _state.update {
                    it.copy(
                        isSaved = false,
                        saved = true,
                        empleadoId = newId,
                        isNew = false
                    )
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isSaved = false,
                        nombresError = error.message
                    )
                }
            }
        }
    }

    private fun onDelete() {
        val id = state.value.empleadoId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteEmpleadoUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}
