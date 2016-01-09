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

package io.mashin.oep.parts.tree;

import io.mashin.oep.model.connection.WorkflowConnection;
import io.mashin.oep.model.editPolicies.WorkflowConnectionEditPolicy;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.widgets.Tree;

public class WorkflowConnectionTreeEditPart extends AbstractTreeEditPart
    implements PropertyChangeListener {

  public WorkflowConnectionTreeEditPart(WorkflowConnection connection) {
    super(connection);
  }
  
  public void activate() {
    super.activate();
    getConnection().addPropertyChangeListener(this);
  }

  protected void createEditPolicies() {
    installEditPolicy(EditPolicy.CONNECTION_ROLE, new WorkflowConnectionEditPolicy());
  }

  public void deactivate() {
    getConnection().removePropertyChangeListener(this);
    super.deactivate();
  }

  protected WorkflowConnection getConnection() {
    return (WorkflowConnection) getModel();
  }

  @SuppressWarnings("rawtypes")
  protected List getModelChildren() {
    return Collections.EMPTY_LIST;
  }

  public void propertyChange(PropertyChangeEvent change) {
    refreshVisuals();
  }

  protected void refreshVisuals() {
    if (getWidget() instanceof Tree)
      return;
    //Image image = ((NodeFigure) nodeEditPart.getFigure()).getIcon();
    ////TreeItem item = (TreeItem) getWidget();
    ////if (image != null)
    ////  image.setBackground(item.getParent().getBackground());
    //setWidgetImage(image);
    setWidgetText(getConnection().getSourceNode().getName() +
        "::" + getConnection().getSourceTerminal().getLabel() +
        " --> " + getConnection().getTargetNode().getName() +
        "::" + getConnection().getTargetTerminal().getLabel());
  }

}
