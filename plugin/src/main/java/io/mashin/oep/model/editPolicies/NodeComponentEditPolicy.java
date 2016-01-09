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

import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.commands.node.NodeDeleteCommand;
import io.mashin.oep.model.node.Node;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public class NodeComponentEditPolicy extends ComponentEditPolicy {

  @Override
  protected Command createDeleteCommand(GroupRequest deleteRequest) {
    Object parent = getHost().getParent().getModel();
    Object child = getHost().getModel();
    if (parent instanceof Workflow && child instanceof Node) {
      return new NodeDeleteCommand((Workflow) parent, (Node) child);
    }
    return super.createDeleteCommand(deleteRequest);
  }

}
