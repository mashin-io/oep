package mashin.oep.model.node;

import java.util.ArrayList;
import java.util.List;

import mashin.oep.Utils;
import mashin.oep.model.HPDLSerializable;
import mashin.oep.model.ModelElementWithSchema;
import mashin.oep.model.property.PointPropertyElement;
import mashin.oep.model.workflow.Workflow;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public abstract class Node extends ModelElementWithSchema implements HPDLSerializable {
  
  private static final String PROP_CONNECTION_SOURCE  = "prop.node.connection.source";
  private static final String PROP_CONNECTION_TARGET  = "prop.node.connection.target";
  private static final String PROP_POS                = "prop.node.pos";
  
  private static IPropertyDescriptor[] NODE_PROPERTY_DESCRIPTORS;
  
  private PointPropertyElement position;
  
  protected Workflow workflow;
  
  /**
   * List of connections at which this node is a source
   */
  private List<Connection> sourceConnections;
  /**
   * List of connections at which this node is a target
   */
  private List<Connection> targetConnections;
  
  public Node(Workflow workflow) {
    this.workflow = workflow;
    this.position = new PointPropertyElement(PROP_POS, "Position");
    this.sourceConnections = new ArrayList<Connection>();
    this.targetConnections = new ArrayList<Connection>();
  }
  
  @Override
  public IPropertyDescriptor[] getPropertyDescriptors() {
    if(NODE_PROPERTY_DESCRIPTORS == null) {
      NODE_PROPERTY_DESCRIPTORS = Utils.combine(position.getPropertyDescriptors());
    }
    return Utils.combine(super.getPropertyDescriptors(), NODE_PROPERTY_DESCRIPTORS);
  }
  
  @Override
  public void setPropertyValue(Object propertyName, Object propertyValue) {
    String propertyNameStr = (String) propertyName;
    switch(propertyNameStr) {
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
    Object oldPosition = this.position.getValue();
    this.position.setFromPoint(point);
    firePropertyChange(PROP_POS, oldPosition, this.position.getValue());
  }
  
  public List<Connection> getSourceConnections() {
    return this.sourceConnections;
  }
  
  public List<Connection> getTargetConnections() {
    return this.targetConnections;
  }
  
  public void addConnection(Connection connection) {
    if (connection.getSource() == connection.getTarget()) {
      return;
    } else if (connection.getSource() == this) {
      sourceConnections.add(connection);
      firePropertyChange(PROP_CONNECTION_SOURCE, null, connection);
    } else if (connection.getTarget() == this) {
      targetConnections.add(connection);
      firePropertyChange(PROP_CONNECTION_TARGET, null, connection);
    }
  }
  
  public void removeConnection(Connection connection) {
    if (connection.getSource() == this) {
      sourceConnections.remove(connection);
      firePropertyChange(PROP_CONNECTION_SOURCE, connection, null);
    } else if (connection.getTarget() == this) {
      targetConnections.remove(connection);
      firePropertyChange(PROP_CONNECTION_TARGET, connection, null);
    }
  }
  
  public abstract boolean canConnectTo(Node target);
  public abstract boolean canConnectFrom(Node source);
  
}
