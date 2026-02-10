package com.example.practicaexamen

import android.content.Context
import android.content.Intent
import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

object ChangeScreen {
    // -------------------------
    // Navegación Activity -> Activity
    // -------------------------
    fun activityToActivity(
        context: Context,
        activityClass: Class<out Activity>,
        extras: Bundle? = null
    ) {
        val intent = Intent(context, activityClass)
        extras?.let { intent.putExtras(it) }
        context.startActivity(intent)
    }

    /*
    Ejemplo de uso:
    val bundle = Bundle().apply {
        putString("nombre", "Tony")
        putInt("edad", 30)
    }
    activityToActivity(this, OtraActivity::class.java, bundle)

    En OtraActivity:
    val nombre = intent.getStringExtra("nombre") // Tony
    val edad = intent.getIntExtra("edad", 0)     // 30
    intent.getParcelableArrayListExtra<Usuario>("usuarios")
    */

    // -------------------------
    // Navegación Activity -> Fragment
    // -------------------------
    fun activityToFragment(
        activity: AppCompatActivity,
        fragment: Fragment,
        containerId: Int,
        args: Bundle? = null
    ) {
        args?.let { fragment.arguments = it }
        activity.supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .addToBackStack(null)
            .commit()
    }

    /*
    Ejemplo de uso:
    val args = Bundle().apply { putString("titulo", "Hola") }
    activityToFragment(this, MiFragment(), R.id.container, args)

    En MiFragment:
    val titulo = arguments?.getString("titulo") // Hola
    arguments?.getParcelableArrayList("usuarios")
    */

    // -------------------------
    // Navegación Fragment -> Fragment
    // -------------------------
    fun fragmentToFragment(
        fragment: Fragment,
        nextFragment: Fragment,
        containerId: Int,
        args: Bundle? = null
    ) {
        args?.let { nextFragment.arguments = it }
        fragment.parentFragmentManager.beginTransaction()
            .replace(containerId, nextFragment)
            .addToBackStack(null)
            .commit()
    }

    /*
    Ejemplo de uso:
    val args = Bundle().apply { putInt("id", 42) }
    fragmentToFragment(this, OtroFragment(), R.id.container, args)

    En OtroFragment:
    val id = arguments?.getInt("id") ?: 0 // 42
    arguments?.getParcelableArrayList("usuarios")
    */

    // -------------------------
    // Navegación Fragment -> Activity
    // -------------------------
    fun fragmentToActivity(
        fragment: Fragment,
        activityClass: Class<out Activity>,
        extras: Bundle? = null
    ) {
        val intent = Intent(fragment.requireContext(), activityClass)
        extras?.let { intent.putExtras(it) }
        fragment.startActivity(intent)
    }

    /*
    Ejemplo de uso:
    val bundle = Bundle().apply { putBoolean("activo", true) }
    fragmentToActivity(this, OtraActivity::class.java, bundle)

    En OtraActivity:
    val activo = intent.getBooleanExtra("activo", false) // true
    intent.getParcelableArrayListExtra<Usuario>("usuarios")
    */


}
