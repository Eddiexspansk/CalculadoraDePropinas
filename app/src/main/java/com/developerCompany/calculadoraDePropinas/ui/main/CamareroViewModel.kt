package com.developerCompany.calculadoraDePropinas.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developerCompany.calculadoraDePropinas.domain.model.Camarero
import com.developerCompany.calculadoraDePropinas.domain.repository.CamareroRepository
import com.developerCompany.calculadoraDePropinas.domain.usecase.GetCalculatedTipsUseCase
import com.developerCompany.calculadoraDePropinas.ui.util.NumberUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CalcUiState(
    val camareros: List<Camarero> = emptyList(),
    val propinaTotal: Double = 0.0,
    val valorPorHora: Double = 0.0,
)

/**
 * Este ViewModel se encarga de conectar la lógica de negocio (Use Cases) con la interfaz de usuario.
 * Utilizo StateFlow para que la UI se actualice automáticamente cuando los datos cambian.
 */
@HiltViewModel
class CamareroViewModel @Inject constructor(
    private val repository: CamareroRepository,
    private val calculateTipsUseCase: GetCalculatedTipsUseCase
) : ViewModel() {

    // Flujo interno para manejar el monto total de propinas introducido
    private val _propinaTotal = MutableStateFlow(0.0)
    
    // uiState combina la lista de camareros de la DB con el monto total para calcular los resultados
    val uiState: StateFlow<CalcUiState> = combine(
        repository.getCamareros(),
        _propinaTotal
    ) { camareros, propinaTotal ->
        // Llamamos al caso de uso para obtener los cálculos actualizados
        val result = calculateTipsUseCase(camareros, propinaTotal)
        CalcUiState(
            camareros = result.updatedCamareros,
            propinaTotal = propinaTotal,
            valorPorHora = result.valorPorHora
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CalcUiState()
    )

    /**
     * Lógica para agregar un nuevo camarero a la base de datos.
     * Uso corrutinas (viewModelScope.launch) para no bloquear la interfaz.
     */
    fun agregarCamarero(nombre: String, horas: Double) {
        viewModelScope.launch {
            repository.addCamarero(Camarero(nombre = nombre, horasTrabajadas = horas))
        }
    }

    fun eliminarCamarero(camarero: Camarero) {
        viewModelScope.launch {
            repository.removeCamarero(camarero)
        }
    }

    fun actualizarPropinaTotal(montoStr: String) {
        _propinaTotal.value = NumberUtils.parse(montoStr)
    }

    fun limpiarTodo() {
        viewModelScope.launch {
            repository.clearAll()
            _propinaTotal.value = 0.0
        }
    }
}
