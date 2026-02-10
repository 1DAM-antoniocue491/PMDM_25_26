package com.example.practicaexamen

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import java.io.File
import java.io.FileInputStream

object FileManager {
    fun leerFichero(context: Context, filename: String): List<Ejemplo> {
        // Abrir un fichero en modo lectura del flujo
        val fis: FileInputStream = context.openFileInput(filename)
        var list = mutableListOf<Ejemplo>()

        // Lectura del flujo
        var contenidoLeido = ""
        fis.use { input ->
            val iterador = input.bufferedReader().lineSequence().iterator()
            while (iterador.hasNext()) {
                val contacto = iterador.next()
                val linea = contacto.split(";")
                list.add(Ejemplo(linea[0], linea[1], linea[2]))
            }
        }
        Log.d("Lectura", "Contenido leído: $contenidoLeido")

        return list
    }

    fun copyFileToInternalStorage(context: Context, fileName: String) {
        try {
            val file = File(context.filesDir, fileName)

            // Solo copiar si no existe
            if (!file.exists()) {
                // Leer desde res/raw o assets
                val inputStream = context.assets.open(fileName)
                val outputStream = context.openFileOutput(fileName, MODE_PRIVATE)

                inputStream.copyTo(outputStream)
                inputStream.close()
                outputStream.close()
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error copying file: ${e.message}")
        }
    }

    fun writeFile(context: Context, fileName: String, content: String) {
        try {
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use { outputStream ->
                outputStream.write(content.toByteArray())
            }
        } catch (e: Exception) {
            Log.e("FileManager", "Error writing file: ${e.message}")
        }
    }

    fun parseContent(content: List<Ejemplo>): String {
        val builder = StringBuilder()
        for (e in content) {
            builder.append("${e.component1()};${e.component2()};${e.component3()}\n")
        }
        return builder.toString()
    }

}