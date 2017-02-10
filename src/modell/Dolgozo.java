package modell;

import java.sql.Date;


public class Dolgozo implements Comparable<Dolgozo> {

  private int empID;
  private String vezetekNev;
  private String keresztNev;
  private String email;
  private String telefonSzam;
  private int depId;
  private String reszlegNev;
  private String munkakor;
  private int fizetes;
  private Date felveteliDatum;

  public Dolgozo(int empID, String vezetekNev, String keresztNev, String email, String telefonSzam, int depId, String reszlegNev, String munkakor, int fizetes, Date felveteliDatum) {
    this.empID = empID;
    this.vezetekNev = vezetekNev;
    this.keresztNev = keresztNev;
    this.email = email;
    this.telefonSzam = telefonSzam;
    this.depId = depId;
    this.reszlegNev = reszlegNev;
    this.munkakor = munkakor;
    this.fizetes = fizetes;
    this.felveteliDatum = felveteliDatum;
  }

  public int getEmpID() {
    return empID;
  }

  public void setEmpID(int empID) {
    this.empID = empID;
  }

  public String getVezetekNev() {
    return vezetekNev;
  }

  public void setVezetekNev(String vezetekNev) {
    this.vezetekNev = vezetekNev;
  }

  public String getKeresztNev() {
    return keresztNev;
  }

  public void setKeresztNev(String keresztNev) {
    this.keresztNev = keresztNev;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelefonSzam() {
    return telefonSzam;
  }

  public void setTelefonSzam(String telefonSzam) {
    this.telefonSzam = telefonSzam;
  }

  public int getDepId() {
    return depId;
  }

  public void setDepId(int depId) {
    this.depId = depId;
  }

  public String getReszlegNev() {
    return reszlegNev;
  }

  public void setReszlegNev(String reszlegNev) {
    this.reszlegNev = reszlegNev;
  }

  public String getMunkakor() {
    return munkakor;
  }

  public void setMunkakor(String munkakor) {
    this.munkakor = munkakor;
  }

  public int getFizetes() {
    return fizetes;
  }

  public void setFizetes(int fizetes) {
    this.fizetes = fizetes;
  }

  public Date getFelveteliDatum() {
    return felveteliDatum;
  }

  public void setFelveteliDatum(Date felveteliDatum) {
    this.felveteliDatum = felveteliDatum;
  }

  @Override
  public String toString() {

    return vezetekNev+" "+keresztNev;
  }

  @Override
  public int compareTo(Dolgozo masik) {
    return (vezetekNev+keresztNev).compareTo(masik.getVezetekNev()+masik.getKeresztNev());
  }
}
