package com.example.fitlife.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

/**
 * Diálogo reutilizable para confirmaciones
 *
 * Características:
 * - Título y mensaje personalizables
 * - Botones Sí/No
 * - Callback para manejar resultados
 */
class ConfirmDialogFragment : DialogFragment() {

    // Interface para comunicar resultado al fragment padre
    interface OnConfirmListener {
        fun onConfirm()
        fun onCancel()
    }

    private var listener: OnConfirmListener? = null

    // Método para establecer el listener
    fun setListener(listener: OnConfirmListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Obtener título y mensaje desde argumentos
        val title = arguments?.getString(ARG_TITLE) ?: ""
        val message = arguments?.getString(ARG_MESSAGE) ?: ""

        // Crear y configurar AlertDialog
        return AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Sí") { _, _ ->
                listener?.onConfirm()  // Usuario presionó Sí
            }
            .setNegativeButton("No") { _, _ ->
                listener?.onCancel()   // Usuario presionó No
            }
            .create()
    }

    companion object {
        // Keys para pasar datos en Bundle
        private const val ARG_TITLE = "title"
        private const val ARG_MESSAGE = "message"

        /**
         * Factory method para crear nueva instancia del diálogo
         */
        fun newInstance(title: String, message: String): ConfirmDialogFragment {
            val fragment = ConfirmDialogFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putString(ARG_MESSAGE, message)
            fragment.arguments = args
            return fragment
        }
    }
}