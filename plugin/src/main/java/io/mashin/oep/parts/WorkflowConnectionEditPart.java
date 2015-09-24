package io.mashin.oep.parts;

import io.mashin.oep.model.ModelElement;
import io.mashin.oep.model.connection.WorkflowConnection;
import io.mashin.oep.model.editPolicies.WorkflowConnectionEditPolicy;
import io.mashin.oep.model.editPolicies.WorkflowConnectionEndpointEditPolicy;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
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
    PolylineConnection connection = new PolylineConnection();
    PolygonDecoration arrow = new PolygonDecoration();
    arrow.setTemplate(PolygonDecoration.TRIANGLE_TIP);
    connection.setTargetDecoration(arrow);
    return connection;
  }

}
