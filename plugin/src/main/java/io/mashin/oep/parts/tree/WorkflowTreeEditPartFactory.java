package io.mashin.oep.parts.tree;

import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.connection.WorkflowConnection;
import io.mashin.oep.model.node.Node;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

public class WorkflowTreeEditPartFactory implements EditPartFactory {

  public EditPart createEditPart(EditPart context, Object model) {
    if (model instanceof Workflow) {
      return new WorkflowTreeEditPart((Workflow) model);
    } else if (model instanceof Node) {
      return new WorkflowNodeTreeEditPart((Node) model);
    } else if (model instanceof WorkflowConnection) {
      return new WorkflowConnectionTreeEditPart((WorkflowConnection) model);
    }
    
    throw new RuntimeException("Can't create tree part for model element: "
        + ((model != null) ? model.getClass().getName()
            : "null"));
  }

}