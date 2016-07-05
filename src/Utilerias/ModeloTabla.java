/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utilerias;

/**
 *
 * @author Paul
 */

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class ModeloTabla extends AbstractTableModel
{
    public ResultSet rs;
    public ResultSetMetaData rsmd;

    // Obtener nombre de columna
    @Override
    public String getColumnName( int c )
    {
        try
        {
            if ( rsmd != null )
                return rsmd.getColumnLabel(c+1);//rsmd.getColumnName(c + 1);            
            return "";
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return "";
        }
    }

    // Contador de columnas
    @Override
    public int getColumnCount()
    {
        try
        {
            if ( rsmd != null )
            return rsmd.getColumnCount();
            return 0;
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,  e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    } // Fin contador de columnas

    // Contador de registros
    @Override
    public int getRowCount()
    {
        try
        {
            if ( rs != null )
            {
                rs.last(); // Nos situamos en la última fila
                return rs.getRow(); // Devolvemos el número de la fila
            }
            return 0;
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
     } // Fin contador de registros


    // Obtener valores de una columna
    @Override
    public Object getValueAt( int fila, int col )
    {
        try
        {
            if ( rs != null )
            {
                rs.absolute( fila + 1 );
                return rs.getObject( col + 1 );
            }
            return "";
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    } // Fin de obtener valores de columna
    

    @Override
    public boolean isCellEditable(int fila, int col) {
       //if (col==4)
       //{
       //   return true;
       //}
       return false;
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) { 
       try 
         { 
         if ( rs != null ) 
           { 
             rs.absolute( row + 1 ); 
             rs.updateObject(col+1,value); 
             fireTableCellUpdated(row+1 , col+1); 
           } 
         } 
       catch(SQLException Error) 
       {  
           JOptionPane.showMessageDialog(null, Error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
       } 
    }
} // Fin de la clase
