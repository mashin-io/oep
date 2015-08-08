package mashin.oep.model.terminal;

import mashin.oep.model.node.Node;

public abstract class InputTerminal extends Terminal {

  public InputTerminal(String label, Node holderNode) {
    super(label, holderNode);
  }

}
