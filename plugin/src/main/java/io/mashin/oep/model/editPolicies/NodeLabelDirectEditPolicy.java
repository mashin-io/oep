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

import io.mashin.oep.figures.NodeFigure;
import io.mashin.oep.model.commands.node.NodeRenameCommand;
import io.mashin.oep.model.node.Node;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

public class NodeLabelDirectEditPolicy extends DirectEditPolicy {

  @Override
  protected Command getDirectEditCommand(DirectEditRequest request) {
    NodeRenameCommand command = new NodeRenameCommand();
    command.setNode((Node) getHost().getModel());
    command.setNewName((String) request.getCellEditor().getValue());
    return command;
  }

  @Override
  protected void showCurrentEditValue(DirectEditRequest request) {
    String value = (String) request.getCellEditor().getValue();
    NodeFigure nodeFigure = (NodeFigure) getHostFigure();
    nodeFigure.setLabel(value);
  }

}
