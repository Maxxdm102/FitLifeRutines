FitLife - App de Fitness:
Descripción del Proyecto:
FitLife es una aplicación Android desarrollada en Kotlin que permite a los usuarios gestionar rutinas de ejercicios de forma eficiente. La aplicación implementa las mejores prácticas de desarrollo Android moderno con una arquitectura limpia y escalable.

-Características Principales:
 - Navegación Avanzada
Navigation Component para gestión de flujos
BottomNavigationView con 3 secciones principales
Deep Links para notificaciones
Safe Args para paso de datos entre fragments

- UI Moderna con Material Design
CardView personalizadas para listas
FloatingActionButton para acciones principales
Chips para etiquetas de nivel
Snackbar con acción interactiva ("DESHACER")
Toast para mensajes rápidos

- Gestión de Datos
ViewModel compartido entre fragments
LiveData para actualizaciones automáticas de UI
Repository Pattern como fuente única de verdad
ListAdapter + DiffUtil para RecyclerView eficiente

- Notificaciones Profesionales
Notification Channel propio ("FitLife Recordatorios")
PendingIntent que abre directamente la sección Rutinas
Compatibilidad total con Android 13+ (permisos POST_NOTIFICATIONS)
Deep Link personalizado: fitlife://routines

 - Diálogos Personalizados
DialogFragment reutilizable para confirmaciones
Callbacks personalizados para manejar respuestas
Bundle Arguments para personalizar títulos y mensajes




Funcionalidades Detalladas:

1.  Pantalla de Inicio (HomeFragment)
Saludo personalizado con nombre de usuario
3 botones principales de navegación rápida
Sistema de permisos para notificaciones (Android 13+)
Diálogo explicativo antes de enviar notificaciones
Feedback visual con Toast tras enviar notificación

2.  Lista de Rutinas (RoutinesFragment)
RecyclerView con 5 rutinas predefinidas
Cada tarjeta muestra:
Imagen del ejercicio (cargada con Glide)
Nombre de la rutina
Duración aproximada
Nivel de dificultad (Principiante/Intermedio/Avanzado)
Icono de favorito con color dinámico
Click en tarjeta → navegación a pantalla de detalle
LayoutManager vertical con animaciones

3.  Detalle de Rutina (RoutineDetailFragment)
Imagen destacada en alta calidad
Descripción completa del ejercicio
Chips informativos (duración + nivel)
Lista numerada de pasos a seguir
Botón "Completado" que:
Cambia visualmente (texto + color)
Muestra Snackbar con opción "DESHACER"
Emite Toast de confirmación
FAB de Favoritos que:
Muestra diálogo de confirmación
Cambia icono y color según estado
Actualiza automáticamente todas las pantallas

4.  Favoritos (FavoritesFragment)
Lista filtrada en tiempo real
Mismo Adapter que RoutinesFragment (reutilización de código)
Actualización automática al cambiar estado de favoritos
Estado vacío con mensaje e icono descriptivo

5.  Sistema de Notificaciones
Canal dedicado: "Recordatorios FitLife"
Prioridad alta para mejor visibilidad
Acción directa: tocar notificación → abre Rutinas
Icono personalizado de la app
Compatibilidad completa con todas las versiones de Andr


