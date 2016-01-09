/**
 * Copyright (c) 2015 Mashin (http://mashin.io). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
