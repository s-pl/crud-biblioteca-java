package es.daw.samuel.biblioteca.vista;

import es.daw.samuel.biblioteca.dao.AutorDAO;
import es.daw.samuel.biblioteca.dao.CategoriaDAO;
import es.daw.samuel.biblioteca.dao.LibroDAO;
import es.daw.samuel.biblioteca.model.Autor;
import es.daw.samuel.biblioteca.model.Categoria;
import es.daw.samuel.biblioteca.model.Libro;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.io.BufferedReader;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author Samuel Ponce Luna
 */
public class VistaLibros extends JPanel {

    private LibroDAO libroDAO;
    private AutorDAO autorDAO;
    private CategoriaDAO categoriaDAO;

    private JTable tablaLibros;
    private DefaultTableModel modeloLibros;

    private JTextField campoISBN;
    private JTextField campoTitulo;
    private JTextField campoAnioPublicacion;
    private JComboBox<String> comboAutores;
    private JComboBox<String> comboCategorias;
    
    private Map<String, Integer> mapaAutores;
    private Map<String, Integer> mapaCategorias;
    
    public VistaLibros() {
        libroDAO = new LibroDAO();
        autorDAO = new AutorDAO();
        categoriaDAO = new CategoriaDAO();

        mapaAutores = new HashMap<>();
        mapaCategorias = new HashMap<>();

        setLayout(new BorderLayout());

        inicializarComponentes();
        cargarDatos();
    }

