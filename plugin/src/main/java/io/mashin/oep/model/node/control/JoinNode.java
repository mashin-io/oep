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

import io.mashin.oep.hpdl.XMLReadUtils;
import io.mashin.oep.hpdl.XMLWriteUtils;
import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.connection.WorkflowConnection;
import io.mashin.oep.model.connection.WorkflowConnectionDummyEndPoint;
import io.mashin.oep.model.connection.WorkflowConnectionEndPoint;
import io.mashin.oep.model.node.Node;
import io.mashin.oep.model.terminal.FanInTerminal;
import io.mashin.oep.model.terminal.SingleOutputTerminal;

import org.dom4j.Element;

public class JoinNode extends ControlNode {

  protected FanInTerminal fanInTerminal;
  protected SingleOutputTerminal singleOutputTerminal;
  
  public JoinNode(Workflow workflow) {
    this(workflow, null);
  }

  public JoinNode(Workflow workflow, org.dom4j.Node hpdlNode) {
    super(workflow, hpdlNode);
    fanInTerminal        = new FanInTerminal(TERMINAL_FANIN, this);
    singleOutputTerminal = new SingleOutputTerminal(TERMINAL_OUT, this);
    terminals.add(fanInTerminal);
    terminals.add(singleOutputTerminal);
  }
  
  @Override
  public void initDefaults() {
    super.initDefaults();
    setName(workflow.nextId("join"));
  }
  
  @Override
  public void write(org.dom4j.Element parentNode) {
    super.write(parentNode);
    
    Element element = (Element) hpdlModel.get();
    
    writeConnections(element);
  }

  @Override
  protected void writeConnections(Element nodeElement) {
    super.writeConnections(nodeElement);
    
    XMLWriteUtils.writeConnectionsAsAttribute(
        singleOutputTerminal.getConnections(), nodeElement, "to");
  }
  
  @Override
  public void read(org.dom4j.Node hpdlNode) {
    super.read(hpdlNode);
    
    // read connections
    
    WorkflowConnection conn = new WorkflowConnection(
        new WorkflowConnectionEndPoint(this, singleOutputTerminal),
        new WorkflowConnectionDummyEndPoint(XMLReadUtils.valueOf("@to", hpdlNode), TERMINAL_FANIN));
    sourceConnections.add(conn);
  }

  @Override
  public String getNodeType() {
    return TYPE_JOIN;
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
