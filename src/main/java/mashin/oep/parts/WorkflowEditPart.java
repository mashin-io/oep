package mashin.oep.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import mashin.oep.model.ModelElement;
import mashin.oep.model.workflow.Workflow;

import org.eclipse.draw2d.ConnectionLayer;
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
    connLayer.setConnectionRouter(new ShortestPathConnectionRouter(f));

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
  public void propertyChange(PropertyChangeEvent evt) {
    
  }

  @Override
  protected void createEditPolicies() {
    installEditPolicy(EditPolicy.NODE_ROLE, null);
    installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, null);
    installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, null);
    installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
  }
}
