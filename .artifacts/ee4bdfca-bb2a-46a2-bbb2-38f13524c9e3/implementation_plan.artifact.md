# Plan de Implementación - DirectorioApp

## Descripción General
Completar la aplicación **DirectorioApp** con arquitectura MVVM, consumo de la API REST de JSONPlaceholder mediante Retrofit, sistema de favoritos persistente localmente (SharedPreferences + Gson), manejo robusto de estados (Carga, Éxito, Error, Sin Internet, Lista Vacía), navegación completa entre pantallas (Lista de Usuarios, Detalle con Publicaciones, y Favoritos), y redacción del documento técnico de entrega universitaria.

## Componentes y Cambios Propuestos

### 1. Dependencias y Configuración
- Configurar Retrofit (`2.11.0`) y Gson Converter en `app/build.gradle.kts`.
- Agregar permisos `INTERNET` y `ACCESS_NETWORK_STATE` en `AndroidManifest.xml`.

### 2. Capa de Datos (`data`)
- **Modelos (`data/model`):** Clases Kotlin `User`, `Address`, `Geo`, `Company`, `Post`.
- **Remoto (`data/remote`):** Interfaz `ApiService` con endpoints `/users` y `/posts?userId={id}`, y objeto `RetrofitClient`.
- **Local (`data/local`):** `FavoritesManager` utilizando SharedPreferences y Gson para persistencia local de favoritos con soporte offline y prevención de duplicados.
- **Repositorio (`data/repository`):** `UserRepository` centralizando acceso a API y almacenamiento local.

### 3. Capa de Presentación e Interfaz (`ui`)
- **Usuarios (`ui/users`):** `UsersViewModel` y `UsersScreen` con gestión de estados (Carga, Éxito, Error, Sin Internet con fallback de favoritos, Lista Vacía) y control de favoritos.
- **Detalle (`ui/detail`):** `UserDetailViewModel` y `UserDetailScreen` mostrando información detallada del usuario y publicaciones filtradas por `userId`, con manejo de errores si fallan las publicaciones.
- **Favoritos (`ui/favorites`):** `FavoritesViewModel` y `FavoritesScreen` para gestionar y consultar favoritos offline.
- **Navegación (`ui/navigation`):** `AppNavGraph` con barra de navegación inferior (Usuarios y Favoritos) y ruta de Detalle.

### 4. Pruebas y Verificación
- Sincronización de Gradle y compilación exitosa en modo debug.
- Ejecución de pruebas unitarias locales (`testDebugUnitTest`).

### 5. Documentación de Entrega
- Elaboración del documento de entrega técnica y académica completo en español con portada, arquitectura MVVM, consumo de API, persistencia, ciclo de vida, evidencias de funcionamiento, pruebas y conclusiones.
