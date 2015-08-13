package mashin.oep.model.editPolicies;

import mashin.oep.model.commands.connection.ConnectionDeleteCommand;
import mashin.oep.model.connection.WorkflowConnection;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public class WorkflowConnectionEditPolicy extends ConnectionEditPolicy {

  @Override
  protected Command getDeleteCommand(GroupRequest request) {
    return new ConnectionDeleteCommand((WorkflowConnection) getHost().getModel());
  }

}
