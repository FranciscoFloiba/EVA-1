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
public class Egel_Area
{
    private int id;
    private String nombre;  
    private int Egel_id;
    protected Conexion bd;         
    private int area;
    private int subarea;
    private int subsubarea;
    private String descripcion;
    
    /**
     * Constructor for objects of class Institucion_Area
     * @param bd
     */
    public Egel_Area(Conexion bd)
    {  
        this.bd = bd;
        Inicializar();
    } 
    
        
    private void Inicializar()
    {
        id      = 0;
        nombre  = null; 
        Egel_id = 0;
        area    = 0;
        subarea = 0;
        subsubarea=0;
        descripcion=null;
    }
    
    /**
     * Metodo para establecer en la clase los datos
     * @param id
     * @param nombre
     * @param Egel_id
     */
    public void setEgel(int id, int area, int subarea, int subsubarea, int Egel_id, String nombre,  String descripcion)
    {
        this.id      = id;
        this.nombre  = nombre;        
        this.Egel_id = Egel_id;
        this.area    = area;
        this.subarea = subarea;
        this.subsubarea=subsubarea;
        this.descripcion=descripcion;
    }
    
    public void setEgel(int id, String nombre, int Egel_id)
    {
        this.id      = id;
        this.nombre  = nombre;        
        this.Egel_id = Egel_id;
    }
    
