package mashin.oep.hpdl;

import java.util.ArrayList;
import java.util.List;

public class HPDLModel {
  
  protected List<org.dom4j.Node> hpdlNodes;
  
  public HPDLModel() {
    hpdlNodes = new ArrayList<org.dom4j.Node>();
  }
  
  public void add(org.dom4j.Node hpdlNode) {
    hpdlNodes.add(hpdlNode);
  }
  
  public void set(org.dom4j.Node hpdlNode) {
    set(0, hpdlNode);
  }
  
  public void set(int i, org.dom4j.Node hpdlNode) {
    for (int j = hpdlNodes.size(); j <= i; j++) {
      add(null);
    }
    hpdlNodes.set(i, hpdlNode);
  }
  
  public org.dom4j.Node get() {
    return get(0);
  }
  
  public org.dom4j.Node get(int i) {
    return hpdlNodes.get(i);
  }
  
  public boolean isSet(int i) {
    try {
      get(i);
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
    return get(i) != null;
  }
  
  public boolean isSet() {
    return get() != null;
  }
  
}
