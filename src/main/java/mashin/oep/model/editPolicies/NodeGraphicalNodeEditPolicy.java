package mashin.oep.model.editPolicies;

import mashin.oep.figures.TerminalConnectionAnchor;
import mashin.oep.model.WorkflowConnection;
import mashin.oep.model.commands.connection.ConnectionCreateCommand;
import mashin.oep.model.commands.connection.ConnectionReconnectCommand;
import mashin.oep.model.node.Node;
import mashin.oep.model.terminal.Terminal;
import mashin.oep.parts.WorkflowNodeEditPart;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

public class NodeGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {

  private Node getNode() {
    return (Node) getHost().getModel();
  }
  
  private WorkflowNodeEditPart getNodeEditPart() {
    return (WorkflowNodeEditPart) getHost();
  }
  
  private Terminal getSourceTerminal(CreateConnectionRequest request) {
    Node node = getNode();
    WorkflowNodeEditPart nodeEditPart = getNodeEditPart();
    TerminalConnectionAnchor terminalConnectionAnchor = (TerminalConnectionAnchor) nodeEditPart
        .getSourceConnectionAnchor(request);
    if (terminalConnectionAnchor == null) {
      return null;
    }
    String terminalLabel = terminalConnectionAnchor.getLabel();
    return node.getTerminal(terminalLabel);
  }
  
  private Terminal getTargetTerminal(CreateConnectionRequest request) {
    Node node = getNode();
    WorkflowNodeEditPart nodeEditPart = getNodeEditPart();
    TerminalConnectionAnchor terminalConnectionAnchor = (TerminalConnectionAnchor) nodeEditPart
        .getTargetConnectionAnchor(request);
    if (terminalConnectionAnchor == null) {
      return null;
    }
    String terminalLabel = terminalConnectionAnchor.getLabel();
    return node.getTerminal(terminalLabel);
  }
  
  @Override
  protected Command getConnectionCompleteCommand(
      CreateConnectionRequest request) {
    ConnectionCreateCommand cmd = (ConnectionCreateCommand) request.getStartCommand();
    cmd.setTargetNode(getNode());
    Terminal terminal = getTargetTerminal(request);
    if (terminal == null) {
      return null;
    }
    cmd.setTargetTerminal(terminal);
    return cmd;
  }

  @Override
  protected Command getConnectionCreateCommand(
      CreateConnectionRequest request) {
    Terminal terminal = getSourceTerminal(request);
    if (terminal == null) {
      return null;
    }
    ConnectionCreateCommand cmd = new ConnectionCreateCommand(
        getNode(),
        terminal);
    request.setStartCommand(cmd);
    return cmd;
  }

  @Override
  protected Command getReconnectSourceCommand(ReconnectRequest request) {
    WorkflowConnection conn = (WorkflowConnection) request.getConnectionEditPart()
        .getModel();
    Node newSource = getNode();
    ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(
        conn);
    cmd.setNewSource(newSource);
    return cmd;
  }

  @Override
  protected Command getReconnectTargetCommand(ReconnectRequest request) {
    WorkflowConnection conn = (WorkflowConnection) request.getConnectionEditPart()
        .getModel();
    Node newTarget = getNode();
    ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(
        conn);
    cmd.setNewTarget(newTarget);
    return cmd;
  }
  
}
