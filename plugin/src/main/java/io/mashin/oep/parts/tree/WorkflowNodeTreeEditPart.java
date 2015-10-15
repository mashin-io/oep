package io.mashin.oep.parts.tree;

import io.mashin.oep.figures.NodeFigure;
import io.mashin.oep.model.connection.WorkflowConnection;
import io.mashin.oep.model.editPolicies.NodeComponentEditPolicy;
import io.mashin.oep.model.node.Node;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;

public class WorkflowNodeTreeEditPart extends AbstractTreeEditPart implements
    PropertyChangeListener {

  public WorkflowNodeTreeEditPart(Node node) {
    super(node);
  }
  
  public void activate() {
    super.activate();
    getNode().addPropertyChangeListener(this);
  }

  protected void createEditPolicies() {
    installEditPolicy(EditPolicy.COMPONENT_ROLE, new NodeComponentEditPolicy());
    //installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new LogicTreeEditPolicy());
  }

  public void deactivate() {
    getNode().removePropertyChangeListener(this);
    super.deactivate();
  }

  protected Node getNode() {
    return (Node) getModel();
  }

  protected List<WorkflowConnection> getModelChildren() {
    List<WorkflowConnection> children = new ArrayList<>();
    children.addAll(getNode().getSourceConnections());
    children.addAll(getNode().getTargetConnections());
    return children;
  }

  public void propertyChange(PropertyChangeEvent change) {
    switch (change.getPropertyName()) {
    case Node.PROP_CONNECTION_SOURCE:
    case Node.PROP_CONNECTION_TARGET:
      //if (change.getNewValue() != null) {
      //  boolean isSource = change.getPropertyName().equals(Node.PROP_CONNECTION_SOURCE);
      //  addChild(createChild(change.getNewValue()), isSource ? 0 : getChildren().size());
      //} else {
      //  removeChild((EditPart) getViewer().getEditPartRegistry().get(change.getOldValue()));
      //}
      refreshChildren();
      break;
      
    default:
      refreshVisuals();
      break;
    }
  }

  protected void refreshVisuals() {
    Image image = NodeFigure.getNodeFigureImage(getNode().getNodeType());
    //TreeItem item = (TreeItem) getWidget();
    //if (image != null)
    //  image.setBackground(item.getParent().getBackground());
    setWidgetImage(image);
    setWidgetText(getNode().getName() + ":" + getNode().getNodeType());
  }

}
