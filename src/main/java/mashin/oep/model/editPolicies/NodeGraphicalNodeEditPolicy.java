package mashin.oep.model.editPolicies;

import mashin.oep.model.commands.connection.ConnectionCreateCommand;
import mashin.oep.model.commands.connection.ConnectionReconnectCommand;
import mashin.oep.model.node.Connection;
import mashin.oep.model.node.Node;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

public class NodeGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {

  @Override
  protected Command getConnectionCompleteCommand(
      CreateConnectionRequest request) {
    ConnectionCreateCommand cmd = (ConnectionCreateCommand) request
        .getStartCommand();
    cmd.setTarget((Node) getHost().getModel());
    return cmd;
  }

  @Override
  protected Command getConnectionCreateCommand(
      CreateConnectionRequest request) {
    Node source = (Node) getHost().getModel();
    ConnectionCreateCommand cmd = new ConnectionCreateCommand(source);
    request.setStartCommand(cmd);
    return cmd;
  }

  @Override
  protected Command getReconnectSourceCommand(ReconnectRequest request) {
    Connection conn = (Connection) request.getConnectionEditPart()
        .getModel();
    Node newSource = (Node) getHost().getModel();
    ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(
        conn);
    cmd.setNewSource(newSource);
    return cmd;
  }

  @Override
  protected Command getReconnectTargetCommand(ReconnectRequest request) {
    Connection conn = (Connection) request.getConnectionEditPart()
        .getModel();
    Node newTarget = (Node) getHost().getModel();
    ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(
        conn);
    cmd.setNewTarget(newTarget);
    return cmd;
  }
  
}
