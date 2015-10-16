package io.mashin.oep.model.connection;

import io.mashin.oep.model.node.Node;
import io.mashin.oep.model.terminal.Terminal;

public class WorkflowConnectionEndPoint extends
    AbstractWorkflowConnectionEndPoint<Node, Terminal> {

  public WorkflowConnectionEndPoint(Node node, Terminal terminal) {
    super(node, terminal);
  }
  
  @Override
  public String toString() {
    return node.toString() + ":" + terminal.getLabel();
  }
  
}
