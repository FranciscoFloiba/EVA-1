/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.sql.*;
import java.util.Vector;
import javax.swing.JOptionPane;
/**
 *
 * @author Edwin
 */
public class Conaic_Area {
    private int id;
    private int area;
    private int subarea;
    private int subsubarea;
    private String nombre;
    private String clave;
    private String descripcion;
    private int areaid;
    protected Conexion bd;
    private int subareaid;
    private int subsubareaid;
    
    public Conaic_Area(Conexion bd)
    {  
        this.bd = bd;
        
        Inicializar();
    } 
    
    public void Inicializar(){
        area=0;
        subarea=0;
        subsubarea=0;
        nombre=null;
        clave=null;
        descripcion=null;
    }
    
    public void setArea(int id,int area, int subarea, int subsubarea, String nombre, String clave, String descripcion){
        this.id=id;
        this.area=area;
        this.subarea=subarea;
        this.subsubarea=subsubarea;
        this.nombre=nombre;
        this.clave=clave;
        this.descripcion=descripcion;
       
    }
    public void setArea(int id,String nombre){
       
      //  this.area=Integer.parseInt(area);
        this.id=id;
        this.nombre=nombre;
    }
    public int getId(){
        String sql;
        sql="SELECT MAX(id) AS id FROM Conaic_Area";
        try
        {
            this.bd.ejecutarSQL(sql);
            if (this.bd.resultset.next())
            {
                this.id  = this.bd.resultset.getInt("id");
            }    
           
        }
        catch (SQLException Error)
        {
            JOptionPane.showMessageDialog(null, Error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return this.id+1;
    }
 
       /* if(ent==1){
            sql = "SELECT id,nombre FROM Conaic_Area WHERE subarea=0 AND subsubarea=0";
        } else if(ent==2){
            sql = "SELECT id,nombre FROM Conaic_Area WHERE subarea!=0 AND subsubarea=0";
        } else if(ent==3){
            sql = "SELECT id,nombre FROM Conaic_Area WHERE subarea!=0 AND subsubarea!=0";
        }*/
    
    public boolean Buscar(String id)
    {
        String sql;
        sql = "SELECT * FROM Conaic_Area "+
              "WHERE id='"+id+"'";  

        try
        {
            this.bd.ejecutarSQL(sql);
            if (this.bd.resultset.next())
            {
                this.id = this.bd.resultset.getInt("id");
                this.area  = this.bd.resultset.getInt("area");
                this.subarea  = this.bd.resultset.getInt("subarea");
                this.subsubarea  = this.bd.resultset.getInt("subsubarea");
                this.nombre  = this.bd.resultset.getString("nombre");
                this.clave  = this.bd.resultset.getString("clave");
                this.descripcion  = this.bd.resultset.getString("descripcion");
                return(true);
            }    
            else
            {
                Inicializar();
            }
        }
        catch (SQLException Error)
        {
            JOptionPane.showMessageDialog(null, Error.getMessage(), "Error7", JOptionPane.ERROR_MESSAGE);
        }
        return(false);
    } 
    
    public int getArea()
    {        
        return(this.area);
    }
    public int getSubarea()
    {        
        return(this.subarea);
    }
    public int getSubsubarea()
    {        
        return(this.subsubarea);
    }
    public String getNombre()
    {        
        return(this.nombre);
    }
    public String getClave()
    {        
        return(this.clave);
    }
    public int getID()
    {
        return(this.id);
    }
    public int getArea_ID()
    {
        return(this.areaid);
    } 
    public int getSubarea_ID()
    {
        return(this.subareaid);
    } 
    public int getSubsubarea_ID()
    {
        return(this.subsubareaid);
    } 
    public String getDescripcion()
    {     
        
        return(this.descripcion);     
    }
    
    public int getNivel()
    {   
        int nivel;
        if(area!=0 && subarea==0 && subsubarea==0 ){
            nivel=1;  
        }else if(area!=0 && subarea!=0 && subsubarea==0 ){
            nivel=2;
        }else if(area!=0 && subarea!=0 && subsubarea!=0 && clave.equals("")){
            nivel=3;
        }else if(area!=0 && subarea!=0 && subsubarea!=0 && clave.length()>0){
            nivel=4;
        }
        else {
            nivel = 0;
        }
        return(nivel);
    }
    
    public void setArea(int id,
                             String nombre,
                             int areaid)
    {
        this.id        = id;
        this.nombre    = nombre;   
        this.areaid = areaid;
    }
    
    
        public boolean Buscar(int id)
    {
        int nivel=validarNivel(id);
        String sql;
        sql = "SELECT * FROM Conaic_Area "+
              "WHERE id="+id;  
        try
        {
            
            this.bd.ejecutarSQL(sql);
            if (this.bd.resultset.next())
            {
                this.id        = this.bd.resultset.getInt("id");
                this.nombre    = this.bd.resultset.getString("nombre");
                this.area = this.bd.resultset.getInt("area");
                this.subarea = this.bd.resultset.getInt("subarea");
                this.subsubarea = this.bd.resultset.getInt("subsubarea");
                this.clave = this.bd.resultset.getString("clave");
                this.descripcion = this.bd.resultset.getString("descripcion");
                
                
                //this.subareaid = this.bd.datos.getInt("subarea");
                //this.subsubareaid = this.bd.datos.getInt("subsubarea");
                return(true);
            }    
            else
            {
                Inicializar();
            }
        }
        catch (SQLException Error)
        {
            JOptionPane.showMessageDialog(null, Error.getMessage(), "Error6", JOptionPane.ERROR_MESSAGE);
        }
        return(false);
    }
        
       public Vector Lista()
    {
      Vector datos = new Vector();

      // Se crea una orden a nivel de base de datos
      String sql = "SELECT id,area,subarea,subsubarea,nombre,clave,descripcion "+
                   "FROM Conaic_Area "+
                   "ORDER BY nombre";
      try
       {
        this.bd.ejecutarSQL(sql);
        while (this.bd.resultset.next())
        {
          // Se introduce el catalogo de Egel_Area como objetos (ver clase ListaEleccion)
          datos.add(new ListaEleccion(
                  this.bd.resultset.getInt("id"),
                  this.bd.resultset.getString("nombre")));
        }
       }
      catch (SQLException Error)
       {
        JOptionPane.showMessageDialog(null, Error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
       }
      return(datos);
    } 
        public Vector Lista(int nivel, int cb1, int cb2, int cb3)
    {
      Vector datos = new Vector();
      // Se crea una orden a nivel de base de datos
      String sql;
      switch(nivel){
          case 1:
                sql ="SELECT id, area,subarea,subsubarea,clave,nombre "
                        + "FROM  Conaic_Area WHERE subarea=0 AND subsubarea=0";
                break;
          case 2:
                sql ="SELECT id, area, subarea, subsubarea, clave, nombre "
                        + "FROM  Conaic_Area WHERE area="+cb1+" AND subarea!=0 AND subsubarea=0";
                break;
          case 3:
                sql ="SELECT id, area,subarea,subsubarea,clave,nombre "
                        + "FROM  Conaic_Area WHERE area="+cb1+" AND subarea="+cb2+" AND subsubarea!=0 AND clave=''";
                
                break;      
          default:
                sql="SELECT id, area,subarea,subsubarea,clave,nombre "
                        + "FROM  Conaic_Area WHERE subarea=0 AND subsubarea=0";
      }

      try
       { 
        this.bd.ejecutarSQL(sql);
        while (this.bd.resultset.next())
        { 
          // Se introduce el catalogo de municipios como objetos (ver clase ListaEleccion)
          datos.add(new ListaConaic(
                  this.bd.resultset.getInt("id"),
                  this.bd.resultset.getInt("area"),
                  this.bd.resultset.getInt("subarea"),
                  this.bd.resultset.getInt("subsubarea"),
                  this.bd.resultset.getString("clave"),
                  this.bd.resultset.getString("nombre")
          ));
        }
       }
      catch (SQLException Error)
       {
        JOptionPane.showMessageDialog(null, Error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
       }
      return(datos);
    }  
        
    public ResultSet DataSetArea()
    {
        ResultSet records=null;
        Statement sql;
        try
        {
          sql = this.bd.conexion.createStatement();
          records = sql.executeQuery("SELECT id,area, subarea, subsubarea, nombre,"+
                                             "clave, descripcion "+
                                     "FROM Conaic_Area "+
                                     "ORDER BY area,subarea,subsubarea");
        }
        catch (SQLException Error)
        {
            JOptionPane.showMessageDialog(null, Error.getMessage(), "Error4", JOptionPane.ERROR_MESSAGE);
        }
        return(records);
    }
    
    //ResultSet Jaz
    public ResultSet DataSetConaic_Areas() throws SQLException
    {
        String sql = "SELECT Conaic_Area.id AS ID, Conaic_Area.area AS Area, Conaic_Area.subarea AS SubArea, " +
        "Conaic_Area.subsubarea AS SubSubArea, Conaic_Area.clave AS Clave, "+ 
        "Conaic_Area.nombre AS Nombre, Conaic_Area.descripcion AS Descripcion " +
        "FROM Conaic_Area " +
        "ORDER BY Conaic_Area.area, Conaic_Area.subarea, Conaic_Area.subsubarea,Conaic_Area.clave";
        this.bd.ejecutarSQL(sql);        
        return(this.bd.resultset);
    }
       public ResultSet DataSetConaic_Areas(String patron, int campo)
    {
        String sql;
        sql = "SELECT Conaic_Area.id AS ID, Conaic_Area.area AS Area, Conaic_Area.subarea AS SubArea, " +
        "Conaic_Area.subsubarea AS SubSubArea, Conaic_Area.clave AS clave," +
        "Conaic_Area.nombre AS Nombre, Conaic_Area.descripcion AS Descripcion " +
        "FROM Conaic_Area ";
                                 
        String order;
        order =  " ORDER BY Conaic_Area.area, Conaic_Area.subarea,Conaic_Area.subsubarea,Conaic_Area.clave";  
 
        switch (campo)
        {
            case 0: {sql = sql +
                           " WHERE Conaic_Area.area="+Integer.valueOf(patron)+order; break;}
            case 1: {sql = sql +
                           " WHERE Conaic_Area.nombre LIKE '%"+ patron.toUpperCase()+"%'"+order; break;}
            case 2: {sql = sql +
                           " WHERE Conaic_Area.clave LIKE '%"+ patron.toUpperCase()+"%'"+order; break;}         
        }        
        this.bd.ejecutarSQL(sql);
        return(this.bd.resultset);
    }
    
    public boolean Nuevo()
    {
        try
        {
            String OrdenSQL = "INSERT INTO Conaic_Area(id,area,"+
                                                   "subarea,"+
                                                   "subsubarea,"+
                                                   "nombre,"+
                                                   "clave,"+
                                                   "descripcion)"+                                                   
                              "VALUES(?,?,?,?,?,?,?)";                       
                         
            // Se crea una orden con parametros                  
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(OrdenSQL);
            
            // Obtenemos una matricula para asignar al nuevo alumno            
            // Se establecen los valores que se van a enviar para insertar
            this.bd.preparedstatement.setInt(1, this.id);
            this.bd.preparedstatement.setInt(2, this.area);
            this.bd.preparedstatement.setInt(3, this.subarea);
            this.bd.preparedstatement.setInt(4, this.subsubarea);
            this.bd.preparedstatement.setString(5, this.nombre.toUpperCase());
            this.bd.preparedstatement.setString(6, this.clave.toUpperCase());
            this.bd.preparedstatement.setString(7, this.descripcion.toUpperCase());
            
 
            this.bd.preparedstatement.executeUpdate();
            return(true);            
        }
        catch (SQLException Error)
        {            
            JOptionPane.showMessageDialog(null, Error.getMessage()+"\n"+Error.toString(), "Error Clase Alumno", JOptionPane.ERROR_MESSAGE);            
        }    
        return(false);
    }
    
    public boolean Editar()
    {
        // Integridad referencial para actualizar cambios en el grado, grupo y turno; ya
        // que con base a estos valores se estructura el codigo del grupo y existen tablas
        try
        {
            String OrdenSQL = "UPDATE Conaic_Area SET "+
                              "area=?,subarea=?,subsubarea=?,nombre=?,clave=?,descripcion=? "+
                                              
                              "WHERE id = '"+ this.id+"'";  
           
            // Se crea una orden con parametros                  
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(OrdenSQL);
            
            // Se establecen los valores que se van a enviar para actualizar
            this.bd.preparedstatement.setInt(1, this.area);
            this.bd.preparedstatement.setInt(2, this.subarea);
            this.bd.preparedstatement.setInt(3, this.subsubarea);
            this.bd.preparedstatement.setString(4, this.nombre.toUpperCase());
            this.bd.preparedstatement.setString(5, this.clave.toUpperCase());
            this.bd.preparedstatement.setString(6, this.descripcion);
            this.bd.preparedstatement.executeUpdate();
            return(true);            
        }
        catch (SQLException Error)
        {
           JOptionPane.showMessageDialog(null, Error.getMessage(), "Error3", JOptionPane.ERROR_MESSAGE);
        }    
        return(false);
    }
    
    public boolean Eliminar()
    {
        // Falta integridad referencial para evitar borrar el grupo cuando
        // esta tenga estudiantes capturados.  
        try
        {
            String OrdenSQL = "DELETE FROM Conaic_Area WHERE id = ?";
                                          
            // Se crea una orden con parametros                  
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(OrdenSQL);
            
            // Se establecen los valores de identificaciÃ³n del registro a borrar
           // System.out.println(this.id);
           // System.out.println(this.nombre);
            this.bd.preparedstatement.setInt(1, this.id);
            this.bd.preparedstatement.executeUpdate();
            //Inicializar();
            return(true);            
        }
        catch (SQLException Error)
        {
            JOptionPane.showMessageDialog(null, Error.getMessage(), "Error2", JOptionPane.ERROR_MESSAGE);
        }    
        return(false);
    } 
    public static void main(String args[])
    {
        Conexion bd = new Conexion();
        bd.conectar();
        
        Conaic_Area x = new Conaic_Area(bd);        
       
        x.Buscar("1");
               
        bd.desconectar();
        
    }

    private int getNID(int area,int sarea,int ssarea) {
        String sql;
        
        int idA=0;
        sql = "SELECT id FROM Conaic_Area "+
              "WHERE area="+area+" AND subarea="+sarea+" AND subsubarea="+ssarea;  

        try
        {
            this.bd.ejecutarSQL(sql);
            if (this.bd.resultset.next())
            {
                idA       = this.bd.resultset.getInt("id");
            }    
        }
        catch (SQLException Error)
        {
            JOptionPane.showMessageDialog(null, Error.getMessage(), "Error1", JOptionPane.ERROR_MESSAGE);
        }
        
        return idA;
    }
    public int validarNivel(int idx){
        int ren=0,a=0, sb=0, sbb=0;
        String cv="";
    String sql = "SELECT * FROM Conaic_Area "+
              "WHERE id="+idx;  
    
        try
        {
            this.bd.ejecutarSQL(sql);
            if (this.bd.resultset.next())
            {
                a= this.bd.resultset.getInt("area");
                sb= this.bd.resultset.getInt("subarea");
                sbb= this.bd.resultset.getInt("subsubarea");
                cv= this.bd.resultset.getString("clave");
            }    
        }
        catch (SQLException Error)
        {
            JOptionPane.showMessageDialog(null, Error.getMessage(), "Error1", JOptionPane.ERROR_MESSAGE);
        }
    int nivel;  
        if(a!=0 && sb==0 && sbb==0 && cv.length()==0){
            nivel = 1;  }
        else if(a!=0 && sb!=0 && sbb==0 && cv.length()==0)
            nivel=2;
        else if (a!=0 && sb!=0 && sbb!=0 && cv.length()==0)
            nivel=3;
        else
            nivel=4;
        
        return nivel;
    }
}