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
public class Modulo
{
    private int id;
    private int modulo;
    private int submodulo;
    private int subsubmodulo;
    private String nombre;
    private boolean etiqueta;    
    private String estado;
    private int tipo;
    private String componente;
    protected Conexion bd;         
    
    /**
     * Constructor for objects of class Modulo
     * @param bd
     */
    public Modulo(Conexion bd)
    {  
        this.bd = bd;
        Inicializar();
    } 
    
        
    private void Inicializar()
    {
        id          =0;
        modulo      =0;
        submodulo   =0;
        subsubmodulo=0;
        nombre  = null; 
        etiqueta= true;    
        estado  = null;
        tipo    = 0;
        componente=null; 
    }
    
    /**
     * Metodo para establecer en la clase los datos
     * @param id
     * @param modulo
     * @param submodulo
     * @param subsubmodulo
     * @param nombre
     * @param etiqueta
     * @param estado
     * @param tipo
     * @param componente
     */
    public void setModulo(int id,
                          int modulo,
                          int submodulo,
                          int subsubmodulo,
                          String nombre,
                          boolean etiqueta,
                          String estado,
                          int tipo,
                          String componente)
    {
        this.id      = id;
        this.modulo  = modulo;
        this.submodulo=submodulo;
        this.subsubmodulo=subsubmodulo;
        this.nombre  = nombre;    
        this.etiqueta= etiqueta;    
        this.estado  = estado;
        this.tipo    = tipo;
        this.componente=componente;
    }
    
