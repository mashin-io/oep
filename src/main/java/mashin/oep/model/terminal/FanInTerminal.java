package mashin.oep.model.terminal;

import mashin.oep.model.node.Connection;
import mashin.oep.model.node.Node;

public class FanInTerminal extends InputTerminal {

  public FanInTerminal(String label, Node holderNode) {
    super(label, holderNode);
  }

  @Override
  public boolean canAddConnection(Connection connection) {
    if (!connection.getTarget().equals(holderNode)
        || hasConnection(connection)) {
      return false;
    }
    return true;
  }

}
