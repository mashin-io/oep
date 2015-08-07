package mashin.oep.model.commands;

import mashin.oep.model.node.Connection;

import org.eclipse.gef.commands.Command;

public class ConnectionDeleteCommand extends Command {

  private final Connection connection;

  public ConnectionDeleteCommand(Connection conn) {
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
