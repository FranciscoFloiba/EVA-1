/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Catalogos;

import Datos.Conexion;
import Datos.ListaEgels;
import Datos.ListaEleccion;
import java.awt.Component;
import java.awt.Container;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Paul
 */
public class Egel_Area extends javax.swing.JDialog {
    private String accion;
    public Conexion enlace;
    public Egel_Areas padre;
    public int idx;
    private int nivel;
    // id_egel e id_area determinan los datos que se van llenar en los combos
    // los valores de estas variables son alimentados desde la llamada a cargarCampos, pero
    // son condicionados por la acción a realizar (NUEVO, EDITAR, etc. )
    private int id_egel, id_area;
    
    /**
     * Creates new form Egel_Area
     */
    public Egel_Area(int id_egel) {
        initComponents();
        this.id_egel = id_egel;
        inicializar();
    }
    
    private void inicializar(){
        nivel=1;
        panelarea.setEnabled(false);
        panelsubarea.setEnabled(false);
        panelsubsubarea.setEnabled(false);
        egel.setEnabled(false);
        nombre.setEnabled(false);
        descripcion.setEnabled(false);
    }
    
    public void InicializarNivel(int level)
    {
        switch(level)
        {
            case 1: area.setText("") ; nombre.setText(""); descripcion.setText("");break;
            case 2: subarea.setText("") ; nombre.setText(""); descripcion.setText("");break;
            case 3: subsubarea.setText("") ; nombre.setText(""); descripcion.setText("");break;
        }
    }
    
    public void configurarPara(String accion)
    {
        this.accion = accion;
        if (accion.equals("NUEVO"))
        {         
           enfocar(true, false, false); 
           // Cargamos los datos de los tipos de egel
           cargarEgel();
           // Posicionamos en el combo el egel que corresponde
           egel.setSelectedItem(new ListaEleccion(id_egel,""));
        }

        if (accion.equals("EDITAR"))
        {            
            // No se llama al metodo enfocar para mostrar los datos.
            // La llamada a este método dependerá del nivel de área
            // Por eso se dejó la responsabilidad al método cargarCampos()
            cargarEgel();
            // Posicionamos en el combo el egel que corresponde
            egel.setSelectedItem(new ListaEleccion(id_egel,""));
        }
        
        if (accion.equals("ELIMINAR"))
        {
           // No se llama al metodo enfocar.
           // La llamada a este método dependerá del nivel de área
           // Por eso se dejó la responsabilidad al método cargarCampos()
           cargarEgel();
           // Posicionamos en el combo el egel que corresponde
           egel.setSelectedItem(new ListaEleccion(id_egel,""));
        }                
    }
    
