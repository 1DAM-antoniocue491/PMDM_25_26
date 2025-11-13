package com.example.pmdm_t3_configapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var prefs: Prefs

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        prefs = Prefs.getInstance(this)
        val email = findViewById<EditText>(R.id.campo_email)
        val passwd = findViewById<EditText>(R.id.campo_password)

        findViewById<Button>(R.id.guardar).setOnClickListener {
            if (email.text.toString() == "" || passwd.text.toString() == "") {
                Toast.makeText(this, "Para guardar los datos no puedes dejar nada en blanco", Toast.LENGTH_SHORT).show()
            } else {
                prefs.putData(email.text.toString(), passwd.text.toString(), this)
                email.setText("")
                passwd.setText("")
            }

        }

        findViewById<Button>(R.id.recuperar).setOnClickListener {
            val data:Array<String?> = prefs.getData()
            if (data[0] == "" || data[1] == "") {
                Toast.makeText(this, "No hay ningún valor guardado en este momento", Toast.LENGTH_SHORT).show()
            } else if (email.text.toString() != "" || passwd.text.toString() != "") {
                Toast.makeText(this, "No es posible recuperar los datos ya que hay elementos escritos", Toast.LENGTH_SHORT).show()
            } else {
                email.setText(data[0])
                passwd.setText(data[1])
                Toast.makeText(this, "Datos recuperados correctamente", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.resetear).setOnClickListener {
            val data:Array<String?> = prefs.getData()
            if (data[0] == "" || data[1] == "") {
                Toast.makeText(this, "No hay ningún valor guardado en este momento", Toast.LENGTH_SHORT).show()
            } else {
                email.setText(data[0])
                passwd.setText(data[1])
                Toast.makeText(this, "Datos reseteados correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
}