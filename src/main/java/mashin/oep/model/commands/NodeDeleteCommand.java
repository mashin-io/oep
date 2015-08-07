package mashin.oep.model.commands;

import java.util.List;

import mashin.oep.model.Workflow;
import mashin.oep.model.node.Connection;
import mashin.oep.model.node.Node;

import org.eclipse.gef.commands.Command;

public class NodeDeleteCommand extends Command {
  
	private final Node node;

	private final Workflow workflow;
	
	/** Holds a copy of the outgoing connections of node. */
	private List<Connection> sourceConnections;
	
	/** Holds a copy of the incoming connections of node. */
	private List<Connection> targetConnections;
	
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
	private void addConnections(List<Connection> connections) {
	  for (Connection conn : connections) {
			conn.reconnect();
		}
	}

	public boolean canUndo() {
		return wasRemoved;
	}

	public void execute() {
		// store a copy of incoming & outgoing connections before proceeding
		sourceConnections = node.getSourceConnections();
		targetConnections = node.getTargetConnections();
		redo();
	}

	public void redo() {
		// remove the node and disconnect its connections
		workflow.removeNode(node);
		wasRemoved = true;
		removeConnections(sourceConnections);
		removeConnections(targetConnections);
	}

	/**
	 * Disconnects a List of Connections from their endpoints.
	 * 
	 * @param connections
	 *            a non-null List of connections
	 */
	private void removeConnections(List<Connection> connections) {
		for (Connection conn : connections) {
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