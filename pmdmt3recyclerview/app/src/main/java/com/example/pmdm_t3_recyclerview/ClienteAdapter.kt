package com.example.pmdm_t3_recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class ClienteAdapter(
    private val clientes: MutableList<Cliente>,
    private val onEliminar: (Cliente) -> Unit
) : RecyclerView.Adapter<ClienteAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.tvNombre)
        val nif: TextView = view.findViewById(R.id.tvNIF)

        init {
            view.setOnClickListener {
                val cliente = clientes[adapterPosition]
                AlertDialog.Builder(view.context)
                    .setTitle("Eliminar cliente")
                    .setMessage("¿Desea eliminar a ${cliente.nombre}?")
                    .setPositiveButton("Eliminar") { _, _ ->
                        onEliminar(cliente)
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cliente, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cliente = clientes[position]
        holder.nombre.text = cliente.nombre
        holder.nif.text = cliente.nif
    }

    override fun getItemCount() = clientes.size
}