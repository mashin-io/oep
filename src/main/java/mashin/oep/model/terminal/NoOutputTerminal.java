package mashin.oep.model.terminal;

import mashin.oep.model.node.Connection;
import mashin.oep.model.node.Node;

public class NoOutputTerminal extends OutputTerminal {

  public NoOutputTerminal(String label, Node holderNode) {
    super(label, holderNode);
  }

  @Override
  public boolean canAddConnection(Connection connection) {
    return false;
  }

}
