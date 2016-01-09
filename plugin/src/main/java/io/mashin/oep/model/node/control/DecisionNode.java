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
import io.mashin.oep.model.connection.WorkflowCaseConnection;
import io.mashin.oep.model.connection.WorkflowConnection;
import io.mashin.oep.model.connection.WorkflowConnectionDummyEndPoint;
import io.mashin.oep.model.connection.WorkflowConnectionEndPoint;
import io.mashin.oep.model.node.Node;
import io.mashin.oep.model.terminal.FanInTerminal;
import io.mashin.oep.model.terminal.FanOutTerminal;
import io.mashin.oep.model.terminal.SingleOutputTerminal;

import java.util.List;

import org.dom4j.Element;

public class DecisionNode extends ControlNode {

  protected FanInTerminal fanInTerminal;
  protected FanOutTerminal caseFanOutTerminal;
  protected SingleOutputTerminal defaultSingleOutputTerminal;
  
  public DecisionNode(Workflow workflow) {
    this(workflow, null);
  }

  public DecisionNode(Workflow workflow, org.dom4j.Node hpdlNode) {
    super(workflow, hpdlNode);
    fanInTerminal               = new FanInTerminal(TERMINAL_FANIN, this);
    caseFanOutTerminal          = new FanOutTerminal(TERMINAL_CASE, this);
    defaultSingleOutputTerminal = new SingleOutputTerminal(TERMINAL_DEFAULT, this);
    terminals.add(fanInTerminal);
    terminals.add(caseFanOutTerminal);
    terminals.add(defaultSingleOutputTerminal);
  }
  
  @Override
  public void initDefaults() {
    super.initDefaults();
    setName(workflow.nextId("decision"));
  }
  
  @Override
  public void write(org.dom4j.Element parentNode) {
    super.write(parentNode);
    
    Element element = (Element) hpdlModel.get();
    Element switchElement = element.addElement("switch");
    
    writeConnections(switchElement);
  }

  @Override
  protected void writeConnections(Element nodeElement) {
    super.writeConnections(nodeElement);
    
    XMLWriteUtils.writeCaseConnections(caseFanOutTerminal.getConnections(),
        nodeElement);
    XMLWriteUtils.writeConnectionsAsElementWithAttribute(
        defaultSingleOutputTerminal.getConnections(), nodeElement, "default",
        "to");
  }
  
  @Override
  public void read(org.dom4j.Node hpdlNode) {
    super.read(hpdlNode);
    
    // read connections
    
    @SuppressWarnings("unchecked")
    List<org.dom4j.Node> caseConnNodes = hpdlNode.selectNodes("./switch/case");
    if (caseConnNodes != null) {
      for (org.dom4j.Node caseConnNode : caseConnNodes) {
        WorkflowCaseConnection conn = new WorkflowCaseConnection(
            new WorkflowConnectionEndPoint(this, caseFanOutTerminal),
            new WorkflowConnectionDummyEndPoint(XMLReadUtils.valueOf("@to", caseConnNode), TERMINAL_FANIN));
        conn.setCondition(caseConnNode.getText().trim());
        sourceConnections.add(conn);
      }
    }
    
    WorkflowConnection conn = new WorkflowConnection(
        new WorkflowConnectionEndPoint(this, defaultSingleOutputTerminal),
        new WorkflowConnectionDummyEndPoint(XMLReadUtils.valueOf("./switch/default/@to", hpdlNode), TERMINAL_FANIN));
    sourceConnections.add(conn);
    
    hpdlNode.selectSingleNode("./switch").detach();
  }

  @Override
  public String getNodeType() {
    return TYPE_DECISION;
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
