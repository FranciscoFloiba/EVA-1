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
public class Institucion
{
    private int    id;
    private String nombre;    
    private String poblacion;
    private String colonia;
    private String calle_y_numero;
    private int Contacto_id;
    private int Municipio_id;
    protected Conexion bd;         
    
    /**
     * Constructor for objects of class Institucion
     * @param bd
     */
    public Institucion(Conexion bd)
    {  
        this.bd = bd;
        Inicializar();
    } 
    
        
    private void Inicializar()
    {
        id             = 0;
        nombre         = null;  
        poblacion      = null;
        colonia        = null;
        calle_y_numero = null;
        Contacto_id    = 0;
        Municipio_id   = 0;
    }
    
    /**
     * Metodo para establecer en la clase los datos
     * @param id
     * @param nombre
     * @param poblacion
     * @param colonia
     * @param calle_y_numero
     * @param Contacto_id
     * @param Municipio_id
     */
    public void setInstitucion(int id,
                         String nombre,
                         String poblacion,
                         String colonia,
                         String calle_y_numero,
                         int Municipio_id,
                         int Contacto_id)
    {
        this.id             = id;
        this.nombre         = nombre; 
        this.poblacion      = poblacion;
        this.colonia        = colonia;
        this.calle_y_numero = calle_y_numero;
        this.Municipio_id   = Municipio_id;
        this.Contacto_id    = Contacto_id;
    }
    
    public boolean Buscar(int id)
    {
        String sql;
        sql = "SELECT * FROM Institucion "+
              "WHERE id="+id;  

        try
        {
            this.bd.ejecutarSQL(sql);
            if (this.bd.resultset.next())
            {
                this.id            = this.bd.resultset.getInt("id");
                this.nombre        = this.bd.resultset.getString("nombre"); 
                this.poblacion     = this.bd.resultset.getString("poblacion");
                this.colonia       = this.bd.resultset.getString("colonia");
                this.calle_y_numero=this.bd.resultset.getString("calle_y_numero");
                this.Contacto_id   = this.bd.resultset.getInt("Contacto_id");
                this.Municipio_id  = this.bd.resultset.getInt("Municipio_id");
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
    
    public String getPoblacion()
    {
        return(this.poblacion);
    }   
    
    public String getColonia()
    {
        return(this.colonia);
    }
    
    public String getCalle_y_Numero()
    {
        return(this.calle_y_numero);
    } 
   
    public int getContacto_Id()
    {
        return(this.Contacto_id);
    } 
    
    public int getMunicipio_Id()
    {
        return(this.Municipio_id);
    } 
    
    public ResultSet DataSetInstitucion() throws SQLException
    {
        String sql = "SELECT Institucion.id AS ID, Institucion.nombre AS Nombre, Concat(Municipio.nombre,', ',Estado.nombre) AS Localidad "+
                     "FROM Institucion "+
                     "INNER JOIN Municipio ON Institucion.Municipio_id = Municipio.id "+
                     "INNER JOIN Estado ON Municipio.Estado_id = Estado.id "+
                     "ORDER BY Institucion.nombre";
        this.bd.ejecutarSQL(sql);        
        return(this.bd.resultset);
    }

    public ResultSet DataSetInstitucion(String patron, int campo)
    {
        String sql;
        sql = null;
        switch (campo)
        {
            case 0: {sql = "SELECT Institucion.id AS ID, Institucion.nombre AS Nombre, Concat(Municipio.nombre,', ',Estado.nombre) AS Localidad "+
                           "FROM Institucion "+
                           "INNER JOIN Municipio ON Institucion.Municipio_id = Municipio.id "+
                           "INNER JOIN Estado ON Municipio.Estado_id = Estado.id "+
                           "WHERE Institucion.id="+Integer.valueOf(patron)+" "+
                           "ORDER BY Institucion.nombre"; break;}
            case 1: {sql = "SELECT Institucion.id AS ID, Institucion.nombre AS Nombre, Concat(Municipio.nombre,', ',Estado.nombre) AS Localidad "+
                           "FROM Institucion "+
                           "INNER JOIN Municipio ON Institucion.Municipio_id = Municipio.id "+
                           "INNER JOIN Estado ON Municipio.Estado_id = Estado.id "+
                           "WHERE Institucion.nombre LIKE '%"+ patron.toUpperCase()+"%'"+" "+
                           "ORDER BY Institucion.nombre"; break;}
        }        
        this.bd.ejecutarSQL(sql);
        return(this.bd.resultset);
    } 
        
    
    public boolean Nuevo()
    {
        try
        {
            String orden;                       
            orden = "INSERT INTO Institucion(id,nombre,poblacion,colonia,"+
                    "calle_y_numero,Contacto_id,Municipio_id) "+
                    "VALUES(?,?,?,?,?,?,?)";
                         
            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden);        
            
            // Se establecen los valores que se van a enviar para insertar
            this.bd.preparedstatement.setInt(1, id);
            this.bd.preparedstatement.setString(2, nombre.toUpperCase());
            this.bd.preparedstatement.setString(3, poblacion.toUpperCase());
            this.bd.preparedstatement.setString(4, colonia.toUpperCase());
            this.bd.preparedstatement.setString(5, calle_y_numero.toUpperCase());
            this.bd.preparedstatement.setInt(6, Contacto_id);
            this.bd.preparedstatement.setInt(7, Municipio_id);
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
            String orden = "UPDATE Institucion SET nombre=?,"+
                           "poblacion=?,"+
                           "colonia=?,"+
                           "calle_y_numero=?,"+
                           "Contacto_id=?,"+
                           "Municipio_id=? "+
                           "WHERE id = ?";  
    
            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden); 
            
            // Se establecen los valores que se van a enviar para actualizar
            this.bd.preparedstatement.setString(1, this.nombre.toUpperCase());
            this.bd.preparedstatement.setString(2, this.poblacion.toUpperCase());
            this.bd.preparedstatement.setString(3, this.colonia.toUpperCase());
            this.bd.preparedstatement.setString(4, this.calle_y_numero.toUpperCase());
            this.bd.preparedstatement.setInt(5, this.Contacto_id);
            this.bd.preparedstatement.setInt(6, this.Municipio_id);
            this.bd.preparedstatement.setInt(7, this.id);
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
            String orden = "DELETE FROM Institucion WHERE id = ?";
            
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
      String sql = "SELECT Institucion.id AS id, Concat(Institucion.nombre,', ',Municipio.nombre,', ',Estado.nombre) AS Nombre "+
                   "FROM Institucion "+
                   "INNER JOIN Municipio ON Institucion.Municipio_id = Municipio.id "+
                   "INNER JOIN Estado ON Municipio.Estado_id = Estado.id "+
                   "ORDER BY Institucion.nombre";
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



