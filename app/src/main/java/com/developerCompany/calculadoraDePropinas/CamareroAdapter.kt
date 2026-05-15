package com.developerCompany.calculadoraDePropinas


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CamareroAdapter(private val camareros: MutableList<Camarero>, private val onRemove: () -> Unit) :
    RecyclerView.Adapter<CamareroAdapter.ViewHolder>() {

    fun removeCamarero(position: Int) {
        if (position in 0 until camareros.size) {
            camareros.removeAt(position)
            notifyItemRemoved(position)
            onRemove()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNombre: TextView = itemView.findViewById(R.id.textViewNombre)
        val textViewHoras: TextView = itemView.findViewById(R.id.textViewHoras)
        val textViewPropina: TextView = itemView.findViewById(R.id.textViewPropinas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_camarero,  parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val camarero = camareros[position]


        holder.textViewNombre.text = camarero.nombre
        holder.textViewHoras.text = "Horas trabajadas: ${camarero.horasTrabajadas}"
        holder.textViewPropina.text = "Propina: $${camarero.propina}"

    }

    override fun getItemCount(): Int {
        return camareros.size
    }

    }
