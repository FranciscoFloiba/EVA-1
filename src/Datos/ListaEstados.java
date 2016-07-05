/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Datos;

/**
 *
 * @author Paul
 */
public class ListaEstados {
    public int id;
    public String nombre;

    public ListaEstados (int id, String nombre)
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
        return this.nombre;
    }

    @Override
    public boolean equals(Object buscado)
    {
        if (buscado == null)
        {
            return false;
        }

        if (!(buscado instanceof ListaEstados))
        {
            return false;
        }

        ListaEstados aux = (ListaEstados) buscado;
        return id==aux.id;
    } // Fin del metodo
} // Fin de la clase

