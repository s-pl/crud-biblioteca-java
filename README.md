# 📚 CRUD Biblioteca Java

[![Estado del Proyecto](https://img.shields.io/badge/Estado-Finalizada-green)]()
[![Licencia](https://img.shields.io/badge/Licencia-MIT-blue.svg)]()
[![JDK](https://img.shields.io/badge/JDK-%3E%3D%208-orange)]()

> 🎯 Una aplicación moderna y eficiente para la gestión de bibliotecas, desarrollada con Java Swing y SQLite.

## ✨ Características Principales

- 🖥️ Interfaz gráfica moderna e intuitiva desarrollada con Java Swing
- 📑 Sistema de pestañas para una gestión organizada
- 📝 CRUD completo para libros, autores y categorías
- 🗄️ Base de datos SQLite para almacenamiento persistente
- ⚙️ Sistema de configuración personalizable

## ⚠️ Requisitos Previos

> **¡IMPORTANTE!** Asegúrate de tener instalado todo lo siguiente antes de comenzar:

- ☕ Java Development Kit (JDK) 8 o superior
  - Puedes descargarlo desde [Oracle](https://www.oracle.com/java/technologies/downloads/)
  - Verifica tu instalación con `java -version` en la terminal

- 🔧 NetBeans IDE (recomendado)
  - [Descarga NetBeans](https://netbeans.apache.org/download/)
  - Versión 8.2 o superior recomendada

- 📦 SQLite JDBC Driver
  - Se incluye en el proyecto
  - Versión: 3.36.0.3

## 🚀 Guía de Instalación

1. **Clonar el Repositorio**
   ```bash
   git clone https://github.com/s-pl/crud-biblioteca-java
   ```

2. **Configuración en NetBeans**
   - Abrir NetBeans IDE
   - File -> Open Project
   - Navegar hasta la carpeta del proyecto
   - Seleccionar y abrir

3. **Verificar Dependencias**
   - Clic derecho en el proyecto
   - Properties -> Libraries
   - Verificar que SQLite JDBC está incluido y configurado, al igual que Lombok

4. **Compilar y Ejecutar**
   - Clic derecho en el proyecto
   - Clean and Build
   - Run Project (F6)

## 📂 Estructura del Proyecto

```
src/
└── es/daw/samuel/biblioteca/
    ├── Biblioteca.java           # Clase principal
    ├── config/
    │   └── ConexionDB.java      # Gestión de conexión a base de datos
    ├── dao/
    │   ├── AutorDAO.java        # Acceso a datos de autores
    │   ├── CategoriaDAO.java    # Acceso a datos de categorías
    │   └── LibroDAO.java        # Acceso a datos de libros
    ├── model/
    │   ├── Autor.java           # Modelo de autor
    │   ├── Categoria.java       # Modelo de categoría
    │   └── Libro.java           # Modelo de libro
    └── vista/
        ├── BibliotecaApp.java   # Ventana principal
        ├── VistaAutores.java    # Panel de gestión de autores
        ├── VistaCategorias.java # Panel de gestión de categorías
        ├── VistaConfig.java     # Panel de configuración
        └── VistaLibros.java     # Panel de gestión de libros
```

## 📖 Guía de Uso

### 📚 Gestión de Libros
- ➕ **Agregar Libro**: Botón "Nuevo" en la pestaña Libros
- 📝 **Editar Libro**: Seleccionar libro y usar botón "Editar"
- 🗑️ **Eliminar Libro**: Seleccionar y confirmar eliminación

### 🔍 Búsqueda y Filtros

- Filtros para categorías y autores




## ⚠️ Advertencias Importantes

- **Backup**: Realiza copias de seguridad regulares de `biblioteca.db`
- **Memoria**: Se recomienda mínimo 4GB de RAM para un funcionamiento óptimo
- **Permisos**: Asegúrate de tener permisos de escritura en la carpeta de instalación

## 🔧 Solución de Problemas Comunes

### Error de Conexión a Base de Datos
1. Verificar que `biblioteca.db` existe en la ruta correcta
2. Comprobar permisos de escritura
3. Reiniciar la aplicación

### La Interfaz No Responde
1. Verificar memoria disponible
2. Cerrar y reabrir la aplicación
3. Comprobar logs en la carpeta del proyecto



## 👥 Contribuir

1. Haz un Fork del proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## ❓ FAQ

**P**: ¿Puedo usar la aplicación sin NetBeans?
**R**: Sí, puedes usar cualquier IDE que soporte Java, pero NetBeans está recomendado para mejor compatibilidad.

**P**: ¿Cómo exporto mis datos?
**R**: Usa la función de exportación a CSV en el menú Archivo.

**P**: ¿Es compatible con otras bases de datos?
**R**: Actualmente solo soporta SQLite, pero el diseño permite implementar otros motores de BD.
##
Made with ❤️ in 🇮🇨 by Samuel.
