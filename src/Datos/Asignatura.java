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
public class Asignatura
{
    private int    id;
    private String nombre;
    private int    Carrera_id;
    private int    Institucion_id;
    private int    Academia_id;
    protected Conexion bd;         
    
    /**
     * Constructor for objects of class Asignatura
     * @param bd
     */
    public Asignatura(Conexion bd)
    {  
        this.bd = bd;
        Inicializar();
    } 
    
        
    private void Inicializar()
    {
        id             = 0;
        nombre         = null;
        Carrera_id     = 0;
        Institucion_id = 0;
        Academia_id    = 0;        
    }
    
    /**
     * Metodo para establecer en la clase los datos
     * @param id
     * @param nombre
     * @param Carrera_id
     * @param Academia_id
     */
    public void setAsignatura(int id,
                              String nombre,
                              int Carrera_id,
                              int Academia_id)
    {
        this.id           = id;
        this.nombre       = nombre;            
        this.Carrera_id   = Carrera_id;
        this.Academia_id  = Academia_id;
    }
    
    public boolean Buscar(int id)
    {
        String sql;
        sql = "SELECT Asignatura.id AS id, Asignatura.nombre AS nombre,"+
                     "Asignatura.Carrera_id AS Carrera_id, Carrera.Institucion_id AS Institucion_id,"+
                     "Asignatura.Academia_id AS Academia_id "+                     
              "FROM Asignatura "+
              "INNER JOIN Carrera ON Asignatura.Carrera_id = Carrera.id "+
              "INNER JOIN Institucion ON Carrera.Institucion_id = Institucion.id "+ 
              "INNER JOIN Academia ON Asignatura.Academia_id = Academia.id "+
              "WHERE Asignatura.id="+id;  

        try
        {
            this.bd.ejecutarSQL(sql);
            if (this.bd.resultset.next())
            {
                this.id           = this.bd.resultset.getInt("id");
                this.nombre       = this.bd.resultset.getString("nombre");
                this.Carrera_id   = this.bd.resultset.getInt("Carrera_id");
                this.Institucion_id=this.bd.resultset.getInt("Institucion_id");
                this.Academia_id = this.bd.resultset.getInt("Academia_id");
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
    
    public int getCarrera_id()
    {
        return(this.Carrera_id);
    }  
    
    public int getInstitucion_id()
    {
        return(this.Institucion_id);
    }  
   
    public int getAcademia_id()
    {
        return(this.Academia_id);
    } 
    
    public ResultSet DataSetAsignatura() throws SQLException
    {
        String sql = "SELECT Asignatura.id AS ID, Asignatura.nombre AS Nombre, Academia.nombre AS Academia, Carrera.nombre AS Carrera, " +
                     "Concat(Institucion.nombre,'\\n',Concat(Municipio.nombre,', ',Estado.nombre)) AS Institución "+
                     "FROM Asignatura "+
                     "INNER JOIN Academia ON Asignatura.Academia_id = Academia.id "+
                     "INNER JOIN Carrera ON Asignatura.Carrera_id = Carrera.id "+
                     "INNER JOIN Institucion ON Carrera.Institucion_id = Institucion.id "+
                     "INNER JOIN Municipio ON Institucion.Municipio_id = Municipio.id "+
                     "INNER JOIN Estado ON Municipio.Estado_id = Estado.id "+
                     "ORDER BY Asignatura.nombre, Academia.nombre, Institucion.nombre, Carrera.nombre";
        this.bd.ejecutarSQL(sql);        
        return(this.bd.resultset);
    }

    public ResultSet DataSetAsignatura(String patron, int campo)
    {
        String sql;
        sql = null;
        switch (campo)
        {
            case 0: {sql = "SELECT Asignatura.id AS ID, Asignatura.nombre AS Nombre, Academia.nombre AS Academia, Carrera.nombre AS Carrera, " +
                           "Concat(Institucion.nombre,'\\n',Concat(Municipio.nombre,', ',Estado.nombre)) AS Institución "+
                           "FROM Asignatura "+
                           "INNER JOIN Academia ON Asignatura.Academia_id = Academia.id "+
                           "INNER JOIN Carrera ON Asignatura.Carrera_id = Carrera.id "+
                           "INNER JOIN Institucion ON Carrera.Institucion_id = Institucion.id "+
                           "INNER JOIN Municipio ON Institucion.Municipio_id = Municipio.id "+
                           "INNER JOIN Estado ON Municipio.Estado_id = Estado.id "+
                           "WHERE Asignatura.id="+Integer.valueOf(patron) + " " +
                           "ORDER BY Asignatura.nombre, Academia.nombre, Institucion.nombre, Carrera.nombre"; break;}
            case 1: {sql = "SELECT Asignatura.id AS ID, Asignatura.nombre AS Nombre, Academia.nombre AS Academia, Carrera.nombre AS Carrera, " +
                           "Concat(Institucion.nombre,'\\n',Concat(Municipio.nombre,', ',Estado.nombre)) AS Institución "+
                           "FROM Asignatura "+
                           "INNER JOIN Academia ON Asignatura.Academia_id = Academia.id "+
                           "INNER JOIN Carrera ON Asignatura.Carrera_id = Carrera.id "+
                           "INNER JOIN Institucion ON Carrera.Institucion_id = Institucion.id "+
                           "INNER JOIN Municipio ON Institucion.Municipio_id = Municipio.id "+
                           "INNER JOIN Estado ON Municipio.Estado_id = Estado.id "+
                           "WHERE Asignatura.nombre LIKE '%"+ patron.toUpperCase()+"%'" + " "+
                           "ORDER BY Asignatura.nombre, Academia.nombre, Institucion.nombre, Carrera.nombre"; break;}
            case 2: {sql = "SELECT Asignatura.id AS ID, Asignatura.nombre AS Nombre, Academia.nombre AS Academia, Carrera.nombre AS Carrera, " +
                           "Concat(Institucion.nombre,'\\n',Concat(Municipio.nombre,', ',Estado.nombre)) AS Institución "+
                           "FROM Asignatura "+
                           "INNER JOIN Academia ON Asignatura.Academia_id = Academia.id "+
                           "INNER JOIN Carrera ON Asignatura.Carrera_id = Carrera.id "+
                           "INNER JOIN Institucion ON Carrera.Institucion_id = Institucion.id "+
                           "INNER JOIN Municipio ON Institucion.Municipio_id = Municipio.id "+
                           "INNER JOIN Estado ON Municipio.Estado_id = Estado.id "+
                           "WHERE Academia.nombre LIKE '%"+ patron.toUpperCase()+"%'" + " "+
                           "ORDER BY Asignatura.nombre, Academia.nombre, Institucion.nombre, Carrera.nombre"; break;}
            case 3: {sql = "SELECT Asignatura.id AS ID, Asignatura.nombre AS Nombre, Academia.nombre AS Academia, Carrera.nombre AS Carrera, " +
                           "Concat(Institucion.nombre,'\\n',Concat(Municipio.nombre,', ',Estado.nombre)) AS Institución "+
                           "FROM Asignatura "+
                           "INNER JOIN Academia ON Asignatura.Academia_id = Academia.id "+
                           "INNER JOIN Carrera ON Asignatura.Carrera_id = Carrera.id "+
                           "INNER JOIN Institucion ON Carrera.Institucion_id = Institucion.id "+
                           "INNER JOIN Municipio ON Institucion.Municipio_id = Municipio.id "+
                           "INNER JOIN Estado ON Municipio.Estado_id = Estado.id "+
                           "WHERE Carrera.nombre LIKE '%"+ patron.toUpperCase()+"%'" + " "+
                           "ORDER BY Asignatura.nombre, Academia.nombre, Institucion.nombre, Carrera.nombre"; break;}
            case 4: {sql = "SELECT Asignatura.id AS ID, Asignatura.nombre AS Nombre, Academia.nombre AS Academia, Carrera.nombre AS Carrera, " +
                           "Concat(Institucion.nombre,'\\n',Concat(Municipio.nombre,', ',Estado.nombre)) AS Institución "+
                           "FROM Asignatura "+
                           "INNER JOIN Academia ON Asignatura.Academia_id = Academia.id "+
                           "INNER JOIN Carrera ON Asignatura.Carrera_id = Carrera.id "+
                           "INNER JOIN Institucion ON Carrera.Institucion_id = Institucion.id "+
                           "INNER JOIN Municipio ON Institucion.Municipio_id = Municipio.id "+
                           "INNER JOIN Estado ON Municipio.Estado_id = Estado.id "+
                           "WHERE Institucion.nombre LIKE '%"+ patron.toUpperCase()+"%'" + " OR "+
                                 "Municipio.nombre LIKE '%"+ patron.toUpperCase()+"%'" + " "+
                           "ORDER BY Asignatura.nombre, Academia.nombre, Institucion.nombre, Carrera.nombre"; break;}
        }        
        this.bd.ejecutarSQL(sql);
        return(this.bd.resultset);
    } 
        
    
    public boolean Nuevo()
    {
        try
        {
            String orden;                       
            orden = "INSERT INTO Asignatura(id,nombre,Carrera_id,Academia_id) VALUES(?,?,?,?)";
                         
            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden);        
            
            // Se establecen los valores que se van a enviar para insertar
            this.bd.preparedstatement.setInt(1, id);
            this.bd.preparedstatement.setString(2, nombre.toUpperCase());    
            this.bd.preparedstatement.setInt(3, Carrera_id);
            this.bd.preparedstatement.setInt(4, Academia_id);
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
            String orden = "UPDATE Asignatura SET nombre=?,Carrera_id=?,Academia_id=? WHERE id = ?";  
    
            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden); 
            
            // Se establecen los valores que se van a enviar para actualizar
            this.bd.preparedstatement.setString(1, this.nombre.toUpperCase());
            this.bd.preparedstatement.setInt(2, this.Carrera_id);
            this.bd.preparedstatement.setInt(3, this.Academia_id);
            this.bd.preparedstatement.setInt(4, this.id);
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
            String orden = "DELETE FROM Asignatura WHERE id = ?";
            
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
               
    
    public Vector Lista(int id)
    {
      Vector datos = new Vector();

      // Se crea una orden a nivel de base de datos
      String sql = "SELECT id, nombre "+
                   "FROM Asignatura "+
                   "WHERE Carrera_id = " + id + " "+
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
        Asignatura x;        
        x = new Asignatura(bd);
       
        x.Buscar(2);
        System.out.println(x.getNombre());
        
        bd.desconectar();     
    }
}



