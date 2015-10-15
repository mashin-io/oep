package io.mashin.oep.parts.tree;

import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.node.Node;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

public class WorkflowTreeEditPart extends AbstractTreeEditPart implements
    PropertyChangeListener {

  public WorkflowTreeEditPart(Workflow workflow) {
    super(workflow);
  }

  public void activate() {
    super.activate();
    getWorkflow().addPropertyChangeListener(this);
  }

  protected void createEditPolicies() {
    installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
    //installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new LogicTreeEditPolicy());
  }

  public void deactivate() {
    getWorkflow().removePropertyChangeListener(this);
    super.deactivate();
  }

  protected Workflow getWorkflow() {
    return (Workflow) getModel();
  }

  protected List<Node> getModelChildren() {
    return getWorkflow().getNodes();
  }

  public void propertyChange(PropertyChangeEvent change) {
    switch (change.getPropertyName()) {
    //case Workflow.PROP_NODE_ADDED:
    //  addChild(createChild(change.getNewValue()), getChildren().size());
    //  break;
    //  
    //case Workflow.PROP_NODE_REMOVED:
    //  removeChild((EditPart) getViewer().getEditPartRegistry().get(change.getOldValue()));
    //  break;
    case Workflow.PROP_NODE_ADDED:
    case Workflow.PROP_NODE_REMOVED:
      refreshChildren();
      break;
      
    default:
      refreshVisuals();
      break;
    }
  }

}
