package com.example.notasapp

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
        context: android.content.Context,
        title: String? = null,
        message: String? = null,
        options: List<DialogOption> = emptyList(),
        isConfirmation: Boolean = false,
        onConfirm: (() -> Unit)? = null,
        onCancel: (() -> Unit)? = null
    ) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(context)
        title?.let { builder.setTitle(it) }
        message?.let { builder.setMessage(it) }

        if (isConfirmation) {
            // Diálogo de confirmación con Sí / No
            builder.setPositiveButton("Sí") { _, _ -> onConfirm?.invoke() }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
                onCancel?.invoke()
            }
        } else {
            // Diálogo de opciones múltiples
            if (options.isNotEmpty()) {
                val optionTitles = options.map { it.title }.toTypedArray()
                builder.setItems(optionTitles) { _, index ->
                    options[index].action()
                }
            }
            builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
        }

        builder.show()
    }
}
