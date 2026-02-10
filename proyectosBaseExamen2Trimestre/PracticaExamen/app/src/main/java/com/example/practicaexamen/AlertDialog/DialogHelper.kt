package com.mm.proyectofinal.ui.common

import android.R
import android.content.Context
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog

object DialogHelper {

    /**
     * Muestra un AlertDialog con opciones o confirmación
     *
     * @param context Context del Fragment o Activity
     * @param title Título opcional
     * @param message Mensaje opcional
     * @param options Lista de opciones (solo si isConfirmation = false)
     * @param isConfirmation Si true, se muestra un diálogo de confirmación con Sí/No
     * @param onConfirm Función a ejecutar si confirma (solo usado en confirmación)
     * @param onCancel Función a ejecutar si cancela (solo usado en confirmación)
     */
    fun showDialog(
        context: Context,
        title: String? = null,
        message: String? = null,
        options: List<DialogOption> = emptyList(),
        isConfirmation: Boolean = false,
        onConfirm: (() -> Unit)? = null,
        onCancel: (() -> Unit)? = null
    ) {
        val builder = AlertDialog.Builder(context)

        // Pon mensaje en el title si hay options, para no bloquear la lista
        if (options.isNotEmpty()) {
            builder.setTitle(message ?: title)
        } else {
            title?.let { builder.setTitle(it) }
            message?.let { builder.setMessage(it) }
        }

        if (isConfirmation) {
            builder.setPositiveButton("Sí") { _, _ -> onConfirm?.invoke() }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
                onCancel?.invoke()
            }
        } else {
            if (options.isNotEmpty()) {
                val adapter = ArrayAdapter<String>(
                    context,
                    R.layout.simple_list_item_1,
                    options.map { it.title }
                )
                builder.setAdapter(adapter) { _, index ->
                    options[index].action()
                }
            }
            builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
        }

        builder.show()
    }
}