package mashin.oep.hpdl;

public interface HPDLSerializable {

  public void read(org.dom4j.Node node);
  public void write(org.dom4j.Element parent);
  
}