    private void enfocar(boolean a, boolean sb, boolean ssb) {
        botonarea.getModel().setSelected(a);
        
        if (nivel==1 ){
            // Se habilitan o deshabilitan paneles 
            EstadoPanel(panelarea,a);
            EstadoPanel(panelsubarea, sb);
            EstadoPanel(panelsubsubarea, ssb);
           
            // Solo se edita el área
            area.setVisible(a);
            cbarea.setVisible(!a);    // Se oculta el combo de area
            cbsubarea.setVisible(sb); // Se oculta el combo de subarea            
            
            // Valores predeterminados en caso de registro nuevo
            if (accion.equals("NUEVO")){
              subarea.setText("0");
              subsubarea.setText("0");  
            }
                       
            // Nombre y descripción editables para NUEVO y EDICION
            if (accion.equals("NUEVO") || accion.equals("EDITAR")){               
               nombre.setEnabled(true);
               descripcion.setEnabled(true);
            }
            else{
               // Para el caso de ELIMINACION se desactiva siempre 
               area.setEnabled(false);   
            }                
        }          
        botonsubarea.getModel().setSelected(sb);
        
        if(nivel==2 ){
            EstadoPanel(panelarea,a);
            EstadoPanel(panelsubarea, sb);
            EstadoPanel(panelsubsubarea, ssb);
            
            // Solo se edita el área
            area.setVisible(!a);       // Se oculta el text de area
            cbarea.setVisible(a);    
            subarea.setVisible(sb);
            cbsubarea.setVisible(!sb); // Se oculta el combo de subarea            
            
            // Valores predeterminados en caso de registro nuevo
            if (accion.equals("NUEVO")){
               cargarAreas(id_egel);
               subarea.setText("");
               subsubarea.setText("0");
            }
            
            // Nombre y descripción editables para NUEVO y EDICION
            if (accion.equals("NUEVO") || accion.equals("EDITAR")){
               nombre.setEnabled(true);
               descripcion.setEnabled(true); 
            }
            else
            {
               // Para el caso de ELIMINACION se desactivan siempre                 
               cbarea.setEnabled(false);
               subarea.setEnabled(false);
               subsubarea.setEnabled(false);
            }
        }        
        
        botonsubsubarea.getModel().setSelected(ssb);        
     
        if(nivel==3 ){
            EstadoPanel(panelarea,a);
            EstadoPanel(panelsubarea, sb);
            EstadoPanel(panelsubsubarea, ssb);
            
            // Solo se edita el área
            area.setVisible(!a);      // Se oculta el text de area 
            cbarea.setVisible(a);   
            subarea.setVisible(!sb);  // Se oculta el text del subarea
            cbsubarea.setVisible(sb);
            
            // Valores predeterminados en caso de registro nuevo
            if (accion.equals("NUEVO")){
                // Cargar combo de areas con base al área elegida
                cargarAreas(id_egel);
                // Recuperamoe el área activo en el combo y posicionamos la subareas
                id_area = ((ListaEgels)cbarea.getSelectedItem()).getArea(); 
                cargarSubAreas(id_egel, id_area); 
                subsubarea.setText("");
            }
            // Nombre y descripción editables en NUEVO y EDICION
            if (accion.equals("NUEVO") || accion.equals("EDITAR")){
               subsubarea.setEditable(ssb);
               nombre.setEnabled(true);
               descripcion.setEnabled(true);    
            }
            // Para el caso de eliminación se desactivan siempre
            else{     
               cbarea.setEnabled(false);
               cbsubarea.setEnabled(false);
               subsubarea.setEnabled(false);               
            }    
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     * 
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        botonarea = new javax.swing.JToggleButton();
        botonsubarea = new javax.swing.JToggleButton();
        botonsubsubarea = new javax.swing.JToggleButton();
        legel = new javax.swing.JLabel();
        egel = new javax.swing.JComboBox();
        lnombre = new javax.swing.JLabel();
        nombre = new javax.swing.JTextField();
        boton_guardar = new javax.swing.JButton();
        boton_cancelar = new javax.swing.JButton();
        ldescipcion = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descripcion = new javax.swing.JTextArea();
        panelarea = new javax.swing.JPanel();
        area = new javax.swing.JTextField();
        cbarea = new javax.swing.JComboBox<String>();
        panelsubarea = new javax.swing.JPanel();
        subarea = new javax.swing.JTextField();
        cbsubarea = new javax.swing.JComboBox<String>();
        panelsubsubarea = new javax.swing.JPanel();
        subsubarea = new javax.swing.JTextField();
        menucaptura = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Area de conocimiento de Egel");
        setMinimumSize(new java.awt.Dimension(380, 300));
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setPreferredSize(new java.awt.Dimension(460, 200));
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        getContentPane().setLayout(null);

        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);
        jToolBar1.setMaximumSize(new java.awt.Dimension(100, 193));
        jToolBar1.setRequestFocusEnabled(false);

        botonarea.setBackground(new java.awt.Color(102, 102, 102));
        botonarea.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        botonarea.setText("Area");
        botonarea.setMaximumSize(new java.awt.Dimension(100, 60));
        botonarea.setPreferredSize(new java.awt.Dimension(100, 60));
        botonarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonareaActionPerformed(evt);
            }
        });
        jToolBar1.add(botonarea);

        botonsubarea.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        botonsubarea.setText("SubArea");
        botonsubarea.setMaximumSize(new java.awt.Dimension(100, 60));
        botonsubarea.setPreferredSize(new java.awt.Dimension(100, 60));
        botonsubarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonsubareaActionPerformed(evt);
            }
        });
        jToolBar1.add(botonsubarea);

        botonsubsubarea.setBackground(new java.awt.Color(153, 153, 153));
        botonsubsubarea.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        botonsubsubarea.setText("SubSubArea");
        botonsubsubarea.setMaximumSize(new java.awt.Dimension(100, 60));
        botonsubsubarea.setPreferredSize(new java.awt.Dimension(100, 60));
        botonsubsubarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonsubsubareaActionPerformed(evt);
            }
        });
        jToolBar1.add(botonsubsubarea);

        getContentPane().add(jToolBar1);
        jToolBar1.setBounds(0, 0, 100, 510);

        legel.setText("Egel");
        getContentPane().add(legel);
        legel.setBounds(140, 30, 60, 14);

        egel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().add(egel);
        egel.setBounds(140, 50, 510, 28);

        lnombre.setText("Nombre");
        getContentPane().add(lnombre);
        lnombre.setBounds(140, 280, 290, 14);

        nombre.setFont(new java.awt.Font("DialogInput", 0, 14)); // NOI18N
        nombre.setMinimumSize(new java.awt.Dimension(8, 24));
        nombre.setPreferredSize(new java.awt.Dimension(8, 24));
        getContentPane().add(nombre);
        nombre.setBounds(140, 300, 500, 28);

        boton_guardar.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        boton_guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/GuardarTodo.png"))); // NOI18N
        boton_guardar.setMnemonic('A');
        boton_guardar.setToolTipText("Guardar registro");
        boton_guardar.setLabel("Aceptar");
        boton_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_guardarActionPerformed(evt);
            }
        });
        getContentPane().add(boton_guardar);
        boton_guardar.setBounds(380, 460, 130, 40);

        boton_cancelar.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
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
        boton_cancelar.setBounds(520, 460, 130, 40);

        ldescipcion.setText("Descripcion");
        getContentPane().add(ldescipcion);
        ldescipcion.setBounds(140, 340, 290, 20);

        descripcion.setColumns(20);
        descripcion.setLineWrap(true);
        descripcion.setRows(5);
        descripcion.setWrapStyleWord(true);
        jScrollPane1.setViewportView(descripcion);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(140, 360, 510, 90);

        panelarea.setBorder(javax.swing.BorderFactory.createTitledBorder("Area"));
        panelarea.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelarea.add(area, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 21, 70, 28));

        cbarea.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbareaItemStateChanged(evt);
            }
        });
        panelarea.add(cbarea, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 21, 500, 28));

        getContentPane().add(panelarea);
        panelarea.setBounds(140, 90, 520, 60);

        panelsubarea.setBorder(javax.swing.BorderFactory.createTitledBorder("SubArea"));
        panelsubarea.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelsubarea.add(subarea, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 21, 70, 28));

        panelsubarea.add(cbsubarea, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 21, 500, 28));

        getContentPane().add(panelsubarea);
        panelsubarea.setBounds(140, 150, 520, 60);

        panelsubsubarea.setBorder(javax.swing.BorderFactory.createTitledBorder("SubSubArea"));
        panelsubsubarea.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelsubsubarea.add(subsubarea, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 21, 70, 28));

        getContentPane().add(panelsubsubarea);
        panelsubsubarea.setBounds(140, 210, 520, 60);
        getContentPane().add(menucaptura);
        menucaptura.setBounds(0, 0, 100, 510);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-697)/2, (screenSize.height-549)/2, 697, 549);
    }// </editor-fold>//GEN-END:initComponents

    private void boton_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_guardarActionPerformed
      Datos.Egel_Area oEgel_Area = new Datos.Egel_Area(enlace);    
       
      // El ID lo genera automáticamente la base de datos cuando el registro es nuevo
      //int areax, subareax;
      if (accion.equals("ELIMINAR")){
         // Otorgamos datos básicos en caso de ELIMINAR (solo se ocupa idx)
         oEgel_Area.setEgel(idx, nombre.getText(),((ListaEleccion)egel.getSelectedItem()).id);  
      }
