package mashin.oep.model.commands;

import mashin.oep.model.node.Node;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

public class ShapeSetConstraintCommand extends Command {
  
	private final Point newPosition;
	private Point oldPosition;
	private final ChangeBoundsRequest request;
	private final Node node;

	public ShapeSetConstraintCommand(Node node, ChangeBoundsRequest req,
			Point newPosition) {
		if (node == null || req == null || newPosition == null) {
			throw new IllegalArgumentException();
		}
		this.node = node;
		this.request = req;
		this.newPosition = newPosition.getCopy();
		setLabel("move");
	}

	public boolean canExecute() {
		Object type = request.getType();
		// make sure the Request is of a type we support:
		return (RequestConstants.REQ_MOVE.equals(type)
				    || RequestConstants.REQ_MOVE_CHILDREN.equals(type));
	}

	public void execute() {
		oldPosition = new Point(node.getPosition());
		redo();
	}

	public void redo() {
		node.setPosition(newPosition);
	}

	public void undo() {
		node.setPosition(oldPosition);
	}
	
}
