/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Catalogos;


import Datos.Conexion;
import Datos.ListaConaic;
//import Datos.ListaEgels;
import java.awt.Component;
import java.awt.Container;
import java.util.Vector;
import javax.swing.*;

/**
 *
 * @author Paul
 */
public class Conaic_Area extends javax.swing.JDialog {
    private String accion;
    private int entrada;
    public Conexion enlace;
    public Conaic_Areas padre;
    public int id;
    private int idx;
    public int nivel;

   private int id_area;
   private int id_subarea;
   private int id_subsubarea;

    /**
     * Creates new form Conaic_Area
     */
    public Conaic_Area() {
            initComponents(); 
            inicializar();          
    }
   
    private void inicializar(){
        nivel=1;  
        panelarea.setEnabled(false);
        panelsubarea.setEnabled(false);
        panelsubsubarea.setEnabled(false);
        panelclave.setEnabled(false);
        tadescripcion.setEnabled(false);
    }
   
    public void InicializarNivel(int level)
    {
        switch(level)
        {
            case 1: ftfarea.setText("") ; tfnombre.setText(""); tadescripcion.setText("");break;
            case 2: ftfsubsubarea.setText("") ; tfnombre.setText(""); tadescripcion.setText("");break;
            case 3: ftfsubsubarea.setText("") ; tfclave.setText("") ; tfnombre.setText(""); tadescripcion.setText("");break;
            case 4: tfclave.setText(""); tfnombre.setText(""); tadescripcion.setText(""); break;
        }
    }
     private void enfocar(boolean a, boolean sb, boolean ssb,boolean cve) {
        barea.getModel().setSelected(a);
        
         EstadoPanel(panelarea,a);
         EstadoPanel(panelsubarea, sb);
         EstadoPanel(panelsubsubarea, ssb);
         EstadoPanel(panelclave,cve);
        
        if (nivel==1 ){
            // Solo se edita el área
            ftfarea.setVisible(a);
            cbarea.setVisible(!a);    // Se oculta el combo de area
            cbsubarea.setVisible(sb); // Se oculta el combo de subarea
            ftfsubarea.setVisible(!sb);
            cbsubsubarea.setVisible(ssb);
            tfclave.setVisible(!cve);
                       
            // Valores predeterminados en caso de registro nuevo
            if (accion.equals("NUEVO")){
              ftfsubsubarea.setText("");
              ftfsubarea.setText("");
              tfclave.setText("");  
            }
            
            if (accion.equals("ELIMINAR") || accion.equals("EDITAR")){
              barea.setEnabled(false);
              bsubarea.setEnabled(false);
              bsubsubarea.setEnabled(false);
              bclave.setEnabled(false);
            }
                       
            // Nombre y descripción editables para NUEVO y EDICION
            if (accion.equals("NUEVO") || accion.equals("EDITAR")){               
               tfnombre.setEnabled(true);
               tadescripcion.setEnabled(false);
            }
            else{
               // Para el caso de ELIMINACION se desactiva siempre 
               ftfarea.setEnabled(false);   
               ftfsubarea.setEnabled(false);
               ftfsubsubarea.setEnabled(false);
               tfnombre.setEnabled(false);
               tfclave.setEnabled(false); 
            }   
            ftfarea.requestFocus();
        }          
        bsubarea.getModel().setSelected(sb);
        
        if(nivel==2 ){
            
            // Solo se edita el área
            ftfarea.setVisible(!a);       // Se oculta el text de area
            cbarea.setVisible(a);    
            ftfsubsubarea.setVisible(sb);
            cbsubarea.setVisible(!sb); // Se oculta el combo de subarea   
            cbsubsubarea.setVisible(ssb);
            // Valores predeterminados en caso de registro nuevo
            if (accion.equals("NUEVO")){
               cargarAreas();
               ftfsubsubarea.setText("");
               tfclave.setText("");   
            }
             if (accion.equals("ELIMINAR") || accion.equals("EDITAR")){
              barea.setEnabled(false);
              bsubarea.setEnabled(false);
              bsubsubarea.setEnabled(false);
              bclave.setEnabled(false);
            }
            
            // Nombre y descripción editables para NUEVO y EDICION
            if (accion.equals("NUEVO") || accion.equals("EDITAR")){
               tfnombre.setEnabled(true);
               tadescripcion.setEnabled(false); 
            }
            else
            {
               // Para el caso de ELIMINACION se desactivan siempre                 
              ftfarea.setVisible(!a);       // Se oculta el text de area
              cbarea.setVisible(a); 
              ftfsubarea.setVisible(a);       
              cbsubarea.setVisible(!a);
              
              cbarea.setEnabled(false);
               ftfsubarea.setEnabled(false);
               ftfsubsubarea.setEnabled(false);
               tfnombre.setEnabled(false);
               tfclave.setEnabled(false); 
            }
            ftfsubarea.requestFocus();
        }        
        
        bsubsubarea.getModel().setSelected(ssb);        
     
        if(nivel==3 ){
            
            // Solo se edita el área
            ftfarea.setVisible(!a);      // Se oculta el text de area 
            cbarea.setVisible(a);   
            cbsubarea.setVisible(sb);
            ftfsubarea.setVisible(!sb);
            ftfsubsubarea.setVisible(ssb);  // Se oculta el text del subarea
            cbsubsubarea.setVisible(!ssb);
                  
            
            // Valores predeterminados en caso de registro nuevo
            if (accion.equals("NUEVO")){
          
                cargarAreas();
                // Recuperamoe el área activo en el combo y posicionamos la subareas
                id_area = ((ListaConaic)cbarea.getSelectedItem()).getArea(); 
                cargarSubAreas(id_area);
                ftfsubsubarea.setText("");
            }
            if (accion.equals("ELIMINAR") || accion.equals("EDITAR")){
              barea.setEnabled(false);
              bsubarea.setEnabled(false);
              bsubsubarea.setEnabled(false);
              bclave.setEnabled(false);
            }
            // Nombre y descripción editables en NUEVO y EDICION
            if (accion.equals("NUEVO") || accion.equals("EDITAR")){
               tfclave.setEditable(cve);
               tfnombre.setEnabled(true);
               tadescripcion.setEnabled(false);    
            }
            // Para el caso de eliminación se desactivan siempre
            else{       
               cbarea.setEnabled(false);
               cbsubarea.setEnabled(false);
               ftfsubsubarea.setEnabled(false);
               tfnombre.setEnabled(false);
               tfclave.setEnabled(false); 
            } 
            ftfsubsubarea.requestFocus();
        }
        
        if(nivel==4 ){
            
            // Solo se edita el área
            ftfarea.setVisible(!a);      // Se oculta el text de area 
            cbarea.setVisible(a);   
            cbsubarea.setVisible(sb);
            ftfsubarea.setVisible(!sb);
            ftfsubsubarea.setVisible(!ssb);  // Se oculta el text del subarea
            cbsubsubarea.setVisible(ssb);
            tfclave.setVisible(cve);
                  
            
            // Valores predeterminados en caso de registro nuevo
            if (accion.equals("NUEVO")){
          
                cargarAreas();
                // Recuperamoe el área activo en el combo y posicionamos la subareas
                id_area = ((ListaConaic)cbarea.getSelectedItem()).getArea(); 
                cargarSubAreas(id_area); 
                id_subarea=((ListaConaic)cbsubarea.getSelectedItem()).getSubArea(); 
                cargarSubSubAreas(id_area,id_subarea);
            }
            if (accion.equals("ELIMINAR") || accion.equals("EDITAR")){
              barea.setEnabled(false);
              bsubarea.setEnabled(false);
              bsubsubarea.setEnabled(false);
              bclave.setEnabled(false);
            }
            // Nombre y descripción editables en NUEVO y EDICION
            if (accion.equals("NUEVO") || accion.equals("EDITAR")){
               tfclave.setEditable(cve);
               tfnombre.setEnabled(true);
               tadescripcion.setEnabled(true);    
            }
            // Para el caso de eliminación se desactivan siempre
            else{     
               cbarea.setEnabled(false);
               cbsubarea.setEnabled(false);
               cbsubsubarea.setEnabled(false);
               tfnombre.setEnabled(false);
               tfclave.setEnabled(false); 
            }   
            tfclave.requestFocus();
        }
    }
     
