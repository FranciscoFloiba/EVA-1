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
 * Este reporte implementa el uso de un cursor implicito para la impresión
 */
public class Municipios_Hector {
       public Municipios_Hector() {       
           Map noparametros = new HashMap();    
           // Conectar a la base de datos
           Datos.Conexion bd = new Datos.Conexion();
           bd.conectar();
           bd.ejecutarSQL("SELECT * FROM Municipio ORDER BY Estado_id, nombre");

           String url = "src/Reportes/Municipios.jasper";
           JasperPrint reporte=null;   
           // 1: Checa esta línea
           JRDataSource jrds = new JRResultSetDataSource(bd.resultset);
           try {
               // 2: y ... también checa esta otra
               reporte = JasperFillManager.fillReport(url, noparametros, jrds);
           } catch (JRException ex) {
               Logger.getLogger(Municipios_Hector.class.getName()).log(Level.SEVERE, null, ex);
           }           
           JasperViewer.viewReport(reporte, false);    
       } 
       
       public static void main(String args[])
       {
           new Municipios_Hector();
       }
}