//       
      
      if (this.accion.equals("EDITAR") || this.accion.equals("NUEVO"))
        {
            if (nombre.getText().length() == 0)
            {
                JOptionPane.showMessageDialog(null, "No se capturó el nombre", "Información", JOptionPane.INFORMATION_MESSAGE);
                nombre.requestFocus();
                return;
            }

            switch (nivel) {
                case 1:
                    if (area.getText().length()==0)
                    {
                        JOptionPane.showMessageDialog(null, "No se capturó el area", "Información", JOptionPane.INFORMATION_MESSAGE);
                        nombre.requestFocus();
                        return;
                    }
                    //////////////////////////////////////////////////////////////////////////
                    oEgel_Area.setEgel(
                            idx,
                            Integer.parseInt(area.getText()),
                            (subarea.getText().isEmpty())?0:Integer.parseInt(subarea.getText()), 
                            (subsubarea.getText().isEmpty())?0:Integer.parseInt(subsubarea.getText()),
                            ((ListaEleccion)egel.getSelectedItem()).id,
                            nombre.getText(),                           
                            "");
                    InicializarNivel(1); area.requestFocus();
                    break;
                    ///////////////////////////////////////////////////////////////////////////////
                case 2:
                    if (subarea.getText().length()==0)
                    {
                        JOptionPane.showMessageDialog(null, "No se capturó subarea", "Información", JOptionPane.INFORMATION_MESSAGE);
                        nombre.requestFocus();
                        return;
                    }
                    if (cbarea.getSelectedIndex()==-1)
                    {
                        JOptionPane.showMessageDialog(this, "No se seleccionó un area",
                                "Mensaje", JOptionPane.WARNING_MESSAGE);
                       nombre.requestFocus();
                       return;
                    } 
                    
                    //areax=oEgel_Area.getArea(((ListaEgels)cbarea.getSelectedItem()).getId());
                    oEgel_Area.setEgel(
                            idx,
                            ((ListaEgels)cbarea.getSelectedItem()).getArea(),
                            (subarea.getText().isEmpty())?0:Integer.valueOf(subarea.getText()), 
                            (subsubarea.getText().isEmpty())?0:Integer.valueOf(subsubarea.getText()), 
                            ((ListaEleccion)egel.getSelectedItem()).id,
                            nombre.getText(),
                            descripcion.getText()
                    );
                    InicializarNivel(2); subarea.requestFocus();
                    break;

                case 3:
                    if (subsubarea.getText().length()==0)
                    {
                        JOptionPane.showMessageDialog(null, "No se capturó subsubarea", "Información", JOptionPane.INFORMATION_MESSAGE);
                        nombre.requestFocus();
                        return;
                    }
                    
                    //subareax=oEgel_Area.getSubarea(((ListaEgels)cbsubarea.getSelectedItem()).getId());
                    //areax=oEgel_Area.getArea(((ListaEgels)cbarea.getSelectedItem()).getId());
                    oEgel_Area.setEgel(
                            idx,
                            ((ListaEgels)cbarea.getSelectedItem()).getArea(),
                            ((ListaEgels)cbsubarea.getSelectedItem()).getSubArea(), 
                            (subsubarea.getText().isEmpty())?0:Integer.valueOf(subsubarea.getText()),
                            ((ListaEleccion)egel.getSelectedItem()).id,
                            nombre.getText(),
                            descripcion.getText()
                    );
                    InicializarNivel(3); subsubarea.requestFocus();
                    break;
                default:
                    break;
            }
        }    
      
      if (this.accion.equals("EDITAR"))
        {
            // Se manda escribir a la base de datos
            if (!oEgel_Area.Editar())
            {
                return;
            }
            this.dispose();
        } // Fin editar

        if (this.accion.equals("NUEVO"))
        {
            // Se manda escribir a la base de datos
            if (!oEgel_Area.Nuevo())
            {
                return;
            }
        }

        if (this.accion.equals("ELIMINAR"))
        {
            // Se se solicita confirmación al usuario
            if (JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el registro?", "Confirmación", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
                if (!oEgel_Area.Eliminar())
                {
                    //boton_guardar.requestFocus();
                    return;
                }
            }
            this.dispose();
        }
      
      padre.cargarTabla();      
    }//GEN-LAST:event_boton_guardarActionPerformed

    private void boton_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_cancelarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_boton_cancelarActionPerformed

    private void botonareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonareaActionPerformed
        // Se establece nivel 1
        nivel=1;
        enfocar(true, false, false);
    }//GEN-LAST:event_botonareaActionPerformed

    private void botonsubareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonsubareaActionPerformed
        // Se establece nivel 2
        nivel=2;
        enfocar(true, true, false);
    }//GEN-LAST:event_botonsubareaActionPerformed

    private void botonsubsubareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonsubsubareaActionPerformed
        // Se establece nivel 3
        nivel=3;
        enfocar(true, true, true); 
    }//GEN-LAST:event_botonsubsubareaActionPerformed

    private void cbareaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbareaItemStateChanged
        // Recuperamoe el área activo en el combo
        id_area = ((ListaEgels)cbarea.getSelectedItem()).getArea(); 
        cargarSubAreas(id_egel, id_area);
    }//GEN-LAST:event_cbareaItemStateChanged

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
            java.util.logging.Logger.getLogger(Egel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Egel_Area(1).setVisible(true);                               
            }
        });
    }
    
    public void cargarEgel()
    {
        Datos.Egel oEgel = new Datos.Egel(enlace);
        DefaultComboBoxModel datos = new DefaultComboBoxModel();
        datos.removeAllElements();
        Vector items = new Vector();
        items.addAll(oEgel.Lista());
        for (int i = 0; i < items.size(); i++)
        {
          datos.addElement(items.get(i));
        }
        egel.setModel(datos);            
    }
    
    public void cargarAreas(int id_egel){
        Datos.Egel_Area oEgel_Area= new Datos.Egel_Area(enlace);
        //JOptionPane.showMessageDialog(null,id_egel);
        DefaultComboBoxModel datos = new DefaultComboBoxModel();
        datos.removeAllElements();
        Vector items = new Vector();
        items.addAll(oEgel_Area.Lista(1,id_egel,0,0));
        for (int i = 0; i < items.size(); i++)
        {
            datos.addElement(items.get(i));
        }
        cbarea.setModel(datos); 
    }
    
    public void cargarSubAreas(int id_egel, int id_area) {
        //int nEgel, nArea;
        
        Datos.Egel_Area oEgel_Area = new Datos.Egel_Area(enlace);
        
        DefaultComboBoxModel datos = new DefaultComboBoxModel();
        datos.removeAllElements();
        Vector items = new Vector();
        items.addAll(oEgel_Area.Lista(2,id_egel,id_area,0));
        
        for (int i = 0; i < items.size(); i++)
        {
            datos.addElement(items.get(i));
        }
        cbsubarea.setModel(datos);
    }    
    
    
   public void cargarCampos(int idx)
    {
        this.idx=idx;
        Datos.Egel_Area oEgel_Area = new Datos.Egel_Area(enlace);
        oEgel_Area.Buscar(idx);
        // Determinamos el nivel del área recuperada
        nivel = oEgel_Area.getNivel();
        // Recuperamos el area
        id_area = oEgel_Area.getArea();  
        
        // Se cargan los datos de acuerdo al nivel que le corresponde al área
        switch(nivel){
            case 1:
                area.setText(Integer.toString(oEgel_Area.getArea()));
                subarea.setText(Integer.toString(oEgel_Area.getSubArea()));
                subsubarea.setText(Integer.toString(oEgel_Area.getSubSubArea()));
                nombre.setText(oEgel_Area.getNombre());  
                descripcion.setText(oEgel_Area.getDescripcion());
                enfocar(true, false, false);
                break;
            case 2:
                // Para mapear adecuadamente se resolvio con la clase ListaEgels                 
                cargarAreas(id_egel);
                cbarea.setSelectedItem(new ListaEgels(oEgel_Area.getID(),
                                                      oEgel_Area.getArea(),
                                                      0,
                                                      0,""));
                subarea.setText(Integer.toString(oEgel_Area.getSubArea()));
                subsubarea.setText(Integer.toString(oEgel_Area.getSubSubArea()));
                nombre.setText(oEgel_Area.getNombre());  
                descripcion.setText(oEgel_Area.getDescripcion());
                enfocar(true, true, false);
                break;
            case 3:
                // Para mapear adecuadamente se resolvio con la clase ListaEgels                 
                cargarAreas(id_egel);
                cbarea.setSelectedItem(new ListaEgels(oEgel_Area.getID(),
                                                      oEgel_Area.getArea(),
                                                      0,
                                                      0,""));
                cargarSubAreas(id_egel, id_area);
                cbsubarea.setSelectedItem(new ListaEgels(oEgel_Area.getID(),
                                                         oEgel_Area.getArea(),
                                                         oEgel_Area.getSubArea(),
                                                         0, ""));
                subsubarea.setText(Integer.toString(oEgel_Area.getSubSubArea()));
                nombre.setText(oEgel_Area.getNombre()); 
                descripcion.setText(oEgel_Area.getDescripcion());
                enfocar(true, true, true);                
                break;
        }           
   } 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField area;
    private javax.swing.JButton boton_cancelar;
    private javax.swing.JButton boton_guardar;
    private javax.swing.JToggleButton botonarea;
    private javax.swing.JToggleButton botonsubarea;
    private javax.swing.JToggleButton botonsubsubarea;
    private javax.swing.JComboBox<String> cbarea;
    private javax.swing.JComboBox<String> cbsubarea;
    private javax.swing.JTextArea descripcion;
    private javax.swing.JComboBox egel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel ldescipcion;
    private javax.swing.JLabel legel;
    private javax.swing.JLabel lnombre;
    private javax.swing.JPanel menucaptura;
    private javax.swing.JTextField nombre;
    private javax.swing.JPanel panelarea;
    private javax.swing.JPanel panelsubarea;
    private javax.swing.JPanel panelsubsubarea;
    private javax.swing.JTextField subarea;
    private javax.swing.JTextField subsubarea;
    // End of variables declaration//GEN-END:variables

  
}
