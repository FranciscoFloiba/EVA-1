package Utilerias;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class JerarquiaCellRendererConaic extends JTextArea implements TableCellRenderer {
 
    public JerarquiaCellRendererConaic() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
        setEditable(false); 
    }    
    
    @Override
    public Component getTableCellRendererComponent(
    JTable table, Object value, boolean isSelected,
    boolean hasFocus, int row, int column) {
    
    // Se pone en negritas el área de conocimiento  
    if(String.valueOf(table.getValueAt(row,1)).equals("0") &&
       String.valueOf(table.getValueAt(row,2)).equals("0") )
    {
       setFont(new Font("Arial", Font.BOLD, 16));
       setForeground(Color.BLACK); 
    }
    else
    {
       setFont(table.getFont()); 
    }      

    if(isSelected) {
      setForeground(table.getSelectionForeground());
      setBackground(table.getSelectionBackground());
    }
    else
    {
      setForeground(table.getForeground());
      setBackground(table.getBackground());   
    }
    setText((value ==null) ? "" : value.toString());
    return this;
  }
    
}
