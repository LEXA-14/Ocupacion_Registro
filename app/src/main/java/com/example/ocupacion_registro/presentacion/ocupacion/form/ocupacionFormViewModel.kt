package com.example.ocupacion_registro.presentacion.ocupacion.form

import com.example.ocupacion_registro.domain.ocupacion.model.Ocupacion
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.ocupacion_registro.domain.userCase.DeleteOcupacionUseCase
import com.example.ocupacion_registro.domain.userCase.GetOcupacionUseCase
import com.example.ocupacion_registro.domain.userCase.UpsertOcupacionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.ocupacion_registro.domain.validacion.validateDescripcion
import com.example.ocupacion_registro.domain.validacion.validateSueldo
import com.example.ocupacion_registro.navegacion.screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow


@HiltViewModel
class ocupacionFormViewModel @Inject constructor(
    private val getOcupacionUseCase: GetOcupacionUseCase,
    private val upsertOcupacionUseCase: UpsertOcupacionUseCase,
    private val deleteOcupacionUseCase: DeleteOcupacionUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val ocupacionId: Int = savedStateHandle.toRoute<screen.ocupacionForm>().ocupacionId

    private val _state = MutableStateFlow(OcupacionFormUiState())
    val state: StateFlow<OcupacionFormUiState> = _state.asStateFlow()

    init {
        loadOcupacion(ocupacionId)
    }

    fun onEvent(event: OcupacionFormUiEvent) {
        when (event) {
            is OcupacionFormUiEvent.Load -> loadOcupacion(event.id)
            is OcupacionFormUiEvent.DescripcionChanged -> _state.update {
                it.copy(descripcion = event.value, descripcionError = null)
            }
            is OcupacionFormUiEvent.SueldoChanged -> _state.update {
                it.copy(sueldo = event.value, sueldoError = null)
            }
            OcupacionFormUiEvent.Save -> onSave()
            OcupacionFormUiEvent.Delete -> onDelete()
        }
    }

    private fun loadOcupacion(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, ocupacionId = null) }
            return
        }

        viewModelScope.launch {
            val ocupacion = getOcupacionUseCase(id)
            if (ocupacion != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        ocupacionId = ocupacion.ocupacionId,
                        descripcion = ocupacion.descripcion,
                        sueldo = ocupacion.sueldo.toString()
                    )
                }
            } else {
                _state.update { it.copy(isNew = true, ocupacionId = null) }
            }
        }
    }

    private fun onSave() {
        val descripcionValidation = validateDescripcion(state.value.descripcion)
        val sueldoValidation = validateSueldo(state.value.sueldo)

        if (!descripcionValidation.isValid || !sueldoValidation.isValid) {
            _state.update {
                it.copy(
                    descripcionError = descripcionValidation.error,
                    sueldoError = sueldoValidation.error
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val ocupacion = Ocupacion(
                ocupacionId = state.value.ocupacionId ?: 0,
                descripcion = state.value.descripcion,
                sueldo = state.value.sueldo.toDouble()
            )

            val result = upsertOcupacionUseCase(ocupacion)
            result.onSuccess { newId ->
                _state.update {
                    it.copy(
                        isSaving = false,
                        saved = true,
                        ocupacionId = newId,
                        isNew = false
                    )
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isSaving = false,
                        descripcionError = error.message
                    )
                }
            }
        }
    }

    private fun onDelete() {
        val id = state.value.ocupacionId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteOcupacionUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}