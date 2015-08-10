package mashin.oep.model.commands.node;

import mashin.oep.model.Workflow;
import mashin.oep.model.node.Node;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

public class NodeCreateCommand extends Command {

  private Node     node;
  private Workflow workflow;
	private Point    position;

	public NodeCreateCommand(Node node, Workflow workflow, Point position) {
		this.node     = node;
		this.workflow = workflow;
		this.position = position;
		setLabel("node creation");
	}

	public boolean canExecute() {
	  if(node != null && workflow != null && position != null) {
	    return workflow.canAddNode(node);
	  }
		return false;
	}

	public void execute() {
		node.setPosition(position);
		redo();
	}

	public void redo() {
		workflow.addNode(node);
	}

	public void undo() {
		workflow.removeNode(node);
	}

}