package es.daw.samuel.biblioteca.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Categoria {
    private int id;
    private String nombre;
}