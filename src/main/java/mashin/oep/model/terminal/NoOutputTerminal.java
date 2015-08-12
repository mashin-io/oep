package mashin.oep.model.terminal;

import mashin.oep.model.WorkflowConnection;
import mashin.oep.model.node.Node;

public class NoOutputTerminal extends OutputTerminal {

  public NoOutputTerminal(String label, Node holderNode) {
    super(label, holderNode);
  }

  @Override
  public boolean canAddConnection(WorkflowConnection connection) {
    return false;
  }

}