    private void inicializarComponentes() {

        JPanel panelTabla = new JPanel(new BorderLayout());

        modeloLibros = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloLibros.addColumn("ISBN");
        modeloLibros.addColumn("Título");
        modeloLibros.addColumn("Año");
        modeloLibros.addColumn("Autor");
        modeloLibros.addColumn("Categoría");

        tablaLibros = new JTable(modeloLibros);
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaLibros.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaLibros.getSelectedRow() != -1) {
                mostrarLibroSeleccionado();
            }
        });
       
        JScrollPane scrollPane = new JScrollPane(tablaLibros);
        panelTabla.add(scrollPane, BorderLayout.CENTER);
        
       
        JPanel panelFormulario = new JPanel(new BorderLayout());
        

        JPanel panelCampos = new JPanel(new GridLayout(5, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        panelCampos.add(new JLabel("ISBN:"));
        campoISBN = new JTextField();
        panelCampos.add(campoISBN);

        panelCampos.add(new JLabel("Título:"));
        campoTitulo = new JTextField();
        panelCampos.add(campoTitulo);

        panelCampos.add(new JLabel("Año de Publicación:"));
        campoAnioPublicacion = new JTextField();
        panelCampos.add(campoAnioPublicacion);

        panelCampos.add(new JLabel("Autor:"));
        comboAutores = new JComboBox<>();
        panelCampos.add(comboAutores);

        panelCampos.add(new JLabel("Categoría:"));
        comboCategorias = new JComboBox<>();
        panelCampos.add(comboCategorias);
        
        panelFormulario.add(panelCampos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton botonNuevo = new JButton("Nuevo");
        botonNuevo.addActionListener(e -> guardarLibro());
        JButton botonExportarAcsv = new JButton("Exportar a CSV");
        botonExportarAcsv.addActionListener(e -> exportarACSV());
        JButton botonImportarCsv = new JButton("Importar CSV");
        botonImportarCsv.addActionListener(e -> elegirArchivo());
        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(e -> guardarLibro());
        JButton botonEliminar = new JButton("Eliminar");
        botonEliminar.addActionListener(e -> eliminarLibro());
        JButton botonFiltrarAutor = new JButton("Filtrar por autor");
        JButton botonFiltrarCategoria = new JButton("Filtrar por categoria");
        botonFiltrarAutor.addActionListener(e -> filtrarPorAutor());
        botonFiltrarCategoria.addActionListener(e -> filtrarPorCategoria());
        panelBotones.add(botonNuevo);
        panelBotones.add(botonGuardar);
        panelBotones.add(botonEliminar);
        panelBotones.add(botonExportarAcsv);
        panelBotones.add(botonImportarCsv);
        panelBotones.add(botonFiltrarAutor);
        panelBotones.add(botonFiltrarCategoria);
        panelFormulario.add(panelBotones, BorderLayout.SOUTH);

        add(panelTabla, BorderLayout.CENTER);
        add(panelFormulario, BorderLayout.SOUTH);
    }

    void cargarDatos() {
        cargarAutores();
        cargarCategorias();
        cargarLibros();
    }

    public void cargarAutores() {

        comboAutores.removeAllItems();
        mapaAutores.clear();

        ArrayList<Autor> autores = autorDAO.obtenerTodosLosAutores();

        for (Autor autor : autores) {
            String item = autor.getId() + " - " + autor.getNombre();
            comboAutores.addItem(item);
            mapaAutores.put(item, autor.getId());
        }
    }

    public void cargarCategorias() {

        comboCategorias.removeAllItems();
        mapaCategorias.clear();

        ArrayList<Categoria> categorias = categoriaDAO.obtenerTodasLasCategorias();

        for (Categoria categoria : categorias) {
            String item = categoria.getId() + " - " + categoria.getNombre();
            comboCategorias.addItem(item);
            mapaCategorias.put(item, categoria.getId());
        }
    }

    public void cargarLibros() {

        modeloLibros.setRowCount(0);

        ArrayList<Libro> libros = libroDAO.obtenerTodosLosLibros();

        Map<Integer, String> nombresAutores = new HashMap<>();
        Map<Integer, String> nombresCategorias = new HashMap<>();

        for (Map.Entry<String, Integer> entry : mapaAutores.entrySet()) {
            nombresAutores.put(entry.getValue(), entry.getKey());
        }

        for (Map.Entry<String, Integer> entry : mapaCategorias.entrySet()) {
            nombresCategorias.put(entry.getValue(), entry.getKey());
        }

        for (Libro libro : libros) {
            String nombreAutor = nombresAutores.getOrDefault(libro.getAutor(), "Desconocido");
            String nombreCategoria = nombresCategorias.getOrDefault(libro.getCategoria(), "Desconocida");

            String[] fila = {
                libro.getIsbn(),
                libro.getTitulo(),
                Integer.toString(libro.getAnio_pub()),
                nombreAutor,
                nombreCategoria
            };

            modeloLibros.addRow(fila);
        }
    }

    private void mostrarLibroSeleccionado() {
        int fila = tablaLibros.getSelectedRow();
        if (fila != -1) {
            String isbn = (String) modeloLibros.getValueAt(fila, 0);
            String titulo = (String) modeloLibros.getValueAt(fila, 1);
            int anio = Integer.parseInt((String) modeloLibros.getValueAt(fila, 2));
            String autorNombre = (String) modeloLibros.getValueAt(fila, 3);
            String categoriaNombre = (String) modeloLibros.getValueAt(fila, 4);

            campoISBN.setText(isbn);
            campoTitulo.setText(titulo);
            campoAnioPublicacion.setText(String.valueOf(anio));

            for (int i = 0; i < comboAutores.getItemCount(); i++) {
                if (comboAutores.getItemAt(i).equals(autorNombre)) {
                    comboAutores.setSelectedIndex(i);
                    break;
                }
            }

            for (int i = 0; i < comboCategorias.getItemCount(); i++) {
                if (comboCategorias.getItemAt(i).equals(categoriaNombre)) {
                    comboCategorias.setSelectedIndex(i);
                    break;
                }
            }

            campoISBN.setEditable(false);
        }
    }

    private void guardarLibro() {

        String isbn = campoISBN.getText().trim();
        String titulo = campoTitulo.getText().trim();
        String anioStr = campoAnioPublicacion.getText().trim();

        if (isbn.isEmpty() || titulo.isEmpty() || anioStr.isEmpty()
                || comboAutores.getSelectedIndex() == -1 || comboCategorias.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int anio;
        try {
            anio = Integer.parseInt(anioStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El año debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String autorSeleccionado = comboAutores.getSelectedItem().toString();
        String categoriaSeleccionada = comboCategorias.getSelectedItem().toString();

        int autorId = mapaAutores.get(autorSeleccionado);
        int categoriaId = mapaCategorias.get(categoriaSeleccionada);

        Libro libro = new Libro(isbn, titulo, anio, autorId, categoriaId);

        int filaSeleccionada = tablaLibros.getSelectedRow();
        boolean esNuevo = filaSeleccionada == -1;

        if (esNuevo) {

            ArrayList<Libro> librosExistentes = libroDAO.obtenerTodosLosLibros();

            for (Libro libroExistente : librosExistentes) {
                if (libroExistente.getIsbn().equals(isbn)) {
                    JOptionPane.showMessageDialog(this, "El ISBN ya existe en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        boolean resultado;
        if (esNuevo) {
            resultado = libroDAO.añadirLibro(libro);
        } else {
            resultado = libroDAO.actualizarLibroDB(libro);
        }

        if (resultado) {
            String mensaje = esNuevo ? "Libro agregado correctamente" : "Libro actualizado correctamente";
            JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarLibros();
        } else {
            String error = esNuevo ? "Error al agregar el libro" : "Error al actualizar el libro";
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarLibro() {
        int filaSeleccionada = tablaLibros.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un libro", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String isbn = (String) modeloLibros.getValueAt(filaSeleccionada, 0);
        String titulo = (String) modeloLibros.getValueAt(filaSeleccionada, 1);

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar el libro '" + titulo + "'?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            Libro libro = new Libro(isbn, "", 0, 0, 0);

            if (libroDAO.eliminarLibro(libro)) {
                JOptionPane.showMessageDialog(this, "Libro eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarLibros();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el libro", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarFormulario() {
        campoISBN.setText("");
        campoISBN.setEditable(true);
        campoTitulo.setText("");
        campoAnioPublicacion.setText("");
        comboAutores.setSelectedIndex(comboAutores.getItemCount() > 0 ? 0 : -1);
        comboCategorias.setSelectedIndex(comboCategorias.getItemCount() > 0 ? 0 : -1);
        tablaLibros.clearSelection();
    }

    public void actualizarDatos() {
        cargarDatos();
    }

   public void exportarACSV() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Guardar como");
    
    
     FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV", "csv");
    fileChooser.setFileFilter(filter);

    int userSelection = fileChooser.showSaveDialog(null);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File archivoSeleccionado = fileChooser.getSelectedFile();

        
        String nombreArchivo = archivoSeleccionado.getAbsolutePath();
        if (!nombreArchivo.toLowerCase().endsWith(".csv")) {
            nombreArchivo += ".csv";
        }

        try (FileWriter writer = new FileWriter(nombreArchivo)) {

            writer.append("ISBN,Titulo,Año_Publicacion,Autor,Categoria\n");

            for (Libro libro : libroDAO.obtenerTodosLosLibros()) {
                writer.append(libro.getIsbn())
                      .append(",")
                      .append(libro.getTitulo())
                      .append(",")
                      .append(String.valueOf(libro.getAnio_pub()))
                      .append(",")
                      .append(String.valueOf(libro.getAutor()))
                      .append(",")
                      .append(String.valueOf(libro.getCategoria()))
                      .append("\n");
            }

            JOptionPane.showMessageDialog(null,
                "Libros exportados correctamente a CSV: " + nombreArchivo,
                "Exportación exitosa",
                JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                "Error al exportar CSV: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}


    private void elegirArchivo() {
        String archivo;

        try {
            FileDialog dialog = new FileDialog((Frame) null, "Selecciona el archivo a abrir");
            dialog.setMode(FileDialog.LOAD);
            dialog.setVisible(true);
            if (dialog.getFile().endsWith(".csv")) {
                archivo = dialog.getDirectory() + dialog.getFile();
                dialog.dispose();
                importarCSV(archivo);
            } else {
                JOptionPane.showMessageDialog(null,
                        "No has elegido un archivo csv.",
                        "Error en la importación",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void importarCSV(String archivo) {

        String nombreArchivo = archivo;

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String line;
            boolean primeraLinea = true;

            while ((line = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] values = line.split(",");

                if (values.length >= 5) {
                    try {
                        String isbn = values[0].trim();
                        String titulo = values[1].trim();
                        int anio = Integer.parseInt(values[2].trim());
                        int autorId = Integer.parseInt(values[3].trim());
                        int categoriaId = Integer.parseInt(values[4].trim());

                        Libro libro = new Libro(isbn, titulo, anio, autorId, categoriaId);
                        libroDAO.añadirLibro(libro);
                    } catch (NumberFormatException e) {
                        System.err.println("Error en línea: " + line + " - " + e.getMessage());
                    }
                }
            }

            JOptionPane.showMessageDialog(null,
                    "Libros importados correctamente desde CSV: " + nombreArchivo,
                    "Importación exitosa",
                    JOptionPane.INFORMATION_MESSAGE);

            cargarLibros();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al importar CSV: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtrarPorAutor() {
        try {
            var autorIntroducido = JOptionPane.showInputDialog("¿Por cual autor quieres filtrar?");
            if (autorIntroducido == null) {
                return; 
            }
            
            if (autorIntroducido.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El autor sin nombre todavía no existe");
            } else {
                modeloLibros.setRowCount(0); 
                boolean autorEncontrado = false;
                
                for (Autor x : autorDAO.obtenerTodosLosAutores()) {
                    if (autorIntroducido.equalsIgnoreCase(x.getNombre())) {
                        autorEncontrado = true;
                        int autorId = x.getId();
                        
                        
                        for (Libro y : libroDAO.obtenerTodosLosLibros()) {
                            if (y.getAutor() == autorId) {
                               
                                String[] fila = {
                                    y.getIsbn(),
                                    y.getTitulo(),
                                    Integer.toString(y.getAnio_pub()),
                                    x.getNombre(),
                                    obtenerNombreCategoria(y.getCategoria()) 
                                };
                                modeloLibros.addRow(fila);
                            }
                        }
                        break;
                    }
                }
                
                if (!autorEncontrado) {
                    JOptionPane.showMessageDialog(null, "No se encontró ningún autor con ese nombre");
                } else if (modeloLibros.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "El autor existe pero no hay libros registrados");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al filtrar por autor: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al filtrar por autor: " + e.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void filtrarPorCategoria() {
        try {
            var categoriaIntroducida = JOptionPane.showInputDialog("¿Por cuál categoría quieres filtrar?");
            if (categoriaIntroducida == null) {
                return; 
            }
            
            if (categoriaIntroducida.isEmpty()) {
                JOptionPane.showMessageDialog(null, "La categoría sin nombre todavía no existe");
            } else {
                modeloLibros.setRowCount(0); 
                boolean categoriaEncontrada = false;
                
                for (Categoria x : categoriaDAO.obtenerTodasLasCategorias()) {
                    if (categoriaIntroducida.equalsIgnoreCase(x.getNombre())) {
                        categoriaEncontrada = true;
                        int categoriaId = x.getId();
                        
                      
                        for (Libro y : libroDAO.obtenerTodosLosLibros()) {
                            if (y.getCategoria() == categoriaId) {
                                
                                String[] fila = {
                                    y.getIsbn(),
                                    y.getTitulo(),
                                    Integer.toString(y.getAnio_pub()),
                                    obtenerNombreAutor(y.getAutor()), 
                                    x.getNombre() 
                                };
                                modeloLibros.addRow(fila);
                            }
                        }
                        break;
                    }
                }
                
                if (!categoriaEncontrada) {
                    JOptionPane.showMessageDialog(null, "No se encontró ninguna categoría con ese nombre");
                } else if (modeloLibros.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "La categoría existe pero no hay libros registrados");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al filtrar por categoría: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al filtrar por categoría: " + e.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
  
    private String obtenerNombreAutor(int autorId) {
        for (Autor aut : autorDAO.obtenerTodosLosAutores()) {
            if (aut.getId() == autorId) {
                return aut.getNombre();
            }
        }
        return "Autor desconocido";
    }
  
    private String obtenerNombreCategoria(int categoriaId) {
        for (Categoria cat : categoriaDAO.obtenerTodasLasCategorias()) {
            if (cat.getId() == categoriaId) {
                return cat.getNombre();
            }
        }
        return "Categoría desconocida";
    }
}
