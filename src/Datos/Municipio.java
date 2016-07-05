package Datos;


/**
 * Clase de la capa de datos
 * 
 * @author (Rubén Benítez) 
 * @version (Ver 1.0)
 */

import java.sql.*;
import java.util.Vector;
import javax.swing.JOptionPane;
public class Municipio
{
    private int    id;
    private String nombre;    
    private int    Estado_id;
    protected Conexion bd;         
    
    /**
     * Constructor for objects of class Municipio
     * @param bd
     */
    public Municipio(Conexion bd)
    {  
        this.bd = bd;
        Inicializar();
    } 
    
        
    private void Inicializar()
    {
        id        = 0;
        nombre    = null;    
        Estado_id = 0;
    }
    
    /**
     * Metodo para establecer en la clase los datos
     * @param id
     * @param nombre
     * @param Estado_id
     */
    public void setMunicipio(int id,
                             String nombre,
                             int Estado_id)
    {
        this.id        = id;
        this.nombre    = nombre;   
        this.Estado_id = Estado_id;
    }
    
    public boolean Buscar(int id)
    {
        String sql;
        sql = "SELECT * FROM Municipio "+
              "WHERE id="+id;  

        try
        {
            this.bd.ejecutarSQL(sql);
            if (this.bd.resultset.next())
            {
                this.id        = this.bd.resultset.getInt("id");
                this.nombre    = this.bd.resultset.getString("nombre");  
                this.Estado_id = this.bd.resultset.getInt("Estado_id"); 
                return(true);
            }    
            else
            {
                Inicializar();
            }
        }
        catch (SQLException Error)
        {
            JOptionPane.showMessageDialog(null, Error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return(false);
    }    
    
    public int getID()
    {
        return(this.id);
    }
    
    public String getNombre()
    {
        return(this.nombre);
    }    
    
    public int getEstado_ID()
    {
        return(this.Estado_id);
    } 
   
    
    public ResultSet DataSetMunicipio() throws SQLException
    {
        String sql = "SELECT Municipio.id AS ID, Municipio.nombre AS Nombre, Estado.nombre AS Estado "+ 
                     "FROM Municipio "+
                     "INNER JOIN Estado ON Municipio.Estado_id = Estado.id "+
                     "ORDER BY Municipio.nombre,Estado.nombre";
        this.bd.ejecutarSQL(sql);        
        return(this.bd.resultset);
    }

    public ResultSet DataSetMunicipio(String patron, int campo)
    {
        String sql;
        sql = null;
        switch (campo)
        {
            case 0: {sql = "SELECT Municipio.id AS ID, Municipio.nombre AS Nombre, Estado.nombre AS Estado "+ 
                           "FROM Municipio "+
                           "INNER JOIN Estado ON Municipio.Estado_id = Estado.id "+
                           "WHERE Municipio.id="+Integer.valueOf(patron) + " "+ 
                           "ORDER BY Municipio.nombre,Estado.nombre"; break;}
            case 1: {sql = "SELECT Municipio.id AS ID, Municipio.nombre AS Nombre, Estado.nombre AS Estado "+ 
                           "FROM Municipio "+
                           "INNER JOIN Estado ON Municipio.Estado_id = Estado.id "+
                           "WHERE Municipio.nombre LIKE '%"+ patron.toUpperCase()+"%'"+" "+
                           "ORDER BY Municipio.nombre,Estado.nombre"; break;}
            case 2: {sql = "SELECT Municipio.id AS ID, Municipio.nombre AS Nombre, Estado.nombre AS Estado "+ 
                           "FROM Municipio "+
                           "INNER JOIN Estado ON Municipio.Estado_id = Estado.id "+
                           "WHERE Estado.nombre LIKE '%"+ patron.toUpperCase()+"%'"+ " "+
                           "ORDER BY Municipio.nombre,Estado.nombre"; break;}
        }        
        this.bd.ejecutarSQL(sql);
        return(this.bd.resultset);
    } 
        
    
    public boolean Nuevo()
    {
        try
        {
            String orden;                       
            orden = "INSERT INTO Municipio(id,nombre,Estado_id) VALUES(?,?,?)";
                         
            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden);        
            
            // Se establecen los valores que se van a enviar para insertar
            this.bd.preparedstatement.setInt(1, this.id);
            this.bd.preparedstatement.setString(2, this.nombre.toUpperCase());  
             this.bd.preparedstatement.setInt(3, this.Estado_id);
            this.bd.preparedstatement.executeUpdate();
            return(true);            
        }
        catch (SQLException Error)
        {
            JOptionPane.showMessageDialog(null, Error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);            
        }    
        return(false);
    }  
    
    public boolean Editar()
    {        
        try
        {
            String orden = "UPDATE Municipio SET nombre=?,Estado_id=? WHERE id = ?";  
    
            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden); 
            
            // Se establecen los valores que se van a enviar para actualizar
            this.bd.preparedstatement.setString(1, this.nombre.toUpperCase());
            this.bd.preparedstatement.setInt(2, this.Estado_id);
            this.bd.preparedstatement.setInt(3, this.id);
            this.bd.preparedstatement.executeUpdate();
            return(true);            
        }
        catch (SQLException Error)
        {
           JOptionPane.showMessageDialog(null, Error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }    
        return(false);
    }  
    
    
    public boolean Eliminar()
    {
        try
        {
            String orden = "DELETE FROM Municipio WHERE id = ?";
            
            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden);                                       
                        
            // Se establecen los valores de identificación del registro a borrar
            this.bd.preparedstatement.setInt(1, this.id);
            this.bd.preparedstatement.executeUpdate();
            return(true);            
        }
        catch (SQLException Error)
        {
            JOptionPane.showMessageDialog(null, Error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }    
        return(false);
    }
               
    public Vector Lista()
    {
      Vector datos = new Vector();

      // Se crea una orden a nivel de base de datos
      String sql = "SELECT Municipio.id AS ID, Concat(Municipio.nombre,', ', Estado.nombre) AS Nombre  "+
                   "FROM Municipio "+
                   "INNER JOIN Estado ON Municipio.Estado_id = Estado.id "+
                   "ORDER BY Municipio.nombre,Estado.nombre";

      try
       {
        this.bd.ejecutarSQL(sql);
        while (this.bd.resultset.next())
        {
          // Se introduce el catalogo de municipios como objetos (ver clase ListaEleccion)
          datos.add(new ListaEleccion(this.bd.resultset.getInt("id"),
                                      this.bd.resultset.getString("nombre")));
        }
       }
      catch (SQLException Error)
       {
        JOptionPane.showMessageDialog(null, Error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
       }
      return(datos);
    }                    
    
    public static void main(String args[])
    {
        Conexion bd = new Conexion();
        bd.conectar();
        Municipio x;        
        x = new Municipio(bd);
       
        x.Buscar(2);
        System.out.println(x.getNombre());
        
        bd.desconectar();     
    }
}



