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
public class Carrera
{
    private int    id;
    private String nombre;
    private int    Egel_id;
    private int    Contacto_id;
    private int    Institucion_id;
    protected Conexion bd;         
    
    /**
     * Constructor for objects of class Egel
     * @param bd
     */
    public Carrera(Conexion bd)
    {  
        this.bd = bd;
        Inicializar();
    } 
    
        
    private void Inicializar()
    {
        id             = 0;
        nombre         = null;
        Egel_id        = 0;
        Contacto_id    = 0;
        Institucion_id = 0;
    }
    
    /**
     * Metodo para establecer en la clase los datos
     * @param id
     * @param Egel_id
     * @param Contacto_id
     * @param Institucion_id
     * @param nombre
     */
    public void setCarrera(int id,
                           String nombre,
                           int Egel_id,
                           int Contacto_id,
                           int Institucion_id)
    {      
        this.id             = id;
        this.nombre         = nombre;
        this.Egel_id        = Egel_id;
        this.Contacto_id    = Contacto_id;
        this.Institucion_id = Institucion_id;
    }
    
    public boolean Buscar(int id)
    {
        String sql;
        sql = "SELECT * FROM Carrera "+
              "WHERE id="+id;  

        try
        {
            this.bd.ejecutarSQL(sql);
            if (this.bd.resultset.next())
            {
                this.id              = this.bd.resultset.getInt("id");
                this.nombre          = this.bd.resultset.getString("nombre");  
                this.Egel_id         = this.bd.resultset.getInt("Egel_id");
                this.Contacto_id     = this.bd.resultset.getInt("Contacto_id");
                this.Institucion_id  = this.bd.resultset.getInt("Institucion_id");
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
   
    public int getEgel_id()
    {
        return(this.Egel_id);
    }
    
    public int getContacto_id()
    {
        return(this.Contacto_id);
    }
    
    public int getInstitucion_id()
    {
        return(this.Institucion_id);
    }
    
    public ResultSet DataSetCarrera() throws SQLException
    {
        String sql = "SELECT Carrera.id AS ID, Carrera.nombre AS Nombre,"+
                     "Concat(Institucion.nombre,'\\n',Concat(Municipio.nombre,', ',Estado.nombre)) AS Institución "+
                     "FROM Carrera "+
                     "INNER JOIN Institucion ON Carrera.Institucion_id = Institucion.id "+
                     "INNER JOIN Municipio ON Institucion.Municipio_id = Municipio.id "+
                     "INNER JOIN Estado ON Municipio.Estado_id = Estado.id "+
                     "ORDER BY Institucion.nombre, Carrera.nombre";
                
        this.bd.ejecutarSQL(sql);        
        return(this.bd.resultset);
    }

    public ResultSet DataSetCarrera(String patron, int campo)
    {
        String sql;
        sql = null;
        switch (campo)
        {
            case 0: {sql = "SELECT Carrera.id AS ID, Carrera.nombre AS Nombre,"+
                           "Concat(Institucion.nombre,'\\n',Concat(Municipio.nombre,', ',Estado.nombre)) AS Institución "+
                           "FROM Carrera "+
                           "INNER JOIN Institucion ON Carrera.Institucion_id = Institucion.id "+
                           "INNER JOIN Municipio ON Institucion.Municipio_id = Municipio.id "+
                           "INNER JOIN Estado ON Municipio.Estado_id = Estado.id "+                          
                           "WHERE Carrera.id="+Integer.valueOf(patron) + " "+
                           "ORDER BY Institucion.nombre, Carrera.nombre"; break;}
            case 1: {sql = "SELECT Carrera.id AS ID, Carrera.nombre AS Nombre,"+
                           "Concat(Institucion.nombre,'\\n',Concat(Municipio.nombre,', ',Estado.nombre)) AS Institución "+
                           "FROM Carrera " +
                           "INNER JOIN Institucion ON Carrera.Institucion_id = Institucion.id "+
                           "INNER JOIN Municipio ON Institucion.Municipio_id = Municipio.id "+
                           "INNER JOIN Estado ON Municipio.Estado_id = Estado.id "+                          
                           "WHERE Carrera.nombre LIKE '%"+ patron.toUpperCase()+"%'" + " "+
                           "ORDER BY Institucion.nombre, Carrera.nombre"; break;}
            case 2: {sql = "SELECT Carrera.id AS ID, Carrera.nombre AS Nombre,"+
                           "Concat(Institucion.nombre,'\\n',Concat(Municipio.nombre,', ',Estado.nombre)) AS Institución "+
                           "FROM Carrera " +
                           "INNER JOIN Institucion ON Carrera.Institucion_id = Institucion.id "+
                           "INNER JOIN Municipio ON Institucion.Municipio_id = Municipio.id "+
                           "INNER JOIN Estado ON Municipio.Estado_id = Estado.id "+                          
                           "WHERE Institucion.nombre LIKE '%"+ patron.toUpperCase()+"%'" + " "+
                           "ORDER BY Institucion.nombre, Carrera.nombre"; break;}
        }        
        this.bd.ejecutarSQL(sql);
        return(this.bd.resultset);
    } 
        
    
    public boolean Nuevo()
    {
        try
        {
            String orden;                       
            orden = "INSERT INTO Carrera(id,nombre,Egel_id,Contacto_id,Institucion_id) VALUES(?,?,?,?,?)";       
           
            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden);        
            
            // Se establecen los valores que se van a enviar para insertar
            this.bd.preparedstatement.setInt(1, id);
            this.bd.preparedstatement.setString(2, nombre.toUpperCase());
            this.bd.preparedstatement.setInt(3, Egel_id);
            this.bd.preparedstatement.setInt(4, Contacto_id);
            this.bd.preparedstatement.setInt(5, Institucion_id);
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
            String orden = "UPDATE Carrera SET nombre=?, Egel_id=?, Contacto_id=?, Institucion_id=? WHERE id = ?";              

            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden); 
            
            // Se establecen los valores que se van a enviar para actualizar
            this.bd.preparedstatement.setString(1, this.nombre.toUpperCase());
            this.bd.preparedstatement.setInt(2, this.Egel_id);
            this.bd.preparedstatement.setInt(3, this.Contacto_id);
            this.bd.preparedstatement.setInt(4, this.Institucion_id);
            this.bd.preparedstatement.setInt(5, this.id);
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
            String orden = "DELETE FROM Carrera WHERE id = ?";
            
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
      String sql = "SELECT id, nombre "+
                   "FROM Carrera "+
                   "ORDER BY nombre";
      try
       {
        this.bd.ejecutarSQL(sql);
        while (this.bd.resultset.next())
        {
          // Se introduce el catalogo de grupos como objetos (ver clase ListaEleccion)
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
        
    public Vector Lista(int id)
    {
      Vector datos = new Vector();

      // Se crea una orden a nivel de base de datos
      String sql = "SELECT id, nombre "+
                   "FROM Carrera "+
                   "WHERE Institucion_id = " + id + " "+
                   "ORDER BY nombre";
      try
       {
        this.bd.ejecutarSQL(sql);
        while (this.bd.resultset.next())
        {
          // Se introduce el catalogo de grupos como objetos (ver clase ListaEleccion)
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
        Carrera x;        
        x = new Carrera(bd);
       
        x.Buscar(2);
        System.out.println(x.getNombre());
        
        bd.desconectar();     
    }
}



