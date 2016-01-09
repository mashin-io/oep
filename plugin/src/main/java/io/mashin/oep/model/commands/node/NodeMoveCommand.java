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

import io.mashin.oep.model.node.Node;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

public class NodeMoveCommand extends Command {
  
	private final Point newPosition;
	private Point oldPosition;
	private final ChangeBoundsRequest request;
	private final Node node;

	public NodeMoveCommand(Node node, ChangeBoundsRequest req,
			Point newPosition) {
		if (node == null || req == null || newPosition == null) {
			throw new IllegalArgumentException();
		}
		this.node = node;
		this.request = req;
		this.newPosition = newPosition.getCopy();
		setLabel("move");
	}

	public boolean canExecute() {
		Object type = request.getType();
		// make sure the Request is of a type we support:
		return (RequestConstants.REQ_MOVE.equals(type)
				    || RequestConstants.REQ_MOVE_CHILDREN.equals(type)
				    || RequestConstants.REQ_ALIGN_CHILDREN.equals(type));
	}

	public void execute() {
		oldPosition = new Point(node.getPosition());
		redo();
	}

	public void redo() {
		node.setPosition(newPosition);
	}

	public void undo() {
		node.setPosition(oldPosition);
	}
	
}
