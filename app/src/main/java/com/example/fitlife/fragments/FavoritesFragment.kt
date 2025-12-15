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
import com.example.fitlife.databinding.FragmentFavoritesBinding
import com.example.fitlife.viewmodel.RoutineViewModel

/**
 * FRAGMENTO DE FAVORITOS: Muestra solo las rutinas marcadas como favoritas.
 *
 * Responsabilidad:
 * - Filtrar y mostrar solo rutinas favoritas
 * - Reutilizar el mismo Adapter de rutinas
 * - Navegar al detalle igual que en rutinas
 * - Observar cambios en favoritos automáticamente
 */
class FavoritesFragment : Fragment() {

    // View Binding
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    // Mismo adaptador que RoutinesFragment (reutilización)
    private lateinit var adapter: RoutineAdapter

    // Mismo ViewModel (comparte estado con RoutinesFragment)
    private val viewModel: RoutineViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. CREAR ADAPTADOR (igual que en RoutinesFragment)
        adapter = RoutineAdapter { routine ->
            // Navegar al detalle desde favoritos
            val action = com.example.fitlife.R.id.action_favoritesFragment_to_routineDetailFragment
            val bundle = Bundle().apply {
                putInt("routineId", routine.id)
            }
            findNavController().navigate(action, bundle)
        }

        // 2. CONFIGURAR RECYCLERVIEW
        binding.recyclerViewFavorites.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFavorites.adapter = adapter

        // 3. OBSERVAR SOLO RUTINAS FAVORITAS
        // LiveData de favorites ya está filtrado en el ViewModel
        viewModel.favorites.observe(viewLifecycleOwner) { favoriteRoutines ->
            adapter.submitList(favoriteRoutines)  // Solo favoritas
        }
    }
    }

