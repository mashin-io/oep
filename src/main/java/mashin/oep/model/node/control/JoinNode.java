package mashin.oep.model.node.control;

import mashin.oep.model.Workflow;
import mashin.oep.model.node.Node;
import mashin.oep.model.terminal.FanInTerminal;
import mashin.oep.model.terminal.SingleOutputTerminal;

public class JoinNode extends ControlNode {

  protected FanInTerminal fanInTerminal;
  protected SingleOutputTerminal singleOutputTerminal;
  
  public JoinNode(Workflow workflow) {
    super(workflow);
    fanInTerminal        = new FanInTerminal(TERMINAL_FANIN, this);
    singleOutputTerminal = new SingleOutputTerminal(TERMINAL_OUT, this);
    terminals.add(fanInTerminal);
    terminals.add(singleOutputTerminal);
    setName("join-" + ID_SEQ.incrementAndGet());
  }

  @Override
  public String toHPDL() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void fromHPDL(String hpdl) {
    // TODO Auto-generated method stub

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
