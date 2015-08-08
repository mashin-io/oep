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
    super(workflow);
    fanInTerminal               = new FanInTerminal("fan-in", this);
    caseFanOutTerminal          = new FanOutTerminal("case", this);
    defaultSingleOutputTerminal = new SingleOutputTerminal("default", this);
    terminals.add(fanInTerminal);
    terminals.add(caseFanOutTerminal);
    terminals.add(defaultSingleOutputTerminal);
    setName("decision-" + ID_SEQ.incrementAndGet());
  }

  @Override
  public String toHPDL() {
    return null;
  }

  @Override
  public void fromHPDL(String hpdl) {
    
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
