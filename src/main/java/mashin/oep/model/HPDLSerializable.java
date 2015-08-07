package mashin.oep.model;

public interface HPDLSerializable {
  public String toHPDL();
  public void fromHPDL(String hpdl);
}
