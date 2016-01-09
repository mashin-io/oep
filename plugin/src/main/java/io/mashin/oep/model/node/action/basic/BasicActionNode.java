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

package io.mashin.oep.model.node.action.basic;

import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.node.Node;
import io.mashin.oep.model.node.action.ActionNode;

public abstract class BasicActionNode extends ActionNode {

  public BasicActionNode(Workflow workflow) {
    this(workflow, null);
  }

  public BasicActionNode(Workflow workflow, org.dom4j.Node hpdlNode) {
    super(workflow, hpdlNode);
    this.setSchemaVersion(workflow.getSchemaVersion());
  }
  
  @Override
  public void initDefaults() {
    super.initDefaults();
  }
  
  @Override
  public boolean canConnectTo(Node target) {
    return true;
  }
  
  @Override
  public boolean canConnectFrom(Node source) {
    return true;
  }
  
}
