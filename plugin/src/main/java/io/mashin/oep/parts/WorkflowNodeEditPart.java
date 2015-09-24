package io.mashin.oep.parts;

import io.mashin.oep.figures.NodeFigure;
import io.mashin.oep.figures.TerminalConnectionAnchor;
import io.mashin.oep.model.ModelElement;
import io.mashin.oep.model.commands.node.CustomActionNodeXMLEditCommand;
import io.mashin.oep.model.connection.WorkflowConnection;
import io.mashin.oep.model.editPolicies.NodeComponentEditPolicy;
import io.mashin.oep.model.editPolicies.NodeGraphicalNodeEditPolicy;
import io.mashin.oep.model.editPolicies.NodeLabelDirectEditPolicy;
import io.mashin.oep.model.node.Node;
import io.mashin.oep.model.node.action.extended.CustomActionNode;
import io.mashin.oep.model.terminal.FanInTerminal;
import io.mashin.oep.model.terminal.FanOutTerminal;
import io.mashin.oep.model.terminal.NoInputTerminal;
import io.mashin.oep.model.terminal.NoOutputTerminal;
import io.mashin.oep.model.terminal.SingleOutputTerminal;
import io.mashin.oep.model.terminal.Terminal;
import io.mashin.oep.ui.editor.XMLEditor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.StringWriter;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.requests.DropRequest;
import org.eclipse.jface.viewers.TextCellEditor;

public class WorkflowNodeEditPart extends AbstractGraphicalEditPart implements
    PropertyChangeListener, NodeEditPart {

  /**
   * Upon activation, attach to the model element as a property change listener.
   */
  @Override
  public void activate() {
    if (!isActive()) {
      super.activate();
      ((ModelElement) getModel()).addPropertyChangeListener(this);
    }
  }

  @Override
  protected void createEditPolicies() {
    // allow direct edit of node label
    installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new NodeLabelDirectEditPolicy());
    
    // allow removal of the associated model element
    installEditPolicy(EditPolicy.COMPONENT_ROLE, new NodeComponentEditPolicy());
    
    // allow the creation of connections and
    // and the reconnection of connections between Shape instances
    installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new NodeGraphicalNodeEditPolicy());
  }

  @Override
  public void performRequest(Request req) {
    if (req.getType() == RequestConstants.REQ_DIRECT_EDIT) {
      performDirectEditing();
    } else if (req.getType() == RequestConstants.REQ_OPEN) {
      performOpen();
    } else {
      super.performRequest(req);
    }
  }
  
  private void performDirectEditing() {
    Label label = ((NodeFigure) getFigure()).getLabelFigure();
    NodeLabelDirectEditManager manager = new NodeLabelDirectEditManager(this,
        TextCellEditor.class, new NodeLabelCellEditorLocator(label), label);
    manager.show();
  }
  
  private void performOpen() {
    if (getCastedModel() instanceof CustomActionNode) {
      CustomActionNode customActionNode = (CustomActionNode) getCastedModel();
      String customActionNodeXML = "";
      
      try {
        StringWriter stringWriter = new StringWriter();
        XMLWriter writer = new XMLWriter(stringWriter,
            OutputFormat.createPrettyPrint());
        writer.write(DocumentHelper.parseText(
            (String) customActionNode
                .getPropertyValue(CustomActionNode.PROP_XML)).getRootElement());
        writer.flush();
        customActionNodeXML = stringWriter.toString();
      } catch (Exception e) {
        e.printStackTrace();
        customActionNodeXML = (String) customActionNode
            .getPropertyValue(CustomActionNode.PROP_XML);
      }
      
      XMLEditor.getInstance().open(
              customActionNode,
              customActionNodeXML,
              customActionNode.getName(),
              xml -> getViewer()
                  .getEditDomain()
                  .getCommandStack()
                  .execute(
                      new CustomActionNodeXMLEditCommand(customActionNode, xml)));
    }
  }
  
  @Override
  protected IFigure createFigure() {
    Node node = getCastedModel();
    NodeFigure nodeFigure = new NodeFigure(node.getNodeType());
    nodeFigure.setLocation(node.getPosition());
    
    List<Terminal> terminals = node.getTerminals();
    for (Terminal terminal : terminals) {
      int terminalConnectionAnchorType = TerminalConnectionAnchor.TYPE_FANIN;
      if (terminal instanceof FanInTerminal) {
        terminalConnectionAnchorType = TerminalConnectionAnchor.TYPE_FANIN;
      } else if (terminal instanceof SingleOutputTerminal) {
        terminalConnectionAnchorType = TerminalConnectionAnchor.TYPE_OUT;
      } else if (terminal instanceof FanOutTerminal) {
        terminalConnectionAnchorType = TerminalConnectionAnchor.TYPE_FANOUT;
      } else if (terminal instanceof NoInputTerminal) {
        terminalConnectionAnchorType = TerminalConnectionAnchor.TYPE_NOIN;
      } else if (terminal instanceof NoOutputTerminal) {
        terminalConnectionAnchorType = TerminalConnectionAnchor.TYPE_NOOUT;
      }
      nodeFigure.addConnectionAnchor(new TerminalConnectionAnchor(
          nodeFigure,
          terminalConnectionAnchorType,
          terminal.getLabel()));
    }
    
    return nodeFigure;
  }

