package mashin.oep.model.commands.connection;

import mashin.oep.model.connection.WorkflowCaseConnection;
import mashin.oep.model.connection.WorkflowConnection;
import mashin.oep.model.node.Node;
import mashin.oep.model.node.control.DecisionNode;
import mashin.oep.model.terminal.FanOutTerminal;
import mashin.oep.model.terminal.Terminal;

import org.eclipse.gef.commands.Command;

public class ConnectionCreateCommand extends Command {

  private WorkflowConnection connection;
  private Node sourceNode;
  private Node targetNode;
  private Terminal sourceTerminal;
  private Terminal targetTerminal;

  public ConnectionCreateCommand(Node sourceNode, Terminal sourceTerminal) {
    if (sourceNode == null) {
      throw new IllegalArgumentException();
    }
    setLabel("connection creation");
    this.sourceNode = sourceNode;
    this.sourceTerminal = sourceTerminal;
  }

  public boolean canExecute() {
    if (sourceNode == null || targetNode == null
        || sourceTerminal == null || targetTerminal == null) {
      return false;
    }
    
    if (sourceNode instanceof DecisionNode 
        && sourceTerminal instanceof FanOutTerminal) {
      connection = new WorkflowCaseConnection(sourceNode, targetNode,
                                              sourceTerminal, targetTerminal);
    } else {
      connection = new WorkflowConnection(sourceNode, targetNode,
                                          sourceTerminal, targetTerminal);
    }
    
    if (sourceNode.equals(targetNode)
        || !sourceNode.canConnectTo(targetNode)
        || !sourceTerminal.canAddConnection(connection)
        || !targetNode.canConnectFrom(connection.getSourceNode())
        || !targetTerminal.canAddConnection(connection)) {
      return false;
    }
      
    return true;
  }

  public void execute() {
    // create a new connection between source and target
    connection.reconnect();
  }

  public void redo() {
    connection.reconnect();
  }

  public void setTargetNode(Node targetNode) {
    if (targetNode == null) {
      throw new IllegalArgumentException();
    }
    this.targetNode = targetNode;
  }
  
  public void setTargetTerminal(Terminal targetTerminal) {
    if (targetTerminal == null) {
      throw new IllegalArgumentException();
    }
    this.targetTerminal = targetTerminal;
  }

  public void undo() {
    connection.disconnect();
  }

}
