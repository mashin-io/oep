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

package io.mashin.oep.model.node.action.extended;

import io.mashin.oep.hpdl.XMLReadUtils;
import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.node.Node;
import io.mashin.oep.model.node.action.ActionNode;

import org.dom4j.Element;

public abstract class ExtendedActionNode extends ActionNode {

  public ExtendedActionNode(Workflow workflow) {
    this(workflow, null);
  }

  public ExtendedActionNode(Workflow workflow, org.dom4j.Node hpdlNode) {
    super(workflow, hpdlNode);
  }
  
  @Override
  public void initDefaults() {
    super.initDefaults();
    this.setSchemaVersion(getDefaultSchemaVersion());
  }
  
  @Override
  public void write(Element parentNode) {
    super.write(parentNode);
  }
  
  @Override
  public void read(org.dom4j.Node hpdlNode) {
    super.read(hpdlNode);
    
    org.dom4j.Node node = XMLReadUtils.schemaVersionParentNode(hpdlNode);
    setSchemaVersion(XMLReadUtils.schemaVersion(node));
  }
  
  @Override
  public boolean canConnectTo(Node target) {
    return true;
  }
  
  @Override
  public boolean canConnectFrom(Node source) {
    return true;
  }
  
  @Override
  protected boolean isSchemaVersionEditable() { return true; }
  
}
