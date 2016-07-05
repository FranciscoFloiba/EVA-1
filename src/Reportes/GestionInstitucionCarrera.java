/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import Datos.Conexion;
import Datos.ListaEleccion;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Paul
 */
public class GestionInstitucionCarrera extends javax.swing.JInternalFrame {
    public Conexion enlace;
    
    /**
     * Formulario para ejecutar reportes
     */
    public GestionInstitucionCarrera() {
        initComponents();               
    }
    
    public void cargarCatalogos()
    {
       cargarInstitucion();
       int idx = ((ListaEleccion)institucion.getSelectedItem()).id;
       cargarCarrera(idx);
    }
    
    private void cargarInstitucion()
    {
        // Cargamos los datos de la tabla Institucion a la lista
       Datos.Institucion oInstitucion = new Datos.Institucion(enlace);
       DefaultComboBoxModel datos_institucion = new DefaultComboBoxModel();
       datos_institucion.removeAllElements();
       Vector items_institucion = new Vector();
       items_institucion.addAll(oInstitucion.Lista());
       for (int i = 0; i < items_institucion.size(); i++)
       {
         datos_institucion.addElement(items_institucion.get(i));
       }
       institucion.setModel(datos_institucion); 
    }   
    
    private void cargarCarrera(int idx)
    {
       // Cargamos los datos de la tabla Carreras a la lista
       Datos.Carrera oCarrera = new Datos.Carrera(enlace);
       DefaultComboBoxModel datos_carrera = new DefaultComboBoxModel();
       datos_carrera.removeAllElements();
       Vector items_carrera = new Vector();
       items_carrera.addAll(oCarrera.Lista(idx));
       for (int i = 0; i < items_carrera.size(); i++)
       {
         datos_carrera.addElement(items_carrera.get(i));
       }
       carrera.setModel(datos_carrera);  
    }
       
         
    
    
    public void cargarCampos(int idx)
    {
       /* Datos.Asignatura oAsignatura = new Datos.Asignatura(enlace);
        oAsignatura.Buscar(idx);
        institucion.setSelectedItem(new ListaEleccion(oAsignatura.getInstitucion_id(),""));
        carrera.setSelectedItem(new ListaEleccion(oAsignatura.getCarrera_id(),""));        
        academia.setSelectedItem(new ListaEleccion(oAsignatura.getEgel_Area_id(),""));
        id.setText(Integer.toString(oAsignatura.getID()));        
        nombre.setText(oAsignatura.getNombre());*/
    } 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     * 
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        boton_guardar = new javax.swing.JButton();
        boton_cancelar = new javax.swing.JButton();
        lcarrera = new javax.swing.JLabel();
        carrera = new javax.swing.JComboBox();
        lnstitucion = new javax.swing.JLabel();
        institucion = new javax.swing.JComboBox();

        setClosable(true);
        setResizable(true);
        setTitle("Reporte");
        setMinimumSize(new java.awt.Dimension(580, 200));
        setName("gestioninstitucioncarrera"); // NOI18N
        setPreferredSize(new java.awt.Dimension(580, 200));
        getContentPane().setLayout(null);

        boton_guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/GuardarTodo.png"))); // NOI18N
        boton_guardar.setMnemonic('G');
        boton_guardar.setText("OK");
        boton_guardar.setToolTipText("Guardar registro");
        boton_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_guardarActionPerformed(evt);
            }
        });
        getContentPane().add(boton_guardar);
        boton_guardar.setBounds(270, 190, 130, 40);

        boton_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cancelar.png"))); // NOI18N
        boton_cancelar.setMnemonic('C');
        boton_cancelar.setText("Cancelar");
        boton_cancelar.setToolTipText("Cancelar actualización");
        boton_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_cancelarActionPerformed(evt);
            }
        });
        getContentPane().add(boton_cancelar);
        boton_cancelar.setBounds(410, 190, 130, 40);

        lcarrera.setText("Carrera");
        getContentPane().add(lcarrera);
        lcarrera.setBounds(40, 100, 80, 14);

        carrera.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                carreraItemStateChanged(evt);
            }
        });
        getContentPane().add(carrera);
        carrera.setBounds(40, 130, 500, 28);

        lnstitucion.setText("Institución");
        getContentPane().add(lnstitucion);
        lnstitucion.setBounds(40, 20, 100, 14);

        institucion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                institucionItemStateChanged(evt);
            }
        });
        getContentPane().add(institucion);
        institucion.setBounds(40, 50, 500, 28);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-602)/2, (screenSize.height-306)/2, 602, 306);
    }// </editor-fold>//GEN-END:initComponents

    private void boton_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_guardarActionPerformed
      
    }//GEN-LAST:event_boton_guardarActionPerformed

    private void boton_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_cancelarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_boton_cancelarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    
    }//GEN-LAST:event_formWindowClosing

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
       
    }//GEN-LAST:event_formWindowActivated

    private void institucionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_institucionItemStateChanged
        // Se cambian las carreras de acuerdo a la institución
        Datos.Carrera oCarrera = new Datos.Carrera(enlace);
        DefaultComboBoxModel datos_carrera = new DefaultComboBoxModel();
        datos_carrera.removeAllElements();
        Vector items_carrera = new Vector();
        items_carrera.addAll(oCarrera.Lista(((ListaEleccion)institucion.getSelectedItem()).id));
        for (int i = 0; i < items_carrera.size(); i++)
        {
          datos_carrera.addElement(items_carrera.get(i));
        }
        carrera.setModel(datos_carrera);  
    }//GEN-LAST:event_institucionItemStateChanged

    private void carreraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_carreraItemStateChanged
        
    }//GEN-LAST:event_carreraItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionInstitucionCarrera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionInstitucionCarrera().setVisible(true);                               
            }
        });
    }
       
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_cancelar;
    private javax.swing.JButton boton_guardar;
    private javax.swing.JComboBox carrera;
    private javax.swing.JComboBox institucion;
    private javax.swing.JLabel lcarrera;
    private javax.swing.JLabel lnstitucion;
    // End of variables declaration//GEN-END:variables
}