package com.example.fitlife.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.fitlife.R
import com.example.fitlife.databinding.FragmentRoutineDetailBinding
import com.example.fitlife.dialogs.ConfirmDialogFragment
import com.example.fitlife.models.Routine
import com.example.fitlife.viewmodel.RoutineViewModel
import com.google.android.material.snackbar.Snackbar

class RoutineDetailFragment : Fragment() {
    /**
     * FRAGMENTO DE DETALLE DE RUTINA
     *
     * Muestra información completa de una rutina específica:
     * - Imagen, nombre, descripción, duración, nivel
     * - Lista de pasos detallados
     * - Botones para marcar como favorito y completado
     */
    private var _binding: FragmentRoutineDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RoutineViewModel by activityViewModels()
    private var isCompleted: Boolean = false
    private var currentRoutineId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoutineDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentRoutineId = arguments?.getInt("routineId") ?: -1

        if (currentRoutineId == -1) {
            Toast.makeText(requireContext(), "Error: Rutina no encontrada", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
            return
        }

        viewModel.routines.observe(viewLifecycleOwner) { routines ->
            val routine = routines.find { it.id == currentRoutineId }
            routine?.let { updateUi(it) }
        }

        binding.fabFavorite.setOnClickListener {
            showFavoriteConfirmationDialog(currentRoutineId)
        }

        binding.btnComplete.setOnClickListener {
            toggleCompletedState()
        }

        // Inicializar el estado visual del botón
        updateCompleteButton()
    }

    private fun updateUi(routine: Routine) {
        binding.textDetailName.text = routine.name
        binding.textDetailDescription.text = routine.description
        binding.textDetailDuration.text = routine.duration
        binding.textDetailLevel.text = routine.level

        val resourceId = requireContext().resources.getIdentifier(
            routine.imageName,
            "drawable",
            requireContext().packageName
        )
        Glide.with(requireContext())
            .load(if (resourceId != 0) resourceId else R.drawable.ic_fitness)
            .into(binding.imageDetail)

        binding.textDetailSteps.text = routine.steps.joinToString("\n\n") { "• $it" }
        updateFavoriteButton(routine.isFavorite)
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        if (isFavorite) {
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite_filled)
            binding.fabFavorite.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.accent)
        } else {
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
            binding.fabFavorite.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.primary)
        }
    }

    /**
     * ACTUALIZA LA APARIENCIA VISUAL DEL BOTÓN DE COMPLETADO
     */
    private fun updateCompleteButton() {
        if (isCompleted) {
            // ESTADO: COMPLETADO ✓
            binding.btnComplete.text = "COMPLETADO ✓"
            binding.btnComplete.setIconResource(R.drawable.ic_check)  // Necesitas este icono
            binding.btnComplete.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.secondary_variant)  // Verde
            binding.btnComplete.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        } else {
            // ESTADO: NO COMPLETADO
            binding.btnComplete.text = "MARCAR COMO COMPLETADO"
            binding.btnComplete.setIconResource(R.drawable.ic_check_empty)  // Icono vacío
            binding.btnComplete.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.secondary)  // Turquesa
            binding.btnComplete.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }
    }

    private fun showFavoriteConfirmationDialog(routineId: Int) {
        val currentRoutine = viewModel.routines.value?.find { it.id == routineId }
        val isCurrentlyFavorite = currentRoutine?.isFavorite == true

        val title = if (isCurrentlyFavorite)
            "¿Quitar de favoritos?"
        else
            "¿Añadir a favoritos?"

        val message = if (isCurrentlyFavorite)
            "¿Estás seguro de que quieres quitar esta rutina de favoritos?"
        else
            "¿Estás seguro de que quieres añadir esta rutina a favoritos?"

        val dialog = ConfirmDialogFragment.newInstance(title, message)

        dialog.setListener(object : ConfirmDialogFragment.OnConfirmListener {
            override fun onConfirm() {
                viewModel.toggleFavorite(routineId)
                val message = if (isCurrentlyFavorite)
                    "Quitado de favoritos"
                else
                    "Añadido a favoritos"
                Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
            }

            override fun onCancel() {
                Toast.makeText(requireContext(), "Acción cancelada", Toast.LENGTH_SHORT).show()
            }
        })

        dialog.show(parentFragmentManager, "confirm_dialog")
    }

    private fun toggleCompletedState() {
        isCompleted = !isCompleted

        if (isCompleted) {
            // Mostrar Snackbar con opción de deshacer
            Snackbar.make(binding.root, "¡Rutina completada! +10 puntos", Snackbar.LENGTH_LONG)
                .setAction("DESHACER") {
                    isCompleted = false
                    updateCompleteButton()  // Actualizar botón al deshacer
                    Toast.makeText(requireContext(), "Acción deshecha", Toast.LENGTH_SHORT).show()
                }
                .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.accent))
                .show()

            Toast.makeText(requireContext(), "¡Excelente trabajo! Rutina completada", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Rutina marcada como no completada", Toast.LENGTH_SHORT).show()
        }

        // ¡IMPORTANTE! Actualizar la apariencia del botón
        updateCompleteButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}