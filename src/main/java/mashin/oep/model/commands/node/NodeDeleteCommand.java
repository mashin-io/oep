package mashin.oep.model.commands.node;

import java.util.ArrayList;
import java.util.List;

import mashin.oep.model.Workflow;
import mashin.oep.model.connection.WorkflowConnection;
import mashin.oep.model.node.Node;
import mashin.oep.model.node.control.StartNode;

import org.eclipse.gef.commands.Command;

public class NodeDeleteCommand extends Command {
  
	private final Node node;

	private final Workflow workflow;
	
	/** Holds a copy of the outgoing connections of node. */
	private List<WorkflowConnection> sourceConnections;
	
	/** Holds a copy of the incoming connections of node. */
	private List<WorkflowConnection> targetConnections;
	
	private boolean wasRemoved;

	public NodeDeleteCommand(Workflow workflow, Node node) {
		if (workflow == null || node == null) {
			throw new IllegalArgumentException();
		}
		setLabel("node deletion");
		this.workflow = workflow;
		this.node     = node;
	}

	/**
	 * Reconnects a List of Connections with their previous endpoints.
	 * 
	 * @param connections
	 *            a non-null List of connections
	 */
	private void addConnections(List<WorkflowConnection> connections) {
	  for (WorkflowConnection conn : connections) {
			conn.reconnect();
		}
	}

	public boolean canUndo() {
		return wasRemoved;
	}

	@Override
	public boolean canExecute() {
	  if (node instanceof StartNode) {
	    return false;
	  }
	  return true;
	}
	
	public void execute() {
		// store a copy of incoming & outgoing connections before proceeding
		sourceConnections = new ArrayList<WorkflowConnection>(node.getSourceConnections());
		targetConnections = new ArrayList<WorkflowConnection>(node.getTargetConnections());
		redo();
	}

	public void redo() {
		// remove the node and disconnect its connections
		removeConnections(sourceConnections);
		removeConnections(targetConnections);
		workflow.removeNode(node);
    wasRemoved = true;
	}

	/**
	 * Disconnects a List of Connections from their endpoints.
	 * 
	 * @param connections
	 *            a non-null List of connections
	 */
	private void removeConnections(List<WorkflowConnection> connections) {
		for (WorkflowConnection conn : connections) {
			conn.disconnect();
		}
	}

	public void undo() {
		// add the node and reconnect its connections
		if (workflow.canAddNode(node)) {
		  workflow.addNode(node);
			addConnections(sourceConnections);
			addConnections(targetConnections);
		}
	}
	
}