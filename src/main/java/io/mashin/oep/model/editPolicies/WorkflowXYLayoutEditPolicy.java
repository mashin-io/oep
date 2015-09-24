package io.mashin.oep.model.editPolicies;

import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.commands.node.NodeCreateCommand;
import io.mashin.oep.model.commands.node.NodeMoveCommand;
import io.mashin.oep.model.node.Node;
import io.mashin.oep.model.node.action.basic.FSActionNode;
import io.mashin.oep.model.node.action.basic.JavaActionNode;
import io.mashin.oep.model.node.action.basic.MapReduceActionNode;
import io.mashin.oep.model.node.action.basic.PigActionNode;
import io.mashin.oep.model.node.action.basic.SubWorkflowActionNode;
import io.mashin.oep.model.node.action.extended.CustomActionNode;
import io.mashin.oep.model.node.action.extended.DistcpActionNode;
import io.mashin.oep.model.node.action.extended.EmailActionNode;
import io.mashin.oep.model.node.action.extended.Hive2ActionNode;
import io.mashin.oep.model.node.action.extended.HiveActionNode;
import io.mashin.oep.model.node.action.extended.SSHActionNode;
import io.mashin.oep.model.node.action.extended.ShellActionNode;
import io.mashin.oep.model.node.action.extended.SparkActionNode;
import io.mashin.oep.model.node.action.extended.SqoopActionNode;
import io.mashin.oep.model.node.control.DecisionNode;
import io.mashin.oep.model.node.control.EndNode;
import io.mashin.oep.model.node.control.ForkNode;
import io.mashin.oep.model.node.control.JoinNode;
import io.mashin.oep.model.node.control.KillNode;

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
        || childClass == JoinNode.class
        || childClass == MapReduceActionNode.class
        || childClass == PigActionNode.class
        || childClass == SubWorkflowActionNode.class
        || childClass == FSActionNode.class
        || childClass == JavaActionNode.class
        || childClass == SSHActionNode.class
        || childClass == SqoopActionNode.class
        || childClass == SparkActionNode.class
        || childClass == ShellActionNode.class
        || childClass == DistcpActionNode.class
        || childClass == HiveActionNode.class
        || childClass == Hive2ActionNode.class
        || childClass == EmailActionNode.class
        || childClass == CustomActionNode.class) {
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
