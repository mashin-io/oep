package io.mashin.oep.model.editPolicies;

import io.mashin.oep.figures.TerminalConnectionAnchor;
import io.mashin.oep.model.commands.connection.ConnectionCreateCommand;
import io.mashin.oep.model.commands.connection.ConnectionReconnectCommand;
import io.mashin.oep.model.connection.WorkflowConnection;
import io.mashin.oep.model.node.Node;
import io.mashin.oep.model.terminal.Terminal;
import io.mashin.oep.parts.WorkflowNodeEditPart;

import org.eclipse.gef.Request;
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
  
  private Terminal getSourceTerminal(Request request) {
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
  
  private Terminal getTargetTerminal(Request request) {
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
    WorkflowConnection conn = (WorkflowConnection) request.getConnectionEditPart().getModel();
    ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(conn);
    cmd.setNewSource(getNode(), getSourceTerminal(request));
    return cmd;
  }

  @Override
  protected Command getReconnectTargetCommand(ReconnectRequest request) {
    WorkflowConnection conn = (WorkflowConnection) request.getConnectionEditPart().getModel();
    ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(conn);
    cmd.setNewTarget(getNode(), getTargetTerminal(request));
    return cmd;
  }
  
}
