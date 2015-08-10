package mashin.oep.model.commands.connection;

import java.util.Iterator;

import mashin.oep.model.node.Connection;
import mashin.oep.model.node.Node;

import org.eclipse.gef.commands.Command;

public class ConnectionCreateCommand extends Command {

  private Connection connection;
  private Node source;
  private Node target;

  public ConnectionCreateCommand(Node source) {
    if (source == null) {
      throw new IllegalArgumentException();
    }
    setLabel("connection creation");
    this.source = source;
  }

  public boolean canExecute() {
    // disallow source -> source connections
    if (source.equals(target)) {
      return false;
    }
    // return false, if the source -> target connection exists already
    for (Iterator<Connection> iter = source.getSourceConnections().iterator(); iter
        .hasNext();) {
      Connection conn = (Connection) iter.next();
      if (conn.getTarget().equals(target)) {
        return false;
      }
    }
    return true;
  }

  public void execute() {
    // create a new connection between source and target
    connection = new Connection(source, target);
  }

  public void redo() {
    connection.reconnect();
  }

  public void setTarget(Node target) {
    if (target == null) {
      throw new IllegalArgumentException();
    }
    this.target = target;
  }

  public void undo() {
    connection.disconnect();
  }

}
