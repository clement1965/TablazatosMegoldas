package nezet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import modell.*;

public class FoAblak extends JFrame implements ActionListener{


  private JLabel lbTalalat = new JLabel("Nincs találat");
  private JComboBox cbReszlegLista;  
  private JComboBox cbreszlegListaForTable;
  private JComboBox cbMunkakorLista;


  private JTextField tfDolgozoKeres = new JTextField("Keresendő dolgozó", 12);

  private JButton btUjDolgozo = new JButton("Új dolgozó felvétele");
  private JButton btDolgozoMentese  = new JButton("Dolgozó mentése");
  private JButton btCancel = new JButton("Új dogozó visszavon");
  private JLabel lbReszleg = new JLabel("Részleg:   ", SwingConstants.RIGHT);
  private JLabel lbKereses = new JLabel("Dolgozó keresése:   ", SwingConstants.RIGHT);
  private AdatBazisKezeles modell;
  private Dolgozo ujDolgozo;
  private MyTable tDolgozoTable = new MyTable(new DefaultTableModel());
  private JScrollPane spDolgozoTable = new JScrollPane(tDolgozoTable);
  private ArrayList<Dolgozo> dolgozok;

  public FoAblak(AdatBazisKezeles modell) {
    this.modell = modell;
    setTitle("HR Fizetésemelés");
    setSize(900, 500);
    setLocationRelativeTo(this);
    setIconImage(Toolkit.getDefaultToolkit().getImage("image1.jpg"));

    addWindowListener(new WindowAdapter() {
      
});
    cbReszlegLista = reszlegListaBetoltes();
    cbreszlegListaForTable=reszlegListaBetoltes();
    cbreszlegListaForTable.removeItemAt(0);
    cbMunkakorLista = munkakorListaBetoltes();
 
    JPanel pnReszlegek = new JPanel(new GridLayout(1, 7));
    pnReszlegek.add(lbReszleg);
    pnReszlegek.add(cbReszlegLista);
    pnReszlegek.add(lbKereses);
    tfDolgozoKeres.setText("");
    tfDolgozoKeres.getDocument().addDocumentListener(new MyDocumentListener());
    tfDolgozoKeres.getDocument().putProperty("name", "Text Area");
    pnReszlegek.add(tfDolgozoKeres);
    lbTalalat.setVisible(false);
    lbTalalat.setForeground(Color.blue);
    lbTalalat.setHorizontalAlignment(SwingConstants.CENTER);
    pnReszlegek.add(lbTalalat);

    JPanel pn = new JPanel(new GridLayout(1, 2));
    pn.add(panelKeszit(pnReszlegek));
    add(pn, BorderLayout.NORTH);
    
    tDolgozoTable.setModel(dolgozoListaBetoltes(-1));
    tDolgozoTable.setCbRecbreszlegListaForTable(cbreszlegListaForTable);
    tDolgozoTable.setCbMunkakorLista(cbMunkakorLista);
    tDolgozoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tDolgozoTable.addRowSelectionInterval(0, 0);
    add(spDolgozoTable);

    JPanel pnDolgozoGomb = new JPanel(new GridLayout(1, 3));
    pnDolgozoGomb.add(btUjDolgozo);
    pnDolgozoGomb.add(btDolgozoMentese);
    pnDolgozoGomb.add(btCancel);
    btDolgozoMentese.setEnabled(false);
    btCancel.setEnabled(false);
    pnDolgozoGomb.setBorder(new EmptyBorder(10, 200, 10, 200));

    add(pnDolgozoGomb, BorderLayout.AFTER_LAST_LINE);
    setVisible(true);

    cbReszlegLista.addActionListener(this);
    btUjDolgozo.addActionListener(this);
    btDolgozoMentese.addActionListener(this);
    btCancel.addActionListener(this);

    tDolgozoTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getSource() == tDolgozoTable) {
          int index = tDolgozoTable.getSelectedRow();
          MyTableModell tableModell = ((MyTableModell) tDolgozoTable.getModel());
          Dolgozo dolgozo = tableModell.getDolgozo(index);
          if (dolgozo != null) {
            new DolgozoFizetesModositas((JFrame) SwingUtilities.getRoot((Component) e.getSource()), dolgozo, modell);
            tableModell.steDolgozo(dolgozo, index);
            tableModell.fireTableDataChanged();
            tDolgozoTable.addRowSelectionInterval(index, index);
          }
        }
      }
    });

