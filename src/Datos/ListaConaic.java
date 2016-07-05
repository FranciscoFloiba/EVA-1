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
public class ListaConaic {
    private int id;
    private int area;
    private int subarea;
    private int subsubarea;   
    private String clave;
    private String nombre;

    public ListaConaic (int id, int area, int subarea, int subsubarea,String clave, String nombre)
    {
        this.id         = id;
        this.area       = area;
        this.subarea    = subarea;
        this.subsubarea = subsubarea;
        this.clave      = clave;
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
    
    public String getClave()
    {
        return this.clave;
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

        if (!(buscado instanceof ListaConaic))
        {
            return false;
        }

        ListaConaic aux = (ListaConaic) buscado;
        return (area==aux.area && subarea==aux.subarea && subsubarea==aux.subsubarea);
    } // Fin del metodo
} // Fin de la clase
