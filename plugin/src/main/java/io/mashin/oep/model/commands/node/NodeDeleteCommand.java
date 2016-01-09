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

package io.mashin.oep.model.commands.node;

import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.connection.WorkflowConnection;
import io.mashin.oep.model.node.Node;
import io.mashin.oep.model.node.control.EndNode;
import io.mashin.oep.model.node.control.StartNode;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;

public class NodeDeleteCommand extends Command {
  
	private final Node node;

	private final Workflow workflow;
	
	/** Holds a copy of the outgoing connections of node. */
	private List<WorkflowConnection> sourceConnections;
	
	/** Holds a copy of the incoming connections of node. */
	private List<WorkflowConnection> targetConnections;
	
	private boolean wasRemoved;

	public NodeDeleteCommand(Workflow workflow, Node node) {
		if (workflow == null || node == null) {
			throw new IllegalArgumentException();
		}
		setLabel("node deletion");
		this.workflow = workflow;
		this.node     = node;
	}

	/**
	 * Reconnects a List of Connections with their previous endpoints.
	 * 
	 * @param connections
	 *            a non-null List of connections
	 */
	private void addConnections(List<WorkflowConnection> connections) {
	  for (WorkflowConnection conn : connections) {
			conn.reconnect();
		}
	}

	public boolean canUndo() {
		return wasRemoved;
	}

	@Override
	public boolean canExecute() {
	  if (node instanceof StartNode
	      || node instanceof EndNode) {
	    return false;
	  }
	  return true;
	}
	
	public void execute() {
		// store a copy of incoming & outgoing connections before proceeding
		sourceConnections = new ArrayList<WorkflowConnection>(node.getSourceConnections());
		targetConnections = new ArrayList<WorkflowConnection>(node.getTargetConnections());
		redo();
	}

	public void redo() {
		// remove the node and disconnect its connections
		removeConnections(sourceConnections);
		removeConnections(targetConnections);
		workflow.removeNode(node);
    wasRemoved = true;
	}

	/**
	 * Disconnects a List of Connections from their endpoints.
	 * 
	 * @param connections
	 *            a non-null List of connections
	 */
	private void removeConnections(List<WorkflowConnection> connections) {
		for (WorkflowConnection conn : connections) {
			conn.disconnect();
		}
	}

	public void undo() {
		// add the node and reconnect its connections
		if (workflow.canAddNode(node)) {
		  workflow.addNode(node);
			addConnections(sourceConnections);
			addConnections(targetConnections);
		}
	}
	
}