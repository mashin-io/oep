package io.mashin.oep.model.commands.node;

import io.mashin.oep.model.node.Node;
import io.mashin.oep.model.node.control.StartNode;

import org.eclipse.gef.commands.Command;

public class NodeRenameCommand extends Command {

  private String oldName, newName;
  private Node node;

  public NodeRenameCommand() {
    setLabel("rename");
  }
  
  @Override
  public boolean canExecute() {
    if (node instanceof StartNode) {
      return false;
    }
    return true;
  }
  
  @Override
  public void execute() {
    oldName = node.getName();
    node.setName(newName);
  }

  @Override
  public void undo() {
    node.setName(oldName);
  }
  
  @Override
  public void redo() {
    node.setName(newName);
  }

  public void setNewName(String newName) {
    this.newName = newName;
  }

  public void setNode(Node node) {
    this.node = node;
  }

}
