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
import io.mashin.oep.model.node.Node;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

public class NodeCreateCommand extends Command {

  private Node     node;
  private Workflow workflow;
	private Point    position;

	public NodeCreateCommand(Node node, Workflow workflow, Point position) {
		this.node     = node;
		this.workflow = workflow;
		this.position = position;
		setLabel("node creation");
	}

	public boolean canExecute() {
	  if(node != null && workflow != null && position != null) {
	    return workflow.canAddNode(node);
	  }
		return false;
	}

	public void execute() {
		node.setPosition(position);
		redo();
	}

	public void redo() {
		workflow.addNode(node);
	}

	public void undo() {
		workflow.removeNode(node);
	}

}