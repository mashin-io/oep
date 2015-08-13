package mashin.oep.model.node;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import mashin.oep.Utils;
import mashin.oep.model.HPDLSerializable;
import mashin.oep.model.ModelElementWithSchema;
import mashin.oep.model.Workflow;
import mashin.oep.model.connection.WorkflowConnection;
import mashin.oep.model.property.PointPropertyElement;
import mashin.oep.model.property.TextPropertyElement;
import mashin.oep.model.terminal.InputTerminal;
import mashin.oep.model.terminal.OutputTerminal;
import mashin.oep.model.terminal.Terminal;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public abstract class Node extends ModelElementWithSchema implements HPDLSerializable {
  
  public static final AtomicLong ID_SEQ = new AtomicLong(0);
  
  public static final String TERMINAL_NONE    = "";
  public static final String TERMINAL_FANIN   = "fan-in";
  public static final String TERMINAL_FANOUT  = "fan-out";
  public static final String TERMINAL_CASE    = "case";
  public static final String TERMINAL_DEFAULT = "default";
  public static final String TERMINAL_OUT     = "out";
  public static final String TERMINAL_OK      = "ok";
  public static final String TERMINAL_ERROR   = "error";
  
  public static final String PROP_CONNECTION_SOURCE  = "prop.node.connection.source";
  public static final String PROP_CONNECTION_TARGET  = "prop.node.connection.target";
  public static final String PROP_POS                = "prop.node.pos";
  public static final String PROP_NODE_NAME          = "prop.node.name";
  
  private static IPropertyDescriptor[] NODE_PROPERTY_DESCRIPTORS;

  protected TextPropertyElement  name;
  protected PointPropertyElement position;
  
  protected Workflow workflow;
  
  protected List<Terminal> terminals;
  
  /**
   * List of connections at which this node is a source
   */
  protected List<WorkflowConnection> sourceConnections;
  
  /**
   * List of connections at which this node is a target
   */
  protected List<WorkflowConnection> targetConnections;
  
  public Node(Workflow workflow) {
    this.workflow = workflow;
    this.name = new TextPropertyElement(PROP_NODE_NAME, "Name");
    this.position = new PointPropertyElement(PROP_POS, "Position");
    this.terminals = new ArrayList<Terminal>();
    this.sourceConnections = new ArrayList<WorkflowConnection>();
    this.targetConnections = new ArrayList<WorkflowConnection>();
  }
  
  @Override
  public IPropertyDescriptor[] getPropertyDescriptors() {
    if(NODE_PROPERTY_DESCRIPTORS == null) {
      NODE_PROPERTY_DESCRIPTORS = Utils.combine(
          name.getPropertyDescriptors(),
          position.getPropertyDescriptors());
    }
    return Utils.combine(super.getPropertyDescriptors(), NODE_PROPERTY_DESCRIPTORS);
  }
  
  @Override
  public void setPropertyValue(Object propertyName, Object propertyValue) {
    String propertyNameStr = (String) propertyName;
    switch(propertyNameStr) {
    case PROP_NODE_NAME:
      setName((String) propertyValue);
      break;
    default:
      if (position.hasId(propertyNameStr)) {
        Object oldValue = position.getValue(propertyNameStr);
        position.setValue(propertyNameStr, propertyValue);
        firePropertyChange(propertyNameStr, oldValue, propertyValue);
      } else {
        super.setPropertyValue(propertyName, propertyValue);
      }
    }
  }
  
  @Override
  public Object getPropertyValue(Object propertyName) {
    String propertyNameStr = (String) propertyName;
    switch(propertyNameStr) {
    case PROP_NODE_NAME:
      return getName();
    default:
      if (position.hasId(propertyNameStr)) {
        return position.getValue(propertyNameStr);
      } else {
        return super.getPropertyValue(propertyName);
      }
    }
  }
  
  public Point getPosition() {
    return this.position.getAsPoint();
  }
  
  public void setPosition(Point point) {
    Point oldPosition = this.position.getAsPoint();
    this.position.setFromPoint(point);
    firePropertyChange(PROP_POS, oldPosition, point);
  }
  
  public String getName() {
    return name.getStringValue();
  }
  
  public void setName(String name) {
    String oldName = this.name.getStringValue();
    this.name.setStringValue(name);
    firePropertyChange(PROP_NODE_NAME, oldName, name);
  }
  
  public List<WorkflowConnection> getSourceConnections() {
    return this.sourceConnections;
  }
  
  public List<WorkflowConnection> getTargetConnections() {
    return this.targetConnections;
  }
  
  public boolean addConnectionInitiate(WorkflowConnection connection) {
    if (connection.getSource() == connection.getTarget()) {
      
      return false;
      
    } else if (connection.getSource() == this) {
      
      if (!canConnectTo(connection.getTarget())
          || !connection.getSourceTerminal().canAddConnection(connection)) {
        return false;
      }
      
      boolean success = sourceConnections.add(connection);
      if (success) {
        connection.getSourceTerminal().addConnectionUpdate(connection);
        firePropertyChange(PROP_CONNECTION_SOURCE, null, connection);
      }
      return success;
      
    } else if (connection.getTarget() == this) {
      
      if (!canConnectFrom(connection.getSource())
          || !connection.getTargetTerminal().canAddConnection(connection)) {
        return false;
      }
      
      boolean success = targetConnections.add(connection);
      if (success) {
        connection.getTargetTerminal().addConnectionUpdate(connection);
        firePropertyChange(PROP_CONNECTION_TARGET, null, connection);
      }
      return success;
      
    }
    
    return false;
  }
  
  public void addConnectionUpdate(WorkflowConnection connection) {
    if (connection.getSource() == this) {
      boolean success = sourceConnections.add(connection);
      if (success) {
        firePropertyChange(PROP_CONNECTION_SOURCE, null, connection);
      }
    } else if (connection.getTarget() == this) {
      boolean success = targetConnections.add(connection);
      if (success) {
        firePropertyChange(PROP_CONNECTION_TARGET, null, connection);
      }
    }
  }
  
  public void removeConnectionInitiate(WorkflowConnection connection) {
    if (connection.getSource() == this) {
      boolean success = sourceConnections.remove(connection);
      if (success) {
        connection.getSourceTerminal().removeConnectionUpdate(connection);
        firePropertyChange(PROP_CONNECTION_SOURCE, connection, null);
      }
    } else if (connection.getTarget() == this) {
      boolean success = targetConnections.remove(connection);
      if (success) {
        connection.getTargetTerminal().removeConnectionUpdate(connection);
        firePropertyChange(PROP_CONNECTION_TARGET, connection, null);
      }
    }
  }
  
  public void removeConnectionUpdate(WorkflowConnection connection) {
    if (connection.getSource() == this) {
      boolean success = sourceConnections.remove(connection);
      if (success) {
        firePropertyChange(PROP_CONNECTION_SOURCE, connection, null);
      }
    } else if (connection.getTarget() == this) {
      boolean success = targetConnections.remove(connection);
      if (success) {
        firePropertyChange(PROP_CONNECTION_TARGET, connection, null);
      }
    }
  }
  
  public abstract boolean canConnectTo(Node target);
  public abstract boolean canConnectFrom(Node source);
  
  public List<Terminal> getTerminals() {
    return new ArrayList<Terminal>(terminals);
  }
  
  public List<Terminal> getInputTerminals() {
    return terminals.stream()
        .filter(t -> t instanceof InputTerminal)
        .collect(Collectors.toList());
  }
  
  public List<Terminal> getOutputTerminals() {
    return terminals.stream()
        .filter(t -> t instanceof OutputTerminal)
        .collect(Collectors.toList());
  }
  
  public Terminal getTerminal(String label) {
    for (Terminal terminal : terminals) {
      if (terminal.getLabel().equals(label)) {
        return terminal;
      }
    }
    return null;
  }
  
}
