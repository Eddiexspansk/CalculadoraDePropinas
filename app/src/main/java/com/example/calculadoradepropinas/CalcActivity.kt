package com.example.calculadoradepropinas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class CalcActivity : AppCompatActivity() {
    private lateinit var editTextNombre: EditText
    private lateinit var editTextHoras: EditText
    private lateinit var editTextPropinasTotal: EditText
    private lateinit var editTextMostrarValorHora: TextView
    private lateinit var btnAgregar: Button
    private lateinit var btnCalcular: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CamareroAdapter
    private val camareros = mutableListOf<Camarero>()


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_calc)

            editTextNombre = findViewById(R.id.editTextNombre)
            editTextHoras = findViewById(R.id.editTextHoras)
            editTextPropinasTotal = findViewById(R.id.editTextPropinaTotal)
            btnAgregar = findViewById(R.id.btnAgregar)
            recyclerView = findViewById(R.id.recyclerView)
            btnCalcular = findViewById(R.id.btnCalcular)
            editTextMostrarValorHora = findViewById(R.id.textViewValorHora)
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this)


            adapter = CamareroAdapter(camareros)
            recyclerView.adapter = adapter

            // Agregar función para eliminar camarero al deslizar
            val swipeToDeleteCallback = SwipeToDeleteCallback(adapter)
            val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
            itemTouchHelper.attachToRecyclerView(recyclerView)

            btnAgregar.setOnClickListener {
                val nombre = editTextNombre.text.toString()
                val horasStr = editTextHoras.text.toString()

                if (nombre.isNotEmpty() && horasStr.isNotEmpty()) {
                    val horas = horasStr.toInt()
                    val camarero = Camarero(nombre, horas)
                    camareros.add(camarero)
                    adapter.notifyItemInserted(camareros.size - 1)

                    // Limpiar los EditText después de agregar
                    editTextNombre.text.clear()
                    editTextHoras.text.clear()
                }
            }
            btnCalcular.setOnClickListener {
                val totalHorasTrabajadas = camareros.sumOf { it.horasTrabajadas }

                // Calcula el valor por hora
                val valorPorHora = if (totalHorasTrabajadas > 0) {

                    val totalPropinasStr = editTextPropinasTotal.text.toString()
                    val totalPropinasDecimal: Double = totalPropinasStr.toDouble()
                    val totProps = totalPropinasDecimal / totalHorasTrabajadas.toDouble()
                    editTextMostrarValorHora.text = totProps.toString()
                    totProps



                } else {
                    0.0 // En caso de que no haya horas trabajadas, el valor por hora es 0
                }

                // Calcular las propinas individuales y actualizar la lista de camareros
                for (camarero in camareros) {
                    val propinaIndividual = camarero.horasTrabajadas * valorPorHora
                    camarero.propina = propinaIndividual
                }

// Notifica al adaptador que los datos han cambiado
                adapter.notifyDataSetChanged()
            }

        }
    }


    class PropinaCalculator {
        var propinasGeneradas: Double = 0.0
        private val trabajadores = mutableListOf<Trabajador>()

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


