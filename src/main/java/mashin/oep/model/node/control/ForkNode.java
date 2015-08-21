package mashin.oep.model.node.control;

import java.util.List;

import mashin.oep.hpdl.XMLUtils;
import mashin.oep.model.Workflow;
import mashin.oep.model.connection.WorkflowConnection;
import mashin.oep.model.connection.WorkflowConnectionEndPoint;
import mashin.oep.model.node.Node;
import mashin.oep.model.terminal.FanInTerminal;
import mashin.oep.model.terminal.FanOutTerminal;

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
    setName("fork-" + ID_SEQ.incrementAndGet());
  }
  
  @Override
  public void write(org.dom4j.Element parentNode) {
    
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
            new WorkflowConnectionEndPoint(XMLUtils.valueOf("@start", forkConnNode), TERMINAL_FANIN));
        sourceConnections.add(conn);
      }
    }
  }

  @Override
  public String getNodeType() {
    return "fork";
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
