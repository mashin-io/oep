package mashin.oep.model.node.action.extended;

import mashin.oep.model.Workflow;
import mashin.oep.model.node.Node;
import mashin.oep.model.node.action.ActionNode;

public abstract class ExtendedActionNode extends ActionNode {

  public ExtendedActionNode(Workflow workflow) {
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
  
  @Override
  protected boolean isSchemaVersionEditable() { return true; }
  
}
