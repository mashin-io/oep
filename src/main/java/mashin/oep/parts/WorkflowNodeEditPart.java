package mashin.oep.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import mashin.oep.figures.NodeFigure;
import mashin.oep.model.ModelElement;
import mashin.oep.model.editPolicies.NodeComponentEditPolicy;
import mashin.oep.model.editPolicies.NodeGraphicalNodeEditPolicy;
import mashin.oep.model.editPolicies.NodeLabelDirectEditPolicy;
import mashin.oep.model.node.Connection;
import mashin.oep.model.node.Node;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.viewers.TextCellEditor;

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
    // allow direct edit of node label
    installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new NodeLabelDirectEditPolicy());
    
    // allow removal of the associated model element
    installEditPolicy(EditPolicy.COMPONENT_ROLE, new NodeComponentEditPolicy());
    
    // allow the creation of connections and
    // and the reconnection of connections between Shape instances
    installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new NodeGraphicalNodeEditPolicy());
  }

  @Override
  public void performRequest(Request req) {
    if (req.getType() == RequestConstants.REQ_DIRECT_EDIT) {
      performDirectEditing();
    } else {
      super.performRequest(req);
    }
  }
  
  private void performDirectEditing() {
    Label label = ((NodeFigure) getFigure()).getLabelFigure();
    NodeLabelDirectEditManager manager = new NodeLabelDirectEditManager(this,
        TextCellEditor.class, new NodeLabelCellEditorLocator(label), label);
    manager.show();
  }
  
  protected IFigure createFigure() {
    IFigure f = new NodeFigure();
    f.setLocation(getCastedModel().getPosition());
    return f;
  }

  /**
   * Return a IFigure depending on the instance of the current model element.
   * This allows this EditPart to be used for all sublasses of Node.
   */
  private IFigure createFigureForModel() {
    RectangleFigure rectangleFigure = new RectangleFigure();
    rectangleFigure.setPreferredSize(30, 40);
    rectangleFigure.setLocation(getCastedModel().getPosition());
    rectangleFigure.setSize(rectangleFigure.getPreferredSize());
    rectangleFigure.setOpaque(true);
    rectangleFigure.setBackgroundColor(ColorConstants.lightGray);
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
  
  private NodeFigure getCastedFigure() {
    return (NodeFigure) getFigure();
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
    switch(prop) {
    case Node.PROP_POS:
    case Node.PROP_NODE_NAME:
      refreshVisuals();
      break;
    case Node.PROP_CONNECTION_SOURCE:
      refreshSourceConnections();
      break;
    case Node.PROP_CONNECTION_TARGET:
      refreshTargetConnections();
      break;
    }
  }

  protected void refreshVisuals() {
    NodeFigure nodeFigure = getCastedFigure();
    Node nodeModel = getCastedModel();
    
    nodeFigure.setLabel(nodeModel.getName());
    
    // notify parent container of changed position & location
    // if this line is removed, the XYLayoutManager used by the parent
    // container
    // (the Figure of the WorkflowEditPart), will not know the bounds
    // of this figure
    // and will not draw it correctly.
    Rectangle bounds = new Rectangle(nodeModel.getPosition(), nodeFigure.getSize());
    ((GraphicalEditPart) getParent()).setLayoutConstraint(this, nodeFigure, bounds);
  }
  
}