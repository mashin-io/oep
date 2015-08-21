package mashin.oep.model.commands.connection;

import mashin.oep.model.connection.WorkflowConnection;
import mashin.oep.model.node.Node;
import mashin.oep.model.terminal.Terminal;

import org.eclipse.gef.commands.Command;

/**
 * A command to reconnect a connection to a different start point or end point.
 * The command can be undone or redone.
 * <p>
 * This command is designed to be used together with a GraphicalNodeEditPolicy.
 * To use this command propertly, following steps are necessary:
 * </p>
 * <ol>
 * <li>Create a subclass of GraphicalNodeEditPolicy.</li>
 * <li>Override the <tt>getReconnectSourceCommand(...)</tt> method. Here you
 * need to obtain the Connection model element from the ReconnectRequest, create
 * a new ConnectionReconnectCommand, set the new connection <i>source</i> by
 * calling the <tt>setNewSource(Node)</tt> method and return the command
 * instance.
 * <li>Override the <tt>getReconnectTargetCommand(...)</tt> method.</li>
 * Here again you need to obtain the Connection model element from the
 * ReconnectRequest, create a new ConnectionReconnectCommand, set the new
 * connection <i>target</i> by calling the <tt>setNewTarget(Node)</tt> method
 * and return the command instance.</li>
 * </ol>
 * 
 */
public class ConnectionReconnectCommand extends Command {

	private WorkflowConnection connection;
	private Node newSourceNode;
	private Node newTargetNode;
	private Terminal newSourceTerminal;
	private Terminal newTargetTerminal;
	private final Node oldSourceNode;
	private final Node oldTargetNode;
	private final Terminal oldSourceTerminal;
  private final Terminal oldTargetTerminal;

	public ConnectionReconnectCommand(WorkflowConnection connection) {
		if (connection == null) {
			throw new IllegalArgumentException();
		}
		this.connection = connection;
		this.oldSourceNode = connection.getSourceNode();
		this.oldTargetNode = connection.getTargetNode();
		this.oldSourceTerminal = connection.getSourceTerminal();
		this.oldTargetTerminal = connection.getTargetTerminal();
	}

	public boolean canExecute() {
		if (newSourceNode != null) {
			return checkSourceReconnection();
		} else if (newTargetNode != null) {
			return checkTargetReconnection();
		}
		return false;
	}

	/**
	 * Return true, if reconnecting the connection-instance to newSource is
	 * allowed.
	 */
	private boolean checkSourceReconnection() {
	  WorkflowConnection conn = new WorkflowConnection(newSourceNode, oldTargetNode,
        newSourceTerminal, oldTargetTerminal);

    if (newSourceNode.equals(oldTargetNode)
        || !newSourceNode.canConnectTo(oldTargetNode)
        || !newSourceTerminal.canAddConnection(conn)
        || !oldTargetNode.canConnectFrom(newSourceNode)) {
      return false;
    }
    
//		// connection endpoints must be different nodes
//		if (newSourceNode.equals(oldTargetNode)) {
//			return false;
//		}
//		// return false, if the connection exists already
//		for (Iterator<WorkflowConnection> iter = newSourceNode.getSourceConnections().iterator(); iter
//				.hasNext();) {
//			WorkflowConnection conn = (WorkflowConnection) iter.next();
//			// return false if a newSource -> oldTarget connection exists
//			// already
//			// and it is a different instance than the connection-field
//			if (conn.getTarget().equals(oldTargetNode)
//			    && conn.getTargetTerminal().equals(oldTargetTerminal)
//			    && conn.getSourceTerminal().equals(newSourceTerminal)
//			    && !conn.equals(connection)) {
//				return false;
//			}
//		}
		return true;
	}

	/**
	 * Return true, if reconnecting the connection-instance to newTarget is
	 * allowed.
	 */
	private boolean checkTargetReconnection() {
	  WorkflowConnection conn = new WorkflowConnection(oldSourceNode, newTargetNode,
        oldSourceTerminal, newTargetTerminal);

    if (oldSourceNode.equals(newTargetNode)
        || !oldSourceNode.canConnectTo(newTargetNode)
        || !newTargetNode.canConnectFrom(oldSourceNode)
        || !newTargetTerminal.canAddConnection(conn)) {
      return false;
    }
    
//		// connection endpoints must be different Shapes
//		if (newTargetNode.equals(oldSourceNode)) {
//			return false;
//		}
//		// return false, if the connection exists already
//		for (Iterator<WorkflowConnection> iter = newTargetNode.getTargetConnections().iterator(); iter
//				.hasNext();) {
//			WorkflowConnection conn = (WorkflowConnection) iter.next();
//			// return false if a oldSource -> newTarget connection exists
//			// already
//			// and it is a differenct instance that the connection-field
//			if (conn.getSource().equals(oldSourceNode) && !conn.equals(connection)) {
//				return false;
//			}
//		}
		return true;
	}

	/**
	 * Reconnect the connection to newSource (if setNewSource(...) was invoked
	 * before) or newTarget (if setNewTarget(...) was invoked before).
	 */
	public void execute() {
		if (newSourceNode != null) {
			connection.reconnect(newSourceNode, oldTargetNode, newSourceTerminal, oldTargetTerminal);
		} else if (newTargetNode != null) {
			connection.reconnect(oldSourceNode, newTargetNode, oldSourceTerminal, newTargetTerminal);
		} else {
			throw new IllegalStateException("Should not happen");
		}
	}

	/**
	 * Set a new source endpoint for this connection. When execute() is invoked,
	 * the source endpoint of the connection will be attached to the supplied
	 * Node instance.
	 * <p>
	 * Note: Calling this method, deactivates reconnection of the <i>target</i>
	 * endpoint. A single instance of this command can only reconnect either the
	 * source or the target endpoint.
	 * </p>
	 * 
	 * @param connectionSource
	 *            a non-null Node instance, to be used as a new source endpoint
	 * @throws IllegalArgumentException
	 *             if connectionSource is null
	 */
	public void setNewSource(Node newSourceNode, Terminal newSourceTerminal) {
		if (newSourceNode == null || newSourceTerminal == null) {
			throw new IllegalArgumentException();
		}
		setLabel("move connection startpoint");
		this.newSourceNode = newSourceNode;
		this.newTargetNode = null;
		this.newSourceTerminal = newSourceTerminal;
		this.newTargetTerminal = null;
	}

	/**
	 * Set a new target endpoint for this connection When execute() is invoked,
	 * the target endpoint of the connection will be attached to the supplied
	 * Node instance.
	 * <p>
	 * Note: Calling this method, deactivates reconnection of the <i>source</i>
	 * endpoint. A single instance of this command can only reconnect either the
	 * source or the target endpoint.
	 * </p>
	 * 
	 * @param connectionTarget
	 *            a non-null Node instance, to be used as a new target endpoint
	 * @throws IllegalArgumentException
	 *             if connectionTarget is null
	 */
	public void setNewTarget(Node newTargetNode, Terminal newTargetTerminal) {
		if (newTargetNode == null || newTargetTerminal == null) {
			throw new IllegalArgumentException();
		}
		setLabel("move connection endpoint");
		this.newSourceNode = null;
		this.newTargetNode = newTargetNode;
		this.newSourceTerminal = null;
		this.newTargetTerminal = newTargetTerminal;
	}

	/**
	 * Reconnect the connection to its original source and target endpoints.
	 */
	public void undo() {
		connection.reconnect(oldSourceNode, oldTargetNode, oldSourceTerminal, oldTargetTerminal);
	}

}
