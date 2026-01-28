package com.example.notasapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.DEBUG_PROPERTY_NAME

class NotaAdapter(
    private val notas: List<Nota>,
    private val onItemClick: (Nota) -> Unit // callback al pulsar
) : RecyclerView.Adapter<NotaAdapter.NotaViewHolder>() {

    class NotaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.titulo)
        val prioridad: TextView = view.findViewById(R.id.prioridad)
        val fecha: TextView = view.findViewById(R.id.fecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_nota, parent, false)
        return NotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = notas[position]
        holder.titulo.text = nota.titulo
        holder.prioridad.text = nota.prioridad.toString()
        holder.fecha.text = nota.fechaCreacion.toString()

        // Configurar el click listener
        holder.itemView.setOnClickListener {
            onItemClick(nota)
        }
    }

    override fun getItemCount(): Int = notas.size
}