package com.example.calculadoradepropinas

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.textfield.TextInputLayout



class CalcActivity : AppCompatActivity() {
/*    private lateinit var editTextNombre: EditText
    private lateinit var editTextHoras: EditText
    private lateinit var editTextPropinasTotal: EditText*/
    private lateinit var editTextMostrarValorHora: TextView
    private lateinit var btnAgregar: Button
    private lateinit var btnCalcular: Button
    private lateinit var btnClear: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CamareroAdapter
    private val camareros = mutableListOf<Camarero>()





        @SuppressLint("NotifyDataSetChanged")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_calc)

            // Función para mostrar una alerta
             fun showAlertDialog(title: String, message: String) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(title)
                builder.setMessage(message)
                builder.setPositiveButton("Aceptar") { dialog, _ ->
                    dialog.dismiss()
                }
                val alertDialog = builder.create()
                alertDialog.show()
            }


            if (savedInstanceState != null) {
                val savedCamareros = savedInstanceState.getParcelableArrayList<Camarero>("camareros")
                if (savedCamareros != null) {
                    camareros.addAll(savedCamareros)
                    adapter.notifyDataSetChanged()

                }
                val valorHora = savedInstanceState.getString("valor por hora")
                editTextMostrarValorHora.text = valorHora
            }

            //val textInputLayoutPropinas = findViewById<TextInputLayout>(R.id.editTextPropinaTotal)
            btnAgregar = findViewById(R.id.btnAgregar)
            recyclerView = findViewById(R.id.recyclerView)
            btnCalcular = findViewById(R.id.btnCalcular)
            editTextMostrarValorHora = findViewById(R.id.textViewValorHora)
            btnClear = findViewById(R.id.btnClear)

            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this)
            adapter = CamareroAdapter(camareros)
            recyclerView.adapter = adapter

            val textInputLayoutNombre = findViewById<TextInputLayout>(R.id.editTextNombre)
            val textInputLayoutHoras = findViewById<TextInputLayout>(R.id.editTextHoras)
            val editTextPropinasTotal = findViewById<TextInputLayout>(R.id.editTextPropinaTotal)
            val editTextMostrarValorHora = findViewById<TextView>(R.id.textViewValorHora)


            // Agregar función para eliminar camarero al deslizar
            val swipeToDeleteCallback = SwipeToDeleteCallback(adapter)
            val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
            itemTouchHelper.attachToRecyclerView(recyclerView)


            btnClear.setOnClickListener {
                textInputLayoutNombre.editText?.text?.clear()
                textInputLayoutHoras.editText?.text?.clear()
                editTextPropinasTotal.editText?.text?.clear()
                editTextMostrarValorHora.setText("0")
                camareros.clear()
                adapter.notifyDataSetChanged()
            }

            btnAgregar.setOnClickListener {
                val nombre = textInputLayoutNombre.editText?.text.toString()
                val horasStr = textInputLayoutHoras.editText?.text.toString()

                if (nombre.isNotEmpty() && horasStr.isNotEmpty()) {
                    val horas = horasStr.toDouble()
                    val camarero = Camarero(nombre, horas)
                    camareros.add(camarero)
                    adapter.notifyItemInserted(camareros.size - 1)

                    // Limpiar los EditText después de agregar
                    textInputLayoutNombre.editText?.text = null
                    textInputLayoutHoras.editText?.text = null

                }
            }

            btnCalcular.setOnClickListener {

                val totalHorasTrabajadas = camareros.sumOf { it.horasTrabajadas }
                val textInputLayoutPropinas = findViewById<TextInputLayout>(R.id.editTextPropinaTotal)



                // Calcula el valor por hora

                val valorPorHora = if (totalHorasTrabajadas > 0) {

                    val textInputLayoutPropinas = findViewById<TextInputLayout>(R.id.editTextPropinaTotal)
                    val editTextPropinas = textInputLayoutPropinas.editText?.text.toString()
                    if (editTextPropinas.isNotEmpty()) {
                        val editTextPropinasReplace = editTextPropinas.replace(",", ".").toDouble()
                        val totProps = editTextPropinasReplace / totalHorasTrabajadas.toDouble()
                        val totPropsFormateado = String.format("%.2f", totProps)
                        totPropsFormateado.also { editTextMostrarValorHora.text = it }
                        totPropsFormateado.replace(",", ".").toDouble()
                    }else{
                        //showAlertDialog("Alerta", "Por favor, ingresa un valor en propinas.")
                        0.0 // En caso de que no haya horas trabajadas, el valor por hora es 0
                    }

                } else {
                    0.0 // En caso de que no haya horas trabajadas, el valor por hora es 0
                }



                // Calcular las propinas individuales y actualizar la lista de camareros
                for (camarero in camareros) {
                    val propinaIndividual = camarero.horasTrabajadas * valorPorHora
                    val propinaIndividualFormat = String.format("%.2f",propinaIndividual)
                    val propinaIndividualReplace = propinaIndividualFormat.replace(",",".")
                    camarero.propina = propinaIndividualReplace.toDouble()

                }
                // Notifica al adaptador que los datos han cambiado
                adapter.notifyDataSetChanged()


            }

            MobileAds.initialize(this) {}
        }

    // Este método para guardar datos en el Bundle cuando sea necesario
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("camareros", ArrayList(camareros))
    }

    // Restaura los datos del Bundle cuando sea necesario
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedCamareros = savedInstanceState.getParcelableArrayList<Camarero>("camareros")
        if (savedCamareros != null) {
            camareros.clear()
            camareros.addAll(savedCamareros)
            adapter.notifyDataSetChanged()
        }
    }
}