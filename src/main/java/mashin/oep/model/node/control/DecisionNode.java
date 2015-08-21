package mashin.oep.model.node.control;

import mashin.oep.model.Workflow;
import mashin.oep.model.node.Node;
import mashin.oep.model.terminal.FanInTerminal;
import mashin.oep.model.terminal.FanOutTerminal;
import mashin.oep.model.terminal.SingleOutputTerminal;

public class DecisionNode extends ControlNode {

  protected FanInTerminal fanInTerminal;
  protected FanOutTerminal caseFanOutTerminal;
  protected SingleOutputTerminal defaultSingleOutputTerminal;
  
  public DecisionNode(Workflow workflow) {
    this(workflow, null);
  }

  public DecisionNode(Workflow workflow, org.dom4j.Node hpdlNode) {
    super(workflow, hpdlNode);
    fanInTerminal               = new FanInTerminal(TERMINAL_FANIN, this);
    caseFanOutTerminal          = new FanOutTerminal(TERMINAL_CASE, this);
    defaultSingleOutputTerminal = new SingleOutputTerminal(TERMINAL_DEFAULT, this);
    terminals.add(fanInTerminal);
    terminals.add(caseFanOutTerminal);
    terminals.add(defaultSingleOutputTerminal);
  }
  
  @Override
  public void initDefaults() {
    super.initDefaults();
    setName("decision-" + ID_SEQ.incrementAndGet());
  }
  
  @Override
  public void write(org.dom4j.Element parentNode) {
    
  }

  @Override
  public void read(org.dom4j.Node hpdlNode) {
    super.read(hpdlNode);
  }

  @Override
  public String getNodeType() {
    return "decision";
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