    public boolean Buscar(int id)
    {
        String sql;
        sql = "SELECT * FROM Egel_Area "+
              "WHERE id="+id;  

        try
        {
            this.bd.ejecutarSQL(sql);
            if (this.bd.resultset.next())
            {
                this.id            = this.bd.resultset.getInt("id");
                this.nombre        = this.bd.resultset.getString("nombre");
                this.Egel_id       = this.bd.resultset.getInt("Egel_id");
                this.descripcion   = this.bd.resultset.getString("descripcion");
                this.area          = this.bd.resultset.getInt("area");
                this.subarea       = this.bd.resultset.getInt("subarea");
                this.subsubarea    = this.bd.resultset.getInt("subsubarea");
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
    
    public int getID(){
        return(this.id);
    }
    
    public String getNombre()
    {
        return(this.nombre);
    }    
    
    public int getID_Egel()
    {
        return(this.Egel_id);
    }
    
    public int getArea()
    {
        return(this.area);
    }
    public int getSubArea()
    {
        return(this.subarea);
    }
    
    public int getSubSubArea()
    {
        return(this.subsubarea);
    }
    
    public String getDescripcion(){
        return(this.descripcion);
    }
    
    public int getNivel()
    {   
        int nivel;
        if(area!=0 && subarea==0 && subsubarea==0 ){
            nivel=1;  
        }else if(area!=0 && subarea!=0 && subsubarea==0 ){
            nivel=2;
        }else if(area!=0 && subarea!=0 && subsubarea!=0 ){
            nivel=3;
        }
        else {
            nivel = 0;
        }
        return(nivel);
    }
    
    
     public int getEgel(int Egel){
        String sql;
        sql = "SELECT Egel_id FROM Egel_Area "+
              "WHERE Egel_id= "+Egel; 
        JOptionPane.showMessageDialog(null, sql);
        try
        {
            this.bd.ejecutarSQL(sql);
            if (this.bd.resultset.next())
            {
                Egel  = this.bd.resultset.getInt("Egel_id");
            }    
        }
        catch (SQLException Error)
        {
            JOptionPane.showMessageDialog(null, Error.getMessage(), "Error1", JOptionPane.ERROR_MESSAGE);
        }
        return Egel;
    }
    
    /* 
    public int getArea(int nArea){
        String sql;
        sql = "SELECT area FROM Egel_Area "+
              "WHERE id="+nArea;  

        try
        {
            this.bd.ejecutarSQL(sql);
            if (this.bd.resultset.next())
            {
                nArea  = this.bd.resultset.getInt("area");
            }    
        }
        catch (SQLException Error)
        {
            JOptionPane.showMessageDialog(null, Error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return nArea;
    }
    
    public int getSubarea(int nSubArea){
        String sql;
        sql = "SELECT subarea FROM Egel_Area "+
              "WHERE id="+nSubArea;  

        try
        {
            this.bd.ejecutarSQL(sql);
            if (this.bd.resultset.next())
            {
                nSubArea  = this.bd.resultset.getInt("subarea");
            }
        }
        catch (SQLException Error)
        {
            JOptionPane.showMessageDialog(null, Error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return nSubArea;
    }
    
    public int getSubsubarea(int nSubSubArea){
        String sql;
        sql = "SELECT subsubarea FROM Egel_Area "+
              "WHERE id="+nSubSubArea;  

        try
        {
            this.bd.ejecutarSQL(sql);
            if (this.bd.resultset.next())
            {
                nSubSubArea        = this.bd.resultset.getInt("subsubarea");
            }    
        }
        catch (SQLException Error)
        {
            JOptionPane.showMessageDialog(null, Error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return nSubSubArea;
    }   
    * */
    
    public ResultSet DataSetEgel_Area(int id_egel) throws SQLException
    {
        String sql = "SELECT Egel_Area.id AS ID, Egel_Area.area AS Area, Egel_Area.subarea AS SubArea, "
                + "Egel_Area.subsubarea AS SubSubArea, Egel_Area.nombre AS Nombre, Egel.nombre AS Egel "+
                     "FROM Egel_Area "+
                     "INNER JOIN Egel ON Egel_Area.Egel_id = Egel.id "+
                     "WHERE Egel_Area.Egel_id="+id_egel+" "+
                     "ORDER BY Egel_Area.Egel_id, Egel_Area.area, Egel_Area.subarea, Egel_Area.subsubarea";
        this.bd.ejecutarSQL(sql);        
        return(this.bd.resultset);
    }

    public ResultSet DataSetEgel_Area(int id_egel, String patron, int campo)
    {
        String sql;
        sql = "SELECT Egel_Area.id AS ID, Egel_Area.area AS Area, Egel_Area.subarea AS SubArea, "
                + "Egel_Area.subsubarea AS SubSubArea, Egel_Area.nombre AS Nombre, Egel.nombre AS Egel "+
                     "FROM Egel_Area "+
                     "INNER JOIN Egel ON Egel_Area.Egel_id = Egel.id "+
                     "WHERE Egel_Area.Egel_id=" + id_egel + " ";
        String order;
        order =  " ORDER BY Egel.nombre, Egel_Area.subarea, Egel_Area.subsubarea, Egel_Area.Egel_id";
 
        switch (campo)
        {
            case 0: {sql = sql +
                           "AND Egel_Area.area="+Integer.valueOf(patron)+order; break;}
            case 1: {sql = sql +
                           "AND Egel_Area.nombre LIKE '%"+ patron.toUpperCase()+"%'"+order; break;}
            case 2: {sql = sql +
                           "AND Egel.nombre LIKE '%"+ patron.toUpperCase()+"%'"+order; break;}
        }        
        this.bd.ejecutarSQL(sql);
        return(this.bd.resultset);
    }         
    
    public boolean Nuevo()
    {
        try
        {
            String orden;                       
            orden = "INSERT INTO Egel_Area(id,nombre,Egel_id,area,subarea,subsubarea,descripcion) VALUES(?,?,?,?,?,?,?)";
                         
            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden);        
            
            // Se establecen los valores que se van a enviar para insertar
            this.bd.preparedstatement.setInt(1, id);
            this.bd.preparedstatement.setString(2, nombre.toUpperCase());   
            this.bd.preparedstatement.setInt(3, Egel_id);
            this.bd.preparedstatement.setInt(4, area);
            this.bd.preparedstatement.setInt(5, subarea);
            this.bd.preparedstatement.setInt(6, subsubarea);
            this.bd.preparedstatement.setString(7, descripcion); 
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
            String orden = "UPDATE Egel_Area SET nombre=?,Egel_id=?, area=?, subarea=?, subsubarea=?, descripcion=? WHERE id = ?";  

            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden); 
            
            // Se establecen los valores que se van a enviar para actualizar
            this.bd.preparedstatement.setString(1, this.nombre.toUpperCase());
            this.bd.preparedstatement.setInt(2, this.Egel_id);
            this.bd.preparedstatement.setInt(3, this.area);
            this.bd.preparedstatement.setInt(4, this.subarea);
            this.bd.preparedstatement.setInt(5, this.subsubarea);
            this.bd.preparedstatement.setString(6, this.descripcion);
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
            String orden = "DELETE FROM Egel_Area WHERE id = ?";
            
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
                   "FROM Egel_Area "+
                   "ORDER BY nombre";
      try
       {
        this.bd.ejecutarSQL(sql);
        while (this.bd.resultset.next())
        {
          // Se introduce el catalogo de Egel_Area como objetos (ver clase ListaEleccion)
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
    
    // Método sobrecargado que devuelve las áreas de conocimiento de un Egel
    public Vector Lista(int nivel, int eg, int cba, int cbs)
    {
        Vector datos = new Vector();
        // Se crea una orden a nivel de base de datos
        String sql=null;
        
        
        switch(nivel){
            case 1:
                sql ="SELECT id, area, subarea, subsubarea, nombre FROM Egel_Area "+
                     "WHERE Egel_id= "+eg+" AND area!=0 AND subarea=0 AND subsubarea=0 "+
                     "ORDER BY Egel_id, area, subarea, subsubarea";
                break;
            case 2:
                sql ="SELECT id, area, subarea, subsubarea, nombre FROM Egel_Area "+
                     "WHERE Egel_id= "+eg+" AND area="+cba+" AND subarea!=0 AND subsubarea=0 "+
                     "ORDER BY Egel_id, area, subarea, subsubarea";
                break;  
            case 3:
                sql ="SELECT id, area, subarea, subsubarea, nombre FROM Egel_Area "+
                     "WHERE Egel_id= "+eg+" AND area="+cba+" AND subarea="+cbs+" AND subsubarea!=0 "+
                     "ORDER BY Egel_id, area, subarea, subsubarea";
                break;             
        }
        try{ 
            this.bd.ejecutarSQL(sql);
            while (this.bd.resultset.next())
            { 
                // Se introduce el catalogo de Egel_Areas como objetos (ver clase ListaEgels)
                datos.add(new ListaEgels(this.bd.resultset.getInt("id"),
                                         this.bd.resultset.getInt("area"),
                                         this.bd.resultset.getInt("subarea"),
                                         this.bd.resultset.getInt("subsubarea"),
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



