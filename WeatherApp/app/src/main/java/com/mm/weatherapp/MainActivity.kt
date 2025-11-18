package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.data.*
import com.example.weatherapp.ui.WeatherViewModel
import com.google.android.material.card.MaterialCardView
import com.mm.weatherapp.R
import com.mm.weatherapp.data.DailyForecast
import com.mm.weatherapp.data.ForecastUiState
import com.mm.weatherapp.data.Weather
import com.mm.weatherapp.data.WeatherUiState

class MainActivity : AppCompatActivity() {

    // Obtener instancia del ViewModel
    private val viewModel: WeatherViewModel by viewModels()

    // Referencias a las vistas
    private lateinit var cityTextView: TextView
    private lateinit var weatherIconTextView: TextView
    private lateinit var temperatureTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var refreshButton: Button
    private lateinit var forecastButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var forecastCard: MaterialCardView
    private lateinit var forecastTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        initViews()

        // Configurar observers
        setupObservers()

        // Configurar listeners de botones
        setupListeners()

        // Cargar clima inicial
        viewModel.loadWeather("Sevilla")
    }

    /**
     * Inicializa todas las referencias a las vistas
     */
    private fun initViews() {
        cityTextView = findViewById(R.id.cityTextView)
        weatherIconTextView = findViewById(R.id.weatherIconTextView)
        temperatureTextView = findViewById(R.id.temperatureTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        refreshButton = findViewById(R.id.refreshButton)
        forecastButton = findViewById(R.id.forecastButton)
        progressBar = findViewById(R.id.progressBar)
        forecastCard = findViewById(R.id.forecastCard)
        forecastTextView = findViewById(R.id.forecastTextView)
    }

    /**
     * Configura los observers para observar cambios en el ViewModel
     */
    private fun setupObservers() {
        // Observer del clima actual
        viewModel.weatherState.observe(this) { state ->
            when (state) {
                is WeatherUiState.Loading -> {
                    showLoading(true)
                    refreshButton.isEnabled = false
                }
                is WeatherUiState.Success -> {
                    showLoading(false)
                    refreshButton.isEnabled = true
                    displayWeather(state.weather)
                }
                is WeatherUiState.Error -> {
                    showLoading(false)
                    refreshButton.isEnabled = true
                    showError(state.message)
                }
            }
        }

        // Observer del pronóstico
        viewModel.forecastState.observe(this) { state ->
            when (state) {
                is ForecastUiState.Loading -> {
                    showLoading(true)
                    forecastButton.isEnabled = false
                    forecastCard.visibility = View.GONE
                }
                is ForecastUiState.Success -> {
                    showLoading(false)
                    forecastButton.isEnabled = true
                    displayForecast(state.forecast)
                }
                is ForecastUiState.Error -> {
                    showLoading(false)
                    forecastButton.isEnabled = true
                    showError(state.message)
                }
            }
        }
    }

    /**
     * Configura los listeners de los botones
     */
    private fun setupListeners() {
        refreshButton.setOnClickListener {
            // Cargar clima al presionar el botón
            viewModel.loadWeather("Sevilla")
        }

        forecastButton.setOnClickListener {
            // Cargar pronóstico al presionar el botón
            viewModel.loadForecast()
        }
    }

    /**
     * Muestra u oculta el indicador de carga
     */
    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    /**
     * Muestra los datos del clima en la UI
     */
    @SuppressLint("SetTextI18n")
    private fun displayWeather(weather: Weather) {
        cityTextView.text = "📍 ${weather.city}"
        weatherIconTextView.text = weather.icon
        temperatureTextView.text = "${weather.temperature}°C"
        descriptionTextView.text = weather.description

        // Mostrar mensaje de éxito
        Toast.makeText(this, "Clima actualizado", Toast.LENGTH_SHORT).show()
    }

    /**
     * Muestra el pronóstico en la UI
     */
    private fun displayForecast(forecast: List<DailyForecast>) {
        val forecastText = forecast.joinToString("\n") { day ->
            "${day.dayOfWeek}: ${day.temperature}°C ${day.icon}"
        }

        forecastTextView.text = forecastText
        forecastCard.visibility = View.VISIBLE

        Toast.makeText(this, "Pronóstico cargado", Toast.LENGTH_SHORT).show()
    }

    /**
     * Muestra un mensaje de error
     */
    private fun showError(message: String) {
        Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
    }
}