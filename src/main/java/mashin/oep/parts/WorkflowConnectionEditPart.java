package mashin.oep.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import mashin.oep.model.ModelElement;
import mashin.oep.model.WorkflowConnection;
import mashin.oep.model.editPolicies.WorkflowConnectionEditPolicy;
import mashin.oep.model.editPolicies.WorkflowConnectionEndpointEditPolicy;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;

public class WorkflowConnectionEditPart extends AbstractConnectionEditPart
    implements PropertyChangeListener {

  @Override
  public void activate() {
    if (!isActive()) {
      super.activate();
      ((ModelElement) getModel()).addPropertyChangeListener(this);
    }
  }
  
  @Override
  public void deactivate() {
    if (isActive()) {
      super.deactivate();
      ((ModelElement) getModel()).removePropertyChangeListener(this);
    }
  }
  
  public WorkflowConnection getCastedModel() {
    return (WorkflowConnection) getModel();
  }
  
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    //String prop = evt.getPropertyName();
  }

  @Override
  protected void createEditPolicies() {
    installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new WorkflowConnectionEndpointEditPolicy());
    installEditPolicy(EditPolicy.CONNECTION_ROLE, new WorkflowConnectionEditPolicy());
  }
  
  @Override
  protected IFigure createFigure() {
    return super.createFigure();
    //return new PolylineConnection();
  }

}
