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
public class Contacto
{
    private int    id;
    private String nombre;  
    private String telefono;
    private String email;
    protected Conexion bd;         
    
    /**
     * Constructor for objects of class Contacto
     * @param bd
     */
    public Contacto(Conexion bd)
    {  
        this.bd = bd;
        Inicializar();
    } 
    
        
    private void Inicializar()
    {
        id      = 0;
        nombre  = null;   
        telefono= null;
        email   = null;
    }
    
    /**
     * Metodo para establecer en la clase los datos
     * @param id
     * @param nombre
     * @param telefono
     * @param email
     */
    public void setContacto(int id,
                            String nombre,
                            String telefono,
                            String email)
    {
        this.id      = id;
        this.nombre  = nombre;    
        this.telefono=telefono;
        this.email   =email;
    }
    
    public boolean Buscar(int id)
    {
        String sql;
        sql = "SELECT * FROM Contacto "+
              "WHERE id="+id;  

        try
        {
            this.bd.ejecutarSQL(sql);
            if (this.bd.resultset.next())
            {
                this.id        = this.bd.resultset.getInt("id");
                this.nombre    = this.bd.resultset.getString("nombre");    
                this.telefono  = this.bd.resultset.getString("telefono");   
                this.email     = this.bd.resultset.getString("email");   
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
    
    public String getTelefono()
    {
        return(this.telefono);
    } 
    
    public String getEmail()
    {
        return(this.email);
    }  
   
    
    public ResultSet DataSetContacto() throws SQLException
    {
        String sql = "SELECT id AS ID, nombre AS Nombre, telefono AS Telefono, email AS Email FROM Contacto";
        this.bd.ejecutarSQL(sql);        
        return(this.bd.resultset);
    }

    public ResultSet DataSetContacto(String patron, int campo)
    {
        String sql;
        sql = null;
        switch (campo)
        {
            case 0: {sql = "SELECT id AS ID, nombre AS Nombre, telefono AS Telefono, email AS Email FROM Contacto "+
                           "WHERE id="+Integer.valueOf(patron); break;}
            case 1: {sql = "SELECT id AS ID, nombre AS Nombre, telefono AS Telefono, email AS Email FROM Contacto "+
                           "WHERE nombre LIKE '%"+ patron.toUpperCase()+"%'"; break;}
        }        
        this.bd.ejecutarSQL(sql);
        return(this.bd.resultset);
    } 
        
    
    public boolean Nuevo()
    {
        try
        {
            String orden;                       
            orden = "INSERT INTO Contacto(id,nombre,telefono,email) VALUES(?,?,?,?)";
                         
            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden);        
            
            // Se establecen los valores que se van a enviar para insertar
            this.bd.preparedstatement.setInt(1, id);
            this.bd.preparedstatement.setString(2, nombre.toUpperCase());   
            this.bd.preparedstatement.setString(3, telefono);
            this.bd.preparedstatement.setString(4, email);
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
            String orden = "UPDATE Contacto SET nombre=?,telefono=?,email=? WHERE id = ?";  
    
            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden); 
            
            // Se establecen los valores que se van a enviar para actualizar
            this.bd.preparedstatement.setString(1, this.nombre.toUpperCase());
            this.bd.preparedstatement.setString(2, this.telefono);
            this.bd.preparedstatement.setString(3, this.email);
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
            String orden = "DELETE FROM Contacto WHERE id = ?";
            
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
                   "FROM Contacto "+
                   "ORDER BY id";
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
        Institucion x;        
        x = new Institucion(bd);
       
        x.Buscar(2);
        System.out.println(x.getNombre());
        
        bd.desconectar();     
    }
}



