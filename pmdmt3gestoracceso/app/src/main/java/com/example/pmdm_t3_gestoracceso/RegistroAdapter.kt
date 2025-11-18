package com.example.pmdm_t3_gestoracceso

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RegistroAdapter(private val registros: List<Registro>)
    : RecyclerView.Adapter<RegistroAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tipo: TextView = view.findViewById(R.id.tipo)
        val fecha: TextView = view.findViewById(R.id.fecha)
        val hora: TextView = view.findViewById(R.id.hora)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_registro, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val registro = registros[position]
        holder.tipo.text = registro.tipo
        holder.fecha.text = registro.fecha
        holder.hora.text = registro.hora
    }

    override fun getItemCount(): Int = registros.size
}