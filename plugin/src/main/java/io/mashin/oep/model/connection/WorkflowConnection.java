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

package io.mashin.oep.model.connection;

import io.mashin.oep.model.ModelElement;
import io.mashin.oep.model.node.Node;
import io.mashin.oep.model.terminal.Terminal;

public class WorkflowConnection extends ModelElement {
  
  public static final String PROP_CONN_SOURCE_NODE     = "prop.conn.source.node";
  public static final String PROP_CONN_SOURCE_TERMINAL = "prop.conn.source.terminal";
  public static final String PROP_CONN_TARGET_NODE     = "prop.conn.target.node";
  public static final String PROP_CONN_TARGET_TERMINAL = "prop.conn.target.terminal";
  
  private boolean isConnected;

  private AbstractWorkflowConnectionEndPoint<?, ?> source;
  private AbstractWorkflowConnectionEndPoint<?, ?> target;
  
  public WorkflowConnection(AbstractWorkflowConnectionEndPoint<?, ?> source,
      AbstractWorkflowConnectionEndPoint<?, ?> target) {
    this.source = source;
    this.target = target;
  }
  
  public WorkflowConnection(Node source, Node target, Terminal sourceTerminal,
      Terminal targetTerminal) {
    this.source = new WorkflowConnectionEndPoint(source, sourceTerminal);
    this.target = new WorkflowConnectionEndPoint(target, targetTerminal);
  }

  public boolean isConnected() {
    return isConnected;
  }
  
  public boolean isConcrete() {
    return source instanceof WorkflowConnectionEndPoint
        && target instanceof WorkflowConnectionEndPoint;
  }
  
  public void disconnect() {
    if (isConnected()) {
      getSourceNode().removeConnectionInitiate(this);
      getTargetNode().removeConnectionInitiate(this);
      isConnected = false;
    }
  }

  public void reconnect() {
    if (!isConnected() && isConcrete()) {
      getSourceNode().addConnectionInitiate(this);
      getTargetNode().addConnectionInitiate(this);
      isConnected = true;
    }
  }

  public void reconnect(WorkflowConnectionEndPoint source,
      WorkflowConnectionEndPoint target) {
    if (source == null || target == null) {
      throw new IllegalArgumentException();
    }
    disconnect();
    this.source = source;
    this.target = target;
    reconnect();
  }
  
  public void reconnect(Node source, Node target,
      Terminal sourceTerminal, Terminal targetTerminal) {
    if (source == null || target == null
        || source == target || sourceTerminal == null
        || targetTerminal == null) {
      throw new IllegalArgumentException();
    }
    disconnect();
    this.source = new WorkflowConnectionEndPoint(source, sourceTerminal);
    this.target = new WorkflowConnectionEndPoint(target, targetTerminal);
    reconnect();
  }

  public AbstractWorkflowConnectionEndPoint<?, ?> getSource() {
    return source;
  }
  
  public AbstractWorkflowConnectionEndPoint<?, ?> getTarget() {
    return target;
  }
  
  public WorkflowConnectionEndPoint getConcreteSource() {
    return (WorkflowConnectionEndPoint) source;
  }
  
  public WorkflowConnectionEndPoint getConcreteTarget() {
    return (WorkflowConnectionEndPoint) target;
  }
  
  public WorkflowConnectionDummyEndPoint getDummySource() {
    return (WorkflowConnectionDummyEndPoint) source;
  }
  
  public WorkflowConnectionDummyEndPoint getDummyTarget() {
    return (WorkflowConnectionDummyEndPoint) target;
  }
  
  public Node getSourceNode() {
    return (Node) source.getNode();
  }

  public Node getTargetNode() {
    return (Node) target.getNode();
  }
  
  public Terminal getSourceTerminal() {
    return (Terminal) source.getTerminal();
  }
  
  public Terminal getTargetTerminal() {
    return (Terminal) target.getTerminal();
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof WorkflowConnection)) {
      return false;
    }
    
    if (this == obj) {
      return true;
    }
    
    WorkflowConnection other = (WorkflowConnection) obj;
    return this.source.equals(other.source)
        && this.target.equals(other.target);
  }
  
  @Override
  public String toString() {
    return source.toString() + " -> " + target.toString();
  }
  
}
