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
import io.mashin.oep.model.node.control.StartNode;

import org.eclipse.gef.commands.Command;

public class NodeRenameCommand extends Command {

  private String oldName, newName;
  private Node node;

  public NodeRenameCommand() {
    setLabel("rename");
  }
  
  @Override
  public boolean canExecute() {
    if (node instanceof StartNode) {
      return false;
    }
    return true;
  }
  
  @Override
  public void execute() {
    oldName = node.getName();
    node.setName(newName);
  }

  @Override
  public void undo() {
    node.setName(oldName);
  }
  
  @Override
  public void redo() {
    node.setName(newName);
  }

  public void setNewName(String newName) {
    this.newName = newName;
  }

  public void setNode(Node node) {
    this.node = node;
  }

}
