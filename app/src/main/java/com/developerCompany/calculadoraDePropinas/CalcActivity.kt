package com.developerCompany.calculadoraDePropinas

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.textfield.TextInputLayout
import java.util.Locale



class CalcActivity : AppCompatActivity() {
    private lateinit var editTextMostrarValorHora: TextView
    private lateinit var btnAgregar: Button
    private lateinit var btnCalcular: Button
    private lateinit var btnClear: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CamareroAdapter
    private val camareros = mutableListOf<Camarero>()
    private lateinit var mAdView : AdView

    private inline fun <reified T : Parcelable> Bundle.getParcelableArrayListCompat(key: String): ArrayList<T>? {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            getParcelableArrayList(key, T::class.java)
        } else {
            @Suppress("DEPRECATION")
            getParcelableArrayList(key)
        }
    }

        @SuppressLint("NotifyDataSetChanged", "CutPasteId", "SetTextI18n")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_calc)

            MobileAds.initialize(this) {}
            mAdView = findViewById(R.id.adView)
            val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)





            btnAgregar = findViewById(R.id.btnAgregar)
            recyclerView = findViewById(R.id.recyclerView)
            btnCalcular = findViewById(R.id.btnCalcular)
            editTextMostrarValorHora = findViewById(R.id.textViewValorHora)
            btnClear = findViewById(R.id.btnClear)

            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this)
            adapter = CamareroAdapter(camareros)
            recyclerView.adapter = adapter


            if (savedInstanceState != null) {
                val savedCamareros = savedInstanceState.getParcelableArrayListCompat<Camarero>("camareros")
                if (savedCamareros != null) {
                    camareros.addAll(savedCamareros)
                    adapter.notifyDataSetChanged()
                }

                val valorHora = savedInstanceState.getString("valor por hora")
                editTextMostrarValorHora.text = valorHora
            }

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
                editTextMostrarValorHora.text = "0"
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

                // Calcula el valor por hora
                val valorPorHora = if (totalHorasTrabajadas > 0) {
                    val textInputLayoutPropinas = findViewById<TextInputLayout>(R.id.editTextPropinaTotal)
                    val editTextPropinas = textInputLayoutPropinas.editText?.text.toString()
                    if (editTextPropinas.isNotEmpty()) {
                        val editTextPropinasReplace = editTextPropinas.replace(",", ".").toDouble()
                        val totProps = editTextPropinasReplace / totalHorasTrabajadas
                        val totPropsFormateado = String.format(Locale.ROOT,"%.2f", totProps)
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
                    val propinaIndividualFormat = String.format(Locale.ROOT,"%.2f",propinaIndividual)
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
        val savedCamareros = savedInstanceState.getParcelableArrayListCompat<Camarero>("camareros")
        if (savedCamareros != null) {
            camareros.clear()
            camareros.addAll(savedCamareros)
            adapter.notifyDataSetChanged()
        }
    }
}