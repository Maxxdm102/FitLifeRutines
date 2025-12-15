package com.example.fitlife.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitlife.R
import com.example.fitlife.databinding.ItemRoutineCardBinding
import com.example.fitlife.models.Routine

/**
 * ADAPTADOR PARA RECYCLERVIEW: Conecta datos de rutinas con la UI de tarjetas.
 *
 * Responsabilidad:
 * - Crear ViewHolders para cada item
 * - Vincular datos a las vistas
 * - Gestionar clics en items
 * - Diferenciar cambios eficientemente
 *
 * Patrón: Adapter (RecyclerView.Adapter)
 * Patrón: ViewHolder (Patrón de diseño)
 * Patrón: ListAdapter (con DiffUtil para animaciones)
 */
class RoutineAdapter(
    // Callback llamado cuando se hace clic en una rutina
    private val onItemClick: (Routine) -> Unit
) : ListAdapter<Routine, RoutineAdapter.RoutineViewHolder>(RoutineDiffCallback()) {

    /**
     * CREA NUEVO VIEWHOLDER cuando RecyclerView necesita uno
     * @param parent Vista padre (RecyclerView)
     * @param viewType Tipo de vista (no usado aquí)
     * @return Nuevo ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        // Infla el layout usando View Binding
        val binding = ItemRoutineCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RoutineViewHolder(binding)
    }

    /**
     * VINCULA DATOS a un ViewHolder existente
     * @param holder ViewHolder a actualizar
     * @param position Posición en la lista
     */
    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * VIEWHOLDER: Representa cada item (tarjeta) en la lista
     * Contiene referencias a las vistas para acceso rápido
     */
    inner class RoutineViewHolder(
        private val binding: ItemRoutineCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        /**
         * VINCULA DATOS DE UNA RUTINA A LAS VISTAS
         * @param routine Rutina a mostrar
         */
        fun bind(routine: Routine) {
            // 1. ASIGNAR TEXTO A LAS VISTAS
            binding.textRoutineName.text = routine.name
            binding.textRoutineDuration.text = routine.duration
            binding.textRoutineLevel.text = routine.level

            // 2. CARGAR IMAGEN CON GLIDE
            val context = binding.root.context
            // Convierte imageName (String) a resource ID (Int)
            val resourceId = context.resources.getIdentifier(
                routine.imageName,  // Nombre del recurso (ej: "img_pushup_real")
                "drawable",          // Tipo de recurso
                context.packageName  // Paquete de la app
            )

            // Usa Glide para cargar la imagen (con fallback si no existe)
            Glide.with(context)
                .load(if (resourceId != 0) resourceId else R.drawable.ic_fitness)
                .into(binding.imageRoutine)

            // 3. ACTUALIZAR ICONO DE FAVORITO
            val favoriteIcon = if (routine.isFavorite)
                R.drawable.ic_favorite_filled  // Corazón lleno
            else
                R.drawable.ic_favorite         // Corazón vacío
            binding.iconFavorite.setImageResource(favoriteIcon)

            // Cambiar color según estado de favorito
            val iconColor = if (routine.isFavorite)
                R.color.accent  // Rosa cuando es favorito
            else
                R.color.primary // Morado cuando no es favorito
            binding.iconFavorite.setColorFilter(
                ContextCompat.getColor(context, iconColor)
            )

            // 4. CONFIGURAR CLIC EN LA TARJETA COMPLETA
            binding.root.setOnClickListener {
                onItemClick(routine)  // Llama al callback con la rutina clicada
            }
        }
    }
}

/**
 * DIFFUTIL CALLBACK: Calcula diferencias entre listas para animaciones eficientes
 *
 * Responsabilidad:
 * - Determinar si dos items son el mismo (misma rutina)
 * - Determinar si dos items tienen el mismo contenido
 *
 * Esto permite que RecyclerView solo actualice lo que cambió
 */
class RoutineDiffCallback : DiffUtil.ItemCallback<Routine>() {
    /**
     * VERIFICA SI SON EL MISMO ITEM (misma rutina)
     * @param oldItem Item viejo
     * @param newItem Item nuevo
     * @return true si tienen el mismo ID (misma rutina)
     */
    override fun areItemsTheSame(oldItem: Routine, newItem: Routine): Boolean {
        return oldItem.id == newItem.id  // Misma rutina si mismo ID
    }

    /**
     * VERIFICA SI TIENEN EL MISMO CONTENIDO
     * @param oldItem Item viejo
     * @param newItem Item nuevo
     * @return true si todos los campos son iguales
     */
    override fun areContentsTheSame(oldItem: Routine, newItem: Routine): Boolean {
        return oldItem == newItem  // Usa equals() de data class
    }
}