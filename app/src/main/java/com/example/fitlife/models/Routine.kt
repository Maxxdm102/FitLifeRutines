package com.example.fitlife.models

import java.io.Serializable

/**
 * MODELO DE DATOS: Representa una rutina de ejercicio.
 *
 * Responsabilidad:
 * - Contener todos los datos de una rutina
 * - Ser serializable para pasar entre fragments
 * - Definir la estructura de datos de la app
 *
 * Patrón: Data Class (Modelo en MVVM)
 */
data class Routine(
    // Identificador único de la rutina
    val id: Int,

    // Nombre de la rutina (ej: "Push-ups")
    val name: String,

    // Descripción detallada del ejercicio
    val description: String,

    // Duración estimada (ej: "10 min")
    val duration: String,

    // Nivel de dificultad (Principiante, Intermedio, Avanzado)
    val level: String,

    // Nombre del recurso de imagen (ej: "img_pushup_real")
    // Se usa con Glide para cargar la imagen
    val imageName: String,

    // Lista de pasos para realizar la rutina
    val steps: List<String>,

    // Estado de favorito (true = en favoritos)
    var isFavorite: Boolean = false
) : Serializable  // Permite pasar objetos entre fragments