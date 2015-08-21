package mashin.oep.figures;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

public class NodeFigure extends Figure {

  public static final int WIDTH  = 90;
  public static final int HEIGHT = 60;
  
  private RectangleFigure body;
  private Label label;
  private Label toolTipLabel;
  private List<TerminalConnectionAnchor> connectionAnchors;
  private String nodeType;
  
  public NodeFigure(String nodeType) {
    FreeformLayout layout = new FreeformLayout();
    setLayoutManager(layout);
    
    setSize(WIDTH, HEIGHT);
    
    body = new RectangleFigure();
    body.setPreferredSize(WIDTH / 3, 2 * HEIGHT / 3);
    body.setSize(body.getPreferredSize());
    body.setLocation(new Point(WIDTH / 3, 0));
    body.setOpaque(true);
    body.setBackgroundColor(ColorConstants.lightGray);
    add(body);
    
    label = new Label();
    label.setLabelAlignment(PositionConstants.CENTER);
    label.setTextAlignment(PositionConstants.CENTER);
    label.setMaximumSize(new Dimension(WIDTH, HEIGHT / 3));
    label.setSize(WIDTH, HEIGHT / 3);
    label.setLocation(new Point(0, 2 * HEIGHT / 3));
    add(label);
    
    toolTipLabel = new Label();
    toolTipLabel.setLabelAlignment(PositionConstants.CENTER);
    toolTipLabel.setTextAlignment(PositionConstants.CENTER);
    setToolTip(toolTipLabel);
    
    connectionAnchors = new ArrayList<TerminalConnectionAnchor>();
    
    this.nodeType = nodeType;
  }
  
  public Label getLabelFigure() {
    return label;
  }
  
  public RectangleFigure getBodyFigure() {
    return body;
  }
  
  public void setLabel(String label) {
    this.label.setText(label);
    this.toolTipLabel.setText(nodeType + ": " + label);
  }
  
  public String getLabel() {
    return label.getText();
  }
  
  public void addConnectionAnchor(TerminalConnectionAnchor terminalConnectionAnchor) {
    connectionAnchors.add(terminalConnectionAnchor);
    Point p = terminalConnectionAnchor.getLocalLocation();
    RectangleFigure r = new RectangleFigure();
    r.setSize(5, 5);
    r.setOpaque(true);
    r.setBackgroundColor(ColorConstants.gray);
    switch (terminalConnectionAnchor.getType()) {
    case TerminalConnectionAnchor.TYPE_FANIN:
      r.setLocation(new Point(p.x, p.y - 2));
      break;
    case TerminalConnectionAnchor.TYPE_FANOUT:
    case TerminalConnectionAnchor.TYPE_OUT:
      r.setLocation(new Point(p.x - 5, p.y - 2));
      break;
    }
    r.setToolTip(new Label(terminalConnectionAnchor.getLabel()));
    add(r);
  }
  
  public TerminalConnectionAnchor getConnectionAnchor(String label) {
    for (TerminalConnectionAnchor ca : connectionAnchors) {
      if (ca.getLabel().equals(label)) {
        return ca;
      }
    }
    return null;
  }
  
  public List<TerminalConnectionAnchor> getConnectionAnchors() {
    return new ArrayList<TerminalConnectionAnchor>(connectionAnchors);
  }
  
  public List<TerminalConnectionAnchor> getSourceConnectionAnchors() {
    return connectionAnchors.stream()
            .filter(ca -> ca.isSource())
            .collect(Collectors.toList());
  }
  
  public List<TerminalConnectionAnchor> getTargetConnectionAnchors() {
    return connectionAnchors.stream()
            .filter(ca -> !ca.isSource())
            .collect(Collectors.toList());
  }
  
  public TerminalConnectionAnchor getSourceConnectionAnchorAt(Point p) {
    return getConnectionAnchorAt(p, getSourceConnectionAnchors());
  }
  
  public TerminalConnectionAnchor getTargetConnectionAnchorAt(Point p) {
    return getConnectionAnchorAt(p, getTargetConnectionAnchors());
  }
  
  private TerminalConnectionAnchor getConnectionAnchorAt(Point p, List<TerminalConnectionAnchor> connectionAnchors) {
    TerminalConnectionAnchor closest = null;
    double min = Double.MAX_VALUE;

    for (TerminalConnectionAnchor connectionAnchor : connectionAnchors) {
      Point p2 = connectionAnchor.getLocation(null);
      double d = Math.abs(p.getDistance(p2));
      if (d < min) {
        min = d;
        closest = connectionAnchor;
      }
    }
    return closest;
  }
  
}
