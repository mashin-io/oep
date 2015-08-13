package mashin.oep.model.commands.connection;

import mashin.oep.model.connection.WorkflowConnection;

import org.eclipse.gef.commands.Command;

public class ConnectionDeleteCommand extends Command {

  private final WorkflowConnection connection;

  public ConnectionDeleteCommand(WorkflowConnection conn) {
    if (conn == null) {
      throw new IllegalArgumentException();
    }
    setLabel("connection deletion");
    this.connection = conn;
  }

  public void execute() {
    connection.disconnect();
  }

  public void undo() {
    connection.reconnect();
  }

}
