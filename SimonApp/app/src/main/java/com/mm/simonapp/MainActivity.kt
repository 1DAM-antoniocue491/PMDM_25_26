package com.mm.simonapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.mm.simonapp.databinding.ActivityMainBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: Controller
    private var funcionamiento: Boolean = true
    private var secuence: ArrayList<ButtonColor> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller = Controller(binding.start, this)

        initButtons()

        binding.start.setOnClickListener {
            lifecycleScope.launch {
                controller.start()
                play()
                Log.i("Informacion", controller.getFunction().toString())
            }
        }



    }

    fun initButtons() {
        binding.btnRed.setOnClickListener {
            if (funcionamiento) {
                lifecycleScope.launch {
                    controller.buttonCliked(binding.btnRed)
                    secuence.add(ButtonColor.RED)
                    controller.playSound(ButtonColor.RED)
                }
            }
        }

        binding.btnGreen.setOnClickListener {
            if (funcionamiento) {
                lifecycleScope.launch {
                    controller.buttonCliked(binding.btnGreen)
                    secuence.add(ButtonColor.GREEN)
                    controller.playSound(ButtonColor.GREEN)
                }
            }
        }

        binding.btnBlue.setOnClickListener {
            if (funcionamiento) {
                lifecycleScope.launch {
                    controller.buttonCliked(binding.btnBlue)
                    secuence.add(ButtonColor.BLUE)
                    controller.playSound(ButtonColor.BLUE)
                }
            }
        }

        binding.btnYellow.setOnClickListener {
            if (funcionamiento) {
                lifecycleScope.launch {
                    controller.buttonCliked(binding.btnYellow)
                    secuence.add(ButtonColor.YELLOW)
                    controller.playSound(ButtonColor.YELLOW)
                }
            }
        }
    }

    suspend fun play() {
        while (funcionamiento) {
            if (!funcionamiento) return

            controller.generateSecuence()

            if (!funcionamiento) return

            // 👉 Esperas a que termine la secuencia
            controller.playSecuence(MainScope())

            if (!funcionamiento) return

            Toast.makeText(this, "Turno del jugador", Toast.LENGTH_SHORT).show()

            // 👉 Esperas a que el jugador complete la secuencia
            while (secuence.size < controller.getLevel()) {
                delay(1000)
            }

            if (!funcionamiento) return

            funcionamiento = controller.checkSequence(secuence)

            secuence.clear()

            if (!funcionamiento) return

            if (funcionamiento) {
                Toast.makeText(this, "Muy bien. Has subido un nivel", Toast.LENGTH_SHORT).show()
                binding.level.text = "Level ${controller.getLevel()}"
            } else {
                Toast.makeText(this, "Ohh. Has perdido", Toast.LENGTH_SHORT).show()
                binding.level.text = "Level 0"
            }

            delay(3000)
        }
    }

}