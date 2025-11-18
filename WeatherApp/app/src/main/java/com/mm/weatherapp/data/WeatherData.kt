package com.mm.weatherapp.data

// Clase que representa el clima actual
data class Weather(
    val city: String,
    val temperature: Int,
    val description: String,
    val icon: String
)

// Clase para el pronóstico de un día
data class DailyForecast(
    val dayOfWeek: String,
    val temperature: Int,
    val icon: String
)

// Estados de la UI
sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(val weather: Weather) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}

sealed class ForecastUiState {
    object Loading : ForecastUiState()
    data class Success(val forecast: List<DailyForecast>) : ForecastUiState()
    data class Error(val message: String) : ForecastUiState()
}