package mashin.oep;

import java.lang.reflect.InvocationTargetException;

import mashin.oep.model.Workflow;
import mashin.oep.model.node.Node;

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
      return type.getConstructor(workflow.getClass()).newInstance(workflow);
      
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