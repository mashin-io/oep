package mashin.oep.model.node.control;

import mashin.oep.hpdl.XMLUtils;
import mashin.oep.model.Workflow;
import mashin.oep.model.connection.WorkflowConnection;
import mashin.oep.model.connection.WorkflowConnectionEndPoint;
import mashin.oep.model.node.Node;
import mashin.oep.model.terminal.FanInTerminal;
import mashin.oep.model.terminal.SingleOutputTerminal;

public class JoinNode extends ControlNode {

  protected FanInTerminal fanInTerminal;
  protected SingleOutputTerminal singleOutputTerminal;
  
  public JoinNode(Workflow workflow) {
    this(workflow, null);
  }

  public JoinNode(Workflow workflow, org.dom4j.Node hpdlNode) {
    super(workflow, hpdlNode);
    fanInTerminal        = new FanInTerminal(TERMINAL_FANIN, this);
    singleOutputTerminal = new SingleOutputTerminal(TERMINAL_OUT, this);
    terminals.add(fanInTerminal);
    terminals.add(singleOutputTerminal);
  }
  
  @Override
  public void initDefaults() {
    super.initDefaults();
    setName("join-" + ID_SEQ.incrementAndGet());
  }
  
  @Override
  public void write(org.dom4j.Element parentNode) {
    
  }

  @Override
  public void read(org.dom4j.Node hpdlNode) {
    super.read(hpdlNode);
    
    // read connections
    
    WorkflowConnection conn = new WorkflowConnection(
        new WorkflowConnectionEndPoint(this, singleOutputTerminal),
        new WorkflowConnectionEndPoint(XMLUtils.valueOf("@to", hpdlNode), TERMINAL_FANIN));
    sourceConnections.add(conn);
  }

  @Override
  public String getNodeType() {
    return "join";
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
