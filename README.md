# Sistema de Gestión de Biblioteca

Este proyecto implementa un sistema de gestión de biblioteca utilizando Java y Swing para la interfaz gráfica. El sistema sigue una arquitectura MVC (Modelo-Vista-Controlador) y utiliza el patrón DAO (Data Access Object) para la persistencia de datos.

## Arquitectura del Sistema

### Estructura del Proyecto

```
src/es/daw/samuel/biblioteca/
├── Biblioteca.java           # Punto de entrada de la aplicación
├── config/                   # Configuración de la base de datos
│   └── ConexionDB.java
├── dao/                      # Capa de acceso a datos
│   ├── AutorDAO.java
│   ├── CategoriaDAO.java
│   └── LibroDAO.java
├── model/                    # Modelos de datos
│   ├── Autor.java
│   ├── Categoria.java
│   └── Libro.java
└── vista/                    # Interfaces gráficas
    ├── BibliotecaApp.java
    ├── VistaAutores.java
    ├── VistaCategorias.java
    ├── VistaConfig.java
    └── VistaLibros.java
```

### Capas de la Aplicación

1. **Capa de Presentación (Vista)**
   - Implementada con Java Swing
   - Interfaces gráficas separadas para cada entidad
   - Sistema de pestañas para navegación
   - Soporte para múltiples temas visuales

2. **Capa de Acceso a Datos (DAO)**
   - Implementación del patrón DAO para cada entidad
   - Operaciones CRUD independientes
   - Gestión de conexiones a base de datos

3. **Capa de Modelo**
   - Clases de entidad para Libros, Autores y Categorías
   - Implementación de relaciones entre entidades

## Componentes Principales

### Gestión de Base de Datos
- Configuración centralizada en `ConexionDB.java`
- Operaciones de creación y destrucción de base de datos
- Verificación de estado de conexión

### Interfaz de Usuario
- **BibliotecaApp**: Ventana principal con sistema de pestañas
- **VistaLibros**: Gestión de libros y sus relaciones
- **VistaAutores**: Administración de autores
- **VistaCategorias**: Gestión de categorías
- **VistaConfig**: Configuración del sistema y base de datos

### Características de la Interfaz
- Sistema de pestañas para navegación intuitiva
- Actualización automática de datos entre vistas
- Personalización de temas visuales
- Validación de entrada de datos

## Funcionalidades Técnicas

### Gestión de Libros
- CRUD completo de libros
- Asociación con autores y categorías
- Validación de datos de entrada
- Actualización automática de listados

### Gestión de Autores
- Mantenimiento de información de autores
- Vinculación con libros
- Actualización en tiempo real

### Gestión de Categorías
- Clasificación de libros por categorías
- Sistema de categorización flexible
- Actualización automática de relaciones

### Configuración del Sistema
- Gestión de conexión a base de datos
- Creación y destrucción de esquema de datos
- Cambio de temas visuales en tiempo real

## Requisitos Técnicos

### Requisitos del Sistema
- Java Runtime Environment (JRE) 8 o superior
- Sistema de gestión de base de datos compatible
- Memoria RAM: 2GB mínimo recomendado
- Espacio en disco: 100MB mínimo

### Dependencias Principales
- Java Swing para la interfaz gráfica
- JDBC para conexión a base de datos
- Bibliotecas estándar de Java

## Guía de Instalación

1. Asegurar la instalación de JRE 8 o superior
2. Configurar la base de datos según los parámetros en ConexionDB
3. Compilar el proyecto usando el sistema de build proporcionado
4. Ejecutar la clase principal Biblioteca.java
5. Usar la interfaz de configuración para inicializar la base de datos

## Consideraciones Técnicas

### Manejo de Eventos
- Implementación de listeners para actualización de datos
- Sistema de eventos para sincronización entre vistas
- Gestión de cambios en tiempo real

### Gestión de Errores
- Sistema de manejo de excepciones
- Validación de entrada de datos
- Mensajes de error informativos

### Optimización
- Carga bajo demanda de datos
- Actualización selectiva de componentes
- Gestión eficiente de recursos de memoria
