package com.example.fitlife

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fitlife.utils.NotificationHelper
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * ACTIVIDAD PRINCIPAL: Contenedor único para todos los fragments
 *
 * Arquitectura: Single Activity con Navigation Component
 */
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Configurar Navigation Component
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)

        // Definir destinos de nivel superior (Bottom Navigation)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.routinesFragment,
                R.id.favoritesFragment
            )
        )

        // Conectar Toolbar y BottomNavigation con NavController
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Procesar si la app se abrió desde una notificación
        handler.postDelayed({
            processNotificationIntent()
        }, 800)
    }

    /**
     * Verifica si la app se abrió desde una notificación
     */
    private fun processNotificationIntent() {
        // Método 1: Verificar SharedPreferences
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        if (prefs.getBoolean("should_open_routines", false)) {
            prefs.edit().remove("should_open_routines").apply()
            navigateToRoutines()
            return
        }

        // Método 2: Verificar extras del Intent
        if (intent?.getStringExtra("source") == "notification") {
            navigateToRoutines()
        }
    }

    /**
     * Navega al fragmento de rutinas
     */
    private fun navigateToRoutines() {
        handler.postDelayed({
            try {
                navController.navigate(R.id.routinesFragment)
                NotificationHelper.clearNotificationFlags(this)
            } catch (e: Exception) {
                // Reintentar si falla
                handler.postDelayed({
                    navController.navigate(R.id.routinesFragment)
                }, 500)
            }
        }, 400)
    }

    /**
     * Maneja nueva intención (cuando se toca notificación con app en background)
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handler.postDelayed({
            processNotificationIntent()
        }, 500)
    }

    /**
     * Habilita flecha de retroceso en Toolbar
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}