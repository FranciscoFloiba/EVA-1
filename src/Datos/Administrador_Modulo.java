package Datos;


/**
 * Clase de la capa de datos
 * 
 * @author (Rubén Benítez) 
 * @version (Ver 1.0)
 */

import Seguridad.Permisos;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Administrador_Modulo
{
    private int    Administrador_id; 
    private int    Modulo_id;
    protected Conexion bd;         
    
    /**
     * Constructor for objects of class Administrador_Modulo
     * @param bd
     */
    public Administrador_Modulo(Conexion bd)
    {  
        this.bd = bd;
        Inicializar();
    } 
    
        
    private void Inicializar()
    {
        Administrador_id = 0;
        Modulo_id = 0;
    }
    
    /**
     * Metodo para establecer en la clase los datos
     * @param Administrador_id
     * @param Modulo_id
     */
    public void setAdministrador_Modulo(int Administrador_id,
                                        int Modulo_id)
    {
        this.Administrador_id  = Administrador_id;
        this.Modulo_id         = Modulo_id;
    }
    
    public boolean Buscar(int Administrador_id, int Modulo_id)
    {
        String sql;
        sql = "SELECT * "+
              "FROM Administrador_has_Modulo "+
              "WHERE Administrador_id="+Administrador_id + " AND Modulo_id="+Modulo_id;  

        try
        {
            this.bd.ejecutarSQL(sql);
            if (this.bd.resultset.next())
            {
                this.Administrador_id  = this.bd.resultset.getInt("Administrador_id");
                this.Modulo_id         = this.bd.resultset.getInt("Modulo_id");
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
    
    public int getAdministradorID()
    {
        return(this.Administrador_id);
    }
    
    public int getModuloID()
    {
        return(this.Modulo_id);
    }    
    
    
    public DefaultTableModel DataSetAdministradorModulo(DefaultTableModel modelo, int idUser) throws SQLException
    {        
        boolean canUse=false;
        Datos.Modulo oModulo = new Datos.Modulo(bd);
        ResultSet programas = oModulo.DataSetModulo();
        // Llenamos un Vector con los datos que recuperamos de la BD. Es más tardado
        // es procedimiento, pero es más flexible para permitirle al usuario visualizar,
        // asignar y retirar permisos de uso sobre los módulos.
        modelo.setNumRows(0);
        while (programas.next()) {
             if (Buscar(idUser, programas.getInt("ID")))
             {
                canUse = true;
             }
             else
             {
                 canUse = false;
             }
             modelo.addRow( new Object[] {programas.getInt("ID"), 
                                          programas.getInt("Modulo"), 
                                          programas.getInt("Sub"), 
                                          programas.getInt("SubSub"), 
                                          programas.getString("Nombre"),
                                          programas.getString("Etiqueta"),
                                          canUse});               
        }
        // Destruímos el objeto
        oModulo = null;
        return(modelo);       
    } 
    
    public boolean Guardar(DefaultTableModel modelo, int idUser) throws SQLException
    {
        // Con base en el modelo vamos a insertar, eliminar o actualizar los permisos
        int registros = modelo.getRowCount(); 
        int contador  = 0;
        int id_modulo = 0;
        int id_admin  = 0;
        String etiqueta = null;
        boolean can_use = false, can_save = false;
        // Establecemos la conexión para escritura diferida
        bd.conexion.setAutoCommit(false);
        
        while (contador < registros)
        {
            // recuperamos id del módulo desde el modelo de datos
            id_modulo = (int)modelo.getValueAt(contador, 0);
            id_admin = idUser;  
            etiqueta  = (String)modelo.getValueAt(contador, 5);
            can_use   = (boolean)modelo.getValueAt(contador, 6);
                // Buscamos si el administrador ya tiene registrado el módulo
            if (Buscar(id_admin, id_modulo))
            {
                // Ya tiene el permiso, le es negado ahora y no es etiqueta
                if (!can_use && etiqueta.equals("No"))
                {
                    can_save = Eliminar(id_admin, id_modulo);
                    if (!can_save)
                    {
                        bd.conexion.rollback();
                        bd.conexion.setAutoCommit(true);
                        return (can_save);
                    }
                }
                else
                {
                   // No hacemos nada si ya tiene el permiso y le es concedido nuevamente
                }                
            } 
            else 
            {
                // Si se trata de cualquier etiqueta se registra si no la tiene
                if (etiqueta.equals("Si"))
                {
                   can_save = Nuevo(id_admin, id_modulo);
                   if (!can_save)
                   {
                      bd.conexion.rollback();
                      bd.conexion.setAutoCommit(true);
                      return (can_save);
                   }
                }
                   
                // No fue encontrado el permiso para el administrador y se le concedio
                if (can_use && etiqueta.equals("No"))
                {
                    can_save = Nuevo(id_admin, id_modulo);
                    if (!can_save)
                    {
                        bd.conexion.rollback();
                        bd.conexion.setAutoCommit(true);
                        return (can_save);
                    }
                }
            }
            contador++;
        } // del while
        bd.conexion.commit();
        // Se pone estado en escritura directa a la base de datos
        bd.conexion.setAutoCommit(true);
        return (can_save);
    }  
    
    public boolean Nuevo(int id_admin, int id_modulo)
    {
        try {
            String orden;                       
            orden = "INSERT INTO Administrador_has_Modulo(Administrador_id, Modulo_id) VALUES(?,?)";
                            
            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden);        
               
            // Se establecen los valores que se van a enviar para insertar
            this.bd.preparedstatement.setInt(1, id_admin);
            this.bd.preparedstatement.setInt(2, id_modulo);  
            this.bd.preparedstatement.executeUpdate();        
            return(true);
        } catch (SQLException ex) {
            Logger.getLogger(Administrador_Modulo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    public boolean Eliminar(int id_admin, int id_modulo)
    {
        try
        {
            String orden = "DELETE FROM Administrador_has_Modulo "+
                           "WHERE Administrador_id = ? AND Modulo_id = ?";
            
            // Se crea una orden con parametros     
            this.bd.preparedstatement = this.bd.conexion.prepareStatement(orden);                                       
                        
            // Se establecen los valores de identificación del registro a borrar
            this.bd.preparedstatement.setInt(1, id_admin);
            this.bd.preparedstatement.setInt(2, id_modulo);
            this.bd.preparedstatement.executeUpdate();
            return(true);            
        }
        catch (SQLException Error)
        {
            JOptionPane.showMessageDialog(null, Error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }    
        return(false);
    }
               
         
    /*public Vector Lista()
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
    } */
   
    
    public static void main(String args[])
    {
        Conexion bd = new Conexion();
        bd.conectar();
        Administrador_Modulo x;        
        x = new Administrador_Modulo(bd);
       
        x.Buscar(1,1);
        System.out.println(x.getAdministradorID());
        
        bd.desconectar();     
    }
}



