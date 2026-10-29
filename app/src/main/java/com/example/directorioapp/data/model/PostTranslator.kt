package com.example.directorioapp.data.model

object PostTranslator {
    private val titleTranslations = mapOf(
        1 to "Sucesos para la autorreflexión y providencia de opciones",
        2 to "Quién es quién en el desarrollo profesional",
        3 to "Molestias y ejercicios de práctica en la repetición de tareas",
        4 to "Evolución y gestión de proyectos tecnológicos",
        5 to "Gestión de incidencias y resolución de problemas",
        6 to "Análisis de rendimiento y optimización de recursos",
        7 to "Facilidades y ventajas de la arquitectura modular",
        8 to "Evaluación de calidad y buenas prácticas de desarrollo",
        9 to "Tiempos de respuesta y control de procesos en sistemas",
        10 to "Opciones de configuración y despliegue continuo"
    )

    private val bodyTranslations = mapOf(
        1 to "Porque la aceptación y el compromiso con los resultados generan experiencias de aprendizaje constructivas y facilitan el desarrollo arquitectónico de proyectos sólidos y escalables.",
        2 to "Es fundamental valorar el tiempo y la dedicación en cada etapa del ciclo de vida del software, evitando distracciones y manteniendo el foco en los objetivos principales de calidad y entrega.",
        3 to "Las prácticas repetitivas permiten consolidar los conocimientos técnicos y mejorar la agilidad operativa ante situaciones imprevistas en entornos de producción.",
        4 to "La planificación estratégica y la adaptación a los cambios tecnológicos aseguran el éxito a largo plazo en equipos de desarrollo multidisciplinares.",
        5 to "Identificar el origen de los errores con precisión reduce significativamente los tiempos de inactividad y optimiza la experiencia del usuario final.",
        6 to "Monitorear los recursos del sistema de forma constante previene cuellos de botella y garantiza una respuesta fluida bajo alta concurrencia.",
        7 to "La modularidad en el código facilita el mantenimiento, la reutilización de componentes y la colaboración simultánea entre múltiples desarrolladores.",
        8 to "Aplicar estándares rigurosos de revisión de código asegura un producto final robusto, seguro y fácil de mantener a lo largo del tiempo.",
        9 to "El control riguroso de los tiempos de ejecución y la sincronización de procesos optimizan el rendimiento global de la aplicación móvil.",
        10 to "Configurar entornos de integración y despliegue automatizado acelera la entrega de nuevas funcionalidades con total fiabilidad."
    )

    fun translateTitle(id: Int, original: String): String {
        return titleTranslations[id] ?: "Publicación profesional del directorio de usuarios #${id}"
    }

    fun translateBody(id: Int, original: String): String {
        return bodyTranslations[id] ?: "Contenido informativo gestionado a través de la plataforma de directorio corporativo, enfocado en la colaboración y el desarrollo de software de alta calidad."
    }
}
