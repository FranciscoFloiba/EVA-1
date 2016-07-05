package Datos;


/**
 * Clase de la capa de datos
 * 
 * @author (Rubén Benítez) 
 * @version (Ver 1.0)
 */

import java.sql.*;
import javax.swing.JOptionPane;
public class Instrumento
{
    private int    id;
    private String nombre; 
    private int    numero_reactivos;
    private int    dificultad;
    private int    Egel_id;
    protected Conexion bd;         
    
    /**
     * Constructor for objects of class Instrumento
     * @param bd
     */
    public Instrumento(Conexion bd)
    {  
        this.bd = bd;
        Inicializar();
    } 
    
        
    private void Inicializar()
    {
       id    = 0;
       nombre=null; 
       numero_reactivos=0;
       dificultad = 0;
       Egel_id=0;
    }
    
    /**
     * Metodo para establecer en la clase los datos
     * @param id_instrumento
     * @param nombre
     * @param numero_reactivos
     * @param dificultad
     * @param egel_id
     */
    public void setInstrumento(int id,
                            String nombre, 
                            int numero_reactivos,
                            int dificultad,
                            int Egel_id)
    {
        this.id               = id;
        this.nombre           = nombre; 
        this.numero_reactivos = numero_reactivos;
        this.dificultad       = dificultad;
        this.Egel_id          = Egel_id;
    }
    
    public boolean Buscar(int id)
    {
        String sql;
        sql = "SELECT * FROM Instrumento "+
              "WHERE id="+id;  

        try
        {
            this.bd.ejecutarSQL(sql);
            if (this.bd.resultset.next())
            {
                this.id                  = this.bd.resultset.getInt("id");
                this.nombre              = this.bd.resultset.getString("nombre");
                this.numero_reactivos    = this.bd.resultset.getInt("numero_reactivos");
                this.dificultad          = this.bd.resultset.getInt("dificultad");
                this.Egel_id             = this.bd.resultset.getInt("Egel_id");
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
    
    public int getNumeroReactivos()
    {
        return(this.numero_reactivos);
    } 
    
    public int getDificultad()
    {
        return(this.dificultad);
    } 
    
    public int getIdEgel()
    {
        return(this.Egel_id);
    } 
   
    
    public ResultSet DataSetInstrumento() throws SQLException
    {
        String sql = "SELECT Instrumento.id AS ID, Instrumento.nombre AS Nombre, Egel.nombre AS Egel "+ 
                     "FROM Instrumento "+
                     "INNER JOIN Egel ON Instrumento.Egel_id = Egel.id "+
                     "ORDER BY Instrumento.nombre, Egel.nombre";
        this.bd.ejecutarSQL(sql);        
        return(this.bd.resultset);
    }

    public ResultSet DataSetInstrumento(String patron, int campo)
    {
        String sql;
        sql = null;
        switch (campo)
        {
            case 0: {sql = "SELECT Instrumento.id AS ID, Instrumento.nombre AS Nombre, Egel.nombre AS Egel "+ 
                           "FROM Instrumento "+
                           "INNER JOIN Egel ON Instrumento.Egel_id = Egel.id "+
                           "WHERE Instrumento.id="+Integer.valueOf(patron)+" "+
                           "ORDER BY Instrumento.nombre, Egel.nombre"; break;}
            case 1: {sql = "SELECT Instrumento.id AS ID, Instrumento.nombre AS Nombre, Egel.nombre AS Egel "+ 
                           "FROM Instrumento "+
                           "INNER JOIN Egel ON Instrumento.Egel_id = Egel.id "+ 
                           "WHERE Instrumento.nombre LIKE '%"+ patron.toUpperCase()+"%'" + " "+
                           "ORDER BY Instrumento.nombre, Egel.nombre";break;}
            case 2: {sql = "SELECT Instrumento.id AS ID, Instrumento.nombre AS Nombre, Egel.nombre AS Egel "+ 
                           "FROM Instrumento "+
                           "INNER JOIN Egel ON Instrumento.Egel_id = Egel.id "+
                           "WHERE Egel.nombre LIKE '%"+ patron.toUpperCase()+"%'" + " "+
                           "ORDER BY Instrumento.nombre, Egel.nombre";break;}
        }        
        this.bd.ejecutarSQL(sql);
        return(this.bd.resultset);
    } 
        
    
    public boolean Nuevo()
    {
        try
        {
            String orden;                       
            orden = "INSERT INTO Instrumento(id,nombre,numero_reactivos,dificultad,Egel_id) VALUES(?,?,?,?,?)";
            
            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden);        
            
            // Se establecen los valores que se van a enviar para insertar
            this.bd.preparedstatement.setInt(1, id);
            this.bd.preparedstatement.setString(2, nombre.toUpperCase());  
            this.bd.preparedstatement.setInt(3, numero_reactivos);
            this.bd.preparedstatement.setInt(4, dificultad);
            this.bd.preparedstatement.setInt(5, Egel_id);
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
            String orden = "UPDATE Instrumento SET nombre=?,"+
                                                  "numero_reactivos=?,"+
                                                  "dificultad=?,"+
                                                  "Egel_id=? "+
                          "WHERE id = ?";  
    
            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden);            
           
            this.bd.preparedstatement.setString(1, nombre.toUpperCase());  
            this.bd.preparedstatement.setInt(2, numero_reactivos);
            this.bd.preparedstatement.setInt(3, dificultad);
            this.bd.preparedstatement.setInt(4, Egel_id);
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
            String orden = "DELETE FROM Instrumento WHERE id = ?";
            
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



