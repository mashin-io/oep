package io.mashin.oep.model.terminal;

import io.mashin.oep.model.node.Node;

public abstract class InputTerminal extends Terminal {

  public InputTerminal(String label, Node holderNode) {
    super(label, holderNode);
  }

}
