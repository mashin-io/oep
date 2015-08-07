package mashin.oep.model.node;

import java.util.ArrayList;
import java.util.List;

import mashin.oep.model.HPDLSerializable;
import mashin.oep.model.ModelElementWithSchema;
import mashin.oep.model.workflow.Workflow;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public abstract class Node extends ModelElementWithSchema implements HPDLSerializable {
  private static final String PROP_CONNECTION_SOURCE = "prop.node.connection.source";
  private static final String PROP_CONNECTION_TARGET = "prop.node.connection.target";
  private static final String PROP_POS = "prop.node.pos";
  private static final String PROP_POS_X = "prop.node.pos.x";
  private static final String PROP_POS_Y = "prop.node.pos.y";
  
  private static final IPropertyDescriptor[] NODE_PROPERTY_DESCRIPTORS = new IPropertyDescriptor[] {
    new TextPropertyDescriptor(PROP_POS_X, "X"),
    new TextPropertyDescriptor(PROP_POS_Y, "Y")
  };
  
  static {
    for(IPropertyDescriptor descriptor: NODE_PROPERTY_DESCRIPTORS) {
      ((PropertyDescriptor) descriptor)
      .setValidator(new ICellEditorValidator() {
        @Override
        public String isValid(Object value) {
          int intValue = -1;
          try {
            intValue = Integer.parseInt((String) value);
          } catch (NumberFormatException exc) {
            return "Not a number";
          }
          return (intValue >= 0) ? null
              : "Value must be >=  0";
        }
      });
    }
  }
  
  private Point position;
  
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
    this.position = new Point();
    this.sourceConnections = new ArrayList<Connection>();
    this.targetConnections = new ArrayList<Connection>();
  }
  
  @Override
  public IPropertyDescriptor[] getPropertyDescriptors() {
    return combine(super.getPropertyDescriptors(), NODE_PROPERTY_DESCRIPTORS);
  }
  
  @Override
  public void setPropertyValue(Object propertyName, Object propertyValue) {
    switch((String) propertyName) {
    case PROP_POS_X:
      setPosition(new Point(Integer.parseInt((String) propertyValue), position.y));
      break;
    case PROP_POS_Y:
      setPosition(new Point(position.x, Integer.parseInt((String) propertyValue)));
      break;
    default:
      super.setPropertyValue(propertyName, propertyValue);
    }
  }
  
  @Override
  public Object getPropertyValue(Object propertyName) {
    switch((String) propertyName) {
    case PROP_POS_X:
      return getPosition().x;
    case PROP_POS_Y:
      return getPosition().y;
    default:
      return super.getPropertyValue(propertyName);
    }
  }
  
  public Point getPosition() {
    return this.position;
  }
  
  public void setPosition(Point point) {
    Point oldPosition = this.position.getCopy();
    this.position.x = point.x;
    this.position.y = point.y;
    firePropertyChange(PROP_POS, oldPosition, point);
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
