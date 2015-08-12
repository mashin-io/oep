package mashin.oep.model.editPolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public class WorkflowConnectionEditPolicy extends ConnectionEditPolicy {

  @Override
  protected Command getDeleteCommand(GroupRequest request) {
    return null;
  }

}
