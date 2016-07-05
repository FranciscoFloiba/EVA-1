/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Datos;

/**
 *
 * @author Paul
 */
public class ListaEleccion {
    public int id;
    public String nombre;

    public ListaEleccion (int id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId()
    {
        return this.id;
    }

    @Override
    public String toString()
    {
        return this.nombre + "  ["+this.id+"]";
    }

    @Override
    public boolean equals(Object buscado)
    {
        if (buscado == null)
        {
            return false;
        }

        if (!(buscado instanceof ListaEleccion))
        {
            return false;
        }

        ListaEleccion aux = (ListaEleccion) buscado;
        return id==aux.id;
    } // Fin del metodo
} // Fin de la clase

