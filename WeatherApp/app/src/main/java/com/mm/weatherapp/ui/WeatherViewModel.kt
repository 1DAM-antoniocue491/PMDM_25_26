package com.example.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.*
import com.mm.weatherapp.data.ForecastUiState
import com.mm.weatherapp.data.WeatherUiState
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    // Instancia del repositorio
    private val repository = WeatherRepository()

    // Estado del clima actual (privado - solo el ViewModel lo modifica)
    private val _weatherState = MutableLiveData<WeatherUiState>()
    // Versión pública para que la Activity observe los cambios
    val weatherState: LiveData<WeatherUiState> = _weatherState

    // Estado del pronóstico
    private val _forecastState = MutableLiveData<ForecastUiState>()
    val forecastState: LiveData<ForecastUiState> = _forecastState

    /**
     * Carga el clima actual de forma asíncrona
     * @param city Ciudad de la que obtener el clima
     */
    fun loadWeather(city: String) {
        // viewModelScope: las corrutinas se cancelan automáticamente
        // cuando el ViewModel es destruido
        viewModelScope.launch {
            // 1. Establecer estado de carga
            _weatherState.value = WeatherUiState.Loading

            try {
                // 2. Llamar al repositorio (operación asíncrona)
                val weather = repository.getCurrentWeather(city)

                // 3. Actualizar con éxito
                _weatherState.value = WeatherUiState.Success(weather)

            } catch (e: Exception) {
                // 4. Manejar errores
                _weatherState.value = WeatherUiState.Error(
                    e.message ?: "Error desconocido"
                )
            }
        }
    }

    /**
     * Carga el pronóstico semanal de forma asíncrona
     */
    fun loadForecast() {
        viewModelScope.launch {
            _forecastState.value = ForecastUiState.Loading

            try {
                val forecast = repository.getWeeklyForecast()
                _forecastState.value = ForecastUiState.Success(forecast)

            } catch (e: Exception) {
                _forecastState.value = ForecastUiState.Error(
                    e.message ?: "Error al cargar pronóstico"
                )
            }
        }
    }
}