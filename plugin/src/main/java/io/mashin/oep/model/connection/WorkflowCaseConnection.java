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

import io.mashin.oep.model.node.Node;
import io.mashin.oep.model.property.TextPropertyElement;
import io.mashin.oep.model.terminal.Terminal;

public class WorkflowCaseConnection extends WorkflowConnection {

  public static final String PROP_CONN_CONDITION = "prop.conn.condition";
  
  protected TextPropertyElement condition;
  
  public WorkflowCaseConnection(AbstractWorkflowConnectionEndPoint<?, ?> source,
      AbstractWorkflowConnectionEndPoint<?, ?> target) {
    super(source, target);
    condition = new TextPropertyElement(PROP_CONN_CONDITION, "Condition");
    addPropertyElement(condition);
  }
  
  public WorkflowCaseConnection(Node source, Node target,
      Terminal sourceTerminal, Terminal targetTerminal) {
    super(source, target, sourceTerminal, targetTerminal);
    condition = new TextPropertyElement(PROP_CONN_CONDITION, "Condition");
    addPropertyElement(condition);
  }
  
  public void setCondition(String condition) {
    setPropertyValue(PROP_CONN_CONDITION, condition);
  }
  
  public String getCondition() {
    return condition.getStringValue();
  }

  @Override
  public boolean equals(Object obj) {
    if (!super.equals(obj) || !(obj instanceof WorkflowCaseConnection)) {
      return false;
    }
    
    return this.getCondition().equals(
        ((WorkflowCaseConnection) obj).getCondition());
  }
  
}
