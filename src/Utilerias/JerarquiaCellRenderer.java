package Utilerias;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

public class JerarquiaCellRenderer extends JTextArea implements TableCellRenderer {
 
    public JerarquiaCellRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
        setEditable(false); //this line doesn't seem to be doing anything
    }
 /*
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected, boolean hasFocus,
        int row, int column) {
      if (isSelected) {
        setForeground(table.getSelectionForeground());
        setBackground(table.getSelectionBackground());
      } else {
        setForeground(table.getForeground());
        setBackground(table.getBackground());
      }
      setFont(table.getFont());
      if (hasFocus) {
        setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
        if (table.isCellEditable(row, column)) {
          setForeground(UIManager.getColor("Table.focusCellForeground"));
          setBackground(UIManager.getColor("Table.focusCellBackground"));
        }
      } else {
        setBorder(new EmptyBorder(1, 2, 1, 2));
      }
      if (value != null) {
        setText(value.toString());
      } else {
        setText("");
      }
      return this;
    }
    */
    
    @Override public Component getTableCellRendererComponent(
    JTable table, Object value, boolean isSelected,
    boolean hasFocus, int row, int column) {
    //if(isSelected) {
    //  setForeground(table.getSelectionForeground());
    //  setBackground(table.getSelectionBackground());
    //} else {
    //  setForeground(table.getForeground());
    //  setBackground(table.getBackground());
        
      // subtema y subsubtema tienen 0
      if(String.valueOf(table.getValueAt(row,2)).equals("0") &&
         String.valueOf(table.getValueAt(row,3)).equals("0") )
      {
         setFont(new Font("Arial", Font.BOLD, 16));
         setForeground(Color.BLACK); 
      }
      // Subsubtema tiene 0
      else if (String.valueOf(table.getValueAt(row,3)).equals("0"))
      {
         setFont(new Font("Arial", Font.BOLD, 14)); 
      }
      else
      {
         setFont(table.getFont()); 
      }
    //}

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
