package mashin.oep.parts;

import mashin.oep.model.Workflow;
import mashin.oep.model.connection.WorkflowConnection;
import mashin.oep.model.node.Node;

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
