package io.mashin.oep.parts.tree;

import io.mashin.oep.model.connection.WorkflowConnection;
import io.mashin.oep.model.editPolicies.WorkflowConnectionEditPolicy;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.widgets.Tree;

public class WorkflowConnectionTreeEditPart extends AbstractTreeEditPart
    implements PropertyChangeListener {

  public WorkflowConnectionTreeEditPart(WorkflowConnection connection) {
    super(connection);
  }
  
  public void activate() {
    super.activate();
    getConnection().addPropertyChangeListener(this);
  }

  protected void createEditPolicies() {
    installEditPolicy(EditPolicy.CONNECTION_ROLE, new WorkflowConnectionEditPolicy());
  }

  public void deactivate() {
    getConnection().removePropertyChangeListener(this);
    super.deactivate();
  }

  protected WorkflowConnection getConnection() {
    return (WorkflowConnection) getModel();
  }

  @SuppressWarnings("rawtypes")
  protected List getModelChildren() {
    return Collections.EMPTY_LIST;
  }

  public void propertyChange(PropertyChangeEvent change) {
    refreshVisuals();
  }

  protected void refreshVisuals() {
    if (getWidget() instanceof Tree)
      return;
    //Image image = ((NodeFigure) nodeEditPart.getFigure()).getIcon();
    ////TreeItem item = (TreeItem) getWidget();
    ////if (image != null)
    ////  image.setBackground(item.getParent().getBackground());
    //setWidgetImage(image);
    setWidgetText(getConnection().getSourceNode().getName() +
        "::" + getConnection().getSourceTerminal().getLabel() +
        " --> " + getConnection().getTargetNode().getName() +
        "::" + getConnection().getTargetTerminal().getLabel());
  }

}
