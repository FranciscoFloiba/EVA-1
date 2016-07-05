/**
 * @author (Ruben Benitez)
 * @version (Version 1.0)
 */

package Datos;

import Seguridad.Login;
import java.sql.*;
import javax.swing.*;

public class Conexion
{
    final private String driver = "com.mysql.jdbc.Driver";

    // Conexion con diversas bases de datos
    final private String url = "jdbc:mysql://192.100.162.120/EI";
    private String usuario = "paul";
    private String password = "a027184a5";

    public Connection conexion;
    public Statement statement;
    public ResultSet resultset;
    public PreparedStatement preparedstatement;
    public static int idUser=1;

    // Conexion general a la base de datos
    public boolean conectar()
    {
        Boolean resultado = true;
        
        try
        {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, usuario, password);
        }
        catch (ClassNotFoundException error)
        {
            JOptionPane.showMessageDialog(null, "Driver no localizado: "+error);
            resultado = false;
        }
        catch (SQLException fuente)
        {
            JOptionPane.showMessageDialog(null, "Hubo un error en la conexion a la BD: "+fuente);
            resultado = false;
        }
        return resultado;
    }
    
    // Conexion a la base de datos basada en seguridad
    public boolean conectar(Conexion bd)
    {
        Boolean resultado = false;
        Login dlg = new Login();
        dlg.setVisible(true);

        // Si el usuario abortó la operación de autentificación
        if (dlg.cancelar)
        {
            System.exit(0);
        }

        Datos.Administrador adm = new Datos.Administrador(bd);
        int id = adm.Antentificar(dlg.login.getText(), dlg.password.getText());
        if (id == -1)
        {
            JOptionPane.showMessageDialog(null, "Error de autentificación", "Error", JOptionPane.ERROR_MESSAGE);
            dlg = null;
            adm = null;
            return(false);
        }
        else
        {
            idUser   = adm.getID();
            usuario  = dlg.login.getText();
            password = dlg.login.getText();
            dlg = null;
            adm = null;
            return(true);
        }
    }
  

    public boolean desconectar()
    {
        boolean resultado = true;
        try
        {
            conexion.close();
        }
        catch (SQLException Error)
        {
            JOptionPane.showMessageDialog(null, Error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            resultado = false;
        }
        return resultado;
    }

    public void ejecutarSQL(String sql)
    {
        try
        {
          statement = conexion.createStatement();
          resultset = statement.executeQuery(sql);
        }
        catch (SQLException Error)
        {
            //JOptionPane.showMessageDialog(null, Error.getMessage());
            JOptionPane.showMessageDialog(null, Error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}