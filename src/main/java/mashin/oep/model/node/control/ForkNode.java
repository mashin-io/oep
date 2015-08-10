package mashin.oep.model.node.control;

import mashin.oep.model.Workflow;
import mashin.oep.model.node.Node;
import mashin.oep.model.terminal.FanInTerminal;
import mashin.oep.model.terminal.FanOutTerminal;

public class ForkNode extends ControlNode {

  protected FanInTerminal fanInTerminal;
  protected FanOutTerminal fanOutTerminal;
  
  public ForkNode(Workflow workflow) {
    super(workflow);
    fanInTerminal  = new FanInTerminal(TERMINAL_FANIN, this);
    fanOutTerminal = new FanOutTerminal(TERMINAL_FANOUT, this);
    terminals.add(fanInTerminal);
    terminals.add(fanOutTerminal);
    setName("fork-" + ID_SEQ.incrementAndGet());
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
