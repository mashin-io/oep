package mashin.oep.model.node.control;

import mashin.oep.hpdl.XMLReadUtils;
import mashin.oep.hpdl.XMLWriteUtils;
import mashin.oep.model.Workflow;
import mashin.oep.model.connection.WorkflowConnection;
import mashin.oep.model.connection.WorkflowConnectionEndPoint;
import mashin.oep.model.node.Node;
import mashin.oep.model.terminal.NoInputTerminal;
import mashin.oep.model.terminal.SingleOutputTerminal;

import org.dom4j.Element;

public class StartNode extends ControlNode {

  protected NoInputTerminal noInputTerminal;
  protected SingleOutputTerminal singleOutputTerminal;
  
  public StartNode(Workflow workflow) {
    this(workflow, null);
  }

  public StartNode(Workflow workflow, org.dom4j.Node hpdlNode) {
    super(workflow, hpdlNode);
    noInputTerminal       = new NoInputTerminal(TERMINAL_NOIN, this);
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
    super.write(parent);
    
    Element element = (Element) hpdlModel.get();
    
    element.selectSingleNode("@name").detach();
    
    XMLWriteUtils.writeConnectionsAsAttribute(
        singleOutputTerminal.getConnections(), element, "to");
  }
  
  @Override
  public void read(org.dom4j.Node hpdlNode) {
    super.read(hpdlNode);
    
    setName("start");
    
    // read connections
    
    WorkflowConnection conn = new WorkflowConnection(
        new WorkflowConnectionEndPoint(this, singleOutputTerminal),
        new WorkflowConnectionEndPoint(XMLReadUtils.valueOf("@to", hpdlNode), TERMINAL_FANIN));
    sourceConnections.add(conn);
  }
  
  @Override
  public String getNodeType() {
    return TYPE_START;
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
