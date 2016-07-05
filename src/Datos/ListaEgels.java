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
public class ListaEgels {
    private int id;
    private int area;
    private int subarea;
    private int subsubarea;
    private String nombre;

    public ListaEgels (int id, int area, int subarea, int subsubarea, String nombre)
    {
        this.id         = id;
        this.area       = area;
        this.subarea    = subarea;
        this.subsubarea = subsubarea;
        this.nombre     = nombre;
    }

    public int getId()
    {
        return this.id;
    }
    
    public int getArea()
    {
        return this.area;
    }
    
    public int getSubArea()
    {
        return this.subarea;
    }
    
    public int getSubSubArea()
    {
        return this.subsubarea;
    }

    @Override
    public String toString()
    {
        return this.area+"."+this.subarea+"."+this.subsubarea+"-"+this.nombre;
    }

    @Override
    public boolean equals(Object buscado)
    {
        if (buscado == null)
        {
            return false;
        }

        if (!(buscado instanceof ListaEgels))
        {
            return false;
        }

        ListaEgels aux = (ListaEgels) buscado;
        return (area==aux.area && subarea==aux.subarea && subsubarea==aux.subsubarea);
    } // Fin del metodo
} // Fin de la clase
