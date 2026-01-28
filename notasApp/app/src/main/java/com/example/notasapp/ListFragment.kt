package com.example.notasapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.MainScope

class ListFragment : Fragment(R.layout.fragment_list) {
    private val fileName: String = "notas.csv"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).copyFileToInternalStorage(fileName)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val lista = (activity as MainActivity).leerFichero(fileName)
        val contactosOrdenados = lista.sortedBy { it.titulo }

        val adapter = NotaAdapter(contactosOrdenados) { nota ->
            // Aquí usamos nuestro DialogHelper
            DialogHelper.showDialog(
                context = requireContext(),
                title = nota.titulo,
                isConfirmation = false,
                options = listOf(
                    DialogOption("Editar") {
                        Toast.makeText(requireContext(), "Editando", Toast.LENGTH_SHORT).show()
                    },
                    DialogOption("Eliminar") {
                        eliminarTarea()
                    },
                    DialogOption("Ver Información") {
                        Toast.makeText(requireContext(), "Viendo Información", Toast.LENGTH_SHORT).show()
                    }
                )
            )
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    fun eliminarTarea() {
        DialogHelper.showDialog(
            context = requireContext(),
            title = "Eliminar nota",
            message = "¿Estás seguro de que quieres eliminar esta nota?",
            isConfirmation = true,
            onConfirm = {
                Toast.makeText(requireContext(), "Nota eliminada", Toast.LENGTH_SHORT).show()
            },
            onCancel = {
                Toast.makeText(requireContext(), "Cancelado", Toast.LENGTH_SHORT).show()
            }
        )
    }
}
