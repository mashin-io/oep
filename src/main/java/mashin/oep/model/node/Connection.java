package mashin.oep.model.node;

import mashin.oep.model.ModelElement;
import mashin.oep.model.terminal.Terminal;

public class Connection extends ModelElement {
  
  private boolean isConnected;

  private Node source;
  private Node target;
  
  private Terminal sourceTerminal;
  private Terminal targetTerminal;

  public Connection(Node source, Node target, Terminal sourceTerminal,
      Terminal targetTerminal) {
    reconnect(source, target, sourceTerminal, targetTerminal);
  }

  public void disconnect() {
    if (isConnected) {
      source.removeConnectionInitiate(this);
      target.removeConnectionInitiate(this);
      isConnected = false;
    }
  }

  public void reconnect() {
    if (!isConnected) {
      source.addConnectionInitiate(this);
      target.addConnectionInitiate(this);
      isConnected = true;
    }
  }

  public void reconnect(Node source, Node target,
      Terminal sourceTerminal, Terminal targetTerminal) {
    if (source == null || target == null
        || source == target || sourceTerminal == null
        || targetTerminal == null) {
      throw new IllegalArgumentException();
    }
    disconnect();
    this.source = source;
    this.target = target;
    this.sourceTerminal = sourceTerminal;
    this.targetTerminal = targetTerminal;
    reconnect();
  }

  public Node getSource() {
    return this.source;
  }

  public Node getTarget() {
    return this.target;
  }
  
  public Terminal getSourceTerminal() {
    return sourceTerminal;
  }
  
  public Terminal getTargetTerminal() {
    return targetTerminal;
  }
  
}
