package mashin.oep.model.editPolicies;

import mashin.oep.figures.NodeFigure;
import mashin.oep.model.commands.node.NodeRenameCommand;
import mashin.oep.model.node.Node;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

public class NodeLabelDirectEditPolicy extends DirectEditPolicy {

  @Override
  protected Command getDirectEditCommand(DirectEditRequest request) {
    NodeRenameCommand command = new NodeRenameCommand();
    command.setNode((Node) getHost().getModel());
    command.setNewName((String) request.getCellEditor().getValue());
    return command;
  }

  @Override
  protected void showCurrentEditValue(DirectEditRequest request) {
    String value = (String) request.getCellEditor().getValue();
    NodeFigure nodeFigure = (NodeFigure) getHostFigure();
    nodeFigure.setLabel(value);
  }

}
