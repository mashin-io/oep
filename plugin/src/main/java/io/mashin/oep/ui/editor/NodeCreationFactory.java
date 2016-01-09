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

package io.mashin.oep.ui.editor;

import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.node.Node;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.gef.requests.CreationFactory;

public class NodeCreationFactory<T extends Node> implements CreationFactory {

  private WorkflowEditor workflowEditor;
  private Class<T> type;
  
  public NodeCreationFactory(WorkflowEditor workflowEditor, Class<T> type) {
    this.workflowEditor = workflowEditor;
    this.type = type;
  }
  
  @Override
  public Object getNewObject() {
    try {
      
      Workflow workflow = workflowEditor.getModel();
      Node node = type.getConstructor(workflow.getClass()).newInstance(workflow);
      node.init();
      return node;
      
    } catch (InstantiationException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException
        | NoSuchMethodException | SecurityException e) {
      
      e.printStackTrace();
      return null;
      
    }
  }

  @Override
  public Object getObjectType() {
    return type;
  }

}
