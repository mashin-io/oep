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
