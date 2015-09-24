package io.mashin.oep.model.terminal;

import io.mashin.oep.model.node.Node;

public abstract class OutputTerminal extends Terminal {

  public OutputTerminal(String label, Node holderNode) {
    super(label, holderNode);
  }

}
