package com.example.notasapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast

class CreateDetailFragment : Fragment(R.layout.fragment_create_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.buttonGuardar)
            .setOnClickListener {
                val titulo = view.findViewById<EditText>(R.id.editTextTitulo).text
                val contenido = view.findViewById<EditText>(R.id.editTextContenido).text
                val prioridad = view.findViewById<Spinner>(R.id.spinnerPrioridad).toString()


                Toast.makeText(MainActivity(), prioridad, Toast.LENGTH_SHORT).show()
//                (activity as MainActivity)
//                    .changeFragment(ListFragment())
            }
    }
}
