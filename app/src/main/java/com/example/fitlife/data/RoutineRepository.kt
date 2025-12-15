package com.example.fitlife.data

import com.example.fitlife.models.Routine

/**
 * REPOSITORIO DE DATOS: Fuente única de datos para rutinas.
 *
 * Responsabilidad:
 * - Proporcionar datos a la app
 * - Manejar operaciones CRUD básicas
 * - Simular una base de datos local
 * - Mantener el estado de favoritos
 *
 * Patrón: Repository (Fuente única de verdad)
 * Patrón: Singleton (Solo una instancia)
 */
object RoutineRepository {

    // Lista en memoria que simula una base de datos
    private val routines = mutableListOf<Routine>()

    /**
     * INICIALIZADOR: Carga datos de ejemplo al crear el repositorio
     */
    init {
        initializeSampleData()
    }

    /**
     * INICIALIZA DATOS DE EJEMPLO: 5 rutinas de fitness
     */
    private fun initializeSampleData() {
        routines.clear()
        routines.addAll(
            listOf(
                // RUTINA 1: PUSH-UPS
                Routine(
                    id = 1,
                    name = "Rutina de Push-ups",
                    description = "Fortalece pecho, hombros y tríceps con este ejercicio fundamental.",
                    duration = "10 min",
                    level = "Principiante",
                    imageName = "img_pushup_real",  // Debe existir en res/drawable/
                    steps = listOf(
                        "Colócate en posición de plancha alta, manos debajo de los hombros.",
                        "Baja el cuerpo hasta que el pecho casi toque el suelo.",
                        "Empuja hacia arriba con fuerza para volver a la posición inicial.",
                        "Realiza 3 series de 10 a 12 repeticiones."
                    ),
                    isFavorite = true  // Por defecto en favoritos
                ),

                // RUTINA 2: PLANCHA
                Routine(
                    id = 2,
                    name = "Rutina de Core con Plancha",
                    description = "Desarrolla un core fuerte y estable para mejorar postura y rendimiento.",
                    duration = "15 min",
                    level = "Intermedio",
                    imageName = "img_plank_real",
                    steps = listOf(
                        "Mantén posición de plancha sobre antebrazos 45 segundos.",
                        "Realiza planchas laterales, 30 segundos por cada lado.",
                        "Incorpora 'mountain climbers' durante 45 segundos.",
                        "Repite el circuito 3 veces con 1 minuto de descanso."
                    ),
                    isFavorite = false
                ),

                // RUTINA 3: CARDIO
                Routine(
                    id = 3,
                    name = "Rutina de Cardio Intenso",
                    description = "Quema calorías y mejora resistencia cardiovascular.",
                    duration = "20 min",
                    level = "Avanzado",
                    imageName = "img_cardio_real",
                    steps = listOf(
                        "5 minutos de saltos de tijera como calentamiento.",
                        "3 series de 15 'burpees' con máxima intensidad.",
                        "3 series de 30 segundos de 'high knees'.",
                        "Finaliza con 2 minutos de enfriamiento."
                    ),
                    isFavorite = true
                ),

                // RUTINA 4: YOGA
                Routine(
                    id = 4,
                    name = "Rutina de Yoga Matutino",
                    description = "Despierta cuerpo y mente con secuencia suave de yoga.",
                    duration = "15 min",
                    level = "Principiante",
                    imageName = "img_yoga_real",
                    steps = listOf(
                        "Comienza en posición de montaña (Tadasana) 1 minuto.",
                        "Realiza 5 saludos al sol (Surya Namaskar).",
                        "Mantén postura del perro boca abajo 30 segundos.",
                        "Finaliza con postura del niño 1 minuto."
                    ),
                    isFavorite = false
                ),

                // RUTINA 5: SENTADILLAS
                Routine(
                    id = 5,
                    name = "Rutina de Sentadillas",
                    description = "Fortalece piernas, glúteos y core con ejercicio completo.",
                    duration = "12 min",
                    level = "Intermedio",
                    imageName = "img_squat_real",
                    steps = listOf(
                        "Pies al ancho de hombros, punta ligeramente hacia afuera.",
                        "Baja como si fueras a sentarte, espalda recta y pecho alto.",
                        "Asegura que rodillas no sobrepasen punta de pies.",
                        "4 series de 15 repeticiones con 45 segundos descanso."
                    ),
                    isFavorite = false
                )
            )
        )
    }

    /**
     * OBTIENE TODAS LAS RUTINAS
     * @return Lista completa de rutinas
     */
    fun getRoutines(): List<Routine> {
        return routines.toList()  // Copia para inmutabilidad
    }

    /**
     * OBTIENE RUTINAS FAVORITAS
     * @return Lista filtrada solo con rutinas marcadas como favoritas
     */
    fun getFavoriteRoutines(): List<Routine> {
        return routines.filter { it.isFavorite }
    }

    /**
     * CAMBIA ESTADO DE FAVORITO
     * @param routineId ID de la rutina a modificar
     */
    fun toggleFavoriteStatus(routineId: Int) {
        val routine = routines.find { it.id == routineId }
        routine?.let {
            it.isFavorite = !it.isFavorite  // Invierte estado
        }
    }

    /**
     * BUSCA RUTINA POR ID
     * @param routineId ID a buscar
     * @return Rutina encontrada o null
     */
    fun getRoutineById(routineId: Int): Routine? {
        return routines.find { it.id == routineId }
    }
}