package mashin.oep.model.connection;

import mashin.oep.model.ModelElement;
import mashin.oep.model.node.Node;
import mashin.oep.model.terminal.Terminal;

public class WorkflowConnection extends ModelElement {
  
  public static final String PROP_CONN_SOURCE_NODE     = "prop.conn.source.node";
  public static final String PROP_CONN_SOURCE_TERMINAL = "prop.conn.source.terminal";
  public static final String PROP_CONN_TARGET_NODE     = "prop.conn.target.node";
  public static final String PROP_CONN_TARGET_TERMINAL = "prop.conn.target.terminal";
  
  private boolean isConnected;

  private Node sourceNode;
  private Node targetNode;
  
  private Terminal sourceTerminal;
  private Terminal targetTerminal;

  public WorkflowConnection(Node source, Node target, Terminal sourceTerminal,
      Terminal targetTerminal) {
    this.sourceNode = source;
    this.targetNode = target;
    this.sourceTerminal = sourceTerminal;
    this.targetTerminal = targetTerminal;
  }

  public void disconnect() {
    if (isConnected) {
      sourceNode.removeConnectionInitiate(this);
      targetNode.removeConnectionInitiate(this);
      isConnected = false;
    }
  }

  public void reconnect() {
    if (!isConnected) {
      sourceNode.addConnectionInitiate(this);
      targetNode.addConnectionInitiate(this);
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
    this.sourceNode = source;
    this.targetNode = target;
    this.sourceTerminal = sourceTerminal;
    this.targetTerminal = targetTerminal;
    reconnect();
  }

  public Node getSource() {
    return this.sourceNode;
  }

  public Node getTarget() {
    return this.targetNode;
  }
  
  public Terminal getSourceTerminal() {
    return sourceTerminal;
  }
  
  public Terminal getTargetTerminal() {
    return targetTerminal;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof WorkflowConnection)) {
      return false;
    }
    
    if (this == obj) {
      return true;
    }
    
    WorkflowConnection other = (WorkflowConnection) obj;
    return this.sourceNode.equals(other.sourceNode)
        && this.sourceTerminal.equals(other.sourceTerminal)
        && this.targetNode.equals(other.targetNode)
        && this.targetTerminal.equals(other.targetTerminal);
  }
  
}
