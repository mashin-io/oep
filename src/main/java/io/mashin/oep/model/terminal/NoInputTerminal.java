package io.mashin.oep.model.terminal;

import io.mashin.oep.model.connection.WorkflowConnection;
import io.mashin.oep.model.node.Node;

public class NoInputTerminal extends InputTerminal {

  public NoInputTerminal(String label, Node holderNode) {
    super(label, holderNode);
  }

  @Override
  public boolean canAddConnection(WorkflowConnection connection) {
    return false;
  }

}
