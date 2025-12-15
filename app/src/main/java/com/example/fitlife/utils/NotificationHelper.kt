package com.example.fitlife.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.fitlife.MainActivity
import com.example.fitlife.R

/**
 * Helper para crear y enviar notificaciones
 *
 * Responsabilidades:
 * - Crear canal de notificación (Android 8+)
 * - Enviar notificaciones con PendingIntent
 * - Guardar flags para navegación desde notificación
 */
object NotificationHelper {

    private const val CHANNEL_ID = "fitlife_reminders"
    private const val NOTIFICATION_ID = 101

    /**
     * Envía notificación de recordatorio de entrenamiento
     */
    fun sendWorkoutNotification(context: Context) {
        createNotificationChannel(context)

        // Guardar flag para que MainActivity sepa abrir Rutinas
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("should_open_routines", true).apply()

        // Intent para abrir MainActivity cuando se toque la notificación
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("source", "notification")
        }

        // PendingIntent para el sistema de notificaciones
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Construir notificación
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_fitness)
            .setContentTitle("🏋️ ¡Hora de entrenar!")
            .setContentText("Toca para ver tus rutinas disponibles")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)  // Qué pasa al tocar
            .setAutoCancel(true)  // Se cierra automáticamente al tocar
            .addAction(R.drawable.ic_fitness, "Abrir Rutinas", pendingIntent)
            .build()

        // Enviar notificación
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    /**
     * Crea canal de notificación (requerido Android 8+)
     */
    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Recordatorios FitLife"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Limpia flags después de navegar desde notificación
     */
    fun clearNotificationFlags(context: Context) {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        prefs.edit().remove("should_open_routines").apply()
    }
}