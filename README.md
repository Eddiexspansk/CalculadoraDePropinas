# 💰 PropiApp - Calculadora de Propinas para Camareros

¡Bienvenido a **PropiApp**! Este es un proyecto desarrollado con el objetivo de facilitar el reparto de propinas en equipos de hostelería, basándose en las horas trabajadas por cada integrante.

Este proyecto representa mi camino de aprendizaje en el desarrollo moderno de Android, donde he aplicado las herramientas y arquitecturas más actuales recomendadas por la comunidad.

## 🚀 Características
- **Cálculo en Tiempo Real**: Introduce el monto total y las horas de cada camarero para ver el reparto al instante.
- **Persistencia Local**: Los datos no se pierden al cerrar la app gracias a una base de datos local.
- **Interfaz Moderna**: Diseño fluido y adaptativo con Jetpack Compose.
- **Gestión Inteligente**: Añade, edita o elimina camareros de forma sencilla.

## 🛠️ Tecnologías y Arquitectura
Para este proyecto decidí salir de mi zona de confort y aplicar una arquitectura robusta:

- **Arquitectura**: Clean Architecture + MVVM (Model-View-ViewModel).
- **UI**: Jetpack Compose (Interfaz declarativa).
- **Inyección de Dependencias**: Hilt (para un código más desacoplado y mantenible).
- **Base de Datos**: Room (Persistencia de datos local).
- **Lenguaje**: Kotlin con flujos reactivos (StateFlow).

## 💡 ¿Por qué esta arquitectura?
Aunque estoy en las primeras etapas de mi carrera como desarrollador, decidí implementar **Clean Architecture** para aprender cómo separar la lógica de negocio de la interfaz. Esto facilita que el código sea:
1. **Testeable**: La lógica de cálculo es independiente de Android.
2. **Escalable**: Es fácil añadir nuevas funciones en el futuro.
3. **Organizado**: Cada capa (`data`, `domain`, `ui`) tiene una responsabilidad clara.

## 📸 Capturas de Pantalla


---
Desarrollado con ❤️ por Eduardo Pinto
- [LinkedIn](https://www.linkedin.com/in/eduardo-pinto-producer/)
- [Portafolio](https://www.github.com/Eddiexspansk/)
