package com.example.weatherapp.data

import com.mm.weatherapp.data.DailyForecast
import com.mm.weatherapp.data.Weather
import kotlinx.coroutines.delay
import kotlin.random.Random

class WeatherRepository {
    // Simula una llamada a API que obtiene el clima actual
    // La palabra 'suspend' indica que es una función asíncrona
    suspend fun getCurrentWeather(city: String): Weather {
        // Simular latencia de red (2 segundos)
        delay(2000)

        // Simular posible error de red (20% de probabilidad)
        if (Random.nextInt(100) < 20) {
            throw Exception("Error de conexión")
        }

        // Generar datos aleatorios del clima
        val temperature = Random.nextInt(15, 35)
        val descriptions = listOf(
            "Soleado" to "☀️",
            "Nublado" to "☁️",
            "Lluvioso" to "🌧️",
            "Parcialmente nublado" to "⛅"
        )
        val (description, icon) = descriptions.random()

        return Weather(
            city = city,
            temperature = temperature,
            description = description,
            icon = icon
        )
    }

    // Simula obtener el pronóstico de 7 días
    suspend fun getWeeklyForecast(): List<DailyForecast> {
        // Simular latencia (3 segundos)
        delay(3000)

        // Simular posible error
        if (Random.nextInt(100) < 15) {
            throw Exception("No se pudo obtener el pronóstico")
        }

        val days = listOf(
            "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"
        )
        val icons = listOf("☀️", "☁️", "🌧️", "⛅", "🌤️")

        return days.map { day ->
            DailyForecast(
                dayOfWeek = day,
                temperature = Random.nextInt(18, 32),
                icon = icons.random()
            )
        }
    }
}