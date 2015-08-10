package mashin.oep.model.node.control;

import mashin.oep.model.Workflow;
import mashin.oep.model.node.Node;
import mashin.oep.model.terminal.NoInputTerminal;
import mashin.oep.model.terminal.SingleOutputTerminal;

public class StartNode extends ControlNode {

  protected NoInputTerminal noInputTerminal;
  protected SingleOutputTerminal singleOutputTerminal;
  
  public StartNode(Workflow workflow) {
    super(workflow);
    noInputTerminal       = new NoInputTerminal(TERMINAL_NONE, this);
    singleOutputTerminal  = new SingleOutputTerminal(TERMINAL_OUT, this);
    terminals.add(noInputTerminal);
    terminals.add(singleOutputTerminal);
    setName(null);
  }

  @Override
  public void setName(String name) {
    super.setName("start");
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
    return false;
  }
  
}
