package com.example.pmdm_t3_recyclerview

object NifUtils {
    private val letras = "TRWAGMYFPDXBNJZSQVHLCKE"

    fun validarNIF(nif: String): Boolean {
        if (!nif.matches(Regex("\\d{8}[A-Z]"))) return false
        val numero = nif.substring(0, 8).toIntOrNull() ?: return false
        val letraCorrecta = letras[numero % 23]
        return letraCorrecta == nif.last()
    }
}