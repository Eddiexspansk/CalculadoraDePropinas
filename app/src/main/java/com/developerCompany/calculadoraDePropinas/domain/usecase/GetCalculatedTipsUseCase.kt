package com.developerCompany.calculadoraDePropinas.domain.usecase

import com.developerCompany.calculadoraDePropinas.domain.model.Camarero
import javax.inject.Inject

data class CalculationResult(
    val updatedCamareros: List<Camarero>,
    val valorPorHora: Double,
)

/**
 * Clase que contiene la lógica pura de negocio para calcular las propinas.
 * Al estar en la capa de Domain, no depende de Android ni de ninguna librería externa,
 * lo que la hace 100% testeable.
 */
class GetCalculatedTipsUseCase @Inject constructor() {
    
    /**
     * Realiza el cálculo del valor por hora y reparte la propina proporcionalmente
     * a cada camarero basado en sus horas trabajadas.
     */
    operator fun invoke(camareros: List<Camarero>, propinaTotal: Double): CalculationResult {
        // 1. Calculamos el total de horas entre todos los empleados
        val totalHoras = camareros.sumOf { it.horasTrabajadas }
        
        // 2. Determinamos cuánto vale cada hora (evitando dividir por cero)
        val valorHora = if (totalHoras > 0) propinaTotal / totalHoras else 0.0
        
        // 3. Generamos una nueva lista con las propinas calculadas para cada uno
        val updatedList = camareros.map { camarero ->
            camarero.copy(propina = camarero.horasTrabajadas * valorHora)
        }
        
        return CalculationResult(updatedList, valorHora)
    }
}
