package io.mashin.oep.model.terminal;

import io.mashin.oep.model.connection.WorkflowConnection;
import io.mashin.oep.model.node.Node;

public class NoOutputTerminal extends OutputTerminal {

  public NoOutputTerminal(String label, Node holderNode) {
    super(label, holderNode);
  }

  @Override
  public boolean canAddConnection(WorkflowConnection connection) {
    return false;
  }

}
