package Datos;


/**
 * Clase de la capa de datos
 * 
 * @author (Rubén Benítez) 
 * @version (Ver 1.0)
 */

import java.sql.*;
import javax.swing.JOptionPane;
public class Asignatura_Tema
{
    private int    id;
    private int    tema;
    private int    subtema;
    private int    subsubtema;
    private String nombre;
    private int    Asignatura_id;
    private int    Conaic_Area_id;
    private int    Egel_Area_id;
    private int    Carrera_id;

    protected Conexion bd;         
    
    /**
     * Constructor for objects of class Asignatura
     * @param bd
     */
    public Asignatura_Tema(Conexion bd)
    {  
        this.bd = bd;
        Inicializar();
    } 
    
        
    private void Inicializar()
    {
        id   = 0;    
        tema = 0;
        subtema = 0;
        subsubtema = 0;
        nombre = null;
        Asignatura_id = 0;
        Conaic_Area_id = 0;
        Egel_Area_id = 0;
        Carrera_id = 0;
    }
    
    /**
     * Metodo para establecer en la clase los datos
     * @param id
     * @param tema
     * @param subtema
     * @param subsubtema
     * @param nombre
     * @param Asignatura_id
     * @param Conaic_Area_id
     * @param Egel_Area_id
     */
    public void setAsignatura_Tema(int id,
                              int tema,
                              int subtema,
                              int subsubtema,
                              String nombre,
                              int Asignatura_id,
                              int Conaic_Area_id,
                              int Egel_Area_id)
    {
        this.id   = id;    
        this.tema = tema;
        this.subtema = subtema;
        this.subsubtema = subsubtema;
        this.nombre = nombre;
        this.Asignatura_id = Asignatura_id;
        this.Conaic_Area_id = Conaic_Area_id;
        this.Egel_Area_id = Egel_Area_id;
    }
    
    public boolean Buscar(int id)
    {
        String sql;
        // Aún falta el enlace con los temas de Conaic y Ceneval
        sql = "SELECT Asignatura_Tema.id AS id, Asignatura_Tema.tema AS tema,"+
                     "Asignatura_Tema.subtema AS subtema, Asignatura_Tema.subsubtema AS subsubtema,"+
                     "Asignatura_Tema.nombre AS nombre, Asignatura.id AS Asignatura_id,"+
                     "Asignatura.Carrera_id AS Carrera_id,"+
                     "Conaic_Area.id AS Conaic_Area_id, Egel_Area.id AS Egel_Area_id "+
              "FROM Asignatura_Tema "+
              "INNER JOIN Asignatura ON Asignatura_Tema.Asignatura_id = Asignatura.id "+ 
              "INNER JOIN Conaic_Area ON Asignatura_Tema.Conaic_Area_id = Conaic_Area.id "+ 
              "INNER JOIN Egel_Area ON Asignatura_Tema.Egel_Area_id = Egel_Area.id "+  
              "WHERE Asignatura_Tema.id="+id;  

        try
        {
            this.bd.ejecutarSQL(sql);
            if (this.bd.resultset.next())
            {
                this.id           = this.bd.resultset.getInt("id");
                this.tema         = this.bd.resultset.getInt("tema");
                this.subtema      = this.bd.resultset.getInt("subtema");
                this.subsubtema   = this.bd.resultset.getInt("subsubtema");
                this.nombre       = this.bd.resultset.getString("nombre");
                this.Asignatura_id= this.bd.resultset.getInt("Asignatura_id");
                this.Conaic_Area_id=this.bd.resultset.getInt("Conaic_Area_id");
                this.Egel_Area_id = this.bd.resultset.getInt("Egel_Area_id");
                this.Carrera_id   = this.bd.resultset.getInt("Carrera_id");
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
    
    public int getTema()
    {
        return(this.tema);
    }
    
    public int getSubTema()
    {
        return(this.subtema);
    }
    
    public int getSubSubTema()
    {
        return(this.subsubtema);
    }
    
    public String getNombre()
    {
        return(this.nombre);
    }  
    
    public int getAsignatura_id()
    {
        return(this.Asignatura_id);
    }  
    
    public int getConaicArea_id()
    {
        return(this.Conaic_Area_id);
    }  
   
    public int getEgelArea_id()
    {
        return(this.Egel_Area_id);
    } 
    
     public int getCarrera_id()
    {
        return(this.Carrera_id);
    } 
    
    public ResultSet DataSetAsignaturaTema(int idMateria) throws SQLException
    {
        String sql = "SELECT Asignatura_Tema.id AS ID, Asignatura_Tema.tema AS Tema, Asignatura_Tema.subtema AS Sub," +
                     "Asignatura_Tema.subsubtema AS SubSub,"+
                     "Asignatura_Tema.nombre AS Nombre " +                     
                     "FROM Asignatura_Tema "+
                     "INNER JOIN Asignatura ON Asignatura_Tema.Asignatura_id = Asignatura.id "+                     
                     "WHERE Asignatura_Tema.Asignatura_id = "+idMateria + " "+
                     "ORDER BY Asignatura.nombre, Asignatura_Tema.tema, Asignatura_Tema.subtema, Asignatura_Tema.subsubtema";
        this.bd.ejecutarSQL(sql);        
        return(this.bd.resultset);
    }
    
    
    public boolean Nuevo()
    {
        try
        {
            String orden;                       
            orden = "INSERT INTO Asignatura_Tema(id,tema,subtema,subsubtema,nombre,Asignatura_id,Conaic_Area_id,Egel_Area_id) VALUES(?,?,?,?,?,?,?,?)";
                         
            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden);        
            
            // Se establecen los valores que se van a enviar para insertar
            this.bd.preparedstatement.setInt(1, id);
            this.bd.preparedstatement.setInt(2, tema);
            this.bd.preparedstatement.setInt(3, subtema);
            this.bd.preparedstatement.setInt(4, subsubtema);
            this.bd.preparedstatement.setString(5, nombre.toUpperCase());  
            this.bd.preparedstatement.setInt(6, Asignatura_id);
            this.bd.preparedstatement.setInt(7, Conaic_Area_id);
            this.bd.preparedstatement.setInt(8, Egel_Area_id);
   
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
            String orden = "UPDATE Asignatura_Tema SET tema=?,subtema=?,subsubtema=?,nombre=?,"+
                                                   "Asignatura_id, Conaic_Area_id, Egel_Area_id "+
                           "WHERE id = ?";  
    
            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden); 
            
            // Se establecen los valores que se van a enviar para actualizar
            this.bd.preparedstatement.setInt(1, this.tema);
            this.bd.preparedstatement.setInt(2, this.subtema);
            this.bd.preparedstatement.setInt(3, this.subsubtema);
            this.bd.preparedstatement.setString(4, this.nombre.toUpperCase());
            this.bd.preparedstatement.setInt(5, this.Asignatura_id);
            this.bd.preparedstatement.setInt(6, this.Conaic_Area_id);
            this.bd.preparedstatement.setInt(7, this.Egel_Area_id);            
            this.bd.preparedstatement.setInt(8, this.id);
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
            String orden = "DELETE FROM Asignatura_Tema WHERE id = ?";
            
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
        Asignatura x;        
        x = new Asignatura(bd);
       
        x.Buscar(2);
        System.out.println(x.getNombre());
        
        bd.desconectar();     
    }
}
