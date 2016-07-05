/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Datos;

import javax.swing.JOptionPane;

/**
 *
 * @author Paul
 */
public class ListaEleccionSeguridad {
    public int id;
    public String nombre;
    public String componente;

    public ListaEleccionSeguridad (int id, String nombre, String componente)
    {
        this.id = id;
        this.nombre = nombre;
        this.componente = componente;
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

        if (!(buscado instanceof ListaEleccionSeguridad))
        {
            return false;
        }

        ListaEleccionSeguridad aux = (ListaEleccionSeguridad) buscado;
        //JOptionPane.showMessageDialog(null, aux + ":"+buscado);
        return componente.equals(aux.componente);
    } // Fin del metodo
} // Fin de la clase
