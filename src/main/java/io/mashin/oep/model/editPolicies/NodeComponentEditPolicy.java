package io.mashin.oep.model.editPolicies;

import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.commands.node.NodeDeleteCommand;
import io.mashin.oep.model.node.Node;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public class NodeComponentEditPolicy extends ComponentEditPolicy {

  @Override
  protected Command createDeleteCommand(GroupRequest deleteRequest) {
    Object parent = getHost().getParent().getModel();
    Object child = getHost().getModel();
    if (parent instanceof Workflow && child instanceof Node) {
      return new NodeDeleteCommand((Workflow) parent, (Node) child);
    }
    return super.createDeleteCommand(deleteRequest);
  }

}
