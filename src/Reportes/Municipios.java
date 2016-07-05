/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Reportes;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author Paúl
 */
public class Municipios {
       public Municipios() {       
           Map noparametros = new HashMap();    
           // Conectar a la base de datos
           Datos.Conexion bd = new Datos.Conexion();
           bd.conectar();

           String url = "src/Reportes/Municipios.jasper";
           JasperPrint reporte=null;           
           try {
               reporte = JasperFillManager.fillReport(url, noparametros, bd.conexion);
           } catch (JRException ex) {
               Logger.getLogger(Municipios.class.getName()).log(Level.SEVERE, null, ex);
           }           
           JasperViewer.viewReport(reporte, false);    
       } 
       
       public static void main(String args[])
       {
           new Municipios();
       }
}
