package mashin.oep.model.node.control;

import org.dom4j.Element;

import mashin.oep.model.Workflow;
import mashin.oep.model.node.Node;
import mashin.oep.model.terminal.NoInputTerminal;
import mashin.oep.model.terminal.SingleOutputTerminal;

public class StartNode extends ControlNode {

  protected NoInputTerminal noInputTerminal;
  protected SingleOutputTerminal singleOutputTerminal;
  
  public StartNode(Workflow workflow) {
    this(workflow, null);
  }

  public StartNode(Workflow workflow, org.dom4j.Node hpdlNode) {
    super(workflow, hpdlNode);
    noInputTerminal       = new NoInputTerminal(TERMINAL_NONE, this);
    singleOutputTerminal  = new SingleOutputTerminal(TERMINAL_OUT, this);
    terminals.add(noInputTerminal);
    terminals.add(singleOutputTerminal);
  }
  
  @Override
  public void initDefaults() {
    super.initDefaults();
    setName("start");
  }
  
  @Override
  public void write(Element parent) {
    
  }
  
  @Override
  public void read(org.dom4j.Node hpdlNode) {
    super.read(hpdlNode);
    setName("start");
  }
  
  @Override
  public String getNodeType() {
    return "start";
  }
  
  @Override
  public void setPropertyValue(Object propertyName, Object propertyValue) {
    if (propertyName.equals(PROP_NODE_NAME)) {
      super.setPropertyValue(PROP_NODE_NAME, "start");
    } else {
      super.setPropertyValue(propertyName, propertyValue);
    }
  }
  
  @Override
  public boolean canConnectTo(Node target) {
    return true;
  }

  @Override
  public boolean canConnectFrom(Node source) {
    return false;
  }

}
