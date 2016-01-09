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

package io.mashin.oep.model.commands.connection;

import io.mashin.oep.model.connection.WorkflowCaseConnection;
import io.mashin.oep.model.connection.WorkflowConnection;
import io.mashin.oep.model.node.Node;
import io.mashin.oep.model.node.control.DecisionNode;
import io.mashin.oep.model.terminal.FanOutTerminal;
import io.mashin.oep.model.terminal.Terminal;

import org.eclipse.gef.commands.Command;

public class ConnectionCreateCommand extends Command {

  private WorkflowConnection connection;
  private Node sourceNode;
  private Node targetNode;
  private Terminal sourceTerminal;
  private Terminal targetTerminal;

  public ConnectionCreateCommand(Node sourceNode, Terminal sourceTerminal) {
    if (sourceNode == null) {
      throw new IllegalArgumentException();
    }
    setLabel("connection creation");
    this.sourceNode = sourceNode;
    this.sourceTerminal = sourceTerminal;
  }

  public boolean canExecute() {
    if (sourceNode == null || targetNode == null
        || sourceTerminal == null || targetTerminal == null) {
      return false;
    }
    
    if (sourceNode instanceof DecisionNode 
        && sourceTerminal instanceof FanOutTerminal) {
      connection = new WorkflowCaseConnection(sourceNode, targetNode,
                                              sourceTerminal, targetTerminal);
    } else {
      connection = new WorkflowConnection(sourceNode, targetNode,
                                          sourceTerminal, targetTerminal);
    }
    
    if (sourceNode.equals(targetNode)
        || !sourceNode.canConnectTo(targetNode)
        || !sourceTerminal.canAddConnection(connection)
        || !targetNode.canConnectFrom(connection.getSourceNode())
        || !targetTerminal.canAddConnection(connection)) {
      return false;
    }
      
    return true;
  }

  public void execute() {
    // create a new connection between source and target
    connection.reconnect();
  }

  public void redo() {
    connection.reconnect();
  }

  public void setTargetNode(Node targetNode) {
    if (targetNode == null) {
      throw new IllegalArgumentException();
    }
    this.targetNode = targetNode;
  }
  
  public void setTargetTerminal(Terminal targetTerminal) {
    if (targetTerminal == null) {
      throw new IllegalArgumentException();
    }
    this.targetTerminal = targetTerminal;
  }

  public void undo() {
    connection.disconnect();
  }

}
