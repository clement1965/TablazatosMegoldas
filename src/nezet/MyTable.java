package nezet;

import java.awt.Component;
import javafx.scene.control.ComboBox;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;


public class MyTable extends JTable {

  private JComboBox cbRecbreszlegListaForTable;
  private JComboBox cbMunkakorLista;

  class ComboBoxTableCellRenderer extends JComboBox implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      setSelectedItem(value);
      return this;
    }

  }

  public MyTable(TableModel dataModel) {
    super(dataModel);
  }

  public void setCbRecbreszlegListaForTable(JComboBox cbRecbreszlegListaForTable) {
    this.cbRecbreszlegListaForTable = cbRecbreszlegListaForTable;
  }

  public void setCbMunkakorLista(JComboBox cbMunkakorLista) {
    this.cbMunkakorLista = cbMunkakorLista;
  }

  public void changeSelection(int row, int column, boolean toggle, boolean extend) {
    super.changeSelection(row, column, toggle, extend);
    if (row == getRowCount() - 1 && column != 6 && this.getModel().isCellEditable(row, column)) {
      this.editCellAt(row, column);
      this.transferFocus();
      this.getEditorComponent().requestFocus();
    }
  }

  @Override
  public TableCellEditor getCellEditor(int row, int column) {
    if (row == getRowCount() - 1 && column == 4 && this.getModel().isCellEditable(row, column)) {
      return new DefaultCellEditor(cbRecbreszlegListaForTable);
    }
    if (row == getRowCount() - 1 && column == 5 && this.getModel().isCellEditable(row, column)) {
      return new DefaultCellEditor(cbMunkakorLista);
    }

    return super.getCellEditor(row, column); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public TableCellRenderer getCellRenderer(int row, int column) {
    if (row == getRowCount() - 1 && column == 4 && this.getModel().isCellEditable(row, column)) {
      ComboBoxTableCellRenderer reender = new ComboBoxTableCellRenderer();
      reender.setModel(cbRecbreszlegListaForTable.getModel());
      this.setRowHeight(row, this.getRowHeight() + 5);
      return reender;
    }
    if (row == getRowCount() - 1 && column == 5 && this.getModel().isCellEditable(row, column)) {
      ComboBoxTableCellRenderer reender2 = new ComboBoxTableCellRenderer();
      reender2.setModel(cbMunkakorLista.getModel());
      this.setRowHeight(row, this.getRowHeight() + 5);
      return reender2;
    }
    return super.getCellRenderer(row, column); //To change body of generated methods, choose Tools | Templates.
  }

}
