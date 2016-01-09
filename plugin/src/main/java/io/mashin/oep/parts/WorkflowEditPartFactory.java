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

import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.connection.WorkflowConnection;
import io.mashin.oep.model.node.Node;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

public class WorkflowEditPartFactory implements EditPartFactory {
  
  @Override
  public EditPart createEditPart(EditPart context, Object model) {
    // get EditPart for model element
    EditPart part = getPartForElement(model);
    // store model element in EditPart
    part.setModel(model);
    return part;
  }
  
  private EditPart getPartForElement(Object model) {
    if (model instanceof Workflow) {
      return new WorkflowEditPart();
    } else if (model instanceof Node) {
      return new WorkflowNodeEditPart();
    } else if (model instanceof WorkflowConnection) {
      return new WorkflowConnectionEditPart();
    }
    throw new RuntimeException("Can't create part for model element: "
        + ((model != null) ? model.getClass().getName()
            : "null"));
  }
  
}
