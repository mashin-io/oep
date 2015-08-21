package mashin.oep.model.terminal;

import java.util.ArrayList;
import java.util.List;

import mashin.oep.model.ModelElement;
import mashin.oep.model.connection.WorkflowConnection;
import mashin.oep.model.node.Node;

public abstract class Terminal extends ModelElement {

  public static final String PROP_CONNECTION_ADD    = "prop.terminal.connection.add";
  public static final String PROP_CONNECTION_REMOVE = "prop.terminal.connection.remove";
  
  protected String label;
  protected List<WorkflowConnection> connections;
  protected Node holderNode;
  
  public Terminal(String label, Node holderNode) {
    this.label = label;
    this.holderNode = holderNode;
    connections = new ArrayList<WorkflowConnection>();
  }
  
  @Override
  public void setPropertyValue(Object propertyName, Object propertyValue) {
    switch ((String) propertyName) {
    case PROP_CONNECTION_ADD:
      addConnectionInitiate((WorkflowConnection) propertyValue);
      break;
    case PROP_CONNECTION_REMOVE:
      removeConnectionInitiate((WorkflowConnection) propertyValue);
      break;
    default:
      super.setPropertyValue(propertyName, propertyValue);
    }
  }
  
  public String getLabel() {
    return label;
  }
  
  public List<WorkflowConnection> getConnections() {
    return new ArrayList<WorkflowConnection>(connections);
  }
  
  public boolean hasConnection(WorkflowConnection connection) {
    if (connections.contains(connection)) {
      return true;
    }
    return false;
  }
  
  public abstract boolean canAddConnection(WorkflowConnection connection);
  
  public boolean addConnectionInitiate(WorkflowConnection connection) {
    if (!canAddConnection(connection)
        || !(holderNode == connection.getSourceNode() 
            && holderNode.canConnectTo(connection.getTargetNode()))
        || !(holderNode == connection.getTargetNode() 
            && holderNode.canConnectFrom(connection.getSourceNode()))) {
      return false;
    }
    boolean success = connections.add(connection);
    if (success) {
      holderNode.addConnectionUpdate(connection);
      firePropertyChange(PROP_CONNECTION_ADD, null, connection);
    }
    return success;
  }
  
  public void addConnectionUpdate(WorkflowConnection connection) {
    boolean success = connections.add(connection);
    if (success) {
      firePropertyChange(PROP_CONNECTION_ADD, null, connection);
    }
  }
  
  public boolean removeConnectionInitiate(WorkflowConnection connection) {
    boolean success = connections.remove(connection);
    if (success) {
      holderNode.removeConnectionUpdate(connection);
      firePropertyChange(PROP_CONNECTION_REMOVE, connection, null);
    }
    return success;
  }
  
  public void removeConnectionUpdate(WorkflowConnection connection) {
    boolean success = connections.remove(connection);
    if (success) {
      firePropertyChange(PROP_CONNECTION_REMOVE, connection, null);
    }
  }
  
}
