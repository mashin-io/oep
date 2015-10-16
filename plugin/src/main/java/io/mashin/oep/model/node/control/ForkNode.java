package io.mashin.oep.model.node.control;

import io.mashin.oep.hpdl.XMLReadUtils;
import io.mashin.oep.hpdl.XMLWriteUtils;
import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.connection.WorkflowConnection;
import io.mashin.oep.model.connection.WorkflowConnectionDummyEndPoint;
import io.mashin.oep.model.connection.WorkflowConnectionEndPoint;
import io.mashin.oep.model.node.Node;
import io.mashin.oep.model.terminal.FanInTerminal;
import io.mashin.oep.model.terminal.FanOutTerminal;

import java.util.List;

import org.dom4j.Element;

public class ForkNode extends ControlNode {

  protected FanInTerminal fanInTerminal;
  protected FanOutTerminal fanOutTerminal;
  
  public ForkNode(Workflow workflow) {
    this(workflow, null);
  }

  public ForkNode(Workflow workflow, org.dom4j.Node hpdlNode) {
    super(workflow, hpdlNode);
    fanInTerminal  = new FanInTerminal(TERMINAL_FANIN, this);
    fanOutTerminal = new FanOutTerminal(TERMINAL_FANOUT, this);
    terminals.add(fanInTerminal);
    terminals.add(fanOutTerminal);
  }
  
  @Override
  public void initDefaults() {
    super.initDefaults();
    setName(workflow.nextId("fork"));
  }
  
  @Override
  public void write(org.dom4j.Element parentNode) {
    super.write(parentNode);
    
    Element element = (Element) hpdlModel.get();
    
    writeConnections(element);
  }

  @Override
  protected void writeConnections(Element nodeElement) {
    super.writeConnections(nodeElement);
    
    XMLWriteUtils.writeConnectionsAsElementWithAttribute(
        fanOutTerminal.getConnections(), nodeElement, "path", "start");
  }
  
  @Override
  public void read(org.dom4j.Node hpdlNode) {
    super.read(hpdlNode);
    
    // read connections
    
    @SuppressWarnings("unchecked")
    List<org.dom4j.Node> forkConnNodes = hpdlNode.selectNodes("./path");
    if (forkConnNodes != null) {
      for (org.dom4j.Node forkConnNode : forkConnNodes) {
        WorkflowConnection conn = new WorkflowConnection(
            new WorkflowConnectionEndPoint(this, fanOutTerminal),
            new WorkflowConnectionDummyEndPoint(XMLReadUtils.valueOf("@start", forkConnNode), TERMINAL_FANIN));
        sourceConnections.add(conn);
        forkConnNode.detach();
      }
    }
  }

  @Override
  public String getNodeType() {
    return TYPE_FORK;
  }
  
  @Override
  public boolean canConnectTo(Node target) {
    return true;
  }

  @Override
  public boolean canConnectFrom(Node source) {
    return true;
  }

}
