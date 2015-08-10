package mashin.oep.model.commands.node;

import mashin.oep.model.node.Node;

import org.eclipse.gef.commands.Command;

public class NodeRenameCommand extends Command {

  private String oldName, newName;
  private Node node;

  @Override
  public void execute() {
    oldName = node.getName();
    node.setName(newName);
  }

  @Override
  public void undo() {
    node.setName(oldName);
    super.redo();
  }

  public void setNewName(String newName) {
    this.newName = newName;
  }

  public void setNode(Node node) {
    this.node = node;
  }

}
