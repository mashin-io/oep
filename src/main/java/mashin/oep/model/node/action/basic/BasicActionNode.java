package mashin.oep.model.node.action.basic;

import mashin.oep.model.Workflow;
import mashin.oep.model.node.Node;
import mashin.oep.model.node.action.ActionNode;

public abstract class BasicActionNode extends ActionNode {

  public BasicActionNode(Workflow workflow) {
    super(workflow);
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
