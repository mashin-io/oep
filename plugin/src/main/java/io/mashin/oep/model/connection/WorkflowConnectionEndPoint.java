package io.mashin.oep.model.connection;

import io.mashin.oep.model.ModelElement;
import io.mashin.oep.model.node.Node;
import io.mashin.oep.model.terminal.Terminal;

public class WorkflowConnectionEndPoint extends ModelElement {

  private Object node;
  private Object terminal;
  
  public WorkflowConnectionEndPoint(Object node, Object terminal) {
    this.node = node;
    this.terminal = terminal;
  }
  
  public String getNodeAsString() {
    return (String) node;
  }
  
  public String getTerminalAsString() {
    return (String) terminal;
  }
  
  public Node getNode() {
    return (Node) node;
  }
  
  public Terminal getTerminal() {
    return (Terminal) terminal;
  }
  
  public void setNode(Node node) {
    this.node = node;
  }
  
  public void setTerminal(Terminal terminal) {
    this.terminal = terminal;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof WorkflowConnectionEndPoint)) {
      return false;
    }
    
    if (this == obj) {
      return true;
    }
    
    WorkflowConnectionEndPoint other = (WorkflowConnectionEndPoint) obj;
    return this.node.equals(other.node)
        && this.terminal.equals(other.terminal);
  }
  
  @Override
  public String toString() {
    String terminalStr;
    if (terminal instanceof Terminal) {
      terminalStr = getTerminal().getLabel();
    } else {
      terminalStr = getTerminalAsString();
    }
    return node.toString() + ":" + terminalStr;
  }
  
}
