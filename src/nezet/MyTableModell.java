
package nezet;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import modell.Dolgozo;
import modell.Reszleg;


public class MyTableModell extends AbstractTableModel{
  private ArrayList<Dolgozo> dolgozoLista = new ArrayList<>();
  private String[] oszlopNevek = new String[]{
    "Kereszt név","Vezeték név","Nick név/email cím",
    "Telefonszam","Részleg","Munkakör","Belépési dátum","Fizetés"};
  private boolean ujDolgozoFelvetel;
  
  public MyTableModell(ArrayList<Dolgozo> dolgozoLista) {
    this.dolgozoLista = dolgozoLista;
  }

  public void setDolgozoLista(ArrayList<Dolgozo> dolgozoLista) {
    this.dolgozoLista = dolgozoLista;
  }

  public void setUjDolgozoFelvetel(boolean ujDolgozoFelvetel) {
    this.ujDolgozoFelvetel = ujDolgozoFelvetel;
  }
  
  ArrayList<Dolgozo> getDolgozLista() {
    return dolgozoLista;
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    if (ujDolgozoFelvetel) {
      if (rowIndex==getRowCount()-1) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    //super.setValueAt(aValue, rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    switch(columnIndex){
      case  0: dolgozoLista.get(rowIndex).setKeresztNev((String)aValue); return ;
      case  1: dolgozoLista.get(rowIndex).setVezetekNev((String)aValue); return ;
      case  2: dolgozoLista.get(rowIndex).setEmail((String)aValue); return ;
      case  3: dolgozoLista.get(rowIndex).setTelefonSzam((String)aValue); return ;
      case  4: dolgozoLista.get(rowIndex).setDepId( ((Reszleg)aValue).getReszlegId()); return ;
      case  5: dolgozoLista.get(rowIndex).setMunkakor(aValue.toString());return ;
      case  6: return ;
      case  7: dolgozoLista.get(rowIndex).setFizetes ((int) aValue);return ;
      default: return ;
    
  }
  }

  
  public Dolgozo getDolgozo (int index){
    return dolgozoLista.get(index);
  }
  
  public void steDolgozo (Dolgozo dolgozo, int index){
    dolgozoLista.set(index, dolgozo);
  }
  
  @Override
  public int getRowCount() {
    return dolgozoLista.size();
  }

  @Override
  public String getColumnName(int column) {
    return oszlopNevek[column];
            
  }

  @Override
  public int getColumnCount() {
    return oszlopNevek.length;
  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    switch(columnIndex){
      case  0: return dolgozoLista.get(0).getKeresztNev().getClass() ;
      case  1: return dolgozoLista.get(0).getVezetekNev().getClass() ;
      case  2: return dolgozoLista.get(0).getEmail().getClass() ;
      case  3: return dolgozoLista.get(0).getTelefonSzam().getClass() ;
      case  4: return dolgozoLista.get(0).getReszlegNev().getClass();
      case  5: return dolgozoLista.get(0).getMunkakor().getClass();
      case  6: return dolgozoLista.get(0).getFelveteliDatum().getClass();
      case  7: return ((Integer)dolgozoLista.get(0).getFizetes()).getClass();
      default: return dolgozoLista.get(0).getVezetekNev().getClass() ;
    }
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    switch(columnIndex){
      case  0: return dolgozoLista.get(rowIndex).getKeresztNev();
      case  1: return dolgozoLista.get(rowIndex).getVezetekNev();
      case  2: return dolgozoLista.get(rowIndex).getEmail();
      case  3: return dolgozoLista.get(rowIndex).getTelefonSzam();
      case  4: return dolgozoLista.get(rowIndex).getReszlegNev();
      case  5: return dolgozoLista.get(rowIndex).getMunkakor();
      case  6: return dolgozoLista.get(rowIndex).getFelveteliDatum();
      case  7: return dolgozoLista.get(rowIndex).getFizetes();
      default: return dolgozoLista.get(rowIndex).getVezetekNev();
    }
  }

}
