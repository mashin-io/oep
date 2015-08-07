package mashin.oep.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import mashin.oep.model.ModelElement;
import mashin.oep.model.Workflow;
import mashin.oep.model.commands.ConnectionCreateCommand;
import mashin.oep.model.commands.ConnectionReconnectCommand;
import mashin.oep.model.commands.NodeDeleteCommand;
import mashin.oep.model.node.Connection;
import mashin.oep.model.node.Node;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.requests.ReconnectRequest;

class WorkflowNodeEditPart extends AbstractGraphicalEditPart implements
    PropertyChangeListener, NodeEditPart {

  private ConnectionAnchor anchor;

  /**
   * Upon activation, attach to the model element as a property change listener.
   */
  public void activate() {
    if (!isActive()) {
      super.activate();
      ((ModelElement) getModel()).addPropertyChangeListener(this);
    }
  }

  protected void createEditPolicies() {
    // allow removal of the associated model element
    installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentEditPolicy() {
      protected Command createDeleteCommand(GroupRequest deleteRequest) {
        Object parent = getHost().getParent().getModel();
        Object child = getHost().getModel();
        if (parent instanceof Workflow && child instanceof Node) {
          return new NodeDeleteCommand((Workflow) parent, (Node) child);
        }
        return super.createDeleteCommand(deleteRequest);
      }
    });
    
    // allow the creation of connections and
    // and the reconnection of connections between Shape instances
    installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
        new GraphicalNodeEditPolicy() {
          protected Command getConnectionCompleteCommand(
              CreateConnectionRequest request) {
            ConnectionCreateCommand cmd = (ConnectionCreateCommand) request
                .getStartCommand();
            cmd.setTarget((Node) getHost().getModel());
            return cmd;
          }

          protected Command getConnectionCreateCommand(
              CreateConnectionRequest request) {
            Node source = (Node) getHost().getModel();
            ConnectionCreateCommand cmd = new ConnectionCreateCommand(source);
            request.setStartCommand(cmd);
            return cmd;
          }

          protected Command getReconnectSourceCommand(ReconnectRequest request) {
            Connection conn = (Connection) request.getConnectionEditPart()
                .getModel();
            Node newSource = (Node) getHost().getModel();
            ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(
                conn);
            cmd.setNewSource(newSource);
            return cmd;
          }

          protected Command getReconnectTargetCommand(ReconnectRequest request) {
            Connection conn = (Connection) request.getConnectionEditPart()
                .getModel();
            Node newTarget = (Node) getHost().getModel();
            ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(
                conn);
            cmd.setNewTarget(newTarget);
            return cmd;
          }
        });
  }

  protected IFigure createFigure() {
    IFigure f = createFigureForModel();
    f.setOpaque(true); // non-transparent figure
    f.setBackgroundColor(ColorConstants.lightGray);
    return f;
  }

  /**
   * Return a IFigure depending on the instance of the current model element.
   * This allows this EditPart to be used for both sublasses of Node.
   */
  private IFigure createFigureForModel() {
    RectangleFigure rectangleFigure = new RectangleFigure();
    rectangleFigure.setSize(50, 50);
    return rectangleFigure;
  }
  
  /**
   * Upon deactivation, detach from the model element as a property change
   * listener.
   */
  public void deactivate() {
    if (isActive()) {
      super.deactivate();
      ((ModelElement) getModel()).removePropertyChangeListener(this);
    }
  }

  private Node getCastedModel() {
    return (Node) getModel();
  }

  protected ConnectionAnchor getConnectionAnchor() {
    if (anchor == null) {
      anchor = new ChopboxAnchor(getFigure());
    }
    return anchor;
  }

  protected List<Connection> getModelSourceConnections() {
    return getCastedModel().getSourceConnections();
  }

  protected List<Connection> getModelTargetConnections() {
    return getCastedModel().getTargetConnections();
  }

  public ConnectionAnchor getSourceConnectionAnchor(
      ConnectionEditPart connection) {
    return getConnectionAnchor();
  }

  public ConnectionAnchor getSourceConnectionAnchor(Request request) {
    return getConnectionAnchor();
  }

  public ConnectionAnchor getTargetConnectionAnchor(
      ConnectionEditPart connection) {
    return getConnectionAnchor();
  }

  public ConnectionAnchor getTargetConnectionAnchor(Request request) {
    return getConnectionAnchor();
  }

  public void propertyChange(PropertyChangeEvent evt) {
    String prop = evt.getPropertyName();
    if (Node.PROP_POS.equals(prop)) {
      refreshVisuals();
    } else if (Node.PROP_CONNECTION_SOURCE.equals(prop)) {
      refreshSourceConnections();
    } else if (Node.PROP_CONNECTION_TARGET.equals(prop)) {
      refreshTargetConnections();
    }
  }

//  protected void refreshVisuals() {
//    // notify parent container of changed position & location
//    // if this line is removed, the XYLayoutManager used by the parent
//    // container
//    // (the Figure of the ShapesDiagramEditPart), will not know the bounds
//    // of this figure
//    // and will not draw it correctly.
//    Rectangle bounds = new Rectangle(getCastedModel().getPosition(),
//        getCastedModel().getSize());
//    ((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(),
//        bounds);
//  }
  
}