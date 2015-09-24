package io.mashin.oep.model.terminal;

import io.mashin.oep.model.connection.WorkflowConnection;
import io.mashin.oep.model.node.Node;

public class SingleOutputTerminal extends OutputTerminal {

  public SingleOutputTerminal(String label, Node holderNode) {
    super(label, holderNode);
  }

  @Override
  public boolean canAddConnection(WorkflowConnection connection) {
    if (connections.isEmpty()
        && connection.getSourceNode().equals(holderNode)) {
      return true;
    }
    return false;
  }

}
