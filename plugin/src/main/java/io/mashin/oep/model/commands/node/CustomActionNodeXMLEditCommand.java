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

import io.mashin.oep.model.node.action.extended.CustomActionNode;

import org.eclipse.gef.commands.Command;

public class CustomActionNodeXMLEditCommand extends Command {

  private CustomActionNode customActionNode;
  private String oldXmlContent;
  private String newXmlContent;
  
  public CustomActionNodeXMLEditCommand(CustomActionNode customActionNode, String xmlContent) {
    super("custom action node XML edit");
    this.customActionNode = customActionNode;
    oldXmlContent = (String) customActionNode.getPropertyValue(CustomActionNode.PROP_XML);
    newXmlContent = xmlContent;
  }
  
  @Override
  public boolean canExecute() {
    return !oldXmlContent.equals(newXmlContent);
  }
  
  @Override
  public void execute() {
    customActionNode.setPropertyValue(CustomActionNode.PROP_XML, newXmlContent);
  }
  
  @Override
  public void undo() {
    customActionNode.setPropertyValue(CustomActionNode.PROP_XML, oldXmlContent);
  }
  
}
