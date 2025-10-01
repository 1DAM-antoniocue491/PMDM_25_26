package com.mm.pmdm_t2_myweatherapp

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    // Creamos un atributo privado qeu obtiene el idioma por defecto
    private var idioma: String = Locale.getDefault().language

    @RequiresApi(Build.VERSION_CODES.O)
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

        val button = findViewById<Button>(R.id.translate)

        button.setOnClickListener {
            buttonConfiguration()
        }

        generateDays(Locale(idioma))
        generateMaxTemperature()
        generateMinTemperature()
        putImage()
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateDays(idioma: Locale) {
        // Obtención de los textView de los días
        val lunes = findViewById<TextView>(R.id.lunes)
        val martes = findViewById<TextView>(R.id.martes)
        val miercoles = findViewById<TextView>(R.id.miercoles)
        val jueves = findViewById<TextView>(R.id.jueves)
        val viernes = findViewById<TextView>(R.id.viernes)


        // Genero la fecha actual y un formateador para el mes
        val fecha_hoy = LocalDate.now()
        val format = DateTimeFormatter.ofPattern("MMM", idioma)

        // Asignación del mes y día a los componentes textView
        lunes.text = dia(fecha_hoy, 0, format)
        martes.text = dia(fecha_hoy, 1, format)
        miercoles.text = dia(fecha_hoy, 2, format)
        jueves.text = dia(fecha_hoy, 3, format)
        viernes.text = dia(fecha_hoy, 4, format)
    }

    // Pasándole la fecha actual, la cantidad de días que quieres sumar y un formateador para el mes,
    // devuelve una cadena String que es la que se inserta en cada TextView
    @RequiresApi(Build.VERSION_CODES.O)
    private fun dia(fechaActual: LocalDate, sum: Int, format: DateTimeFormatter): String {
        val nuevaFecha = fechaActual.plusDays(sum.toLong())
        val nuevoDia = nuevaFecha.dayOfMonth
        val nuevoMes = nuevaFecha.format(format)

        return "$nuevoMes $nuevoDia"
    }

    @SuppressLint("SetTextI18n")
    private fun generateMaxTemperature() {
        // Genero una horquilla de temperatura
        val min: Int = 15
        val max: Int = 30

        // Obtención de los textView de los días
        val lunes = findViewById<TextView>(R.id.max_lunes)
        val martes = findViewById<TextView>(R.id.max_martes)
        val miercoles = findViewById<TextView>(R.id.max_miercoles)
        val jueves = findViewById<TextView>(R.id.max_jueves)
        val viernes = findViewById<TextView>(R.id.max_viernes)

        // Inserto los datos en el TextView
        lunes.text = Random.nextInt(min, max).toString() + " ºC"
        martes.text = Random.nextInt(min, max).toString() + " ºC"
        miercoles.text = Random.nextInt(min, max).toString() + " ºC"
        jueves.text = Random.nextInt(min, max).toString() + " ºC"
        viernes.text = Random.nextInt(min, max).toString() + " ºC"
    }

    @SuppressLint("SetTextI18n")
    private fun generateMinTemperature() {
        // Genero una horquilla de temperatura
        val min: Int = 5
        val max: Int = 15

        // Obtención de los textView de los días
        val lunes = findViewById<TextView>(R.id.min_lunes)
        val martes = findViewById<TextView>(R.id.min_martes)
        val miercoles = findViewById<TextView>(R.id.min_miercoles)
        val jueves = findViewById<TextView>(R.id.min_jueves)
        val viernes = findViewById<TextView>(R.id.min_viernes)

        // Inserto los datos en el TextView
        lunes.text = Random.nextInt(min, max).toString() + " ºC"
        martes.text = Random.nextInt(min, max).toString() + " ºC"
        miercoles.text = Random.nextInt(min, max).toString() + " ºC"
        jueves.text = Random.nextInt(min, max).toString() + " ºC"
        viernes.text = Random.nextInt(min, max).toString() + " ºC"
    }

    @SuppressLint("WrongViewCast")
    private fun putImage() {
        // Obtención de los textView de los días
        val lunes = findViewById<ImageView>(R.id.estado_lunes)
        val martes = findViewById<ImageView>(R.id.estado_martes)
        val miercoles = findViewById<ImageView>(R.id.estado_miercoles)
        val jueves = findViewById<ImageView>(R.id.estado_jueves)
        val viernes = findViewById<ImageView>(R.id.estado_viernes)

        val imagenes = arrayOf(R.drawable.llueve, R.drawable.nubes, R.drawable.sol)

        // Inserto los datos en el TextView
        lunes.setImageResource(ramdomImg(imagenes))
        martes.setImageResource(ramdomImg(imagenes))
        miercoles.setImageResource(ramdomImg(imagenes))
        jueves.setImageResource(ramdomImg(imagenes))
        viernes.setImageResource(ramdomImg(imagenes))
    }

    private fun ramdomImg(imagenes: Array<Int>): Int {
        val i: Int = Random.nextInt(0, imagenes.size)

        return imagenes[i]
    }

    private fun buttonConfiguration() {
        var locale: Locale = Locale(idioma)

        if (idioma.equals("es")) {
            locale = Locale("en")
        } else {
            locale = Locale("es")
        }

        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        // Reinicia la actividad para que se apliquen los cambios
        recreate()
    }
}