//    tDolgozoTable.addKeyListener(new KeyAdapter() {
//      @Override
//      public void keyPressed(KeyEvent e) {
//        if (e.getSource() == tDolgozoTable) {
//          if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//            int index = tDolgozoTable.getSelectedRow();
//            MyTableModell tableModel = ((MyTableModell) tDolgozoTable.getModel());
//            Dolgozo dolgozo = tableModel.getDolgozo(index);
//            if (dolgozo != null) {
//              new DolgozoFizetesModositas((JFrame) SwingUtilities.getRoot((Component) e.getSource()), dolgozo, modell);
//              tableModel.steDolgozo(dolgozo, index);
//              tableModel.fireTableDataChanged();
//              tDolgozoTable.addRowSelectionInterval(index, index);
//              tDolgozoTable.setRowSelectionInterval(index, index);
//            }
//          }
//        }
//      }
//    });

  }

  private JPanel panelKeszit(JPanel pnReszlegek) {
    JPanel ujPn = new JPanel();
    ujPn.add(pnReszlegek);
    return ujPn;
  }

  private JComboBox munkakorListaBetoltes() {
    JComboBox cbMunkakorLista = new JComboBox();
    ArrayList<Munkakor> munkakorok=modell.lekerdezMunkakorok();
    for (Munkakor munkakor : munkakorok)
      cbMunkakorLista.addItem(munkakor);
    return cbMunkakorLista;
  }
  
  private JComboBox reszlegListaBetoltes() {
    JComboBox cbReszlegLista = new JComboBox();
    ArrayList<Reszleg> reszlegek = modell.lekerdezReszleg();
    cbReszlegLista.addItem(new Reszleg(" -- Összes dolgozó", -1));
    for (Reszleg reszleg : reszlegek) {
      cbReszlegLista.addItem(reszleg);
    }
    return cbReszlegLista;
  }

  private MyTableModell dolgozoListaBetoltes(int reszlegID) {
    dolgozok = modell.lekerdezDolgozokListajaAdottReszleghez(reszlegID);
    MyTableModell mytm = new MyTableModell(dolgozok);
    return mytm;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == cbReszlegLista) {
      Reszleg reszleg = (Reszleg) cbReszlegLista.getSelectedItem();
      tDolgozoTable.setModel(dolgozoListaBetoltes(reszleg.getReszlegId()));
      tfDolgozoKeres.setText("");
      lbTalalat.setVisible(false);
      tDolgozoTable.requestFocus();
      tDolgozoTable.addRowSelectionInterval(0, 0);
    }
    if (e.getSource()==btUjDolgozo){
      //new DolgozoFelvetel(this, modell);
      btUjDolgozo.setEnabled(false);
      cbReszlegLista.setEnabled(false);
      tfDolgozoKeres.setEnabled(false);
      ujDolgozo = new Dolgozo(0, "","","","", 0, "", "", 0, new Date(new java.util.Date().getTime()));
      dolgozok.add(ujDolgozo);
      
      ((MyTableModell)(tDolgozoTable.getModel())).setUjDolgozoFelvetel(true);
      ((MyTableModell)(tDolgozoTable.getModel())).fireTableDataChanged();
      int sorokSzama=tDolgozoTable.getRowCount();
      tDolgozoTable.changeSelection(sorokSzama-1, 0, false, false);
      
      tDolgozoTable.editCellAt(sorokSzama-1, 0);
      tDolgozoTable.getEditorComponent().requestFocus();
      btDolgozoMentese.setEnabled(true);
      btCancel.setEnabled(true);
    }
    if (e.getSource()==btDolgozoMentese) {
      btDolgozoMentese.setEnabled(false);
      btCancel.setEnabled(false);
      System.out.println(ujDolgozo.getKeresztNev()+ujDolgozo.getVezetekNev()+
              ujDolgozo.getEmail()+ujDolgozo.getTelefonSzam()+ujDolgozo.getFizetes()+" "+
              ujDolgozo.getDepId()+" " +ujDolgozo.getFelveteliDatum()+ujDolgozo.getMunkakor());
      if (ujDolgozoMentese(ujDolgozo)) {
        tDolgozoTable.setModel(dolgozoListaBetoltes(((Reszleg) cbReszlegLista.getSelectedItem()).getReszlegId()));
        ((MyTableModell) (tDolgozoTable.getModel())).setUjDolgozoFelvetel(false);
        btUjDolgozo.setEnabled(true);
        cbReszlegLista.setEnabled(true);
        tfDolgozoKeres.setEnabled(true);
        tDolgozoTable.addRowSelectionInterval(0, 0);
      } else {
        btDolgozoMentese.setEnabled(true);
        btCancel.setEnabled(true);
//        tDolgozoTable.addRowSelectionInterval(tDolgozoTable.getRowCount()-1, tDolgozoTable.getRowCount()-1);
//        tDolgozoTable.changeSelection(tDolgozoTable.getRowCount()-1, 0, false, false);
      }
    }
    if (e.getSource()==btCancel) {
      btDolgozoMentese.setEnabled(false);
      btCancel.setEnabled(false);
      dolgozok.remove(ujDolgozo);
      tDolgozoTable.setModel(new MyTableModell(dolgozok));
      ((MyTableModell)(tDolgozoTable.getModel())).setUjDolgozoFelvetel(false);
      btUjDolgozo.setEnabled(true);
      cbReszlegLista.setEnabled(true);
      tfDolgozoKeres.setEnabled(true);
    }
  }

  private boolean ujDolgozoMentese(Dolgozo ujDolgozo) {
    System.out.println("Még nem csinál semit");
    boolean ok = false;
    try {
      boolean mehetAMentes = Ellenorzes();
      if (mehetAMentes) {
        try {
          //int managerId=AdatBazisKezeles.lekerdezReszlegFonoke(((Reszleg)cbReszlegLista.getSelectedItem()).getReszlegId());
          boolean siker = modell.ujDolgozoFelvetele(ujDolgozo, ((Munkakor) cbMunkakorLista.getSelectedItem()).getMunkakorId(), 100);
          if (siker) {
            JOptionPane.showMessageDialog(this,
                    "A mentés sikeres volt.",
                    "Információ",
                    JOptionPane.INFORMATION_MESSAGE);
            ok = true;
          } else {
            JOptionPane.showMessageDialog(this,
                    "A mentés nem sikerült. felvétel false",
                    "Hibaüzenet",
                    JOptionPane.ERROR_MESSAGE);
                    ok = false;
          }
        }catch (SQLException ex) {
          System.out.println("Nem sikerult a mentes SQLException!");
                      JOptionPane.showMessageDialog(this,
                    "A mentés nem sikerült. SQL hiba",
                    "Hibaüzenet",
                    JOptionPane.ERROR_MESSAGE);
          ok = false;
        }
      }
    } catch (IllegalArgumentException hiba) {
      JOptionPane.showMessageDialog(this,
              hiba.getMessage(),
              "Hibaüzenet",
              JOptionPane.ERROR_MESSAGE);
      tDolgozoTable.addRowSelectionInterval(tDolgozoTable.getRowCount() - 1, tDolgozoTable.getRowCount() - 1);
      tDolgozoTable.changeSelection(tDolgozoTable.getRowCount() - 1, 7, false, false);
      ok = false;
    }
    return ok;
  }

  private boolean Ellenorzes() throws IllegalArgumentException {
    int[] osszFizetesosszLetszam = modell.lekerdezesOsszFizLetszReszlegenBelul( ((Reszleg)cbreszlegListaForTable.getSelectedItem()).getReszlegId()  );
    int osszFiz = osszFizetesosszLetszam[0];
    int osszLetszam = osszFizetesosszLetszam[1];
    long adhatoMinFizetes = Math.max(Math.round(osszFiz * (-0.05) + (osszFiz * 0.95 / osszLetszam)),
            ((Munkakor) cbMunkakorLista.getSelectedItem()).getMinFizetes());
    long adhatoMaxFizetes = Math.min(Math.round(osszFiz * 0.05 + (osszFiz * 1.05 / osszLetszam)),
            ((Munkakor) cbMunkakorLista.getSelectedItem()).getMaxFizetes());
    //lbFizetes.setText("* Fizetés (" + adhatoMinFizetes + " - " + adhatoMaxFizetes + "):    ");

    boolean kotelezoAdatokMegadva=false;
//    Ellenorzesek.hosszEllenorzes("A keresztnév túl hosszú", tfKeresztnev.getText(), 20, false);
//    kotelezoAdatokMegadva=Ellenorzesek.hosszEllenorzes("A vezetéknév túl hosszú", tfVezeteknev.getText(), 25, true);
//    kotelezoAdatokMegadva=kotelezoAdatokMegadva && Ellenorzesek.hosszEllenorzes("Az email túl hosszú", tfEmail.getText(), 25, true);
//    Ellenorzesek.hosszEllenorzes("A telefonszám túl hosszú", tfTelefonszam.getText(), 20, false);
//    kotelezoAdatokMegadva=Ellenorzesek.hosszEllenorzes("Fizetés megadása kötelező", tfFizetes.getText(), 8, true);
    kotelezoAdatokMegadva=(ujDolgozo.getVezetekNev().length()>0 &&
                           ujDolgozo.getEmail().length()>0 &&
                           ujDolgozo.getFizetes()>0);
    if (!kotelezoAdatokMegadva)
      throw new IllegalArgumentException("A kötelező adatok nincsenek megadva!");
    if (!emailEllenorzes(ujDolgozo.getEmail()))
      throw new IllegalArgumentException("A megadott email nick már létezik!");
    try {
      int ujFizetes=ujFizetes=ujDolgozo.getFizetes();
//      Reszleg reszleg = (Reszleg)cbReszlegLista.getSelectedItem();
//      int[] osszFizetesosszLetszam=AdatBazisKezeles.lekerdezesOsszFizLetszReszlegenBelul(reszleg.getReszlegId());
//      int osszFiz=osszFizetesosszLetszam[0];
//      int osszLetszam=osszFizetesosszLetszam[1];
//      long adhatoMinFizetes=Math.max(Math.round( osszFiz*(-0.05) + (osszFiz*0.95/osszLetszam)), 
//              ((Munkakor)cbMunkakorLista.getSelectedItem()).getMinFizetes() );
//      long adhatoMaxFizetes=Math.min( Math.round( osszFiz*0.05 + (osszFiz*1.05/osszLetszam)),
//              ((Munkakor)cbMunkakorLista.getSelectedItem()).getMaxFizetes());
      
//      System.out.println("Osszfizetes: "+osszFiz+
//              "\n osszletszam: "+osszLetszam+
//              "\n (min) Math.round( osszFiz*(-0.05) + (osszFiz*0.95/osszLetszam): "+Math.round( osszFiz*(-0.05) + (osszFiz*0.95/osszLetszam))+
//              "\n (max) Math.round( osszFiz*0.05 + (osszFiz*1.05/osszLetszam): "+Math.round( osszFiz*0.05 + (osszFiz*1.05/osszLetszam))+
//              "\n minsalary: "+((Munkakor)cbMunkakorLista.getSelectedItem()).getMinFizetes()+
//              "\n maxsalary: "+((Munkakor)cbMunkakorLista.getSelectedItem()).getMaxFizetes());
      if ( ujFizetes>adhatoMaxFizetes || ujFizetes < adhatoMinFizetes)
        throw new IllegalArgumentException("A fizetés "+adhatoMinFizetes+" és "+adhatoMaxFizetes+" között lehet!"); 
    } 
    catch (NumberFormatException e) {
      throw new IllegalArgumentException("A fizetés szám kell, hogy legyen!");
    }    
    return kotelezoAdatokMegadva;    
  }  
  
  private boolean emailEllenorzes(String email) throws IllegalArgumentException {
    boolean megfeleloEmail = false;
    ArrayList<String> emailLista = modell.lekerdezEmail();
    return !emailLista.contains(email);
  }

  
  class MyDocumentListener implements DocumentListener {

    final String newline = "\n";

    public void insertUpdate(DocumentEvent e) {
      updateLog(e);
    }

    public void removeUpdate(DocumentEvent e) {
      updateLog(e);
    }

    public void changedUpdate(DocumentEvent e) {
      updateLog(e);
    }

    public void updateLog(DocumentEvent e) {
      if (tfDolgozoKeres.getText().length() > 0) {
        String keres = tfDolgozoKeres.getText().toLowerCase();
        ArrayList<Dolgozo> dolgozokListaSzükitett = new ArrayList<>();
        for (int i = 0; i < dolgozok.size(); i++) {
          if ((dolgozok.get(i).getVezetekNev()+dolgozok.get(i).getKeresztNev()).toLowerCase().contains(keres)) {
            dolgozokListaSzükitett.add(dolgozok.get(i));
          }
        }
        if (dolgozokListaSzükitett.size() == 0) {
          lbTalalat.setVisible(true);
          dolgozokListaSzükitett.clear();
        } else {
          lbTalalat.setVisible(false);
        }
        ((MyTableModell) tDolgozoTable.getModel()).setDolgozoLista(dolgozokListaSzükitett);
        ((MyTableModell) tDolgozoTable.getModel()).fireTableDataChanged();
      } else {
        lbTalalat.setVisible(false);
        ((MyTableModell) tDolgozoTable.getModel()).setDolgozoLista(dolgozok);
        ((MyTableModell) tDolgozoTable.getModel()).fireTableDataChanged();
        
        tfDolgozoKeres.setText("");
        lbTalalat.setVisible(false);
        tDolgozoTable.addRowSelectionInterval(0, 0);
      }
    }
  }
}
