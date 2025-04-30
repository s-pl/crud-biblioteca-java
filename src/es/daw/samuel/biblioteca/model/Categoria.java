package es.daw.samuel.biblioteca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 *
 * @author Samuel Ponce Luna
 */

@Data
@AllArgsConstructor
public class Categoria {
    private int id;
    private String nombre;
}