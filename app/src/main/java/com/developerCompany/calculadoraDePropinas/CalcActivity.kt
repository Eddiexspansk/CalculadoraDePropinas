package com.developerCompany.calculadoraDePropinas

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
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
import java.util.ArrayList

class CalcActivity : AppCompatActivity() {
    private lateinit var textViewValorHora: TextView
    private lateinit var btnAgregar: Button
    private lateinit var btnClear: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CamareroAdapter
    private val camareros = ArrayList<Camarero>()
    private lateinit var mAdView : AdView
    
    private lateinit var textInputLayoutNombre: TextInputLayout
    private lateinit var textInputLayoutHoras: TextInputLayout
    private lateinit var textInputLayoutPropinasTotal: TextInputLayout

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

        // Initialize Mobile Ads once
        MobileAds.initialize(this) {}
        
        mAdView = findViewById(R.id.adView)
        // Set Ad Unit ID from BuildConfig if you want to be extra secure
        // mAdView.adUnitId = BuildConfig.AD_UNIT_ID 
        
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        btnAgregar = findViewById(R.id.btnAgregar)
        recyclerView = findViewById(R.id.recyclerView)
        textViewValorHora = findViewById(R.id.textViewValorHora)
        btnClear = findViewById(R.id.btnClear)
        
        textInputLayoutNombre = findViewById(R.id.editTextNombre)
        textInputLayoutHoras = findViewById(R.id.editTextHoras)
        textInputLayoutPropinasTotal = findViewById(R.id.editTextPropinaTotal)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        adapter = CamareroAdapter(camareros) {
            calcularPropinas()
        }
        recyclerView.adapter = adapter

        if (savedInstanceState != null) {
            val savedCamareros = savedInstanceState.getParcelableArrayListCompat<Camarero>("camareros")
            if (savedCamareros != null) {
                camareros.addAll(savedCamareros)
                adapter.notifyDataSetChanged()
                calcularPropinas()
            }

            val valorHora = savedInstanceState.getString("valor por hora")
            textViewValorHora.text = valorHora ?: getString(R.string.cero_con_decimales)
        }

        val swipeToDeleteCallback = SwipeToDeleteCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        textInputLayoutPropinasTotal.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                calcularPropinas()
            }
        })

        btnClear.setOnClickListener {
            textInputLayoutNombre.editText?.text?.clear()
            textInputLayoutHoras.editText?.text?.clear()
            textInputLayoutPropinasTotal.editText?.text?.clear()
            textViewValorHora.text = getString(R.string.cero_con_decimales)
            camareros.clear()
            adapter.notifyDataSetChanged()
        }

        btnAgregar.setOnClickListener {
            val nombre = textInputLayoutNombre.editText?.text.toString()
            val horasStr = textInputLayoutHoras.editText?.text.toString()

            if (nombre.isNotEmpty() && horasStr.isNotEmpty()) {
                val horas = horasStr.toDoubleOrNull() ?: 0.0
                val camarero = Camarero(nombre, horas)
                camareros.add(camarero)
                adapter.notifyItemInserted(camareros.size - 1)
                calcularPropinas()

                textInputLayoutNombre.editText?.text = null
                textInputLayoutHoras.editText?.text = null
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun calcularPropinas() {
        val totalHorasTrabajadas = camareros.sumOf { it.horasTrabajadas }

        val valorPorHora = if (totalHorasTrabajadas > 0) {
            val editTextPropinasStr = textInputLayoutPropinasTotal.editText?.text.toString()
            if (editTextPropinasStr.isNotEmpty()) {
                val propinasMonto = editTextPropinasStr.replace(",", ".").toDoubleOrNull() ?: 0.0
                val valorHora = propinasMonto / totalHorasTrabajadas
                val valorHoraFormateado = String.format(Locale.ROOT, "%.2f", valorHora)
                textViewValorHora.text = valorHoraFormateado
                valorHora
            } else {
                textViewValorHora.text = getString(R.string.cero_con_decimales)
                0.0
            }
        } else {
            textViewValorHora.text = getString(R.string.cero_con_decimales)
            0.0
        }

        for (camarero in camareros) {
            val propinaIndividual = camarero.horasTrabajadas * valorPorHora
            val propinaIndividualFormat = String.format(Locale.ROOT, "%.2f", propinaIndividual)
            camarero.propina = propinaIndividualFormat.replace(",", ".").toDoubleOrNull() ?: 0.0
        }
        
        adapter.notifyDataSetChanged()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("camareros", camareros)
        outState.putString("valor por hora", textViewValorHora.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedCamareros = savedInstanceState.getParcelableArrayListCompat<Camarero>("camareros")
        if (savedCamareros != null) {
            camareros.clear()
            camareros.addAll(savedCamareros)
            adapter.notifyDataSetChanged()
            calcularPropinas()
        }
    }
}
