package com.example.fitlife.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitlife.data.RoutineRepository
import com.example.fitlife.models.Routine

/**
 * VIEWMODEL: Gestiona datos de rutinas para la UI.
 *
 * Responsabilidad:
 * - Proporcionar datos observables a los Fragments
 * - Mantener datos durante cambios de configuración (rotación)
 * - Separar lógica de negocio de la UI
 * - Notificar cambios a los observadores
 *
 * Patrón: MVVM (ViewModel)
 * Patrón: Observer (LiveData)
 */
class RoutineViewModel : ViewModel() {

    // Referencia al repositorio (fuente de datos)
    private val repository = RoutineRepository

    // LiveData observable para todas las rutinas
    private val _routines = MutableLiveData<List<Routine>>()
    val routines: LiveData<List<Routine>> get() = _routines

    // LiveData observable solo para favoritos
    private val _favorites = MutableLiveData<List<Routine>>()
    val favorites: LiveData<List<Routine>> get() = _favorites

    /**
     * INICIALIZADOR: Carga datos iniciales
     */
    init {
        loadRoutines()
    }

    /**
     * CARGA RUTINAS: Obtiene datos del repositorio y los publica en LiveData
     */
    private fun loadRoutines() {
        _routines.value = repository.getRoutines()
        _favorites.value = repository.getFavoriteRoutines()
    }

    /**
     * CAMBIA ESTADO DE FAVORITO
     * @param routineId ID de la rutina a modificar
     */
    fun toggleFavorite(routineId: Int) {
        repository.toggleFavoriteStatus(routineId)
        loadRoutines()  // Recarga para notificar a todos los observadores
    }

    /**
     * BUSCA RUTINA POR ID
     * @param routineId ID a buscar
     * @return Rutina encontrada o null
     */
    fun getRoutineById(routineId: Int): Routine? {
        return repository.getRoutineById(routineId)
    }
}