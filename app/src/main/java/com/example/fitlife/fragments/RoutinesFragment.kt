package com.example.fitlife.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitlife.adapters.RoutineAdapter
import com.example.fitlife.databinding.FragmentRoutinesBinding
import com.example.fitlife.viewmodel.RoutineViewModel

/**
 * FRAGMENTO DE RUTINAS: Muestra lista completa de rutinas disponibles.
 *
 * Responsabilidad:
 * - Mostrar todas las rutinas en RecyclerView
 * - Observar cambios en los datos del ViewModel
 * - Navegar al detalle al hacer clic en una rutina
 * - Configurar y gestionar el RecyclerView
 */
class RoutinesFragment : Fragment() {

    // View Binding
    private var _binding: FragmentRoutinesBinding? = null
    private val binding get() = _binding!!

    // Adaptador para el RecyclerView
    private lateinit var adapter: RoutineAdapter

    // ViewModel compartido con otros fragments
    private val viewModel: RoutineViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoutinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. CONFIGURAR LAYOUT MANAGER (lista vertical)
        binding.recyclerViewRoutines.layoutManager = LinearLayoutManager(context)

        // 2. CREAR ADAPTADOR con callback para clics
        adapter = RoutineAdapter { routine ->
            // Al hacer clic en una rutina, navegar a su detalle
            val action = com.example.fitlife.R.id.action_routinesFragment_to_routineDetailFragment
            val bundle = Bundle().apply {
                putInt("routineId", routine.id)  // Pasar ID como argumento
            }
            findNavController().navigate(action, bundle)
        }

        // 3. ASIGNAR ADAPTADOR AL RECYCLERVIEW
        binding.recyclerViewRoutines.adapter = adapter

        // 4. OBSERVAR CAMBIOS EN LAS RUTINAS
        // LiveData notifica automáticamente cuando cambian los datos
        viewModel.routines.observe(viewLifecycleOwner) { routines ->
            adapter.submitList(routines)  // Actualiza lista en RecyclerView
        }
    }

    /**
     * LIMPIA REFERENCIAS cuando se destruye la vista
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}