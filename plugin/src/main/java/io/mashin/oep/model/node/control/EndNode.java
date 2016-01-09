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

package io.mashin.oep.model.node.control;

import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.node.Node;
import io.mashin.oep.model.terminal.FanInTerminal;
import io.mashin.oep.model.terminal.NoOutputTerminal;

import org.dom4j.Element;

public class EndNode extends ControlNode {

  protected FanInTerminal fanInTerminal;
  protected NoOutputTerminal noOutputTerminal;
  
  public EndNode(Workflow workflow) {
    this(workflow, null);
  }

  public EndNode(Workflow workflow, org.dom4j.Node hpdlNode) {
    super(workflow, hpdlNode);
    fanInTerminal     = new FanInTerminal(TERMINAL_FANIN, this);
    noOutputTerminal  = new NoOutputTerminal(TERMINAL_NOOUT, this);
    terminals.add(fanInTerminal);
    terminals.add(noOutputTerminal);
  }
  
  @Override
  public void initDefaults() {
    super.initDefaults();
    setName(workflow.nextId("end"));
  }
  
  @Override
  public void write(Element parent) {
    super.write(parent);
  }
  
  @Override
  public void read(org.dom4j.Node hpdlNode) {
    super.read(hpdlNode);
  }
  
  @Override
  public String getNodeType() {
    return TYPE_END;
  }
  
  @Override
  public boolean canConnectTo(Node target) {
    return false;
  }

  @Override
  public boolean canConnectFrom(Node source) {
    return true;
  }

}
