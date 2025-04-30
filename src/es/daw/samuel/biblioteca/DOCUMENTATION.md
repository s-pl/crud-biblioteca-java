# Documentación del Sistema de Gestión de Biblioteca

Este documento proporciona una descripción detallada de la arquitectura y componentes del sistema de gestión de biblioteca.

## Estructura del Proyecto

El proyecto está organizado en los siguientes paquetes principales:

```
es.daw.samuel.biblioteca/
├── config/     # Configuración de la base de datos
├── dao/        # Capa de acceso a datos
├── model/      # Modelos de datos
└── vista/      # Interfaces gráficas de usuario
```

## Componentes Principales

### Modelos (model)

#### Libro
Clase que representa un libro en el sistema.

**Atributos:**
- `isbn` (String): Identificador único del libro en formato ISBN
- `titulo` (String): Título del libro
- `anio_pub` (int): Año de publicación del libro
- `autor` (int): ID del autor del libro (clave foránea)
- `categoria` (int): ID de la categoría del libro (clave foránea)

**Características:**
- Utiliza anotaciones Lombok (@Data, @AllArgsConstructor) para generar automáticamente getters, setters y constructor
- Implementa relaciones con las entidades Autor y Categoria mediante IDs

#### Autor
Clase que representa un autor de libros.

**Atributos:**
- `id` (int): Identificador único del autor
- `nombre` (String): Nombre completo del autor
- `nacionalidad` (String): País de origen del autor

**Características:**
- Utiliza anotaciones Lombok (@Data, @AllArgsConstructor) para generar automáticamente getters, setters y constructor
- Puede estar relacionado con múltiples libros (relación uno a muchos)

#### Categoria
Clase que representa una categoría de libros.

**Atributos:**
- `id` (int): Identificador único de la categoría
- `nombre` (String): Nombre de la categoría

**Características:**
- Utiliza anotaciones Lombok (@Data, @AllArgsConstructor) para generar automáticamente getters, setters y constructor
- Puede estar relacionada con múltiples libros (relación uno a muchos)
- Los atributos están declarados como private para garantizar el encapsulamiento

### Acceso a Datos (DAO)

#### LibroDAO
Gestiona las operaciones de base de datos relacionadas con los libros.

**Atributos:**
- `conexion` (ConexionDB): Instancia para manejar la conexión con la base de datos

**Métodos principales:**
- `obtenerTodosLosLibros()`: Retorna ArrayList<Libro> con todos los libros de la base de datos
  - Ejecuta SELECT * FROM Libro
  - Maneja excepciones SQL y conexiones nulas
  - Mapea resultados a objetos Libro

- `actualizarLibroDB(Libro l)`: Actualiza la información de un libro
  - Retorna boolean indicando éxito/fallo
  - Verifica conexión antes de ejecutar
  - Utiliza PreparedStatement para prevenir SQL injection

#### AutorDAO
Maneja las operaciones de base de datos para autores.

**Atributos:**
- `conexion` (ConexionDB): Instancia para manejar la conexión con la base de datos

**Métodos principales:**
- `obtenerTodosLosAutores()`: Retorna ArrayList<Autor> con todos los autores
  - Ejecuta SELECT * FROM Autor
  - Maneja excepciones SQL y conexiones nulas
  - Mapea resultados a objetos Autor

- `actualizarAutorDB(Autor a)`: Actualiza la información de un autor
  - Retorna boolean indicando éxito/fallo
  - Verifica conexión antes de ejecutar
  - Utiliza PreparedStatement para seguridad

#### CategoriaDAO
Gestiona las operaciones de base de datos para categorías.

**Atributos:**
- `conexion` (ConexionDB): Instancia para manejar la conexión con la base de datos

**Métodos principales:**
- `obtenerTodasLasCategorias()`: Retorna ArrayList<Categoria> con todas las categorías
  - Ejecuta SELECT * FROM Categoria
  - Maneja excepciones SQL y conexiones nulas
  - Mapea resultados a objetos Categoria

- `actualizarCategoriaDB(Categoria c)`: Actualiza la información de una categoría
  - Retorna boolean indicando éxito/fallo
  - Verifica conexión antes de ejecutar
  - Utiliza PreparedStatement para seguridad

### Interfaz de Usuario (vista)

#### BibliotecaApp
Ventana principal de la aplicación que extiende JFrame.

**Atributos principales:**
- `tabbedPane` (JTabbedPane): Panel de pestañas para la navegación

