package com.example.fitlife.fragments

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.fitlife.R
import com.example.fitlife.databinding.FragmentHomeBinding
import com.example.fitlife.utils.NotificationHelper

/**
 * FRAGMENTO DE INICIO: Pantalla principal de bienvenida.
 *
 * Responsabilidad:
 * - Mostrar nombre de usuario
 * - Proporcionar navegación a otras secciones
 * - Gestionar permisos y enviar notificaciones
 * - UI de bienvenida con botones principales
 */
class HomeFragment : Fragment() {

    // View Binding: acceso seguro a vistas del layout
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // SharedPreferences para guardar preferencias del usuario
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. CONFIGURAR NOMBRE DE USUARIO
        sharedPreferences = requireActivity().getSharedPreferences(
            "user_prefs",  // Nombre del archivo de preferencias
            Context.MODE_PRIVATE
        )
        val userName = sharedPreferences.getString("user_name", "Atleta")
        binding.textUserName.text = userName

        // 2. CONFIGURAR OPCIONES DE NAVEGACIÓN (limpia back stack)
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.homeFragment, true)  // Limpia historial al navegar
            .build()

        // 3. BOTÓN "VER RUTINAS" - Navega al fragmento de rutinas
        binding.btnRoutines.setOnClickListener {
            findNavController().navigate(R.id.routinesFragment, null, navOptions)
        }

        // 4. BOTÓN "FAVORITOS" - Navega al fragmento de favoritos
        binding.btnFavorites.setOnClickListener {
            findNavController().navigate(R.id.favoritesFragment, null, navOptions)
        }

        // 5. BOTÓN "RECORDARME ENTRENAR" - Envía notificación
        binding.btnNotify.setOnClickListener {
            showNotificationInfo()  // Muestra diálogo de confirmación primero
        }
    }

    /**
     * MUESTRA DIÁLOGO EXPLICATIVO antes de enviar notificación
     */
    private fun showNotificationInfo() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Recordatorio de entrenamiento")
            .setMessage("¿Enviar notificación que al tocarla abrirá Rutinas?")
            .setPositiveButton("Enviar") { _, _ ->
                checkAndSendNotification()  // Verifica permisos y envía
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    /**
     * VERIFICA Y MANEJA PERMISOS DE NOTIFICACIÓN
     * Android 13+ (API 33) requiere permiso explícito POST_NOTIFICATIONS
     */
    private fun checkAndSendNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ necesita permiso
            val permission = Manifest.permission.POST_NOTIFICATIONS

            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission
                ) == PackageManager.PERMISSION_GRANTED) {
                // Ya tiene permiso: enviar notificación
                sendWorkoutNotification()
            } else {
                // Solicitar permiso al usuario
                requestPermissions(arrayOf(permission), NOTIFICATION_PERMISSION_REQUEST_CODE)
            }
        } else {
            // Android 12 o inferior no necesita permiso explícito
            sendWorkoutNotification()
        }
    }

    /**
     * ENVÍA NOTIFICACIÓN Y MUESTRA FEEDBACK AL USUARIO
     */
    private fun sendWorkoutNotification() {
        // Usa NotificationHelper para crear y enviar notificación
        NotificationHelper.sendWorkoutNotification(requireContext())

        // Muestra Toast confirmando el envío
        Toast.makeText(
            requireContext(),
            "📲 Notificación enviada\nTócala para abrir Rutinas",
            Toast.LENGTH_LONG
        ).show()
    }

    /**
     * MANEJA RESULTADO DE SOLICITUD DE PERMISOS
     * @param requestCode Código de la solicitud
     * @param permissions Permisos solicitados
     * @param grantResults Resultados de la solicitud
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido: enviar notificación
                sendWorkoutNotification()
            } else {
                // Permiso denegado: informar al usuario
                Toast.makeText(
                    requireContext(),
                    "Permiso denegado para notificaciones",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    /**
     * CÓDIGO DE SOLICITUD DE PERMISO (identificador único)
     */
    companion object {
        private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 200
    }

    /**
     * LIMPIA REFERENCIAS cuando se destruye la vista
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Previene memory leaks
    }
}