package mashin.oep.model.terminal;

import java.util.ArrayList;
import java.util.List;

import mashin.oep.model.ModelElement;
import mashin.oep.model.node.Connection;
import mashin.oep.model.node.Node;

public abstract class Terminal extends ModelElement {

  public static final String PROP_CONNECTION_ADD    = "prop.terminal.connection.add";
  public static final String PROP_CONNECTION_REMOVE = "prop.terminal.connection.remove";
  
  protected String label;
  protected List<Connection> connections;
  protected Node holderNode;
  
  public Terminal(String label, Node holderNode) {
    this.label = label;
    this.holderNode = holderNode;
    connections = new ArrayList<Connection>();
  }
  
  @Override
  public void setPropertyValue(Object propertyName, Object propertyValue) {
    switch ((String) propertyName) {
    case PROP_CONNECTION_ADD:
      addConnectionInitiate((Connection) propertyValue);
      break;
    case PROP_CONNECTION_REMOVE:
      removeConnectionInitiate((Connection) propertyValue);
      break;
    default:
      super.setPropertyValue(propertyName, propertyValue);
    }
  }
  
  public String getLabel() {
    return label;
  }
  
  public List<Connection> getConnections() {
    return new ArrayList<Connection>(connections);
  }
  
  public boolean hasConnection(Connection connection) {
    if (connections.contains(connection)) {
      return true;
    }
    for (Connection myConnection : connections) {
      if (connection.getSource().equals(myConnection.getSource())
          && connection.getTarget().equals(myConnection.getTarget())) {
        return true;
      }
    }
    return false;
  }
  
  public abstract boolean canAddConnection(Connection connection);
  
  public boolean addConnectionInitiate(Connection connection) {
    if (!canAddConnection(connection)
        || !(holderNode == connection.getSource() 
            && holderNode.canConnectTo(connection.getTarget()))
        || !(holderNode == connection.getTarget() 
            && holderNode.canConnectFrom(connection.getSource()))) {
      return false;
    }
    boolean success = connections.add(connection);
    if (success) {
      holderNode.addConnectionUpdate(connection);
      firePropertyChange(PROP_CONNECTION_ADD, null, connection);
    }
    return success;
  }
  
  public void addConnectionUpdate(Connection connection) {
    boolean success = connections.add(connection);
    if (success) {
      firePropertyChange(PROP_CONNECTION_ADD, null, connection);
    }
  }
  
  public boolean removeConnectionInitiate(Connection connection) {
    boolean success = connections.remove(connection);
    if (success) {
      holderNode.removeConnectionUpdate(connection);
      firePropertyChange(PROP_CONNECTION_REMOVE, connection, null);
    }
    return success;
  }
  
  public void removeConnectionUpdate(Connection connection) {
    boolean success = connections.remove(connection);
    if (success) {
      firePropertyChange(PROP_CONNECTION_REMOVE, connection, null);
    }
  }
  
}