**Características:**
- Dimensiones: 800x600 píxeles
- Centrada en la pantalla
- Cierre de aplicación al cerrar la ventana

**Pestañas implementadas:**
- Libros: Gestión de libros
- Autores: Gestión de autores
- Categorías: Gestión de categorías
- Configuración: Ajustes del sistema

**Eventos:**
- ChangeListener en tabbedPane para actualizar datos al cambiar de pestaña
- Actualización automática de datos al seleccionar cada vista

#### VistaLibros
Panel para la gestión completa de libros.

**Componentes principales:**
- `tablaLibros` (JTable): Tabla para mostrar lista de libros
- `modeloLibros` (DefaultTableModel): Modelo de datos para la tabla
- Campos de entrada:
  - `campoISBN` (JTextField)
  - `campoTitulo` (JTextField)
  - `campoAnioPublicacion` (JTextField)
  - `comboAutores` (JComboBox)
  - `comboCategorias` (JComboBox)

**Gestión de datos:**
- Mapeo de autores y categorías mediante HashMaps
- Integración con LibroDAO, AutorDAO y CategoriaDAO
- Actualización automática de datos

**Funcionalidades:**
- CRUD completo de libros
- Validación de datos de entrada
- Selección de autor y categoría mediante desplegables

#### VistaAutores
Panel para la gestión de autores.

**Componentes principales:**
- `tablaAutores` (JTable): Tabla para mostrar lista de autores
- `modeloAutores` (DefaultTableModel): Modelo de datos para la tabla
- Campos de entrada:
  - `campoNombreAutor` (JTextField)
  - `campoNacionalidadAutor` (JTextField)

**Características:**
- Selección única de autores en la tabla
- Actualización automática al seleccionar un autor
- Integración con AutorDAO para operaciones CRUD

**Eventos:**
- ListSelectionListener para mostrar detalles del autor seleccionado
- Actualización automática de la tabla al realizar cambios

#### VistaCategorias
Panel para la gestión de categorías.
- Interfaz para gestionar categorías de libros
- Visualización de lista de categorías

#### VistaConfig
Panel de configuración del sistema.
- Gestión de la conexión a la base de datos
- Configuración de temas visuales
- Operaciones de mantenimiento de la base de datos

### Configuración (config)

#### ConexionDB
Gestiona la conexión con la base de datos SQLite y la estructura de las tablas.

**Atributos principales:**
- `conn` (Connection): Conexión activa a la base de datos
- `stmt` (Statement): Objeto para ejecutar consultas SQL

**Definición de tablas:**
- Tabla Autor:
  ```sql
  CREATE TABLE IF NOT EXISTS Autor (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      nombre VARCHAR(100) NOT NULL,
      nacionalidad VARCHAR(50)
  );
  ```
- Tabla Categoria:
  ```sql
  CREATE TABLE IF NOT EXISTS Categoria (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      nombre VARCHAR(100) NOT NULL
  );
  ```
- Tabla Libro:
  ```sql
  CREATE TABLE IF NOT EXISTS Libro (
      isbn VARCHAR(20) PRIMARY KEY,
      titulo VARCHAR(200) NOT NULL,
      anio_publicacion INTEGER,
      autor_id INTEGER,
      categoria_id INTEGER,
      FOREIGN KEY (autor_id) REFERENCES Autor(id),
      FOREIGN KEY (categoria_id) REFERENCES Categoria(id)
  );
  ```

**Características:**
- Utiliza anotaciones Lombok (@Data, @AllArgsConstructor)
- Manejo automático de creación de tablas
- Control de integridad referencial mediante claves foráneas
- Manejo de excepciones SQL

## Punto de Entrada

### Biblioteca
Clase principal que inicia la aplicación.

**Características principales:**
- Configura el look and feel de Windows para la interfaz gráfica
- Utiliza SwingUtilities.invokeLater para la inicialización segura de la GUI
- Manejo de excepciones para la configuración del look and feel
- Inicialización de BibliotecaApp como ventana principal

## Características Principales

- Interfaz gráfica intuitiva con sistema de pestañas
- Gestión completa de libros, autores y categorías
- Sistema de temas visuales personalizables
- Operaciones de mantenimiento de base de datos
- Arquitectura modular y extensible

## Patrones de Diseño Utilizados

- Patrón DAO para el acceso a datos
- Patrón MVC (Modelo-Vista-Controlador)
- Singleton para la conexión a la base de datos