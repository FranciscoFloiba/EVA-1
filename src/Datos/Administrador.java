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
public class Administrador
{
    private int    id;
    private String nombre;  
    private String apellido;
    private String telefono;
    private String login;
    private String password;
    private String email1;
    private String email2;
    private String status;
    protected Conexion bd;         
    
    /**
     * Constructor for objects of class Administrador
     * @param bd
     */
    public Administrador(Conexion bd)
    {  
        this.bd = bd;
        Inicializar();
    } 
    
        
    private void Inicializar()
    {
        id      = 0;
        nombre  = null; 
        apellido= null;
        telefono= null;
        login   = null;
        password= null;
        email1  = null;
        email2  = null;
        status  = null;
    }
    
    /**
     * Metodo para establecer en la clase los datos
     * @param id
     * @param nombre
     * @param apellido
     * @param telefono
     * @param login
     * @param password
     * @param email1
     * @param email2
     * @param status
     */
    public void setAdministrador(int id,
                                 String nombre,
                                 String apellido,
                                 String telefono,
                                 String login,
                                 String password,
                                 String email1,
                                 String email2,
                                 String status)
    {
        this.id      = id;
        this.nombre  = nombre;    
        this.apellido= apellido;
        this.telefono= telefono;
        this.login   = login;
        this.password= password;
        this.email1  = email1;
        this.email2  = email2;
        this.status  = status;
    }
    
    public boolean Buscar(int id)
    {
        String sql;
        sql = "SELECT * FROM Administrador "+
              "WHERE id="+id;  

        try
        {
            this.bd.ejecutarSQL(sql);
            if (this.bd.resultset.next())
            {
                this.id        = this.bd.resultset.getInt("id");
                this.nombre    = this.bd.resultset.getString("nombre");   
                this.apellido  = this.bd.resultset.getString("apellido"); 
                this.telefono  = this.bd.resultset.getString("telefono"); 
                this.login     = this.bd.resultset.getString("login");
                this.password  = this.bd.resultset.getString("password"); 
                this.email1    = this.bd.resultset.getString("email1");  
                this.email2    = this.bd.resultset.getString("email2");
                this.status    = this.bd.resultset.getString("status");
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
    
    
    public int Antentificar(String login, String pwd)
    {
        String sql;
        sql = "SELECT * FROM Administrador "+
              "WHERE login='"+login+"' AND password='"+pwd+"'";  

        try
        {
            this.bd.ejecutarSQL(sql);
            if (this.bd.resultset.next())
            {
                this.id        = this.bd.resultset.getInt("id");
                this.nombre    = this.bd.resultset.getString("nombre");   
                this.apellido  = this.bd.resultset.getString("apellido"); 
                this.telefono  = this.bd.resultset.getString("telefono"); 
                this.login     = this.bd.resultset.getString("login");
                this.password  = this.bd.resultset.getString("password"); 
                this.email1    = this.bd.resultset.getString("email1");  
                this.email2    = this.bd.resultset.getString("email2");
                this.status    = this.bd.resultset.getString("status");
                return(this.id);
            }                
        }
        catch (SQLException Error)
        {
            JOptionPane.showMessageDialog(null, Error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return(-1);
    }    
     
    
    public int getID()
    {
        return(this.id);
    }
    
    public String getNombre()
    {
        return(this.nombre);
    }  
    
    public String getApellido()
    {
        return(this.apellido);
    } 
    
    public String getTelefono()
    {
        return(this.telefono);
    } 
    
    public String getLogin()
    {
        return(this.login);
    } 
    
    public String getPassword()
    {
        return(this.password);
    }   
    
    public String getEmail1()
    {
        return(this.email1);
    }  
    
    public String getEmail2()
    {
        return(this.email2);
    }  
   
    public String getStatus()
    {
        return(this.status);
    }  
    
    public ResultSet DataSetAdministrador() throws SQLException
    {
        String sql = "SELECT id AS ID, Concat(apellido,' ',nombre) AS Nombre, telefono AS Telefono, email1 AS Email1 FROM Administrador";
        this.bd.ejecutarSQL(sql);        
        return(this.bd.resultset);
    }

    public ResultSet DataSetAdministrador(String patron, int campo)
    {
        String sql;
        sql = null;
        switch (campo)
        {
            case 0: {sql = "SELECT id AS ID, Concat(apellido,' ',nombre) AS Nombre, telefono AS Telefono, email1 AS Email1 FROM Administrador "+
                           "WHERE id="+Integer.valueOf(patron); break;}
            case 1: {sql = "SELECT id AS ID, Concat(apellido, ' ',nombre) AS Nombre, telefono AS Telefono, email1 AS Email1 FROM Administrador "+
                           "WHERE nombre LIKE '%"+ patron.toUpperCase()+"%'" + " OR "+
                                 "apellido LIKE '%"+ patron.toUpperCase()+"%'"; break;}
        }        
        this.bd.ejecutarSQL(sql);
        return(this.bd.resultset);
    } 
        
    
    public boolean Nuevo()
    {
        try
        {
            String orden;                       
            orden = "INSERT INTO Administrador(id,nombre,apellido,telefono,login,password,email1,email2,status) "+
                    "VALUES(?,?,?,?,?,?,?,?,?)";
                         
            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden);        
            
            // Se establecen los valores que se van a enviar para insertar
            this.bd.preparedstatement.setInt(1, id);
            this.bd.preparedstatement.setString(2, nombre.toUpperCase()); 
            this.bd.preparedstatement.setString(3, apellido.toUpperCase());
            this.bd.preparedstatement.setString(4, telefono);
            this.bd.preparedstatement.setString(5, login);
            this.bd.preparedstatement.setString(6, password);
            this.bd.preparedstatement.setString(7, email1);
            this.bd.preparedstatement.setString(8, email2);
            this.bd.preparedstatement.setString(9, status);
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
            String orden = "UPDATE Administrador SET nombre=?,apellido=?,telefono=?,"+
                           "login=?,password=?,email1=?,email2=?,status=? WHERE id = ?";  
    
            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden); 
            
            // Se establecen los valores que se van a enviar para actualizar
            this.bd.preparedstatement.setString(1, this.nombre.toUpperCase());
            this.bd.preparedstatement.setString(2, this.apellido.toUpperCase());
            this.bd.preparedstatement.setString(3, this.telefono);
            this.bd.preparedstatement.setString(4, this.login);
            this.bd.preparedstatement.setString(5, this.password);
            this.bd.preparedstatement.setString(6, this.email1);
            this.bd.preparedstatement.setString(7, this.email2);
            this.bd.preparedstatement.setString(8, this.status);
            this.bd.preparedstatement.setInt(9, this.id);
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
            String orden = "DELETE FROM Administrador WHERE id = ?";
            
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
      String sql = "SELECT id, Concat(apellido,' ',nombre) AS nombre "+
                   "FROM Administrador "+
                   "ORDER BY id";
      try
       {
        this.bd.ejecutarSQL(sql);
        while (this.bd.resultset.next())
        {
          // Se introduce el catalogo de Administradores como objetos (ver clase ListaEleccion)
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
        Administrador x;        
        x = new Administrador(bd);
       
        x.Buscar(1);
        System.out.println(x.getNombre());
        
        bd.desconectar();     
    }
}

