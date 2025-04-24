package es.daw.samuel.biblioteca.vista;

import es.daw.samuel.biblioteca.dao.AutorDAO;
import es.daw.samuel.biblioteca.model.Autor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VistaAutores extends JPanel {
    
    private AutorDAO autorDAO;
    private JTable tablaAutores;
    private DefaultTableModel modeloAutores;
    private JTextField campoNombreAutor;
    private JTextField campoNacionalidadAutor;
    
    public VistaAutores() {
        autorDAO = new AutorDAO();
        setLayout(new BorderLayout());
        
        inicializarComponentes();
        cargarAutores();
    }
    
    private void inicializarComponentes() {
       
        JPanel panelTabla = new JPanel(new BorderLayout());
        
      
        modeloAutores = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloAutores.addColumn("ID");
        modeloAutores.addColumn("Nombre");
        modeloAutores.addColumn("Nacionalidad");
        
        tablaAutores = new JTable(modeloAutores);
        tablaAutores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaAutores.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaAutores.getSelectedRow() != -1) {
                mostrarAutorSeleccionado();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tablaAutores);
        panelTabla.add(scrollPane, BorderLayout.CENTER);
        
      
        JPanel panelFormulario = new JPanel(new BorderLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Autor"));
        
   
        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelCampos.add(new JLabel("Nombre:"));
        campoNombreAutor = new JTextField();
        panelCampos.add(campoNombreAutor);
        
        panelCampos.add(new JLabel("Nacionalidad:"));
        campoNacionalidadAutor = new JTextField();
        panelCampos.add(campoNacionalidadAutor);
        
        panelFormulario.add(panelCampos, BorderLayout.CENTER);
        
     
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton botonNuevo = new JButton("Nuevo");
        botonNuevo.addActionListener(e -> guardarAutor());
        
        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(e -> guardarAutor());
        
        JButton botonEliminar = new JButton("Eliminar");
        botonEliminar.addActionListener(e -> eliminarAutor());
        
        panelBotones.add(botonNuevo);
        panelBotones.add(botonGuardar);
        panelBotones.add(botonEliminar);
        
        panelFormulario.add(panelBotones, BorderLayout.SOUTH);
        
        
        add(panelTabla, BorderLayout.CENTER);
        add(panelFormulario, BorderLayout.SOUTH);
    }
    
    private void cargarAutores() {
      
        modeloAutores.setRowCount(0);
        
       
        ArrayList<Autor> autores = autorDAO.obtenerTodosLosAutores();
        
      
        for (Autor autor : autores) {
            Object[] fila = {autor.getId(), autor.getNombre(), autor.getNacionalidad()};
            modeloAutores.addRow(fila);
        }
    }
    
    private void mostrarAutorSeleccionado() {
        int fila = tablaAutores.getSelectedRow();
        if (fila != -1) {
            campoNombreAutor.setText((String) modeloAutores.getValueAt(fila, 1));
            campoNacionalidadAutor.setText((String) modeloAutores.getValueAt(fila, 2));
        }
    }
    
    private void guardarAutor() {
        String nombre = campoNombreAutor.getText().trim();
        String nacionalidad = campoNacionalidadAutor.getText().trim();
        
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del autor es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int filaSeleccionada = tablaAutores.getSelectedRow();
        
        if (filaSeleccionada == -1) {
          
            Autor autor = new Autor(0, nombre, nacionalidad);
            if (autorDAO.añadirAutor(autor)) {
                JOptionPane.showMessageDialog(this, "Autor agregado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarAutores();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar el autor", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
          
            int id = (int) modeloAutores.getValueAt(filaSeleccionada, 0);
            Autor autor = new Autor(id, nombre, nacionalidad);
            
            if (autorDAO.actualizarAutorDB(autor)) {
                JOptionPane.showMessageDialog(this, "Autor actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarAutores();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el autor", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarAutor() {
        int filaSeleccionada = tablaAutores.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un autor", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int id = (int) modeloAutores.getValueAt(filaSeleccionada, 0);
        String nombre = (String) modeloAutores.getValueAt(filaSeleccionada, 1);
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de eliminar al autor " + nombre + "?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            Autor autor = new Autor(id, nombre, "");
            
            if (autorDAO.eliminarAutor(autor)) {
                JOptionPane.showMessageDialog(this, "Autor eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarAutores();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el autor", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void limpiarFormulario() {
        campoNombreAutor.setText("");
        campoNacionalidadAutor.setText("");
        tablaAutores.clearSelection();
    }

    
    public void actualizarDatos() {
        cargarAutores();
    }
}