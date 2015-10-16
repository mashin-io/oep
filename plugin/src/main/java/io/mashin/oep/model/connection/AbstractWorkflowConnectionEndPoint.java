package io.mashin.oep.model.connection;

import io.mashin.oep.model.ModelElement;

public abstract class AbstractWorkflowConnectionEndPoint<N, T> extends ModelElement {

  protected N node;
  protected T terminal;
  
  public AbstractWorkflowConnectionEndPoint(N node, T terminal) {
    this.node = node;
    this.terminal = terminal;
  }
  
  public N getNode() {
    return node;
  }
  
  public T getTerminal() {
    return terminal;
  }
  
  public void setNode(N node) {
    this.node = node;
  }
  
  public void setTerminal(T terminal) {
    this.terminal = terminal;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null 
        || !(obj instanceof AbstractWorkflowConnectionEndPoint)) {
      return false;
    }
    
    if (this == obj) {
      return true;
    }
    
    @SuppressWarnings("unchecked")
    AbstractWorkflowConnectionEndPoint<N, T> other = 
        (AbstractWorkflowConnectionEndPoint<N, T>) obj;
    return this.node.equals(other.node)
        && this.terminal.equals(other.terminal);
  }
  
  @Override
  public String toString() {
    return node.toString() + ":" + terminal.toString();
  }
  
}
