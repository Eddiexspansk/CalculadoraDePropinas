package com.example.calculadoradepropinas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

enum class ProviderType{
    BASIC
}
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_logout)
    }

    class PropinaCalculator {
        var propinasGeneradas: Double = 0.0
        val trabajadores = mutableListOf<Trabajador>()
    fun agregarTrabajador(nombre: String, horasTrabajadas: Double) {
        val trabajador = Trabajador(nombre, horasTrabajadas)
        trabajadores.add(trabajador)
    }

    fun calcularPropina(): Map<String, Double> {
        val totalHorasTrabajadas = trabajadores.sumOf { it.horasTrabajadas }
        val propinaPorHora = propinasGeneradas / totalHorasTrabajadas
        return trabajadores.associate { it.nombre to it.horasTrabajadas * propinaPorHora }
    }
}

data class Trabajador(val nombre: String, val horasTrabajadas: Double)

}