    public boolean Buscar(int id)
    {
        String sql;
        sql = "SELECT * FROM Modulo "+
              "WHERE id="+id;  

        try
        {
            this.bd.ejecutarSQL(sql);
            if (this.bd.resultset.next())
            {
                this.id           = this.bd.resultset.getInt("id");
                this.modulo       = this.bd.resultset.getInt("modulo");   
                this.submodulo    = this.bd.resultset.getInt("submodulo"); 
                this.subsubmodulo = this.bd.resultset.getInt("subsubmodulo"); 
                this.nombre       = this.bd.resultset.getString("nombre");
                this.etiqueta     = this.bd.resultset.getBoolean("etiqueta"); 
                this.estado       = this.bd.resultset.getString("estado");  
                this.tipo         = this.bd.resultset.getInt("tipo");
                this.componente   = this.bd.resultset.getString("componente");
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
    
    public int getModulo()
    {
        return(this.modulo);
    }  
    
    public int getSubModulo()
    {
        return(this.submodulo);
    } 
    
    public int getSubSubModulo()
    {
        return(this.subsubmodulo);
    } 
    
    public String getNombre()
    {
        return(this.nombre);
    } 
    
    public boolean getEtiqueta()
    {
        return(this.etiqueta);
    }   
    
    public String getEstado()
    {
        return(this.estado);
    }  
    
    public int getTipo()
    {
        return(this.tipo);
    }  
   
    public String getComponente()
    {
        return(this.componente);
    }  
    
    /*
    public int getNivel(int modulo, int sub, int subsub)
    {
        int nivel = 0;
        if (modulo > 0 && sub == 0 && subsub == 0)
        {
            nivel = 1;
        }
        
        if (modulo > 0 && sub > 0 && subsub == 0)
        {
            nivel = 2;
        }
        
        if (modulo > 0 && sub > 0 && subsub > 0)
        {
            nivel = 3;
        }
        return(nivel);
    }*/
    
    public ResultSet DataSetModulo() throws SQLException
    {
        String sql = "SELECT id AS ID, modulo AS Modulo, submodulo AS Sub, subsubmodulo AS SubSub, "+
                            "nombre AS Nombre, IF(etiqueta,'Si','No') AS Etiqueta, componente AS Componente, "+
                            "CASE tipo WHEN 0 THEN 'No Aplica' "+
                                      "WHEN 1 THEN 'Ventana' "+
                                      "WHEN 2 THEN 'Reporte' "+
                            "END "+
                            "AS Tipo "+ 
                     "FROM Modulo "+
                     "ORDER BY modulo, submodulo, subsubmodulo";
        this.bd.ejecutarSQL(sql);        
        return(this.bd.resultset);
    }

    public ResultSet DataSetModulo(String patron, int campo)
    {
        String sql;
        sql = null;
        switch (campo)
        {
            /*case 0: {sql = "SELECT id AS ID, modulo AS Modulo, submodulo AS Sub, subsubmodulo AS SubSub, "+
                           "nombre AS Nombre, IF(etiqueta,'Si','No') AS Etiqueta, componente AS Componente, "+
                           "CASE tipo WHEN 0 THEN 'No Aplica' "+
                                     "WHEN 1 THEN 'Ventana' "+
                                     "WHEN 2 THEN 'Reporte' "+
                           "END "+
                           "AS Tipo "+ 
                           "FROM Modulo "+
                           "WHERE id="+Integer.valueOf(patron); break;}*/
            case 0: {sql = "SELECT id AS ID, modulo AS Modulo, submodulo AS Sub, subsubmodulo AS SubSub, "+
                           "nombre AS Nombre, IF(etiqueta,'Si','No') AS Etiqueta, componente AS Componente, "+
                           "CASE tipo WHEN 0 THEN 'No Aplica' "+
                                     "WHEN 1 THEN 'Ventana' "+
                                     "WHEN 2 THEN 'Reporte' "+
                           "END "+
                           "AS Tipo "+ 
                           "FROM Modulo "+
                           "WHERE modulo="+Integer.valueOf(patron); break;}
            case 1: {sql = "SELECT id AS ID, modulo AS Modulo, submodulo AS Sub, subsubmodulo AS SubSub, "+
                            "nombre AS Nombre, IF(etiqueta,'Si','No') AS Etiqueta, componente AS Componente, "+
                            "CASE tipo WHEN 0 THEN 'No Aplica' "+
                                      "WHEN 1 THEN 'Ventana' "+
                                      "WHEN 2 THEN 'Reporte' "+
                            "END "+
                            "AS Tipo "+ 
                            "FROM Modulo "+
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
            orden = "INSERT INTO Modulo(id,modulo,submodulo,subsubmodulo,nombre,etiqueta,estado,tipo,componente) "+
                    "VALUES(?,?,?,?,?,?,?,?,?)";
                         
            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden);        
            
            // Se establecen los valores que se van a enviar para insertar
            this.bd.preparedstatement.setInt(1, id);
            this.bd.preparedstatement.setInt(2, modulo); 
            this.bd.preparedstatement.setInt(3, submodulo);
            this.bd.preparedstatement.setInt(4, subsubmodulo);
            this.bd.preparedstatement.setString(5, nombre);
            this.bd.preparedstatement.setBoolean(6, etiqueta);
            this.bd.preparedstatement.setString(7, estado);
            this.bd.preparedstatement.setInt(8, tipo);
            this.bd.preparedstatement.setString(9, componente);
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
            String orden = "UPDATE Modulo SET modulo=?,submodulo=?,subsubmodulo=?,"+
                           "nombre=?,etiqueta=?,estado=?,tipo=?,componente=? WHERE id = ?";  
    
            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden); 
            
            // Se establecen los valores que se van a enviar para actualizar
            this.bd.preparedstatement.setInt(1, this.modulo);
            this.bd.preparedstatement.setInt(2, this.submodulo);
            this.bd.preparedstatement.setInt(3, this.subsubmodulo);
            this.bd.preparedstatement.setString(4, this.nombre);
            this.bd.preparedstatement.setBoolean(5, this.etiqueta);
            this.bd.preparedstatement.setString(6, this.estado);
            this.bd.preparedstatement.setInt(7, this.tipo);
            this.bd.preparedstatement.setString(8, this.componente);
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
            String orden = "DELETE FROM Modulo WHERE id = ?";
            
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
      String sql = "SELECT id, componente as nombre "+
                   "FROM Modulo "+
                   "ORDER BY modulo, submodulo, subsubmodulo";
      try
       {
        this.bd.ejecutarSQL(sql);
        while (this.bd.resultset.next())
        {
          // Se introduce el catalogo de Modulos como objetos (ver clase ListaEleccionSeguridad)
          datos.add(new ListaEleccionSeguridad(this.bd.resultset.getInt("id"),
                                               this.bd.resultset.getString("nombre"),
                                               this.bd.resultset.getString("componente")));
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
        Modulo x;        
        x = new Modulo(bd);
       
        //x.Buscar(2);
        //System.out.println(x.getNombre());
        
        bd.desconectar();     
    }
}

