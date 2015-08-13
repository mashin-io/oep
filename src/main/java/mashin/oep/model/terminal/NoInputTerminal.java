package mashin.oep.model.terminal;

import mashin.oep.model.connection.WorkflowConnection;
import mashin.oep.model.node.Node;

public class NoInputTerminal extends InputTerminal {

  public NoInputTerminal(String label, Node holderNode) {
    super(label, holderNode);
  }

  @Override
  public boolean canAddConnection(WorkflowConnection connection) {
    return false;
  }

}
