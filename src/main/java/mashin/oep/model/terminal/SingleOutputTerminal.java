package mashin.oep.model.terminal;

import mashin.oep.model.WorkflowConnection;
import mashin.oep.model.node.Node;

public class SingleOutputTerminal extends OutputTerminal {

  public SingleOutputTerminal(String label, Node holderNode) {
    super(label, holderNode);
  }

  @Override
  public boolean canAddConnection(WorkflowConnection connection) {
    if (connections.isEmpty()
        && connection.getSource().equals(holderNode)) {
      return true;
    }
    return false;
  }

}