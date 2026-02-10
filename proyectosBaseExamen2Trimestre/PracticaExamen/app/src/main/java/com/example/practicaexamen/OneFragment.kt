package com.example.practicaexamen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.practicaexamen.Corrutinas.MainActivity3

class OneFragment : Fragment(R.layout.fragment_one) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button4).setOnClickListener {
            ChangeScreen.fragmentToActivity(this, MainActivity::class.java)
        }

        view.findViewById<Button>(R.id.button5).setOnClickListener {
            ChangeScreen.fragmentToActivity(this, MainActivity2::class.java)
        }

        view.findViewById<Button>(R.id.button6).setOnClickListener {
            ChangeScreen.fragmentToFragment(this, SecondFragment(), R.id.fragmentContainer)
        }

        view.findViewById<Button>(R.id.button10).setOnClickListener {
            ChangeScreen.fragmentToActivity(this, MainActivity3::class.java)
        }
    }
}