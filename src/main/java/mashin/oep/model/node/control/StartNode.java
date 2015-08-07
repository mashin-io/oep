package mashin.oep.model.node.control;

import mashin.oep.model.node.Node;
import mashin.oep.model.workflow.Workflow;

public class StartNode extends ControlNode {

  public StartNode(Workflow workflow) {
    super(workflow);
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
    return false;
  }
  
}
