package com.example.practicaexamen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class SecondFragment : Fragment(R.layout.fragment_second) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button7).setOnClickListener {
            ChangeScreen.fragmentToActivity(this, MainActivity::class.java)
        }

        view.findViewById<Button>(R.id.button8).setOnClickListener {
            ChangeScreen.fragmentToActivity(this, MainActivity2::class.java)
        }

        view.findViewById<Button>(R.id.button9).setOnClickListener {
            ChangeScreen.fragmentToFragment(this, OneFragment(), R.id.fragmentContainer)
        }
    }
}