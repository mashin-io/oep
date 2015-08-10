package mashin.oep.model.node.control;

import mashin.oep.model.Workflow;
import mashin.oep.model.node.Node;
import mashin.oep.model.terminal.FanInTerminal;
import mashin.oep.model.terminal.NoOutputTerminal;

public class EndNode extends ControlNode {

  protected FanInTerminal fanInTerminal;
  protected NoOutputTerminal noOutputTerminal;
  
  public EndNode(Workflow workflow) {
    super(workflow);
    fanInTerminal     = new FanInTerminal(TERMINAL_FANIN, this);
    noOutputTerminal  = new NoOutputTerminal(TERMINAL_NONE, this);
    terminals.add(fanInTerminal);
    terminals.add(noOutputTerminal);
    setName("end-" + ID_SEQ.incrementAndGet());
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
    return false;
  }

  @Override
  public boolean canConnectFrom(Node source) {
    return true;
  }

}
