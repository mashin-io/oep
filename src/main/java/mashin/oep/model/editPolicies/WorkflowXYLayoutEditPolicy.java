package mashin.oep.model.editPolicies;

import mashin.oep.model.Workflow;
import mashin.oep.model.commands.NodeCreateCommand;
import mashin.oep.model.commands.NodeMoveCommand;
import mashin.oep.model.node.Node;
import mashin.oep.model.node.control.DecisionNode;
import mashin.oep.model.node.control.EndNode;
import mashin.oep.model.node.control.ForkNode;
import mashin.oep.model.node.control.JoinNode;
import mashin.oep.model.node.control.KillNode;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

public class WorkflowXYLayoutEditPolicy extends XYLayoutEditPolicy {

  @Override
  protected Command getCreateCommand(CreateRequest request) {
    Object childClass = request.getNewObjectType();
    if (childClass == Node.class
        || childClass == EndNode.class
        || childClass == KillNode.class
        || childClass == DecisionNode.class
        || childClass == ForkNode.class
        || childClass == JoinNode.class) {
      return new NodeCreateCommand((Node) request.getNewObject(),
          (Workflow) getHost().getModel(),
          ((Rectangle) getConstraintFor(request)).getLocation());
    }
    return null;
  }
  
  protected Command createChangeConstraintCommand(
      ChangeBoundsRequest request, EditPart child, Object constraint) {
    if (child instanceof NodeEditPart
        && constraint instanceof Rectangle) {
      // return a command that can move and/or resize a Shape
      return new NodeMoveCommand((Node) child.getModel(),
          request, ((Rectangle) constraint).getLocation());
    }
    return super.createChangeConstraintCommand(request, child,
        constraint);
  }

  @Override
  protected EditPolicy createChildEditPolicy(EditPart child) {
    return new NonResizableEditPolicy();
  }
  
}
