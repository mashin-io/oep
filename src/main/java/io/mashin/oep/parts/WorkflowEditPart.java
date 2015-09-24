package io.mashin.oep.parts;

import io.mashin.oep.model.ModelElement;
import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.editPolicies.WorkflowXYLayoutEditPolicy;
import io.mashin.oep.model.node.Node;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FanRouter;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

public class WorkflowEditPart extends AbstractGraphicalEditPart implements
    PropertyChangeListener {
  
  /**
   * Upon activation, attach to the model element as a property change
   * listener.
   */
  public void activate() {
    if (!isActive()) {
      super.activate();
      ((ModelElement) getModel()).addPropertyChangeListener(this);
    }
  }
  
  @Override
  protected IFigure createFigure() {
    Figure f = new FreeformLayer();
    f.setBorder(new MarginBorder(3));
    f.setLayoutManager(new FreeformLayout());

    // Create the static router for the connection layer
    ConnectionLayer connLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
    FanRouter router = new FanRouter();
    router.setNextRouter(new ShortestPathConnectionRouter(f));
    connLayer.setConnectionRouter(router);

    return f;
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
  
  private Workflow getCastedModel() {
    return (Workflow) getModel();
  }
  
  @Override
  public List<Node> getModelChildren() {
    return getCastedModel().getNodes();
  }
  
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String prop = evt.getPropertyName();
    // these properties are fired when Nodes are added into or removed from
    // the Workflow instance and must cause a call of refreshChildren()
    // to update the diagram's contents.
    if (Workflow.PROP_NODE_ADDED.equals(prop)
        || Workflow.PROP_NODE_REMOVED.equals(prop)) {
      refreshChildren();
    }
  }

  @Override
  protected void createEditPolicies() {
    installEditPolicy(EditPolicy.NODE_ROLE, null);
    installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, null);
    installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, null);
    installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
    // handles constraint changes (e.g. moving) of model elements
    // and creation of new model elements
    installEditPolicy(EditPolicy.LAYOUT_ROLE, new WorkflowXYLayoutEditPolicy());
  }
}
