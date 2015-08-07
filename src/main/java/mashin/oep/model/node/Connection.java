package mashin.oep.model.node;

import mashin.oep.model.ModelElement;

public class Connection extends ModelElement {
  private boolean isConnected;

  private Node source;
  private Node target;

  public Connection(Node source, Node target) {
    reconnect(source, target);
  }

  public void disconnect() {
    if (isConnected) {
      source.removeConnection(this);
      target.removeConnection(this);
      isConnected = false;
    }
  }

  public void reconnect() {
    if (!isConnected) {
      source.addConnection(this);
      target.addConnection(this);
      isConnected = true;
    }
  }

  public void reconnect(Node newSource, Node newTarget) {
    if (newSource == null || newTarget == null || newSource == newTarget) {
      throw new IllegalArgumentException();
    }
    disconnect();
    this.source = newSource;
    this.target = newTarget;
    reconnect();
  }

  public Node getSource() {
    return this.source;
  }

  public Node getTarget() {
    return this.target;
  }
}
