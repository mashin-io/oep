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

package io.mashin.oep.model.terminal;

import io.mashin.oep.model.ModelElement;
import io.mashin.oep.model.connection.WorkflowConnection;
import io.mashin.oep.model.node.Node;

import java.util.ArrayList;
import java.util.List;

public abstract class Terminal extends ModelElement {

  public static final String PROP_CONNECTION_ADD    = "prop.terminal.connection.add";
  public static final String PROP_CONNECTION_REMOVE = "prop.terminal.connection.remove";
  
  protected String label;
  protected List<WorkflowConnection> connections;
  protected Node holderNode;
  
  public Terminal(String label, Node holderNode) {
    this.label = label;
    this.holderNode = holderNode;
    connections = new ArrayList<WorkflowConnection>();
  }
  
  @Override
  public void setPropertyValue(Object propertyName, Object propertyValue) {
    switch ((String) propertyName) {
    case PROP_CONNECTION_ADD:
      addConnectionInitiate((WorkflowConnection) propertyValue);
      break;
    case PROP_CONNECTION_REMOVE:
      removeConnectionInitiate((WorkflowConnection) propertyValue);
      break;
    default:
      super.setPropertyValue(propertyName, propertyValue);
    }
  }
  
  public String getLabel() {
    return label;
  }
  
  public List<WorkflowConnection> getConnections() {
    return new ArrayList<WorkflowConnection>(connections);
  }
  
  public boolean hasConnection(WorkflowConnection connection) {
    if (connections.contains(connection)) {
      return true;
    }
    return false;
  }
  
  public abstract boolean canAddConnection(WorkflowConnection connection);
  
  public boolean addConnectionInitiate(WorkflowConnection connection) {
    if (!canAddConnection(connection)
        || !(holderNode == connection.getSourceNode() 
            && holderNode.canConnectTo(connection.getTargetNode()))
        || !(holderNode == connection.getTargetNode() 
            && holderNode.canConnectFrom(connection.getSourceNode()))) {
      return false;
    }
    boolean success = connections.add(connection);
    if (success) {
      holderNode.addConnectionUpdate(connection);
      firePropertyChange(PROP_CONNECTION_ADD, null, connection);
    }
    return success;
  }
  
  public void addConnectionUpdate(WorkflowConnection connection) {
    boolean success = connections.add(connection);
    if (success) {
      firePropertyChange(PROP_CONNECTION_ADD, null, connection);
    }
  }
  
  public boolean removeConnectionInitiate(WorkflowConnection connection) {
    boolean success = connections.remove(connection);
    if (success) {
      holderNode.removeConnectionUpdate(connection);
      firePropertyChange(PROP_CONNECTION_REMOVE, connection, null);
    }
    return success;
  }
  
  public void removeConnectionUpdate(WorkflowConnection connection) {
    boolean success = connections.remove(connection);
    if (success) {
      firePropertyChange(PROP_CONNECTION_REMOVE, connection, null);
    }
  }
  
}
