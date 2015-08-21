package mashin.oep.model.node.action.basic;

import mashin.oep.model.Workflow;
import mashin.oep.model.node.Node;
import mashin.oep.model.node.action.ActionNode;

public abstract class BasicActionNode extends ActionNode {

  public BasicActionNode(Workflow workflow) {
    this(workflow, null);
  }

  public BasicActionNode(Workflow workflow, org.dom4j.Node hpdlNode) {
    super(workflow, hpdlNode);
  }
  
  @Override
  public void initDefaults() {
    super.initDefaults();
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
