/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Seguridad;

import Datos.Conexion;
import Datos.ListaEleccionSeguridad;
import eva.MDIApplication;
import java.awt.Rectangle;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

/**
 *
 * @author RubenPaul
 */
public class Permisos {
    Vector modulos = new Vector();
    static JMenuBar menuBar;
    static boolean isWorking = false;
    
    public static void setPermisos(JMenuBar menuBar)
    {
        Permisos.menuBar = menuBar;
    }
    
    public static boolean isWorking()
    {
        return(isWorking);
    }
    
    public void definirMenu(int idUser)
    {
        Permisos.isWorking = true;
        lookMyModules(idUser);
        for ( int i = 0; i <= menuBar.getMenuCount()-1; i++ )
        {
            JMenu menu = (JMenu)(menuBar.getMenu(i));
            boolean validMenu=false;
            for (int j = 0; j <= menu.getItemCount()-1; j++ )
            {
                // Suprimimos el procesamiento del menu window
                if (menu.getName().equals("window"))
                {
                  continue;
                }
                // Tiene permiso para usar la opción del menú
                if (!canUseModule(menu.getItem(j).getText())) 
                {    
                    menu.getItem(j).setVisible(false); 
                }
                else
                {
                    validMenu = !validMenu ? true: true;
                }
            }
            // Solo es visible el menú si este tiene opciones visibles o es el menú Window
            menu.setVisible(validMenu || (i == menuBar.getMenuCount()-1));
        }   
    }
    
    public void renewMenu()
    {        
        for ( int i = 0; i <= menuBar.getMenuCount()-1; i++ )
        {
            JMenu menu = (JMenu)(menuBar.getMenu(i));
            // Suprimimos el procesamiento del menu window
            if (menu.getName().equals("window"))
            {
              continue;
            }

            for (int j = 0; j <= menu.getItemCount()-1; j++ )
            {                
                if (!menu.getItem(j).getName().equals(null))
                {
                   menu.getItem(j).setVisible(true);
                }
            }
            // Solo es visible el menú si este tiene opciones visibles o es el menú Window
            menu.setVisible(true);
        } 
    }
    
    // Método para forzar repintado de un componente
    private void repintar(javax.swing.JComponent componente) {
        Rectangle area = componente.getBounds();
        area.x = 0;
        area.y = 0;
        componente.paintImmediately(area);
    }
    
    // Revisa los permisos específicos del administrador
    private void lookMyModules(int idUser)
    {
        Conexion bd = MDIApplication.database;
        // Recuperamos los módulos disponibles que esten activos y correspondan al administrador
        String sql;
        sql = "SELECT Modulo.nombre AS nombre, Modulo.etiqueta AS etiqueta "+
              "FROM Modulo "+
              "INNER JOIN Administrador_has_Modulo ON Modulo.id = Administrador_has_Modulo.Modulo_id "+
              "WHERE Administrador_has_Modulo.Administrador_id="+ idUser + " "+
              "AND Modulo.estado = 'A' "+  
              "ORDER BY Modulo.modulo, Modulo.submodulo, Modulo.subsubmodulo";
               
        bd.ejecutarSQL(sql); 
        try {
            if (!modulos.isEmpty())
              modulos.removeAllElements();
            while(bd.resultset.next())
            {
               modulos.add(bd.resultset.getString("nombre"));               
            }
        } catch (SQLException ex) {
            Logger.getLogger(Permisos.class.getName()).log(Level.SEVERE, null, ex);
        }        
        //return(modulos);
    }
    
    private boolean canUseModule(String module)
    {
        for (int i = 0; i < modulos.size(); i++)
        {
            if (modulos.get(i).toString().equals(module))
            {
               return (true);
            }
        }
        return(false);
    }
    
    // Construye un modelo a partir de la barra de menú
    public DefaultComboBoxModel getModelo()
    {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Vector items_modelo = new Vector();
        items_modelo.addAll(lookModules(Permisos.menuBar));
        for (int i = 0; i < items_modelo.size(); i++)
        {
          modelo.addElement(items_modelo.get(i));
        }
        return(modelo);
    }
    
    private Vector lookModules(JMenuBar menuBar)
    {
        Vector items = new Vector();

        for ( int i = 0; i <= menuBar.getMenuCount()-1; i++ )
        {
            JMenu menu = (JMenu)(menuBar.getMenu(i));
            // Suprimimos el procesamiento del menu window
            if (menu.getName().equals("window"))
            {
              continue;
            }
            // Menus || Todos los componentes MenuItem deben tener nombre para que esto funcione
            items.add(new ListaEleccionSeguridad(0, "["+menu.getText()+"] "+ menu.getText(), menu.getName()));
            for (int j = 0; j <= menu.getItemCount()-1; j++ )
            {
                // Submenus || Todos los componentes MenuItem deben tener nombre para que esto funcione
                items.add(new ListaEleccionSeguridad(0, "["+menu.getText()+"] "+ menu.getItem(j).getText(), menu.getItem(j).getName()));
            }
        }

        return(items);
    }
}
