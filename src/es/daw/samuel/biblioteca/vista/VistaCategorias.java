package es.daw.samuel.biblioteca.vista;

import es.daw.samuel.biblioteca.dao.CategoriaDAO;
import es.daw.samuel.biblioteca.model.Categoria;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VistaCategorias extends JPanel {
    
    private CategoriaDAO categoriaDAO;
    private JTable tablaCategorias;
    private DefaultTableModel modeloCategorias;
    private JTextField campoNombreCategoria;
    
    public VistaCategorias() {
        categoriaDAO = new CategoriaDAO();
        setLayout(new BorderLayout());
        
        inicializarComponentes();
        cargarCategorias();
    }
    
    private void inicializarComponentes() {
      
        JPanel panelTabla = new JPanel(new BorderLayout());
        
    
        modeloCategorias = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloCategorias.addColumn("ID");
        modeloCategorias.addColumn("Nombre");
        
        tablaCategorias = new JTable(modeloCategorias);
        tablaCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaCategorias.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaCategorias.getSelectedRow() != -1) {
                mostrarCategoriaSeleccionada();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tablaCategorias);
        panelTabla.add(scrollPane, BorderLayout.CENTER);
        
      
        JPanel panelFormulario = new JPanel(new BorderLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la Categoría"));
        
        
        JPanel panelCampos = new JPanel(new GridLayout(1, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelCampos.add(new JLabel("Nombre:"));
        campoNombreCategoria = new JTextField();
        panelCampos.add(campoNombreCategoria);
        
        panelFormulario.add(panelCampos, BorderLayout.CENTER);
        
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton botonNuevo = new JButton("Nuevo");
        botonNuevo.addActionListener(e -> guardarCategoria());
        
        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(e -> guardarCategoria());
        
        JButton botonEliminar = new JButton("Eliminar");
        botonEliminar.addActionListener(e -> eliminarCategoria());
        
        panelBotones.add(botonNuevo);
        panelBotones.add(botonGuardar);
        panelBotones.add(botonEliminar);
        
        panelFormulario.add(panelBotones, BorderLayout.SOUTH);
        
      
        add(panelTabla, BorderLayout.CENTER);
        add(panelFormulario, BorderLayout.SOUTH);
    }
    
    private void cargarCategorias() {
     
        modeloCategorias.setRowCount(0);
        
       
        ArrayList<Categoria> categorias = categoriaDAO.obtenerTodasLasCategorias();
        
    
        for (Categoria categoria : categorias) {
            Object[] fila = {categoria.getId(), categoria.getNombre()};
            modeloCategorias.addRow(fila);
        }
    }
    
    private void mostrarCategoriaSeleccionada() {
        int fila = tablaCategorias.getSelectedRow();
        if (fila != -1) {
            campoNombreCategoria.setText((String) modeloCategorias.getValueAt(fila, 1));
        }
    }
    
    private void guardarCategoria() {
        String nombre = campoNombreCategoria.getText().trim();
        
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de la categoría es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int filaSeleccionada = tablaCategorias.getSelectedRow();
        
        if (filaSeleccionada == -1) {
           
            Categoria categoria = new Categoria(0, nombre);
            if (categoriaDAO.añadirCategoria(categoria)) {
                JOptionPane.showMessageDialog(this, "Categoría agregada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarCategorias();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar la categoría", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
         
            int id = (int) modeloCategorias.getValueAt(filaSeleccionada, 0);
            Categoria categoria = new Categoria(id, nombre);
            
            if (categoriaDAO.actualizarCategoriaDB(categoria)) {
                JOptionPane.showMessageDialog(this, "Categoría actualizada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarCategorias();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar la categoría", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarCategoria() {
        int filaSeleccionada = tablaCategorias.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una categoría", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int id = (int) modeloCategorias.getValueAt(filaSeleccionada, 0);
        String nombre = (String) modeloCategorias.getValueAt(filaSeleccionada, 1);
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de eliminar la categoría " + nombre + "?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            Categoria categoria = new Categoria(id, nombre);
            
            if (categoriaDAO.eliminarCategoria(categoria)) {
                JOptionPane.showMessageDialog(this, "Categoría eliminada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarCategorias();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar la categoría", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void limpiarFormulario() {
        campoNombreCategoria.setText("");
        tablaCategorias.clearSelection();
    }

    
    public void actualizarDatos() {
        cargarCategorias();
    }
}