     private void EstadoPanel(Container c, boolean estado)
   {
       Component[] components = c.getComponents(); 
       for(int i=0; i<components.length; i++) { 
          components[i].setEnabled(false); 
          if(components[i] instanceof Container) { 
             ((Container)components[i]).setEnabled(estado); 
          } 
       }   
       c.setEnabled(estado);
   }
 
    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(new Runnable(){
            public void run(){
                new Conaic_Area().setVisible(true);
            }
                });
        
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tadescripcion = new javax.swing.JTextArea();
        panelarea = new javax.swing.JPanel();
        ftfarea = new javax.swing.JTextField();
        cbarea = new javax.swing.JComboBox<String>();
        panelsubarea = new javax.swing.JPanel();
        ftfsubarea = new javax.swing.JTextField();
        cbsubarea = new javax.swing.JComboBox<String>();
        panelsubsubarea = new javax.swing.JPanel();
        ftfsubsubarea = new javax.swing.JTextField();
        cbsubsubarea = new javax.swing.JComboBox<String>();
        panelclave = new javax.swing.JPanel();
        tfclave = new javax.swing.JTextField();
        tfnombre = new javax.swing.JTextField();
        lbnombre = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        barea = new javax.swing.JButton();
        bsubarea = new javax.swing.JButton();
        bsubsubarea = new javax.swing.JButton();
        bclave = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(3, 0), new java.awt.Dimension(3, 0), new java.awt.Dimension(3, 32767));
        baceptar = new javax.swing.JButton();
        bcancelar = new javax.swing.JButton();
        ldescipcion1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tadescripcion.setColumns(20);
        tadescripcion.setLineWrap(true);
        tadescripcion.setRows(5);
        tadescripcion.setWrapStyleWord(true);
        jScrollPane2.setViewportView(tadescripcion);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 330, 520, 140));

        panelarea.setBorder(javax.swing.BorderFactory.createTitledBorder("Area"));
        panelarea.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelarea.add(ftfarea, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 21, 70, 28));

        cbarea.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbareaItemStateChanged(evt);
            }
        });
        panelarea.add(cbarea, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 21, 510, 28));

        jPanel2.add(panelarea, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 530, 60));

        panelsubarea.setBorder(javax.swing.BorderFactory.createTitledBorder("SubArea"));
        panelsubarea.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelsubarea.add(ftfsubarea, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 21, 70, 28));

        cbsubarea.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbsubareaItemStateChanged(evt);
            }
        });
        panelsubarea.add(cbsubarea, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 21, 510, 28));

        jPanel2.add(panelsubarea, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 530, 60));

        panelsubsubarea.setBorder(javax.swing.BorderFactory.createTitledBorder("SubsubArea"));
        panelsubsubarea.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelsubsubarea.add(ftfsubsubarea, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 21, 70, 28));

        cbsubsubarea.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbsubsubareaItemStateChanged(evt);
            }
        });
        cbsubsubarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbsubsubareaActionPerformed(evt);
            }
        });
        panelsubsubarea.add(cbsubsubarea, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 21, 510, 28));

        jPanel2.add(panelsubsubarea, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 530, 60));

        panelclave.setBorder(javax.swing.BorderFactory.createTitledBorder("Clave"));
        panelclave.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelclave.add(tfclave, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 21, 70, 28));

        jPanel2.add(panelclave, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 530, 60));
        jPanel2.add(tfnombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, 520, 28));

        lbnombre.setText("Nombre");
        jPanel2.add(lbnombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, -1, -1));

        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);

        barea.setBackground(new java.awt.Color(102, 102, 102));
        barea.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        barea.setText("Area");
        barea.setAlignmentX(0.5F);
        barea.setFocusable(false);
        barea.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        barea.setMaximumSize(new java.awt.Dimension(100, 60));
        barea.setMinimumSize(new java.awt.Dimension(90, 25));
        barea.setOpaque(false);
        barea.setPreferredSize(new java.awt.Dimension(100, 60));
        barea.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bareaActionPerformed(evt);
            }
        });
        jToolBar1.add(barea);

        bsubarea.setBackground(new java.awt.Color(102, 102, 102));
        bsubarea.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        bsubarea.setText("Subarea");
        bsubarea.setAlignmentX(0.5F);
        bsubarea.setFocusable(false);
        bsubarea.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bsubarea.setMaximumSize(new java.awt.Dimension(100, 60));
        bsubarea.setMinimumSize(new java.awt.Dimension(90, 25));
        bsubarea.setOpaque(false);
        bsubarea.setPreferredSize(new java.awt.Dimension(100, 60));
        bsubarea.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bsubarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsubareaActionPerformed(evt);
            }
        });
        jToolBar1.add(bsubarea);

        bsubsubarea.setBackground(new java.awt.Color(102, 102, 102));
        bsubsubarea.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        bsubsubarea.setText("Subsubarea");
        bsubsubarea.setAlignmentX(0.5F);
        bsubsubarea.setFocusable(false);
        bsubsubarea.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bsubsubarea.setMaximumSize(new java.awt.Dimension(100, 60));
        bsubsubarea.setMinimumSize(new java.awt.Dimension(90, 25));
        bsubsubarea.setOpaque(false);
        bsubsubarea.setPreferredSize(new java.awt.Dimension(100, 60));
        bsubsubarea.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bsubsubarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsubsubareaActionPerformed(evt);
            }
        });
        jToolBar1.add(bsubsubarea);

        bclave.setBackground(new java.awt.Color(102, 102, 102));
        bclave.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        bclave.setText("Clave");
        bclave.setAlignmentX(0.5F);
        bclave.setFocusable(false);
        bclave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bclave.setMaximumSize(new java.awt.Dimension(100, 60));
        bclave.setMinimumSize(new java.awt.Dimension(90, 25));
        bclave.setOpaque(false);
        bclave.setPreferredSize(new java.awt.Dimension(100, 60));
        bclave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bclave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bclaveActionPerformed(evt);
            }
        });
        jToolBar1.add(bclave);
        jToolBar1.add(filler1);

        jPanel2.add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 98, 555));

        baceptar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        baceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/GuardarTodo.png"))); // NOI18N
        baceptar.setText("Aceptar");
        baceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baceptarActionPerformed(evt);
            }
        });
        jPanel2.add(baceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 480, 130, 40));

        bcancelar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        bcancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cancelar.png"))); // NOI18N
        bcancelar.setText("Cancelar");
        bcancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcancelarActionPerformed(evt);
            }
        });
        jPanel2.add(bcancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 480, 130, 40));

        ldescipcion1.setText("Descripcion");
        jPanel2.add(ldescipcion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 310, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 530));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void baceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baceptarActionPerformed
        // TODO add your handling code here:
        Datos.Conaic_Area oArea = new Datos.Conaic_Area(enlace);
        if (accion.equals("ELIMINAR")){
         // Otorgamos datos básicos en caso de ELIMINAR (solo se ocupa idx)
         oArea.setArea(idx, tfnombre.getText());  
      }
        if (this.accion.equals("EDITAR") || this.accion.equals("NUEVO"))
        {
            if (tfnombre.getText().length() == 0)
            {
                JOptionPane.showMessageDialog(null, "No se capturó el nombre", "Información", JOptionPane.INFORMATION_MESSAGE);
                tfnombre.requestFocus();
                return;
            }

            switch (nivel) {
                case 1:
                    if (ftfarea.getText().length()==0)
                    {
                        JOptionPane.showMessageDialog(null, "No se capturó el area", "Información", JOptionPane.INFORMATION_MESSAGE);
                        tfnombre.requestFocus();
                        return;
                    }
                    //////////////////////////////////////////////////////////////////////////
                    oArea.setArea(
                            idx,
                            Integer.parseInt(ftfarea.getText()),
                            (ftfsubarea.getText().isEmpty())?0:Integer.parseInt(ftfsubarea.getText()), 
                            (ftfsubsubarea.getText().isEmpty())?0:Integer.parseInt(ftfsubsubarea.getText()),
                            (tfnombre.getText().isEmpty())?"":tfnombre.getText(),
                            (tfclave.getText().isEmpty())?"":tfclave.getText(),
                            (tadescripcion.getText().isEmpty())?"":tadescripcion.getText()
                            );
                    InicializarNivel(1); 
                    ftfarea.requestFocus();
                    break;
                    ///////////////////////////////////////////////////////////////////////////////
                case 2:
                    if (ftfsubarea.getText().length()==0)
                    {
                        JOptionPane.showMessageDialog(null, "No se capturó subarea", "Información", JOptionPane.INFORMATION_MESSAGE);
                        tfnombre.requestFocus();
                        return;
                    }
                    if (cbarea.getSelectedIndex()==-1)
                    {
                        JOptionPane.showMessageDialog(this, "No se seleccionó un area",
                                "Mensaje", JOptionPane.WARNING_MESSAGE);
                       tfnombre.requestFocus();
                       return;
                    }
                    System.out.println(cbarea.getSelectedIndex());
                    oArea.setArea(
                            idx,
                           ((ListaConaic)cbarea.getSelectedItem()).getArea(),
                            (ftfsubarea.getText().isEmpty())?0:Integer.parseInt(ftfsubarea.getText()), 
                            (ftfsubsubarea.getText().isEmpty())?0:Integer.parseInt(ftfsubsubarea.getText()),
                            (tfnombre.getText().isEmpty())?"":tfnombre.getText(),
                            (tfclave.getText().isEmpty())?"":tfclave.getText(),
                            (tadescripcion.getText().isEmpty())?"":tadescripcion.getText()
                            );
                    
                    //areax=oEgel_Area.getArea(((ListaEgels)cbarea.getSelectedItem()).getId());
                    
                    InicializarNivel(2); ftfsubarea.requestFocus();
                    break;

                case 3:
                    if (ftfsubsubarea.getText().length()==0)
                    {
                        JOptionPane.showMessageDialog(null, "No se capturó subsubarea", "Información", JOptionPane.INFORMATION_MESSAGE);
                        tfnombre.requestFocus();
                        return;
                    }
                    oArea.setArea(
                            idx,
                           ((ListaConaic)cbarea.getSelectedItem()).getArea(),
                            ((ListaConaic)cbsubarea.getSelectedItem()).getSubArea(), 
                            (ftfsubsubarea.getText().isEmpty())?0:Integer.parseInt(ftfsubsubarea.getText()),
                            (tfnombre.getText().isEmpty())?"":tfnombre.getText(),
                            (tfclave.getText().isEmpty())?"":tfclave.getText(),
                            (tadescripcion.getText().isEmpty())?"":tadescripcion.getText()
                            );
                    
                    //subareax=oEgel_Area.getSubarea(((ListaEgels)cbsubarea.getSelectedItem()).getId());
                    //areax=oEgel_Area.getArea(((ListaEgels)cbarea.getSelectedItem()).getId());
                   
                    InicializarNivel(3); ftfsubsubarea.requestFocus();
                    break;
                case 4:
                    if (tfclave.getText().length()==0)
                    {
                        JOptionPane.showMessageDialog(null, "No se capturó Clave", "Información", JOptionPane.INFORMATION_MESSAGE);
                        tfnombre.requestFocus();
                        return;
                    }
                    oArea.setArea(
                            idx,
                           ((ListaConaic)cbarea.getSelectedItem()).getArea(),
                            ((ListaConaic)cbsubarea.getSelectedItem()).getSubArea(), 
                            ((ListaConaic)cbsubsubarea.getSelectedItem()).getSubSubArea(),
                            (tfnombre.getText().isEmpty())?"":tfnombre.getText(),
                            (tfclave.getText().isEmpty())?"":tfclave.getText(),
                            (tadescripcion.getText().isEmpty())?"":tadescripcion.getText()
                            );
                    
                    //subareax=oEgel_Area.getSubarea(((ListaEgels)cbsubarea.getSelectedItem()).getId());
                    //areax=oEgel_Area.getArea(((ListaEgels)cbarea.getSelectedItem()).getId());
                   
                    InicializarNivel(4); ftfsubsubarea.requestFocus();
                default:
                    break;
            }
        }
        if (this.accion.equals("EDITAR"))
        {
            // Se manda escribir a la base de datos
            if (!oArea.Editar())
            {
                return;
            }
            this.dispose();
        } // Fin editar

        if (this.accion.equals("NUEVO"))
        {
            // Se manda escribir a la base de datos
            if (!oArea.Nuevo())
            {
                return;
            }
        }
        if (this.accion.equals("ELIMINAR"))
        {
            // Se se solicita confirmación al usuario
            if (JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el registro?", "Confirmación", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
                if (!oArea.Eliminar())
                {
                    return;
                }
            }
            this.dispose();
        }
      
      padre.cargarTabla(); 
    }//GEN-LAST:event_baceptarActionPerformed

    private void bclaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bclaveActionPerformed
        // TODO add your handling code here:
        nivel=4;
        //entrada=4;
        enfocar(true,true,true,true);
    }//GEN-LAST:event_bclaveActionPerformed

    private void bsubsubareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsubsubareaActionPerformed
        // TODO add your handling code here:
        nivel=3;
        //entrada=3;
        enfocar(true,true,true,false);
    }//GEN-LAST:event_bsubsubareaActionPerformed

    private void bsubareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsubareaActionPerformed
        // TODO add your handling code here:
        nivel=2;
        //entrada=2;
        enfocar(true,true,false,false);
        
    }//GEN-LAST:event_bsubareaActionPerformed

    private void bcancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_bcancelarActionPerformed

    private void cbareaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbareaItemStateChanged
        // Recuperamoe el área activo en el combo
        id_area = ((ListaConaic)cbarea.getSelectedItem()).getArea();
       cargarSubAreas(id_area);
    }//GEN-LAST:event_cbareaItemStateChanged

    private void bareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bareaActionPerformed
        // TODO add your handling code here:
        nivel=1;
        //entrada=1;
        enfocar(true,false,false,false);

    }//GEN-LAST:event_bareaActionPerformed

    private void cbsubareaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbsubareaItemStateChanged
        // TODO add your handling code here:
        id_area = ((ListaConaic)cbarea.getSelectedItem()).getArea(); 
        id_subarea = ((ListaConaic)cbsubarea.getSelectedItem()).getSubArea();
        cargarSubSubAreas(id_area,id_subarea);        
    }//GEN-LAST:event_cbsubareaItemStateChanged

    private void cbsubsubareaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbsubsubareaItemStateChanged
        // TODO add your handling code here:
       /* id_area = ((ListaConaic)cbarea.getSelectedItem()).getArea(); 
        id_subarea = ((ListaConaic)cbsubarea.getSelectedItem()).getSubArea();
        cargarSubSubAreas(id_area,id_subarea); */
    }//GEN-LAST:event_cbsubsubareaItemStateChanged

    private void cbsubsubareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbsubsubareaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbsubsubareaActionPerformed

    
    public void cargarAreas(){
        Datos.Conaic_Area oArea= new Datos.Conaic_Area(enlace);

        DefaultComboBoxModel datos = new DefaultComboBoxModel();
        datos.removeAllElements();
        Vector items = new Vector();
        items.addAll(oArea.Lista(1,0,0,0));
        for (int i = 0; i < items.size(); i++)
        {
            datos.addElement(items.get(i));
        }
        cbarea.setModel(datos); 
    }
    
    public void cargarSubAreas(int id_area) {
        Datos.Conaic_Area oArea = new Datos.Conaic_Area(enlace);
        
        DefaultComboBoxModel datos = new DefaultComboBoxModel();
        datos.removeAllElements();
        Vector items = new Vector();
        items.addAll(oArea.Lista(2,id_area,0,0));
        
        for (int i = 0; i < items.size(); i++)
        {
            datos.addElement(items.get(i));
        }
        cbsubarea.setModel(datos);
        id_subarea = ((ListaConaic)cbsubarea.getSelectedItem()).getSubArea();
        cargarSubSubAreas(id_area,id_subarea);
    }    
    
    public void cargarSubSubAreas(int id_area, int id_subarea) {
        Datos.Conaic_Area oArea = new Datos.Conaic_Area(enlace);
        
        DefaultComboBoxModel datos = new DefaultComboBoxModel();
        datos.removeAllElements();
        Vector items = new Vector();
        items.addAll(oArea.Lista(3,id_area,id_subarea,0));
        
        for (int i = 0; i < items.size(); i++)
        {
            datos.addElement(items.get(i));
        }
        cbsubsubarea.setModel(datos);
    } 
  
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton baceptar;
    private javax.swing.JButton barea;
    private javax.swing.JButton bcancelar;
    private javax.swing.JButton bclave;
    private javax.swing.JButton bsubarea;
    private javax.swing.JButton bsubsubarea;
    private javax.swing.JComboBox<String> cbarea;
    private javax.swing.JComboBox<String> cbsubarea;
    private javax.swing.JComboBox<String> cbsubsubarea;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JTextField ftfarea;
    private javax.swing.JTextField ftfsubarea;
    private javax.swing.JTextField ftfsubsubarea;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lbnombre;
    private javax.swing.JLabel ldescipcion1;
    private javax.swing.JPanel panelarea;
    private javax.swing.JPanel panelclave;
    private javax.swing.JPanel panelsubarea;
    private javax.swing.JPanel panelsubsubarea;
    private javax.swing.JTextArea tadescripcion;
    private javax.swing.JTextField tfclave;
    private javax.swing.JTextField tfnombre;
    // End of variables declaration//GEN-END:variables

    public void configurarPara(String accion)
    {

        this.accion = accion;        
        if (accion.equals("NUEVO"))
        {  
            enfocar(true,false,false,false);

        }

        if (accion.equals("EDITAR"))
        {
         
        }
        
        if (accion.equals("ELIMINAR"))
        {
           
        }
    }
    
    public void cargarCampos(int idx)
    {
        this.idx=idx;
        Datos.Conaic_Area oArea = new Datos.Conaic_Area(enlace);
        oArea.Buscar(idx); 
        // Determinamos el nivel del área recuperada
        nivel = oArea.getNivel();
        // Recuperamos el area
        id_area = oArea.getArea(); 
        id_subarea=oArea.getSubarea();
        switch(nivel){
            case 1:
                ftfarea.setText(Integer.toString(oArea.getArea()));
                ftfsubarea.setText(Integer.toString(oArea.getSubarea()));
                ftfsubsubarea.setText(Integer.toString(oArea.getSubsubarea()));
                tfnombre.setText(oArea.getNombre());  
                tadescripcion.setText(oArea.getDescripcion());
                enfocar(true,false, false, false);
                break;
            case 2:
                 cargarAreas();
                cbarea.setSelectedItem(new ListaConaic(oArea.getID(),
                                                      oArea.getArea(),
                                                      0,
                                                      0,"",""));
                ftfsubarea.setText(Integer.toString(oArea.getSubarea()));
                ftfsubsubarea.setText(Integer.toString(oArea.getSubsubarea()));
                tfnombre.setText(oArea.getNombre());  
                tadescripcion.setText(oArea.getDescripcion());
                enfocar(true,true,false, false);
                break;
            case 3:
                 cargarAreas();
                cbarea.setSelectedItem(new ListaConaic(oArea.getID(),
                                                      oArea.getArea(),
                                                      0,
                                                      0,"",""));
                cargarSubAreas(id_area);
                cbsubarea.setSelectedItem(new ListaConaic(oArea.getID(),
                                                         oArea.getArea(),
                                                         oArea.getSubarea(),
                                                         0, "",""));
                ftfsubsubarea.setText(Integer.toString(oArea.getSubsubarea()));
                tfnombre.setText(oArea.getNombre()); 
                tadescripcion.setText(oArea.getDescripcion());
                enfocar(true,true, true, false);                
                break;
            case 4:
                cargarAreas();
                cbarea.setSelectedItem(new ListaConaic(oArea.getID(),
                                                      oArea.getArea(),
                                                      0,
                                                      0,
                                                      "",
                                                      ""));
                cargarSubAreas(id_area);
                cbsubarea.setSelectedItem(new ListaConaic(oArea.getID(),
                                                         oArea.getArea(),
                                                         oArea.getSubarea(),
                                                         0,
                                                         "",""));
                
                cargarSubSubAreas(id_area,id_subarea);
                cbsubsubarea.setSelectedItem(new ListaConaic(oArea.getID(),
                                                         oArea.getArea(),
                                                         oArea.getSubarea(),
                                                         oArea.getSubsubarea(),
                                                         oArea.getClave(),""));
                tfnombre.setText(oArea.getNombre()); 
                tfclave.setText(oArea.getClave()); 
                tadescripcion.setText(oArea.getDescripcion());
                enfocar(true,true, true, true);                
                break;
        }
        //id=oArea.getID();      
               
   } 
 
      
    public int validarnivel(int idx){
    Datos.Conaic_Area oArea = new Datos.Conaic_Area(enlace);
        oArea.Buscar(idx);    
        
        int ren,a, sb, sbb;
        String cv;
        a = oArea.getArea();
        sb = oArea.getSubarea();
        sbb = oArea.getSubsubarea();
        cv = oArea.getClave();
        
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
