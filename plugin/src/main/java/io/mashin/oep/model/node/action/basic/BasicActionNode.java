package io.mashin.oep.model.node.action.basic;

import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.node.Node;
import io.mashin.oep.model.node.action.ActionNode;

public abstract class BasicActionNode extends ActionNode {

  public BasicActionNode(Workflow workflow) {
    this(workflow, null);
  }

  public BasicActionNode(Workflow workflow, org.dom4j.Node hpdlNode) {
    super(workflow, hpdlNode);
    this.setSchemaVersion(workflow.getSchemaVersion());
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
