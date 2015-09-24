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
