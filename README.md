FitLife - App de Fitness:
Descripción del Proyecto:
FitLife es una aplicación Android desarrollada en Kotlin para gestionar rutinas de ejercicios. Permite a los usuarios explorar rutinas, ver detalles, marcar favoritos y recibir recordatorios de entrenamiento.

-Características Principales:
 Navegación entre Fragmentos con Navigation Component
 RecyclerView con CardView personalizadas
 Notificaciones con canal propio y PendingIntent
 Snackbar con acción interactiva
 Toast para mensajes rápidos
 DialogFragment con callbacks personalizados
 Menú global con BottomNavigationView
 View Binding en todos los layouts
 Material Design con temas personalizados
 FloatingActionButton para acciones principales

Arquitectura y Estructura:

app/
├── src/main/java/com/example/fitlife/
│   ├── MainActivity.kt                 # Actividad principal con NavHost
│   ├── base/
│   │   └── BaseActivity.kt            # Actividad base con menú (opcional)
│   ├── fragments/
│   │   ├── HomeFragment.kt            # Pantalla de inicio
│   │   ├── RoutinesFragment.kt        # Lista de rutinas (RecyclerView)
│   │   ├── RoutineDetailFragment.kt   # Detalle de rutina
│   │   └── FavoritesFragment.kt       # Rutinas favoritas
│   ├── adapters/
│   │   └── RoutineAdapter.kt          # Adapter + ViewHolder
│   ├── models/
│   │   └── Routine.kt                 # Data class para rutinas
│   ├── utils/
│   │   ├── NotificationHelper.kt      # Gestión de notificaciones
│   │   └── DataSource.kt              # Datos de ejemplo
│   └── dialogs/
│       └── ConfirmDialogFragment.kt   # Diálogo de confirmación
└── res/
    ├── layout/                        # Todos los XML de layout
    ├── navigation/
    │   └── nav_graph.xml              # Grafo de navegación
    ├── menu/
    │   └── main_menu.xml              # Menú bottom navigation
    └── values/                        # Strings, colores, temas

Funcionalidades Detalladas:

1. Pantalla de Inicio (HomeFragment)
-Saludo personalizado
-Botones de navegación principales
-Botón para enviar notificación de recordatorio

2. Lista de Rutinas (RoutinesFragment)
-RecyclerView con layout CardView
-Cada tarjeta muestra:
-Imagen del ejercicio
-Nombre de la rutina
-Duración aproximada
-Nivel de dificultad (chip)
-Icono de favorito
-Click en tarjeta → navega a detalle

3. Detalle de Rutina (RoutineDetailFragment)
-Imagen destacada
-Descripción completa
-Chips de duración y nivel
-Lista de pasos a seguir
-FAB para marcar como completado (muestra Snackbar)
-FAB para añadir a favoritos

4. Favoritos (FavoritesFragment)
-Lista filtrada de rutinas marcadas como favoritas
-Mismo diseño que lista principal

5. Notificaciones
-Canal propio "FitLife Notificaciones"
-Notificación que abre la app al hacer click
-Contenido personalizado
-Compatible con Android 8.0+