//  /**
//   * Return a IFigure depending on the instance of the current model element.
//   * This allows this EditPart to be used for all sublasses of Node.
//   */
//  private IFigure createFigureForModel() {
//    RectangleFigure rectangleFigure = new RectangleFigure();
//    rectangleFigure.setPreferredSize(30, 40);
//    rectangleFigure.setLocation(getCastedModel().getPosition());
//    rectangleFigure.setSize(rectangleFigure.getPreferredSize());
//    rectangleFigure.setOpaque(true);
//    rectangleFigure.setBackgroundColor(ColorConstants.lightGray);
//    return rectangleFigure;
//  }
  
  /**
   * Upon deactivation, detach from the model element as a property change
   * listener.
   */
  @Override
  public void deactivate() {
    if (isActive()) {
      super.deactivate();
      ((ModelElement) getModel()).removePropertyChangeListener(this);
    }
  }

  private Node getCastedModel() {
    return (Node) getModel();
  }
  
  private NodeFigure getCastedFigure() {
    return (NodeFigure) getFigure();
  }

  @Override
  protected List<WorkflowConnection> getModelSourceConnections() {
    return getCastedModel().getSourceConnections();
  }
  
  @Override
  protected List<WorkflowConnection> getModelTargetConnections() {
    return getCastedModel().getTargetConnections();
  }

  @Override
  public ConnectionAnchor getSourceConnectionAnchor(
      ConnectionEditPart connection) {
    return getCastedFigure().getConnectionAnchor(
        ((WorkflowConnection) connection.getModel())
        .getSourceTerminal().getLabel());
  }

  @Override
  public ConnectionAnchor getSourceConnectionAnchor(Request request) {
    Point pt = new Point(((DropRequest) request).getLocation());
    return getCastedFigure().getSourceConnectionAnchorAt(pt);
  }

  @Override
  public ConnectionAnchor getTargetConnectionAnchor(
      ConnectionEditPart connection) {
    return getCastedFigure().getConnectionAnchor(
        ((WorkflowConnection) connection.getModel())
        .getTargetTerminal().getLabel());
  }

  @Override
  public ConnectionAnchor getTargetConnectionAnchor(Request request) {
    Point pt = new Point(((DropRequest) request).getLocation());
    return getCastedFigure().getTargetConnectionAnchorAt(pt);
  }

  @Override
  public EditPart getTargetEditPart(Request request) {
    return super.getTargetEditPart(request);
  }
  
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String prop = evt.getPropertyName();
    switch(prop) {
    case Node.PROP_POS:
    case Node.PROP_NODE_NAME:
      refreshVisuals();
      break;
    case Node.PROP_CONNECTION_SOURCE:
      refreshSourceConnections();
      break;
    case Node.PROP_CONNECTION_TARGET:
      refreshTargetConnections();
      break;
    }
  }

  @Override
  protected void refreshVisuals() {
    NodeFigure nodeFigure = getCastedFigure();
    Node nodeModel = getCastedModel();
    
    nodeFigure.setLabel(nodeModel.getName());
    
    // notify parent container of changed position & location
    // if this line is removed, the XYLayoutManager used by the parent
    // container
    // (the Figure of the WorkflowEditPart), will not know the bounds
    // of this figure
    // and will not draw it correctly.
    Rectangle bounds = new Rectangle(nodeModel.getPosition(), nodeFigure.getSize());
    ((GraphicalEditPart) getParent()).setLayoutConstraint(this, nodeFigure, bounds);
  }
  
}