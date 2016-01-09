/**
 * Copyright (c) 2015 Mashin (http://mashin.io). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
