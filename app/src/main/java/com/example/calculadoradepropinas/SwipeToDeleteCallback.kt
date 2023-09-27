package com.example.calculadoradepropinas

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView



class SwipeToDeleteCallback(private val adapter: CamareroAdapter) : ItemTouchHelper.SimpleCallback(
    0, // No hay arrastrar, por lo que pasamos 0
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT // Habilita el deslizamiento a la izquierda y a la derecha
) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        // No necesitamos manejar el movimiento, así que devolvemos false
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // Maneja el deslizamiento y elimina el elemento del adaptador
        val position = viewHolder.bindingAdapterPosition
        adapter.removeCamarero(position)
    }
}